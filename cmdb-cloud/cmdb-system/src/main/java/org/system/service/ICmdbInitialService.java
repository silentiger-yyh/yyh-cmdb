package org.system.service;

import org.silentiger.api.CommonResult;

/**
 * ICmdbInitialService接口
 * 初始化cmdb数据库
 *
 * @Author silentiger@yyh
 * @Date 2023-12-28 18:39:59
 */

public interface ICmdbInitialService {

    CommonResult initCollections(String name);
}
