package org.system.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.silentiger.api.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.system.entity.ModelGroup;
import org.system.service.IModelGroupService;

import javax.validation.Valid;

/**
 * ModelGroupController类
 * 模型分组Controller
 *
 * @Author silentiger@yyh
 * @Date 2024-01-01 17:53:22
 */

@RestController
@RequestMapping("/model-group")
@Api(tags = "模型分组")
public class ModelGroupController {

    @Autowired
    private IModelGroupService modelGroupService;

    @PostMapping("/save")
    @ApiOperation("保存模型分组")
    public CommonResult<Object> saveModelGroup(@RequestBody @Valid ModelGroup modelGroup) {
        return modelGroupService.saveModelGroup(modelGroup);
    }

    @GetMapping("/level1")
    @ApiOperation("获取一级分组")
    public CommonResult<Object> getLevel1Groups() {
        return modelGroupService.getLevel1Groups();
    }

    @GetMapping("/level2/{id}")
    @ApiOperation("获取二级分组")
    public CommonResult<Object> getLevel2Groups(@PathVariable String id) {
        return modelGroupService.getLevel2Groups(id);
    }
}
