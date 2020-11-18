package com.github.cola.flow.server.app.eventhandler;

import com.alibaba.cola.dto.Response;
import com.alibaba.cola.event.EventHandler;
import com.alibaba.cola.event.EventHandlerI;
import com.github.cola.flow.client.constants.Constants;
import com.github.cola.flow.client.dto.domainevent.EventStateSuccessUpdateEvent;
import com.github.cola.flow.server.client.dto.domainmodel.ExceptionEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import com.github.cola.flow.server.infrastructure.repository.EventStateRepository;

/**
 * Desc: 节点状态更新
 *
 * @author doufuche

 */
@Slf4j
@EventHandler
public class EventStateSuccessUpdateHandler implements EventHandlerI<Response, EventStateSuccessUpdateEvent> {
    @Autowired
    private EventStateRepository eventStateRepository;

    @Override
    public Response execute(EventStateSuccessUpdateEvent event) {
        Long bizId = Long.parseLong(event.getBizId());
        String nodeName = event.getEventName();

        Integer result = eventStateRepository.insertOrupdate(bizId, nodeName, Constants.EVENT_STATE_SUCCESS);
        if(result!=null && result >0){
            return Response.buildSuccess();
        }
        return Response.buildFailure(ExceptionEnum.EVENT_STATE_SUCCESS_UPDATE_FAIL.getCode(),this.getClass().getName()+ " " + ExceptionEnum.EVENT_STATE_SUCCESS_UPDATE_FAIL.getMessage());

    }
}
