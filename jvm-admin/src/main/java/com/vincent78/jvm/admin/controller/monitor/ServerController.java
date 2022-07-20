package com.vincent78.jvm.admin.controller.monitor;


import com.vincent78.jvm.common.core.domain.AjaxResult;
import com.vincent78.jvm.framework.server.Server;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 服务器监控
 *
 * @author ivan
 */
@RestController
@RequestMapping("/monitor")
public class ServerController
{
    @GetMapping("/info")
    public AjaxResult getInfo() throws Exception
    {
        Server server = new Server();
        server.copyTo();
        return AjaxResult.success(server);
    }
}

