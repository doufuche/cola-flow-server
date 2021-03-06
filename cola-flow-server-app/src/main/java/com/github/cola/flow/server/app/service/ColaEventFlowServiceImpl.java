package com.github.cola.flow.server.app.service;

import com.alibaba.cola.dto.Response;
import com.alibaba.cola.event.EventBusI;
import com.github.cola.flow.client.baseevent.FlowBaseEvent;
import com.github.cola.flow.client.baseevent.StartEvent;
import com.github.cola.flow.client.dto.domainevent.EventFlowDeleteEvent;
import com.github.cola.flow.server.client.api.ColaEventFlowServiceI;
import com.github.cola.flow.server.client.dto.EventFlowErrorRetryCmd;
import com.github.cola.flow.server.client.dto.StartEventFlowCmd;
import com.github.cola.flow.server.client.dto.StartEventFlowListCmd;
import com.github.cola.flow.server.app.executor.EventFlowErrorRetryCmdExe;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Desc: 订单流程服务管理接口
 *
 * @author doufuche
 * @date 2020/11/16
 */
@Service
@Slf4j
public class ColaEventFlowServiceImpl implements ColaEventFlowServiceI {
    @Autowired
    private EventBusI eventBusI;

    @Autowired
    private com.github.cola.flow.client.api.ColaFlowServiceI eventFlowServiceI;
    @Autowired
    private EventFlowErrorRetryCmdExe eventFlowErrorRetryCmdExe;

    @Override
    public Boolean closeRetryJob(EventFlowDeleteEvent eventFlowDeleteEvent) {
        Response response = eventBusI.fire(eventFlowDeleteEvent);
        return response.isSuccess();
    }

    @Override
    public void eventFlowInit(Long orderId, Integer traceFlowId, String flowInfo) throws Exception {
        //todo 此處代碼僅演示，需要各業務自定義
        StartEvent startEvent = new StartEvent();
        startEvent.setBizId(orderId);
        startEvent.setTraceFlowId(traceFlowId);

        eventFlowServiceI.init(startEvent, flowInfo);
    }

    @Override
    public Boolean errorFlowContinue(EventFlowErrorRetryCmd eventFlowErrorRetryCmd) {
        //各業務可自定義
        Response response = eventFlowErrorRetryCmdExe.execute(eventFlowErrorRetryCmd);
        return response.isSuccess();
    }

    @Override
    public Response startEventFlow(StartEventFlowCmd startEventFlowCmd) {
        //todo 此處代碼僅演示，需要各業務自定義
//        return startEventFlowCmdExe.execute(startEventFlowCmd);
        return Response.buildSuccess();
    }

    @Override
    public Response startEventFlowByList(StartEventFlowListCmd startEventFlowListCmd) {
        //todo 此處代碼僅演示，需要各業務自定義
//        return startEventFlowListCmdExe.execute(startEventFlowListCmd);
        return Response.buildSuccess();
    }

    @Override
    public void continueEventFlow(FlowBaseEvent suspendEvent) throws Exception {
        eventFlowServiceI.continueEventFlow(suspendEvent);
    }
}
