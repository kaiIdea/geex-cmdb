package com.geex.cmdb.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.geex.cmdb.common.core.mybatisplus.core.ServicePlusImpl;
import com.geex.cmdb.common.core.page.PagePlus;
import com.geex.cmdb.common.core.page.TableDataInfo;
import com.geex.cmdb.common.utils.PageUtils;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.geex.cmdb.system.domain.bo.AppBuiltDetailsBo;
import com.geex.cmdb.system.domain.vo.AppBuiltDetailsVo;
import com.geex.cmdb.system.domain.AppBuiltDetails;
import com.geex.cmdb.system.mapper.AppBuiltDetailsMapper;
import com.geex.cmdb.system.service.IAppBuiltDetailsService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 应用构建详情Service业务层处理
 *
 * @author kk
 * @date 2021-12-17
 */
@Service
public class AppBuiltDetailsServiceImpl extends ServicePlusImpl<AppBuiltDetailsMapper, AppBuiltDetails, AppBuiltDetailsVo> implements IAppBuiltDetailsService {

    @Override
    public AppBuiltDetailsVo queryById(Long id){
        return getVoById(id);
    }

    @Override
    public TableDataInfo<AppBuiltDetailsVo> queryPageList(AppBuiltDetailsBo bo) {
        PagePlus<AppBuiltDetails, AppBuiltDetailsVo> result = pageVo(PageUtils.buildPagePlus(), buildQueryWrapper(bo));
        return PageUtils.buildDataInfo(result);
    }

    @Override
    public List<AppBuiltDetailsVo> queryList(AppBuiltDetailsBo bo) {
        return listVo(buildQueryWrapper(bo));
    }

    private LambdaQueryWrapper<AppBuiltDetails> buildQueryWrapper(AppBuiltDetailsBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<AppBuiltDetails> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getBuildId() != null, AppBuiltDetails::getBuildId, bo.getBuildId());
        lqw.eq(bo.getBuildResult() != null, AppBuiltDetails::getBuildResult, bo.getBuildResult());
        lqw.eq(bo.getAcceptCheck() != null, AppBuiltDetails::getAcceptCheck, bo.getAcceptCheck());
        lqw.eq(bo.getProcessId() != null, AppBuiltDetails::getProcessId, bo.getProcessId());
        lqw.eq(bo.getAcceptCheckTime() != null, AppBuiltDetails::getAcceptCheckTime, bo.getAcceptCheckTime());
        lqw.eq(bo.getReviewStatus() != null, AppBuiltDetails::getReviewStatus, bo.getReviewStatus());
        return lqw;
    }

    @Override
    public Boolean insertByBo(AppBuiltDetailsBo bo) {
        AppBuiltDetails add = BeanUtil.toBean(bo, AppBuiltDetails.class);
        validEntityBeforeSave(add);
        boolean flag = save(add);
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    @Override
    public Boolean updateByBo(AppBuiltDetailsBo bo) {
        AppBuiltDetails update = BeanUtil.toBean(bo, AppBuiltDetails.class);
        validEntityBeforeSave(update);
        return updateById(update);
    }

    /**
     * 保存前的数据校验
     *
     * @param entity 实体类数据
     */
    private void validEntityBeforeSave(AppBuiltDetails entity){
        //TODO 做一些数据校验,如唯一约束
    }

    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return removeByIds(ids);
    }
}
