package com.geex.cmdb.system.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.geex.cmdb.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.experimental.Accessors;


/**
 * 应用构建任务对象 app_build_mission
 *
 * @author kk
 * @date 2021-12-16
 */
@Data
@Accessors(chain = true)
@TableName("app_build_mission")
public class AppBuildMission extends BaseEntity {

    private static final long serialVersionUID=1L;

    /**
     *
     */
    private Long id;
    /**
     * 项目名称
     */
    private String projectName;
    /**
     * 子项目名称
     */
    private String subProjectName;
    /**
     * 分支
     */
    private String branch;
    /**
     * 分支提交描述
     */
    private String commitMessage;
    /**
     * 运行环境
     */
    private String runProfile;
    /**
     * gitlab分组
     */
    private String gitGroup;
    /**
     *
     */
    private String appPort;
    /**
     *
     */
    private String xxlPort;
    /**
     * 备注
     */
    private String remark;

}
