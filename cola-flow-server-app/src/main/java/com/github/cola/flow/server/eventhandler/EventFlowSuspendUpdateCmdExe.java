package com.github.cola.flow.server.eventhandler;

import com.alibaba.cola.dto.Response;
import com.alibaba.cola.event.EventHandler;
import com.alibaba.cola.event.EventHandlerI;
import com.github.cola.flow.client.dto.domainevent.EventFlowSuspendUpdateEvent;
import com.github.cola.flow.server.common.DbConstant;
import com.github.cola.flow.server.dto.domainmodel.ExceptionEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import com.github.cola.flow.server.repository.EventFlowRepository;

/**
 * Desc: 流程暂停时更新流程信息
 * @author doufuche
 * @date 2020/11/16
 */
@Slf4j
@EventHandler
public class EventFlowSuspendUpdateCmdExe implements EventHandlerI<Response, EventFlowSuspendUpdateEvent> {
    @Autowired
    private EventFlowRepository eventFlowRepository;

    @Override
    public Response execute(EventFlowSuspendUpdateEvent event) {
        Long orderId = Long.parseLong(event.getBizId());

        Integer result = eventFlowRepository.insertOrUpdateEventFlow(orderId, event.getEventName(), event.getFlowInfo(), DbConstant.EVENT_FLOW_TRACE_TYPE_SUSPEND);

        if(result!=null && result.equals(DbConstant.UPDATE_YES)){
            return Response.buildSuccess();
        }
        return Response.buildFailure(ExceptionEnum.EVENT_FLOW_SUSPEND_UPDATE_FAIL.getCode(),this.getClass().getName()+ " " + ExceptionEnum.EVENT_FLOW_SUSPEND_UPDATE_FAIL.getMessage());

    }
}
