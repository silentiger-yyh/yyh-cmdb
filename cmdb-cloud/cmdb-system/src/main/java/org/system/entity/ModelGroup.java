package org.system.entity;

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

/**
 * ModelGroup类
 * 模型分组
 *
 * @Author silentiger@yyh
 * @Date 2024-01-01 17:55:59
 */

@Data
@Builder
@Document(collection = "model_group")
@ApiModel("模型")
public class ModelGroup implements Serializable {
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
    private Long index;

}
