package com.geex.cmdb.system.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import java.util.Date;



/**
 * 应用管理视图对象 application_manage_message
 *
 * @author kk
 * @date 2021-12-13
 */
@Data
@ExcelIgnoreUnannotated
public class ApplicationManageMessageVo {

	private static final long serialVersionUID = 1L;

    /**
     *
     */
	@ExcelProperty(value = "")
	private Long ID;


	/**
	 * 应用类型
	 * */
	private String applicationType;
    /**
     * 应用名称
     */
	@ExcelProperty(value = "应用名称")
	private String applicationName;

    /**
     * 应用唯一标识符
     */
	@ExcelProperty(value = "应用唯一标识符")
	private String applicationMark;

    /**
     * 描述信息
     */
	@ExcelProperty(value = "描述信息")
	private String applicationDesc;

    /**
     * 备注
     */
	@ExcelProperty(value = "备注")
	private String remark;


	private String gitlabGroup;
}
