package com.geex.cmdb.system.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.geex.cmdb.common.core.domain.entity.SysUser;
import com.geex.cmdb.common.core.mybatisplus.core.BaseMapperPlus;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户表 数据层
 *
 * @author Lion Li
 */
public interface SysUserMapper extends BaseMapperPlus<SysUser> {

    Page<SysUser> selectPageUserList(@Param("page") Page<SysUser> page, @Param("user") SysUser user);

    /**
     * 根据条件分页查询用户列表
     *
     * @param sysUser 用户信息
     * @return 用户信息集合信息
     */
    List<SysUser> selectUserList(SysUser sysUser);

    /**
     * 根据条件分页查询未已配用户角色列表
     *
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    Page<SysUser> selectAllocatedList(@Param("page") Page<SysUser> page, @Param("user") SysUser user);

    /**
     * 根据条件分页查询未分配用户角色列表
     *
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    Page<SysUser> selectUnallocatedList(@Param("page") Page<SysUser> page, @Param("user") SysUser user);

    /**
     * 通过用户名查询用户
     *
     * @param userName 用户名
     * @return 用户对象信息
     */
    SysUser selectUserByUserName(String userName);

    /**
     * 通过用户ID查询用户
     *
     * @param userId 用户ID
     * @return 用户对象信息
     */
    SysUser selectUserById(Long userId);

}
