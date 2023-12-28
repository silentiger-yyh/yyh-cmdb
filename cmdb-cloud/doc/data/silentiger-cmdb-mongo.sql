/*
 Navicat Premium Data Transfer

 Source Server         : VM-mongo1-192.168.83.141
 Source Server Type    : MongoDB
 Source Server Version : 40426
 Source Host           : 192.168.83.141:27017
 Source Schema         : silentiger-cmdb

 Target Server Type    : MongoDB
 Target Server Version : 40426
 File Encoding         : 65001

 Date: 28/12/2023 21:25:19
*/


// ----------------------------
// Collection structure for data
// ----------------------------
db.getCollection("data").drop();
db.createCollection("data");
db.getCollection("data").createIndex({
    code: NumberInt("1")
}, {
    name: "code_1",
    unique: true
});
db.getCollection("data").createIndex({
    model: NumberInt("1"),
    updateTime: NumberInt("1")
}, {
    name: "model_1_updateTime_1"
});

// ----------------------------
// Documents of data
// ----------------------------
db.getCollection("data").insert([ {
    _id: ObjectId("658d75c7f13e8d0937423637"),
    ename: "initial data",
    code: "init",
    createTime: "记录创建时间",
    name: "初始化数据",
    updateTime: "记录修改时间",
    status: "记录的状态，0-正常，1-删除，其他数字备用",
    model: "所属模型"
} ]);

// ----------------------------
// Collection structure for data_relation
// ----------------------------
db.getCollection("data_relation").drop();
db.createCollection("data_relation");
db.getCollection("data_relation").createIndex({
    code: NumberInt("1")
}, {
    name: "code_1",
    unique: true
});
db.getCollection("data_relation").createIndex({
    model1: NumberInt("1"),
    data1: NumberInt("1"),
    model2: NumberInt("1")
}, {
    name: "model1_1_data1_1_model2_1"
});
db.getCollection("data_relation").createIndex({
    model2: NumberInt("1"),
    data2: NumberInt("1"),
    model1: NumberInt("1")
}, {
    name: "model2_1_data2_1_model1_1"
});

// ----------------------------
// Documents of data_relation
// ----------------------------
db.getCollection("data_relation").insert([ {
    _id: ObjectId("658d75c7f13e8d0937423638"),
    ename: "initial data",
    code: "init",
    createTime: "记录创建时间",
    name: "初始化数据",
    updateTime: "记录修改时间",
    status: "记录的状态，0-正常，1-删除，其他数字备用",
    model1: "模型1",
    model2: "模型2",
    data1: "实例1",
    data2: "实例2",
    type: "关系类型"
} ]);

// ----------------------------
// Collection structure for field
// ----------------------------
db.getCollection("field").drop();
db.createCollection("field");
db.getCollection("field").createIndex({
    code: NumberInt("1")
}, {
    name: "code_1",
    unique: true
});
db.getCollection("field").createIndex({
    model: NumberInt("1"),
    index: NumberInt("1")
}, {
    name: "model_1_index_1"
});

// ----------------------------
// Documents of field
// ----------------------------
db.getCollection("field").insert([ {
    _id: ObjectId("658d75c6f13e8d0937423633"),
    ename: "initial data",
    code: "init",
    createTime: "记录创建时间",
    name: "初始化数据",
    updateTime: "记录修改时间",
    status: "记录的状态，0-正常，1-删除，其他数字备用",
    group: "属性分组",
    type: "属性类型",
    rule: "校验规则",
    model: "所属模型",
    relationType: "关系类型（如果属性类型为关系的话）",
    remodel: "关联模型",
    isDisplay: "是否在列表中展示",
    isSearch: "是否作为搜索条件",
    isRemote: "是否远程调用获取数据",
    remoteURI: "远程接口",
    remoteKey: "接口中对应的key",
    remoteValue: "接口中value字段",
    attributes: " 附加属性，比如下拉多选选项",
    index: "排序号"
} ]);

// ----------------------------
// Collection structure for field_group
// ----------------------------
db.getCollection("field_group").drop();
db.createCollection("field_group");
db.getCollection("field_group").createIndex({
    code: NumberInt("1")
}, {
    name: "code_1",
    unique: true
});

// ----------------------------
// Documents of field_group
// ----------------------------
db.getCollection("field_group").insert([ {
    _id: ObjectId("658d75c6f13e8d0937423636"),
    ename: "initial data",
    code: "init",
    createTime: "记录创建时间",
    name: "初始化数据",
    updateTime: "记录修改时间",
    status: "记录的状态，0-正常，1-删除，其他数字备用",
    model: "所属模型",
    index: "排序号"
} ]);

