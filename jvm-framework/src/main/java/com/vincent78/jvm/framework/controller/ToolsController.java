package com.vincent78.jvm.framework.controller;


import com.vincent78.jvm.common.core.domain.AjaxResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/tools")
public class ToolsController {

    @GetMapping("/now")
    public AjaxResult getNow() {
        return AjaxResult.success(new Date().getTime());
    }

}
