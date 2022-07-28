package com.vincent78.jvm.framework.controller;

import com.vincent78.jvm.common.core.domain.AjaxResult;
import com.vincent78.jvm.framework.service.JVMInfoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/jvm")
public class JVMController {

    private JVMInfoService jvmInfoService;

    public JVMController(JVMInfoService jvmInfoService) {
        this.jvmInfoService = jvmInfoService;
    }

    @GetMapping("/summary")
    public AjaxResult getSummary() {
        return AjaxResult.success(this.jvmInfoService.summary());
    }

    @GetMapping("/mem/add/{size}")
    public AjaxResult addMem(@PathVariable("size") int size) {
        return AjaxResult.success(this.jvmInfoService.addMem(size));
    }
    @GetMapping("/mem/sub/{size}")
    public AjaxResult subtractMem(@PathVariable("size") int size) {
        return AjaxResult.success(this.jvmInfoService.subtractMem(size));
    }

    @GetMapping("/gc")
    public AjaxResult gc() {
        this.jvmInfoService.gc();
        return AjaxResult.success("ok");
    }
}
