package com.geex.cmdb.common.core.service;

import com.geex.cmdb.common.core.domain.dto.OperLogDTO;
import org.springframework.scheduling.annotation.Async;

/**
 * 通用 操作日志
 *
 * @author Lion Li
 */
public interface OperLogService {

    @Async
    void recordOper(OperLogDTO operLogDTO);
}
