package com.xd.pre.log.event;

import com.xd.pre.log.domain.SysLog;
import com.xd.pre.log.service.ISysLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @Classname SysLogListener
 * @Description 注解形式的监听 异步监听日志事件
 */
@Slf4j
@Component
public class SysLogListener {

    @Autowired
    private ISysLogService sysLogService;

    @Async
    @Order
    @EventListener(SysLogEvent.class)
    public void saveSysLog(SysLogEvent event) {
        SysLog sysLog = (SysLog) event.getSource();
        // 保存日志
        sysLogService.save(sysLog);
    }
}
