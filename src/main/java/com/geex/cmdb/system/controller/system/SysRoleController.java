package com.geex.cmdb.system.controller.system;

import com.geex.cmdb.common.annotation.Log;
import com.geex.cmdb.common.constant.UserConstants;
import com.geex.cmdb.common.core.controller.BaseController;
import com.geex.cmdb.common.core.domain.AjaxResult;
import com.geex.cmdb.common.core.domain.entity.SysRole;
import com.geex.cmdb.common.core.domain.entity.SysUser;
import com.geex.cmdb.common.core.domain.model.LoginUser;
import com.geex.cmdb.common.core.page.TableDataInfo;
import com.geex.cmdb.common.core.service.TokenService;
import com.geex.cmdb.common.enums.BusinessType;
import com.geex.cmdb.common.utils.StringUtils;
import com.geex.cmdb.common.utils.poi.ExcelUtil;
import com.geex.cmdb.system.domain.SysUserRole;
import com.geex.cmdb.system.service.ISysRoleService;
import com.geex.cmdb.system.service.ISysUserService;
import com.geex.cmdb.system.service.SysPermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 角色信息
 *
 * @author Lion Li
 */
@Validated
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/system/role")
public class SysRoleController extends BaseController {

    private final ISysRoleService roleService;
    private final TokenService tokenService;
    private final ISysUserService userService;
    private final SysPermissionService permissionService;

    //@ApiOperation("查询角色信息列表")
    @PreAuthorize("@ss.hasPermi('system:role:list')")
    @GetMapping("/list")
    public TableDataInfo<SysRole> list(SysRole role) {
        return roleService.selectPageRoleList(role);
    }

