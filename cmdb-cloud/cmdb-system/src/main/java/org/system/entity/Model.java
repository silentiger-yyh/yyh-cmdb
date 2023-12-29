package org.system.entity;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Model类
 * 模型集合
 *
 * @Author silentiger@yyh
 * @Date 2023-12-29 20:31:25
 */
@Data
@Document(collection = "model")
@ApiModel("模型")
@Builder
public class Model implements Serializable {
    @Id
    @Field("_id")
    private ObjectId _id;
    @NotBlank(message = "编码不能为空")
    private String code;
    @NotBlank(message = "名称不能为空")
    private String name;
    private String ename;
    private Date createTime;
    private Date updateTime;
    private Integer status;

    private ObjectId parent;
    @NotBlank(message = "分组不能为空")
    private ObjectId group;
    private List<Model> remodels;

}
