package com.github.cola.flow.server.infrastructure.common;

/**
 * Desc:
 *
 * @author doufuche
 * @date 2020/11/15
 */
public interface DbConstant {
    /**
     *  記錄邏輯刪除標記，1表示已刪除
     */
    Integer UPDATE_YES = 1;
    /**
     * 記錄邏輯刪除標記，0表示未刪除
     */
    Integer UPDATE_NO = 0;
    /**
     * eventflow表記錄流程暫停類型，0表示異常
     */
    Integer EVENT_FLOW_TRACE_TYPE_EXCEPTION = 0;
    /**
     * eventflow表記錄流程暫停類型，1表示暫停
     */
    Integer EVENT_FLOW_TRACE_TYPE_SUSPEND = 1;
}
