package com.github.cola.flow.server.client.dto;

import com.alibaba.cola.dto.Query;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * 查询流程数据
 * @author doufuche
 * @date 2020/11/15
 */
@Data
public class OrderFlowDataQry extends Query {
    /**
     * 订单号查询条件
     */
    List<Long> bizIds;
    /**
     * 流程触发记录类型，1 暂停，0 异常
     */
    Integer traceType;
    /**
     * 开始时间
     */
    Date beginTime;
    /**
     * 结束时间
     */
    Date endTime;

    /**
     * 请求页大小
     */
    @Min(value = 1, message = "pageSize >= 1")
    @Max(value = 100, message = "pageSize <= 100")
    @NotNull
    private Integer pageSize = 20;

    /**
     * 第几页
     */
    @Min(value = 1, message = "pageNo >= 1")
    @NotNull
    private Integer pageNo = 1;

    public Integer getBegin() {
        return (pageNo - 1) * pageSize;
    }

    @Override
    public String toString() {
        return "OrderFlowDataQry{" +
                "bizIds=" + bizIds +
                ", traceType=" + traceType +
                ", beginTime=" + beginTime +
                ", endTime=" + endTime +
                ", pageSize=" + pageSize +
                ", pageNo=" + pageNo +
                '}';
    }
}
