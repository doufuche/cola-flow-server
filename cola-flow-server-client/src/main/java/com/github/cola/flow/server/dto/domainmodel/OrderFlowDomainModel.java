package com.github.cola.flow.server.dto.domainmodel;

import lombok.Data;

import java.util.Date;

/**
 * Project Name: ele-newretail-trade
 * Confidential and Proprietary
 * Copyright 2020 By alibaba-inc.com
 * All Rights Reserved
 * Desc: 订单流程领域对象, 用来做数据传输的轻量级领域对象
 *
 * @author lj235678
 * @date 2020/5/29
 */
@Data
public class OrderFlowDomainModel {
    /**
     * 订单号
     */
    private Long bizId;

    /**
     * 节点名
     */
    private String nodeName;

    /**
     * 流程定义表中对应，暂时自定义用于区分多流程
     */
    private Integer traceFlowId;

    /**
     * 流程信息
     */
    private String flowInfo;

    /**
     * 创建时间
     */
    private Date created;

    /**
     * 更新时间
     */
    private Date modified;

    /**
     * 是否已删除 0：未删除 1：已删除
     */
    private Byte isDelete;

    /**
     * 流程暂停触发记录类型，1 暂停，0 异常
     */
    private Integer traceType;

}
