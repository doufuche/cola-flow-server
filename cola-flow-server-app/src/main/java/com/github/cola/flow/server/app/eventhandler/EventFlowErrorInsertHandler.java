package com.github.cola.flow.server.app.eventhandler;

import com.alibaba.cola.dto.Response;
import com.alibaba.cola.event.EventHandler;
import com.alibaba.cola.event.EventHandlerI;
import com.github.cola.flow.client.constants.Constants;
import com.github.cola.flow.client.dto.domainevent.EventFlowErrorInsertEvent;
import com.github.cola.flow.server.infrastructure.common.DbConstant;
import com.github.cola.flow.server.client.dto.domainmodel.ExceptionEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import com.github.cola.flow.server.infrastructure.repository.EventFlowRepository;

/**
 * Desc:
 *
 * @author doufuche

 */
@Slf4j
@EventHandler
public class EventFlowErrorInsertHandler implements EventHandlerI<Response, EventFlowErrorInsertEvent> {
    @Autowired
    private EventFlowRepository eventFlowRepository;

    @Override
    public Response execute(EventFlowErrorInsertEvent event) {
        Long bizId = Long.parseLong(event.getBizId());

        Integer traceType = DbConstant.EVENT_FLOW_TRACE_TYPE_EXCEPTION;
        boolean isSuspend = event.getEventName().contains(Constants.SUSPEND_EVENT_SUFFIX);
        if(isSuspend){
            traceType = DbConstant.EVENT_FLOW_TRACE_TYPE_SUSPEND;
        }else{
            log.error(event.getBizId()+ExceptionEnum.FLOW_EXECUTE_ERROR.getMessage());
        }

        Integer result = eventFlowRepository.insertOrUpdateEventFlow(bizId, event.getEventName(), event.getFlowInfo(), traceType);
        if(result!=null && result.equals(DbConstant.UPDATE_YES)){
            return Response.buildSuccess();
        }
        return Response.buildFailure(ExceptionEnum.EVENT_FLOW_ERROR_INSERT_FAIL.getCode(),this.getClass().getName()+ " " + ExceptionEnum.EVENT_FLOW_ERROR_INSERT_FAIL.getMessage());

    }
}
