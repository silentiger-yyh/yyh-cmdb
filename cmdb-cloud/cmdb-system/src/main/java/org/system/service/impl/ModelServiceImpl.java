package org.system.service.impl;

import org.bson.Document;
import org.silentiger.api.CommonResult;
import org.springframework.stereotype.Service;
import org.system.service.IModelService;

/**
 * ModelService类
 * 模型管理Serivce实现类
 *
 * @Author silentiger@yyh
 * @Date 2023-12-28 22:05:09
 */

@Service
public class ModelServiceImpl implements IModelService {
    @Override
    public CommonResult<Object> saveModel(Document document) {
        return CommonResult.success("");
    }
}
