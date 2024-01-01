package org.system.service;

import com.mongodb.client.ClientSession;
import org.silentiger.api.CommonResult;

/**
 * ICmdbInitialService接口
 * 初始化cmdb数据库
 *
 * @Author silentiger@yyh
 * @Date 2023-12-28 18:39:59
 */

public interface ICmdbInitialService {

    /**
     * 初始化创建集合
     * @param name
     * @return
     */
    CommonResult<Object> initCollections(String name);

    /**
     * 初始化时创建索引
     * @return
     */
    CommonResult<Object> createIndex();
}
