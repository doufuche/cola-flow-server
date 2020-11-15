package com.github.cola.flow.server.dto.domainevent;

import com.alibaba.cola.dto.Command;
import lombok.Data;

/**
 * 手动发起流程
 * @author doufuche
 * @date 2020/11/15
 */
@Data
public class StartEventFlowCmd extends Command {

    /**
     * 业务id
     */
    private String bizId;
    /**
     * traceFlowId 流程id
     */
    private Integer traceFlowId;
}
