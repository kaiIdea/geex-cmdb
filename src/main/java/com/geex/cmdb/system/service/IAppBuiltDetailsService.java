package com.geex.cmdb.system.service;


import com.geex.cmdb.common.core.mybatisplus.core.IServicePlus;
import com.geex.cmdb.common.core.page.TableDataInfo;
import com.geex.cmdb.system.domain.AppBuiltDetails;
import com.geex.cmdb.system.domain.bo.AppBuiltDetailsBo;
import com.geex.cmdb.system.domain.vo.AppBuiltDetailsVo;

import java.util.Collection;
import java.util.List;

/**
 * 应用构建详情Service接口
 *
 * @author kk
 * @date 2021-12-17
 */
public interface IAppBuiltDetailsService extends IServicePlus<AppBuiltDetails, AppBuiltDetailsVo> {
	/**
	 * 查询单个
	 * @return
	 */
	AppBuiltDetailsVo queryById(Long ID);

	/**
	 * 查询列表
	 */
    TableDataInfo<AppBuiltDetailsVo> queryPageList(AppBuiltDetailsBo bo);

	/**
	 * 查询列表
	 */
	List<AppBuiltDetailsVo> queryList(AppBuiltDetailsBo bo);

	/**
	 * 根据新增业务对象插入应用构建详情
	 * @param bo 应用构建详情新增业务对象
	 * @return
	 */
	Boolean insertByBo(AppBuiltDetailsBo bo);

	/**
	 * 根据编辑业务对象修改应用构建详情
	 * @param bo 应用构建详情编辑业务对象
	 * @return
	 */
	Boolean updateByBo(AppBuiltDetailsBo bo);

	/**
	 * 校验并删除数据
	 * @param ids 主键集合
	 * @param isValid 是否校验,true-删除前校验,false-不校验
	 * @return
	 */
	Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
