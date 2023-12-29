package org.system.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.bson.Document;
import org.silentiger.api.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.web.bind.annotation.*;
import org.system.entity.Model;
import org.system.service.IModelService;

import javax.validation.Valid;
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

    @PostMapping("/add")
    @ApiOperation("保存模型信息")
    public CommonResult<Object> addModelInfo(@RequestBody @Valid Model model) {
        return modelService.saveModel(model, 0);
    }
    @PostMapping("/edit")
    @ApiOperation("保存模型信息")
    public CommonResult<Object> editModelInfo(@RequestBody @Valid Model model) {
        return modelService.saveModel(model, 1);
    }
    @GetMapping("/get/{id}")
    @ApiOperation("根据Id获取模型信息")
    public CommonResult<Object> getModelInfoById(@ApiParam(name = "id", value = "模型记录id") @PathVariable String id) {
        return modelService.getModelInfoById(id);
    }

}
