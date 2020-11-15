package com.github.cola.flow.server.dto.domainevent;

import com.alibaba.cola.dto.Command;
import lombok.Data;

/**
 * Desc: 异常的流程尝试继续执行Cmd
 *
 * @author doufuche
 * @date 2020/11/15
 */
@Data
public class EventFlowErrorRetryCmd extends Command {

    /**
     * 业务id
     */
    private String bizId;
}
