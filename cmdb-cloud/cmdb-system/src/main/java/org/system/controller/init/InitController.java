package org.system.controller.init;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.silentiger.api.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.system.service.ICmdbInitialService;

/**
 * InitController类
 * 初始化数据库
 *
 * @Author silentiger@yyh
 * @Date 2023-12-28 18:33:28
 */

@RestController
@RequestMapping("init")
@Api(tags = "初始化数据库")
public class InitController {

    @Autowired
    private ICmdbInitialService cmdbInitialService;

    @PostMapping("collections/{name}")
    @ApiOperation("初始化集合")
    public CommonResult initApi(@PathVariable @ApiParam(name = "name", value = "集合名称") String name) {
        return cmdbInitialService.initCollections(name);
    }
}
