package com.geex.cmdb.system.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import java.util.Date;



/**
 * 应用构建任务视图对象 app_build_mission
 *
 * @author kk
 * @date 2021-12-16
 */
@Data
@ExcelIgnoreUnannotated
public class AppBuildMissionVo {

	private static final long serialVersionUID = 1L;

    /**
     *
     */
	@ExcelProperty(value = "")
	private Long id;

    /**
     * 项目名称
     */
	@ExcelProperty(value = "项目名称")
	private String projectName;

    /**
     * 子项目名称
     */
	@ExcelProperty(value = "子项目名称")
	private String subProjectName;

    /**
     * 分支
     */
	@ExcelProperty(value = "分支")
	private String branch;

    /**
     * 分支提交描述
     */
	@ExcelProperty(value = "分支提交描述")
	private String commitMessage;

    /**
     * 运行环境
     */
	@ExcelProperty(value = "运行环境")
	private String runProfile;

    /**
     * gitlab分组
     */
	@ExcelProperty(value = "gitlab分组")
	private String gitGroup;

    /**
     *
     */
	@ExcelProperty(value = "")
	private String appPort;

    /**
     *
     */
	@ExcelProperty(value = "")
	private String xxlPort;

    /**
     * 备注
     */
	@ExcelProperty(value = "备注")
	private String remark;

	private Date createTime;
}
