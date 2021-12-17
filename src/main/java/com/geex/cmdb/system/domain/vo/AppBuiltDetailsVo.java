package com.geex.cmdb.system.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.util.Date;


/**
 * 应用构建详情视图对象 app_built_details
 *
 * @author kk
 * @date 2021-12-17
 */
@Data
@ExcelIgnoreUnannotated
public class AppBuiltDetailsVo {

	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	@ExcelProperty(value = "")
	private Long id;


	private String buildName;
	/**
	 * 构建任务Id
	 */
	@ExcelProperty(value = "构建任务Id")
	private Long buildId;


	private Date buildTime;

	private String imageTag;

	/**
	 * 构建状态，0初始化，1成功，2失败，3异常
	 */
	@ExcelProperty(value = "构建状态，0初始化，1成功，2失败，3异常")
	private Integer buildResult;

	/**
	 * 是否提交验收，0未提交，1已提交
	 */
	@ExcelProperty(value = "是否提交验收，0未提交，1已提交")
	private Integer acceptCheck;

	/**
	 * 构建进程ID
	 */
	@ExcelProperty(value = "构建进程ID")
	private Integer processId;

	/**
	 * 提交验收时间
	 */
	@ExcelProperty(value = "提交验收时间")
	private Date acceptCheckTime;

	/**
	 * 审核状态，0待审核，1审核中，2审核成功，3审核失败
	 */
	@ExcelProperty(value = "审核状态，0待审核，1审核中，2审核成功，3审核失败")
	private Integer reviewStatus;

	/**
	 * 备注
	 */
	@ExcelProperty(value = "备注")
	private String remark;


}
