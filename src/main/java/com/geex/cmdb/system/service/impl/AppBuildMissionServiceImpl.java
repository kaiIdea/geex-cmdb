package com.geex.cmdb.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.geex.cmdb.common.core.mybatisplus.core.ServicePlusImpl;
import com.geex.cmdb.common.core.page.PagePlus;
import com.geex.cmdb.common.core.page.TableDataInfo;
import com.geex.cmdb.common.utils.PageUtils;
import com.geex.cmdb.common.utils.StringUtils;
import com.geex.cmdb.system.mapper.AppBuildMissionMapper;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.geex.cmdb.system.domain.bo.AppBuildMissionBo;
import com.geex.cmdb.system.domain.vo.AppBuildMissionVo;
import com.geex.cmdb.system.domain.AppBuildMission;
import com.geex.cmdb.system.service.IAppBuildMissionService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 应用构建任务Service业务层处理
 *
 * @author kk
 * @date 2021-12-16
 */
@Service
public class AppBuildMissionServiceImpl extends ServicePlusImpl<AppBuildMissionMapper, AppBuildMission, AppBuildMissionVo> implements IAppBuildMissionService {

    @Override
    public AppBuildMissionVo queryById(Long ID){
        return getVoById(ID);
    }

    @Override
    public TableDataInfo<AppBuildMissionVo> queryPageList(AppBuildMissionBo bo) {
        PagePlus<AppBuildMission, AppBuildMissionVo> result = pageVo(PageUtils.buildPagePlus(), buildQueryWrapper(bo));
        return PageUtils.buildDataInfo(result);
    }

    @Override
    public List<AppBuildMissionVo> queryList(AppBuildMissionBo bo) {
        return listVo(buildQueryWrapper(bo));
    }

    private LambdaQueryWrapper<AppBuildMission> buildQueryWrapper(AppBuildMissionBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<AppBuildMission> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getId() != null, AppBuildMission::getId, bo.getId());
        lqw.like(StringUtils.isNotBlank(bo.getProjectName()), AppBuildMission::getProjectName, bo.getProjectName());
        lqw.like(StringUtils.isNotBlank(bo.getSubProjectName()), AppBuildMission::getSubProjectName, bo.getSubProjectName());
        lqw.eq(StringUtils.isNotBlank(bo.getBranch()), AppBuildMission::getBranch, bo.getBranch());
        lqw.eq(StringUtils.isNotBlank(bo.getCommitMessage()), AppBuildMission::getCommitMessage, bo.getCommitMessage());
        lqw.eq(StringUtils.isNotBlank(bo.getRunProfile()), AppBuildMission::getRunProfile, bo.getRunProfile());
        lqw.eq(StringUtils.isNotBlank(bo.getGitGroup()), AppBuildMission::getGitGroup, bo.getGitGroup());
        lqw.eq(StringUtils.isNotBlank(bo.getAppPort()), AppBuildMission::getAppPort, bo.getAppPort());
        lqw.eq(StringUtils.isNotBlank(bo.getXxlPort()), AppBuildMission::getXxlPort, bo.getXxlPort());
        return lqw;
    }

    @Override
    public Boolean insertByBo(AppBuildMissionBo bo) {
        AppBuildMission add = BeanUtil.toBean(bo, AppBuildMission.class);
        validEntityBeforeSave(add);
        boolean flag = save(add);
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    @Override
    public Boolean updateByBo(AppBuildMissionBo bo) {
        AppBuildMission update = BeanUtil.toBean(bo, AppBuildMission.class);
        validEntityBeforeSave(update);
        return updateById(update);
    }

    /**
     * 保存前的数据校验
     *
     * @param entity 实体类数据
     */
    private void validEntityBeforeSave(AppBuildMission entity){
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
