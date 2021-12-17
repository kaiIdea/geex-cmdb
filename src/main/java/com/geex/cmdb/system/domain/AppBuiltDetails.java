package com.geex.cmdb.system.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.geex.cmdb.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 应用构建详情对象 app_built_details
 *
 * @author kk
 * @date 2021-12-17
 */
@Data
@Accessors(chain = true)
@TableName("app_built_details")
public class AppBuiltDetails extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     *
     */
    private Long id;


    private String buildName;

    private Date buildTime;

    private String imageTag;
    /**
     * 构建任务Id
     */
    private Long buildId;
    /**
     * 构建状态，0初始化，1成功，2失败，3异常
     */
    private Integer buildResult;
    /**
     * 是否提交验收，0未提交，1已提交
     */
    private Integer acceptCheck;
    /**
     * 构建进程ID
     */
    private Integer processId;
    /**
     * 提交验收时间
     */
    private Date acceptCheckTime;
    /**
     * 审核状态，0待审核，1审核中，2审核成功，3审核失败
     */
    private Integer reviewStatus;
    /**
     * 备注
     */
    private String remark;
}
