package com.vincent78.jvm.framework.service;

import com.alibaba.fastjson.JSONObject;

public interface JVMInfoService {
    JSONObject summary();

    JSONObject addMem(int size);

    JSONObject subtractMem(int size);

    void gc();
}
