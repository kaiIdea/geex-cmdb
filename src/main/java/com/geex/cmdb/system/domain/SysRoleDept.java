package com.geex.cmdb.system.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 角色和部门关联 sys_role_dept
 *
 * @author Lion Li
 */

@Data
@Accessors(chain = true)
@TableName("sys_role_dept")
//@ApiModel("角色和部门关联")
public class SysRoleDept {

    /**
     * 角色ID
     */
    //@ApiModelProperty(value = "角色ID")
    private Long roleId;

    /**
     * 部门ID
     */
    //@ApiModelProperty(value = "部门ID")
    private Long deptId;

}
