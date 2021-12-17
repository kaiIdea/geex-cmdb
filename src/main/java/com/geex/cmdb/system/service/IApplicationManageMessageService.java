package com.geex.cmdb.system.service;


import com.geex.cmdb.common.core.mybatisplus.core.IServicePlus;
import com.geex.cmdb.common.core.page.TableDataInfo;
import com.geex.cmdb.system.domain.ApplicationManageMessage;
import com.geex.cmdb.system.domain.bo.ApplicationManageMessageBo;
import com.geex.cmdb.system.domain.vo.ApplicationManageMessageVo;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 应用管理Service接口
 *
 * @author kk
 * @date 2021-12-13
 */
public interface IApplicationManageMessageService extends IServicePlus<ApplicationManageMessage, ApplicationManageMessageVo> {
	/**
	 * 查询单个
	 * @return
	 */
	ApplicationManageMessageVo queryById(Long ID);

	/**
	 * 查询列表
	 */
    TableDataInfo<ApplicationManageMessageVo> queryPageList(ApplicationManageMessageBo bo);

	/**
	 * 查询列表
	 */
	List<ApplicationManageMessageVo> queryList(ApplicationManageMessageBo bo);

	/**
	 * 根据新增业务对象插入应用管理
	 * @param bo 应用管理新增业务对象
	 * @return
	 */
	Boolean insertByBo(ApplicationManageMessageBo bo);

	/**
	 * 根据编辑业务对象修改应用管理
	 * @param bo 应用管理编辑业务对象
	 * @return
	 */
	Boolean updateByBo(ApplicationManageMessageBo bo);

	/**
	 * 校验并删除数据
	 * @param ids 主键集合
	 * @param isValid 是否校验,true-删除前校验,false-不校验
	 * @return
	 */
	Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);



	List<Map<String,Object>> queryByGitGroup(String gitGroup);
}
