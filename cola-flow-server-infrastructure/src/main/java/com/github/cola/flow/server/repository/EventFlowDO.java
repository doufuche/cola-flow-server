package com.github.cola.flow.server.repository;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Desc:
 *
 * @author doufuche
 * @date 2020/11/16
 */
@Data
@Table(name = "trace_event_flow")
public class EventFlowDO {

    /**
     *主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 业务id
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
