package org.system.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.bson.Document;
import org.silentiger.api.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.system.service.IModelService;

import java.util.HashMap;

/**
 * ModelController类
 * 模型管理接口
 *
 * @Author silentiger@yyh
 * @Date 2023-12-28 21:32:27
 */

@RestController
@Api("模型管理接口")
@RequestMapping("model")
public class ModelController {

    @Autowired
    private IModelService modelService;

    @PostMapping("/save")
    @ApiOperation("保存模型信息")
    public CommonResult<Object> saveModelInfo(@RequestBody HashMap<String, Object> paramsMap) {
        return modelService.saveModel(new Document(paramsMap));
    }
}
