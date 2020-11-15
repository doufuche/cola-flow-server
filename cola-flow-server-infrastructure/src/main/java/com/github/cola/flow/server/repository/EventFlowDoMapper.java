package com.github.cola.flow.server.repository;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Desc:
 *
 * @author doufuche
 * @date 2020/11/16
 */
public interface EventFlowDoMapper extends BaseMapper<EventFlowDO> {

    /**
     *  补偿查询，指定时间段
     *
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @return
     */
    @Select({
            "select",
            "id ",
            "from trace_event_flow",
            "where updated_at >= #{beginTime,jdbcType=TIMESTAMP} ",
            "and updated_at < #{endTime,jdbcType=TIMESTAMP} " +
            "and is_delete = 0 and trace_type = 0",
    })
    List<Long> selectByTimeRange(LocalDateTime beginTime, LocalDateTime endTime);

    /**
     * 补偿查询，指定批量id
     * @param bizIdList
     * @return
     */
    @Select({
            "select id as id,order_id as bizId,flow_info as flowInfo from trace_event_flow ",
            " where order_id in (${bizIdList} ) ",
    })
    List<EventFlowDO> selectByBizIdList(@Param(value = "bizIdList") String bizIdList);

}
