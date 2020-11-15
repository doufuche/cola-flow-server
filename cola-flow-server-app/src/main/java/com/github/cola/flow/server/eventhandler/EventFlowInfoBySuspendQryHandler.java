package com.github.cola.flow.server.eventhandler;

import com.alibaba.cola.dto.SingleResponse;
import com.alibaba.cola.event.EventHandler;
import com.alibaba.cola.event.EventHandlerI;
import com.github.cola.flow.client.dto.domainevent.query.EventFlowInfoBySuspendQryEvent;
import com.github.cola.flow.server.common.DbConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import com.github.cola.flow.server.repository.EventFlowDO;
import com.github.cola.flow.server.repository.EventFlowRepository;

/**
 * Desc: 查询该业务暂停时保存的eventFlow信息
 *
 * @author doufuche

 */
@Slf4j
@EventHandler
public class EventFlowInfoBySuspendQryHandler implements EventHandlerI<SingleResponse<String>, EventFlowInfoBySuspendQryEvent> {

    @Autowired
    private EventFlowRepository eventFlowRepository;

    @Override
    public SingleResponse<String> execute(EventFlowInfoBySuspendQryEvent event) {
        SingleResponse<String> response = new SingleResponse();

        Long bizId = Long.parseLong(event.getBizId());
        EventFlowDO eventFlowDO = eventFlowRepository.get(bizId, event.getEventName());
        if(eventFlowDO!=null && DbConstant.EVENT_FLOW_TRACE_TYPE_SUSPEND.equals(eventFlowDO.getTraceType())){
            response.setData(eventFlowDO.getFlowInfo());
            response.setSuccess(Boolean.TRUE);
        }else{
            response.setSuccess(Boolean.FALSE);
        }

        return response;
    }
}
