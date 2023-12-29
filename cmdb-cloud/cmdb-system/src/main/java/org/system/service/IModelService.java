package org.system.service;

import org.silentiger.api.CommonResult;
import org.system.entity.Model;

/**
 * IModelService接口
 *
 * @Author silentiger@yyh
 * @Date 2023-12-28 22:03:25
 */

public interface IModelService {
    CommonResult<Object> saveModel(Model document, Integer flag);

    CommonResult<Object> getModelInfoById(String id);
}