    //@ApiOperation("导出角色信息列表")
    @Log(title = "角色管理", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('system:role:export')")
    @PostMapping("/export")
    public void export(SysRole role, HttpServletResponse response) {
        List<SysRole> list = roleService.selectRoleList(role);
        ExcelUtil.exportExcel(list, "角色数据", SysRole.class, response);
    }

    /**
     * 根据角色编号获取详细信息
     */
    //@ApiOperation("根据角色编号获取详细信息")
    @PreAuthorize("@ss.hasPermi('system:role:query')")
    @GetMapping(value = "/{roleId}")
    public AjaxResult<SysRole> getInfo(@PathVariable Long roleId) {
        roleService.checkRoleDataScope(roleId);
        return AjaxResult.success(roleService.selectRoleById(roleId));
    }

    /**
     * 新增角色
     */
    //@ApiOperation("新增角色")
    @PreAuthorize("@ss.hasPermi('system:role:add')")
    @Log(title = "角色管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult<Void> add(@Validated @RequestBody SysRole role) {
        if (UserConstants.NOT_UNIQUE.equals(roleService.checkRoleNameUnique(role))) {
            return AjaxResult.error("新增角色'" + role.getRoleName() + "'失败，角色名称已存在");
        } else if (UserConstants.NOT_UNIQUE.equals(roleService.checkRoleKeyUnique(role))) {
            return AjaxResult.error("新增角色'" + role.getRoleName() + "'失败，角色权限已存在");
        }
        return toAjax(roleService.insertRole(role));

    }

    /**
     * 修改保存角色
     */
    //@ApiOperation("修改保存角色")
    @PreAuthorize("@ss.hasPermi('system:role:edit')")
    @Log(title = "角色管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult<Void> edit(@Validated @RequestBody SysRole role) {
        roleService.checkRoleAllowed(role);
        if (UserConstants.NOT_UNIQUE.equals(roleService.checkRoleNameUnique(role))) {
            return AjaxResult.error("修改角色'" + role.getRoleName() + "'失败，角色名称已存在");
        } else if (UserConstants.NOT_UNIQUE.equals(roleService.checkRoleKeyUnique(role))) {
            return AjaxResult.error("修改角色'" + role.getRoleName() + "'失败，角色权限已存在");
        }

        if (roleService.updateRole(role) > 0) {
            // 更新缓存用户权限
            LoginUser loginUser = getLoginUser();
            SysUser sysUser = userService.selectUserById(loginUser.getUserId());
            if (StringUtils.isNotNull(sysUser) && !sysUser.isAdmin()) {
                loginUser.setPermissions(permissionService.getMenuPermission(sysUser));
                tokenService.setLoginUser(loginUser);
            }
            return AjaxResult.success();
        }
        return AjaxResult.error("修改角色'" + role.getRoleName() + "'失败，请联系管理员");
    }

    /**
     * 修改保存数据权限
     */
    //@ApiOperation("修改保存数据权限")
    @PreAuthorize("@ss.hasPermi('system:role:edit')")
    @Log(title = "角色管理", businessType = BusinessType.UPDATE)
    @PutMapping("/dataScope")
    public AjaxResult<Void> dataScope(@RequestBody SysRole role) {
        roleService.checkRoleAllowed(role);
        return toAjax(roleService.authDataScope(role));
    }

    /**
     * 状态修改
     */
    //@ApiOperation("状态修改")
    @PreAuthorize("@ss.hasPermi('system:role:edit')")
    @Log(title = "角色管理", businessType = BusinessType.UPDATE)
    @PutMapping("/changeStatus")
    public AjaxResult<Void> changeStatus(@RequestBody SysRole role) {
        roleService.checkRoleAllowed(role);
        return toAjax(roleService.updateRoleStatus(role));
    }

    /**
     * 删除角色
     */
    //@ApiOperation("删除角色")
    @PreAuthorize("@ss.hasPermi('system:role:remove')")
    @Log(title = "角色管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{roleIds}")
    public AjaxResult<Void> remove( @PathVariable Long[] roleIds) {
        return toAjax(roleService.deleteRoleByIds(roleIds));
    }

    /**
     * 获取角色选择框列表
     */
    //@ApiOperation("获取角色选择框列表")
    @PreAuthorize("@ss.hasPermi('system:role:query')")
    @GetMapping("/optionselect")
    public AjaxResult<List<SysRole>> optionselect() {
        return AjaxResult.success(roleService.selectRoleAll());
    }

    /**
     * 查询已分配用户角色列表
     */
    //@ApiOperation("查询已分配用户角色列表")
    @PreAuthorize("@ss.hasPermi('system:role:list')")
    @GetMapping("/authUser/allocatedList")
    public TableDataInfo<SysUser> allocatedList(SysUser user) {
        return userService.selectAllocatedList(user);
    }

    /**
     * 查询未分配用户角色列表
     */
    //@ApiOperation("查询未分配用户角色列表")
    @PreAuthorize("@ss.hasPermi('system:role:list')")
    @GetMapping("/authUser/unallocatedList")
    public TableDataInfo<SysUser> unallocatedList(SysUser user) {
        return userService.selectUnallocatedList(user);
    }

    /**
     * 取消授权用户
     */
    //@ApiOperation("取消授权用户")
    @PreAuthorize("@ss.hasPermi('system:role:edit')")
    @Log(title = "角色管理", businessType = BusinessType.GRANT)
    @PutMapping("/authUser/cancel")
    public AjaxResult<Void> cancelAuthUser(@RequestBody SysUserRole userRole) {
        return toAjax(roleService.deleteAuthUser(userRole));
    }

    /**
     * 批量取消授权用户
     */
    //@ApiOperation("批量取消授权用户")
//    @ApiImplicitParams({
//        @ApiImplicitParam(name = "roleId", value = "角色ID", paramType = "query", dataTypeClass = String.class),
//        @ApiImplicitParam(name = "userIds", value = "用户ID串", paramType = "query", dataTypeClass = String.class)
//    })
    @PreAuthorize("@ss.hasPermi('system:role:edit')")
    @Log(title = "角色管理", businessType = BusinessType.GRANT)
    @PutMapping("/authUser/cancelAll")
    public AjaxResult<Void> cancelAuthUserAll(Long roleId, Long[] userIds) {
        return toAjax(roleService.deleteAuthUsers(roleId, userIds));
    }

    /**
     * 批量选择用户授权
     */
    //@ApiOperation("批量选择用户授权")
//    @ApiImplicitParams({
//        @ApiImplicitParam(name = "roleId", value = "角色ID", paramType = "query", dataTypeClass = String.class),
//        @ApiImplicitParam(name = "userIds", value = "用户ID串", paramType = "query", dataTypeClass = String.class)
//    })
    @PreAuthorize("@ss.hasPermi('system:role:edit')")
    @Log(title = "角色管理", businessType = BusinessType.GRANT)
    @PutMapping("/authUser/selectAll")
    public AjaxResult<Void> selectAuthUserAll(Long roleId, Long[] userIds) {
        return toAjax(roleService.insertAuthUsers(roleId, userIds));
    }
}
