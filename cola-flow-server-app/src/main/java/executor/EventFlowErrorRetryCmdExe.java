package executor;

import com.alibaba.cola.dto.Response;
import com.alibaba.cola.exception.framework.BasicErrorCode;
import com.alibaba.fastjson.JSON;
import com.github.cola.flow.client.api.ColaFlowServiceI;
import com.github.cola.flow.client.baseevent.FlowBaseEvent;
import com.github.cola.flow.client.dto.event.Event;
import com.github.cola.flow.engine.event.FlowEventEngine;
import com.github.cola.flow.server.common.DbConstant;
import com.github.cola.flow.server.dto.domainevent.EventFlowErrorRetryCmd;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.github.cola.flow.server.repository.EventFlowDO;
import com.github.cola.flow.server.repository.EventFlowRepository;

import java.util.List;

/**
 * Desc: 异常的流程尝试继续执行, 可自定義
 *
 * @author doufuche
 */
@Slf4j
@Component
public class EventFlowErrorRetryCmdExe {

    @Autowired
    private EventFlowRepository eventFlowRepository;
    @Autowired
    private ColaFlowServiceI eventFlowServiceI;

    public Response execute(EventFlowErrorRetryCmd cmd) {
        log.info("EventFlowErrorRetryCmdExe start,bizId:{}", cmd.getBizId());
        Long bizId = Long.parseLong(cmd.getBizId());
        try {
            EventFlowDO eventFlowDO = new EventFlowDO();
            eventFlowDO.setIsDelete(DbConstant.UPDATE_NO.byteValue());
            eventFlowDO.setTraceType(DbConstant.EVENT_FLOW_TRACE_TYPE_EXCEPTION);

            eventFlowDO.setBizId(bizId);

            Integer selectCount = eventFlowRepository.selectCount(eventFlowDO);
            log.info("EventFlowErrorRetryCmdExe start,bizId:{}, selectCount:{}", cmd.getBizId(), selectCount);
            int offset = 0;

            List<EventFlowDO> resultList = eventFlowRepository.selectList(eventFlowDO, offset, selectCount);
            log.info("EventFlowErrorRetryCmdExe start,bizId:{}, resultList:{}", cmd.getBizId(), resultList!=null ? JSON.toJSONString(resultList) : "");
            for (EventFlowDO resultDO : resultList) {

                Event etEvent = FlowEventEngine.jsonToEvent(resultDO.getFlowInfo());

                Class catClass = Class.forName(etEvent.getEventClassMap().get(etEvent.getEventName()).toString());
                Object obj = catClass.newInstance();

                FlowBaseEvent thisEvent = (FlowBaseEvent) obj;
                thisEvent.setBizId(resultDO.getBizId());
                thisEvent.setTraceFlowId(etEvent.getTraceFlowId());
                thisEvent.setParameterMap(etEvent.getParameterMap());
                thisEvent.setStartEvent(etEvent.getStartEvent());
                thisEvent.setWaitToExecuteEvents(etEvent.getEvents());
                thisEvent.setFlowEventClassMap(etEvent.getEventClassMap());
                thisEvent.setFlowEventMap(etEvent.getEventMap());

                log.info("EventFlowErrorRetryCmdExe errorFlowContinue,bizId:{}, thisEvent:{}", cmd.getBizId(), JSON.toJSONString(thisEvent));
                eventFlowServiceI.errorFlowContinue(thisEvent);

            }
        } catch (Exception e) {
            log.error("该执行错误的流程，重试发生异常,bizId:"+bizId, e);
            return Response.buildFailure(BasicErrorCode.SYS_ERROR.getErrCode(), "该执行错误的流程，重试发生异常,bizId:"+bizId+",errorMessage:"+e.getMessage());
        }

        return Response.buildSuccess();
    }
}
