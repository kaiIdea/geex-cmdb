package com.geex.cmdb.system.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.geex.cmdb.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 应用管理对象 application_manage_message
 *
 * @author kk
 * @date 2021-12-13
 */
@Data
@Accessors(chain = true)
@TableName("application_manage_message")
public class ApplicationManageMessage extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     *
     */
    @TableId(value = "ID")
    private Long ID;

    /**
     * 应用类型
     * */
    private String applicationType;
    /**
     * 应用名称
     */
    private String applicationName;
    /**
     * 应用唯一标识符
     */
    private String applicationMark;
    /**
     * 描述信息
     */
    private String applicationDesc;
    /**
     * 备注
     */
    private String remark;


    private String gitlabGroup;

}