// ----------------------------
// Collection structure for field_rule
// ----------------------------
db.getCollection("field_rule").drop();
db.createCollection("field_rule");
db.getCollection("field_rule").createIndex({
    code: NumberInt("1")
}, {
    name: "code_1",
    unique: true
});

// ----------------------------
// Documents of field_rule
// ----------------------------
db.getCollection("field_rule").insert([ {
    _id: ObjectId("658d75c6f13e8d0937423635"),
    ename: "initial data",
    code: "init",
    createTime: "记录创建时间",
    name: "初始化数据",
    updateTime: "记录修改时间",
    status: "记录的状态，0-正常，1-删除，其他数字备用",
    fieldType: "属性类型",
    regular: "校验规则"
} ]);

// ----------------------------
// Collection structure for field_type
// ----------------------------
db.getCollection("field_type").drop();
db.createCollection("field_type");
db.getCollection("field_type").createIndex({
    code: NumberInt("1")
}, {
    name: "code_1",
    unique: true
});

// ----------------------------
// Documents of field_type
// ----------------------------
db.getCollection("field_type").insert([ {
    _id: ObjectId("658d75c6f13e8d0937423634"),
    ename: "initial data",
    code: "init",
    createTime: "记录创建时间",
    name: "初始化数据",
    updateTime: "记录修改时间",
    status: "记录的状态，0-正常，1-删除，其他数字备用"
} ]);

// ----------------------------
// Collection structure for model
// ----------------------------
db.getCollection("model").drop();
db.createCollection("model");
db.getCollection("model").createIndex({
    code: NumberInt("1")
}, {
    name: "code_1",
    unique: true
});

// ----------------------------
// Documents of model
// ----------------------------
db.getCollection("model").insert([ {
    _id: ObjectId("658d75c6f13e8d093742362f"),
    ename: "initial data",
    code: "init",
    createTime: "记录创建时间",
    name: "初始化数据",
    updateTime: "记录修改时间",
    status: "记录的状态，0-正常，1-删除，其他数字备用",
    groupCode: "模型分组",
    parentCode: "父模型",
    remodels: "无向关联的模型"
} ]);

// ----------------------------
// Collection structure for model_group
// ----------------------------
db.getCollection("model_group").drop();
db.createCollection("model_group");
db.getCollection("model_group").createIndex({
    code: NumberInt("1")
}, {
    name: "code_1",
    unique: true
});
db.getCollection("model_group").createIndex({
    parentCode: NumberInt("1"),
    index: NumberInt("1")
}, {
    name: "parentCode_1_index_1"
});

// ----------------------------
// Documents of model_group
// ----------------------------
db.getCollection("model_group").insert([ {
    _id: ObjectId("658d75c6f13e8d0937423630"),
    ename: "initial data",
    code: "init",
    createTime: "记录创建时间",
    name: "初始化数据",
    updateTime: "记录修改时间",
    status: "记录的状态，0-正常，1-删除，其他数字备用",
    parentCode: "父级分组",
    index: "排序号"
} ]);

// ----------------------------
// Collection structure for model_relation
// ----------------------------
db.getCollection("model_relation").drop();
db.createCollection("model_relation");
db.getCollection("model_relation").createIndex({
    code: NumberInt("1")
}, {
    name: "code_1",
    unique: true
});

// ----------------------------
// Documents of model_relation
// ----------------------------
db.getCollection("model_relation").insert([ {
    _id: ObjectId("658d75c6f13e8d0937423631"),
    ename: "initial data",
    code: "init",
    createTime: "记录创建时间",
    name: "初始化数据",
    updateTime: "记录修改时间",
    status: "记录的状态，0-正常，1-删除，其他数字备用",
    model1: "模型1，父",
    model2: "模型2，子",
    relationType: "关系类型"
} ]);

// ----------------------------
// Collection structure for model_relation_type
// ----------------------------
db.getCollection("model_relation_type").drop();
db.createCollection("model_relation_type");
db.getCollection("model_relation_type").createIndex({
    code: NumberInt("1")
}, {
    name: "code_1",
    unique: true
});

// ----------------------------
// Documents of model_relation_type
// ----------------------------
db.getCollection("model_relation_type").insert([ {
    _id: ObjectId("658d75c6f13e8d0937423632"),
    ename: "initial data",
    code: "init",
    createTime: "记录创建时间",
    name: "初始化数据",
    updateTime: "记录修改时间",
    status: "记录的状态，0-正常，1-删除，其他数字备用",
    type: "0-无向，1-有向"
} ]);
