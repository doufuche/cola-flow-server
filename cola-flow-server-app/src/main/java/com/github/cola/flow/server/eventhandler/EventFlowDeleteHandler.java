package com.github.cola.flow.server.eventhandler;

import com.alibaba.cola.dto.Response;
import com.alibaba.cola.event.EventHandler;
import com.alibaba.cola.event.EventHandlerI;
import com.github.cola.flow.client.dto.domainevent.EventFlowDeleteEvent;
import com.github.cola.flow.server.dto.domainmodel.ExceptionEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import com.github.cola.flow.server.repository.EventFlowRepository;

/**
 * Desc:
 *
 * @author doufuche
 */
@Slf4j
@EventHandler
public class EventFlowDeleteHandler implements EventHandlerI<Response, EventFlowDeleteEvent> {
    @Autowired
    private EventFlowRepository eventFlowRepository;

    @Override
    public Response execute(EventFlowDeleteEvent event) {
        if(StringUtils.isEmpty(event.getBizId())){
            log.warn("EventFlowDeleteCmd bizId can not is null!");
            return Response.buildFailure(ExceptionEnum.PARAMS_ERROR.getCode(), ExceptionEnum.PARAMS_ERROR.getMessage());
        }
        Long bizId = Long.parseLong(event.getBizId());
        String nodeName = event.getEventName();

        Integer deleteResult = eventFlowRepository.deleteEventFlow(bizId, nodeName);
        if(deleteResult!=null && deleteResult.equals(1)){
            return Response.buildSuccess();
        }
        return Response.buildFailure(ExceptionEnum.EVENT_FLOW_DELETE_FAIL.getCode(),this.getClass().getName()+ " " + ExceptionEnum.EVENT_FLOW_DELETE_FAIL.getMessage());
    }
}
