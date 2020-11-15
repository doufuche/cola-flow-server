package com.github.cola.flow.server.api;

import com.alibaba.cola.dto.Response;
import com.github.cola.flow.client.dto.domainevent.EventFlowDeleteEvent;
import com.github.cola.flow.server.dto.domainevent.EventFlowErrorRetryCmd;
import com.github.cola.flow.server.dto.domainevent.StartEventFlowCmd;
import com.github.cola.flow.server.dto.domainevent.StartEventFlowListCmd;

/**
 * Desc: 订单流程服务管理接口
 *
 * @author doufuche
 * @date 2020/11/15
 */
public interface OrderFlowServiceI {

    /**
     * 调用流程引擎初始化流程
     * @param bizId 业务id
     * @param traceFlowId 流程id
     * @param flowInfo 流程信息，格式如[{"id":"StartEvent","pid":""},{"id":"BizCallOneEvent","pid":"StartEvent"},{"id":"BizSuspendEvent","pid":"BizCallOneEvent"},{"id":"BizCallTowEvent","pid":"BizSuspendEvent"},{"id":"EndEvent","pid":"BizCallTowEvent"}]
     * @throws Exception
     */
    void eventFlowInit(Long bizId, Integer traceFlowId, String flowInfo) throws Exception;

    /**
     * 关闭该订单的流程重试job
     * @param eventFlowDeleteCmd
     * @return
     */
    Boolean closeRetryJob(EventFlowDeleteEvent eventFlowDeleteCmd);

    /**
     * 异常的流程尝试继续执行
     * @param eventFlowErrorRetryCmd
     * @return
     */
    Boolean errorFlowContinue(EventFlowErrorRetryCmd eventFlowErrorRetryCmd);

    /**
     * 手动发起某订单流程，背景可能由于某订单数据错误导致识别流程失败，如生鲜自提三期place缓存与数据不一致
     * @return
     */
    Response startEventFlow(StartEventFlowCmd startEventFlowCmd);

    /**
     * 批量手动发起某订单流程
     * @return
     */
    Response startEventFlowByList(StartEventFlowListCmd startEventFlowListCmd);

}
