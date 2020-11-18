package com.github.cola.flow.server.infrastructure.repository;

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
@Table(name = "trace_event_status")
public class EventStateDO {

    /**
     *主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 订单号
     */
    private Long bizId;

    /**
     * 节点名
     */
    private String nodeName;

    /**
     * 创建时间
     */
    private Date created;

    /**
     * 更新时间
     */
    private Date modified;

    /**
     * 该node执行状态，0表示成功，1表示失败
     */
    private Integer state;

    /**
     * version
     */
    private Long version;

    /**
     * 是否已删除 0：未删除 1：已删除
     */
    private Byte isDelete;
}
