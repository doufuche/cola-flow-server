package com.github.cola.flow.server.dto.domainevent;

import com.alibaba.cola.dto.Command;
import lombok.Data;

import java.util.List;

/**
 * Desc: 手动批量发起流程
 * @author doufuche
 */
@Data
public class StartEventFlowListCmd extends Command {

    /**
     * 业务id
     */
    private List<String> bizIds;
    /**
     * traceFlowId 流程id
     */
    private Integer traceFlowId;
}
