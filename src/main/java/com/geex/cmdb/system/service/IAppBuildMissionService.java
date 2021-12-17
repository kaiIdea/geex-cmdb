package com.geex.cmdb.system.service;

import com.geex.cmdb.common.core.mybatisplus.core.IServicePlus;
import com.geex.cmdb.common.core.page.TableDataInfo;
import com.geex.cmdb.system.domain.AppBuildMission;
import com.geex.cmdb.system.domain.vo.AppBuildMissionVo;
import com.geex.cmdb.system.domain.bo.AppBuildMissionBo;

import java.util.Collection;
import java.util.List;

/**
 * 应用构建任务Service接口
 *
 * @author kk
 * @date 2021-12-16
 */
public interface IAppBuildMissionService extends IServicePlus<AppBuildMission, AppBuildMissionVo> {
	/**
	 * 查询单个
	 * @return
	 */
	AppBuildMissionVo queryById(Long ID);

	/**
	 * 查询列表
	 */
    TableDataInfo<AppBuildMissionVo> queryPageList(AppBuildMissionBo bo);

	/**
	 * 查询列表
	 */
	List<AppBuildMissionVo> queryList(AppBuildMissionBo bo);

	/**
	 * 根据新增业务对象插入应用构建任务
	 * @param bo 应用构建任务新增业务对象
	 * @return
	 */
	Boolean insertByBo(AppBuildMissionBo bo);

	/**
	 * 根据编辑业务对象修改应用构建任务
	 * @param bo 应用构建任务编辑业务对象
	 * @return
	 */
	Boolean updateByBo(AppBuildMissionBo bo);

	/**
	 * 校验并删除数据
	 * @param ids 主键集合
	 * @param isValid 是否校验,true-删除前校验,false-不校验
	 * @return
	 */
	Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
