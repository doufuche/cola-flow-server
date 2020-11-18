/*
package com.github.cola.flow.server.eventhandler;

import com.alibaba.cola.dto.Response;
import com.alibaba.cola.event.EventHandler;
import com.alibaba.cola.event.EventHandlerI;
import com.github.cola.flow.client.dto.domainevent.query.BizCancelledQryEvent;
import ExceptionEnum;

*/
/**
 * Desc: 该业务订单是否已取消, 需要接入業務方自定義
 *
 * @author lj235678
 *//*

@Deprecated
@EventHandler
public class BizCancelledQryHandler implements EventHandlerI<Response, BizCancelledQryEvent> {

    @Override
    public Response execute(BizCancelledQryEvent cmd) {
        */
/*Long bizId = Long.parseLong(cmd.getBizId());
        boolean isDelete = orderRepository.selectIsDelete(bizId);
        if(isDelete) {
            return Response.buildSuccess();
        }*//*

        return Response.buildFailure(ExceptionEnum.CANCELLED_QRY_FAIL.getCode(),this.getClass().getName()+ " " + ExceptionEnum.CANCELLED_QRY_FAIL.getMessage());
    }
}
*/
