package com.github.cola.flow.server.app.eventhandler;

import com.alibaba.cola.dto.Response;
import com.alibaba.cola.event.EventHandler;
import com.alibaba.cola.event.EventHandlerI;
import com.github.cola.flow.client.constants.Constants;
import com.github.cola.flow.client.dto.domainevent.query.EventStateFinishCheckQryEvent;
import com.github.cola.flow.server.client.dto.domainmodel.ExceptionEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import com.github.cola.flow.server.infrastructure.repository.EventStateDO;
import com.github.cola.flow.server.infrastructure.repository.EventStateRepository;

/**
 * Desc:
 *
 * @author doufuche

 */
@Slf4j
@EventHandler
public class EventStateFinishCheckQryHandler implements EventHandlerI<Response, EventStateFinishCheckQryEvent> {
    @Autowired
    private EventStateRepository eventStateRepository;

    @Override
    public Response execute(EventStateFinishCheckQryEvent event) {
        Long bizId = Long.parseLong(event.getBizId());
        String nodeName = event.getEventName();
        EventStateDO eventStateDO = eventStateRepository.get(bizId, nodeName);
        if(eventStateDO!=null && Constants.EVENT_STATE_SUCCESS.equals(eventStateDO.getState())){
            return Response.buildSuccess();
        }
        return Response.buildFailure(ExceptionEnum.EVENT_STATE_FINISH_CHECK_QRY_NO.getCode(),this.getClass().getName()+ " " +ExceptionEnum.EVENT_STATE_FINISH_CHECK_QRY_NO.getMessage());
    }
}
