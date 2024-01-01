package org.system.service;

import org.silentiger.api.CommonResult;
import org.system.entity.ModelGroup;

/**
 * IModelGroupService接口
 * 模型分组管理
 *
 * @Author silentiger@yyh
 * @Date 2024-01-01 18:03:51
 */

public interface IModelGroupService {
    CommonResult<Object> saveModelGroup(ModelGroup modelGroup);

    CommonResult<Object> getLevel1Groups();

    CommonResult<Object> getLevel2Groups(String id);
}
