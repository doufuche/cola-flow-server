package com.github.cola.flow.server.dto.domainmodel;

/**
 * Desc:
 *
 * @author doufuche
 * @date 2020/11/15
 */
public enum ExceptionEnum {
    /**
     *
     */
    FLOW_EXECUTE_ERROR("-0098", "流程執行異常！"),

    //參數相關
    /**
     *
     */
    PARAMS_ERROR("-0099", "缺少必要的請求參數！"),

    //event相關
    CANCELLED_QRY_OK("0100", "查詢該業務訂單已取消"),
    CANCELLED_QRY_FAIL("-0100", "查詢該業務訂單未取消"),

    EVENT_FLOW_DELETE_OK("0110", "刪除該業務id的流程信息，執行成功"),
    EVENT_FLOW_DELETE_FAIL("-0110", "刪除該業務id的流程信息，執行失敗"),

    EVENT_FLOW_ERROR_INSERT_OK("0120", "流程執行錯誤時保存流程信息，執行成功"),
    EVENT_FLOW_ERROR_INSERT_FAIL("-0120", "流程執行錯誤時保存流程信息，執行失敗"),

    EVENT_FLOW_SUSPEND_UPDATE_OK("0130", "流程暫停時更新流程信息，執行成功"),
    EVENT_FLOW_SUSPEND_UPDATE_FAIL("-0130", "流程暫停時更新流程信息，執行失敗"),

    EVENT_STATE_FINISH_CHECK_QRY_YES("0140", "查詢該節點已執行完成"),
    EVENT_STATE_FINISH_CHECK_QRY_NO("-0140", "查詢該節點未執行完成"),

    EVENT_STATE_SUCCESS_UPDATE_OK("0150", "節點狀態插入或更新，執行成功"),
    EVENT_STATE_SUCCESS_UPDATE_FAIL("-0150", "節點狀態插入或更新，執行失敗"),
    ;

    private String code;

    private String message;

    ExceptionEnum(String code, String message){
        this.code = code;
        this.message = message;
    }

    public String getCode(){
        return code;
    }

    public String getMessage(){
        return message;
    }
}
