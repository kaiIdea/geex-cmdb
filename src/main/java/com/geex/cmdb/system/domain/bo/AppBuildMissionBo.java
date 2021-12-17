package com.geex.cmdb.system.domain.bo;

import com.geex.cmdb.common.core.domain.BaseEntity;
import com.geex.cmdb.common.core.validate.AddGroup;
import com.geex.cmdb.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;



/**
 * 应用构建任务业务对象 app_build_mission
 *
 * @author kk
 * @date 2021-12-16
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class AppBuildMissionBo extends BaseEntity {

    /**
     *
     */
    @NotNull(message = "不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long id;

    /**
     * 项目名称
     */
    @NotBlank(message = "项目名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String projectName;

    /**
     * 子项目名称
     */
    @NotBlank(message = "子项目名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String subProjectName;

    /**
     * 分支
     */
    @NotBlank(message = "分支不能为空", groups = { AddGroup.class, EditGroup.class })
    private String branch;

    /**
     * 分支提交描述
     */
    @NotBlank(message = "分支提交描述不能为空", groups = { AddGroup.class, EditGroup.class })
    private String commitMessage;

    /**
     * 运行环境
     */
    @NotBlank(message = "运行环境不能为空", groups = { AddGroup.class, EditGroup.class })
    private String runProfile;

    /**
     * gitlab分组
     */
    @NotBlank(message = "gitlab分组不能为空", groups = { AddGroup.class, EditGroup.class })
    private String gitGroup;

    /**
     *
     */
    @NotBlank(message = "不能为空", groups = { AddGroup.class, EditGroup.class })
    private String appPort;

    /**
     *
     */
    @NotBlank(message = "不能为空", groups = { AddGroup.class, EditGroup.class })
    private String xxlPort;

    /**
     * 备注
     */
    @NotBlank(message = "备注不能为空", groups = { AddGroup.class, EditGroup.class })
    private String remark;


    /**
     * 分页大小
     */
    private Integer pageSize;

    /**
     * 当前页数
     */
    private Integer pageNum;

    /**
     * 排序列
     */
    private String orderByColumn;

    /**
     * 排序的方向desc或者asc
     */
    private String isAsc;

}
