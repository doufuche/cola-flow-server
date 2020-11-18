package com.github.cola.flow.server.infrastructure.repository;

import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 交易订单mapper
 *
 * @author wmx
 * @date 2019-10-31
 */
public interface EventStateDoMapper extends BaseMapper<EventStateDO> {

    /**
     *  查询所有父节点状态
     *
     * @param orderId 订单号
     * @param parentNodeNames 父节点名称,逗号分割
     * @return
     */
    @Select({
            "select ",
            "state ",
            "from trace_event_status ",
            "where order_id = #{bizId,jdbcType=BIGINT} ",
            " and node_name in ('${parentNodeNames}' ) ",
    })
    List<Integer> isOkParentState(Long orderId, String parentNodeNames);

}
