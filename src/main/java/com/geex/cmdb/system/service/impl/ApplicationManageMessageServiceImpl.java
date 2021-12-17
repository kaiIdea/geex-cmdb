package com.geex.cmdb.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.geex.cmdb.common.core.mybatisplus.core.ServicePlusImpl;
import com.geex.cmdb.common.core.page.PagePlus;
import com.geex.cmdb.common.core.page.TableDataInfo;
import com.geex.cmdb.common.utils.PageUtils;
import com.geex.cmdb.common.utils.StringUtils;
import com.geex.cmdb.system.domain.ApplicationManageMessage;
import com.geex.cmdb.system.domain.bo.ApplicationManageMessageBo;
import com.geex.cmdb.system.domain.vo.ApplicationManageMessageVo;
import com.geex.cmdb.system.mapper.ApplicationManageMessageMapper;
import com.geex.cmdb.system.service.IApplicationManageMessageService;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Auther: GEEX1428
 * @Date: 2021/12/13 11:11
 * @Description: TODO
 */
@Service
public class ApplicationManageMessageServiceImpl extends ServicePlusImpl<ApplicationManageMessageMapper, ApplicationManageMessage, ApplicationManageMessageVo> implements IApplicationManageMessageService {

    @Override
    public ApplicationManageMessageVo queryById(Long ID){
        return getVoById(ID);
    }

    @Override
    public TableDataInfo<ApplicationManageMessageVo> queryPageList(ApplicationManageMessageBo bo) {
        PagePlus<ApplicationManageMessage, ApplicationManageMessageVo> result = pageVo(PageUtils.buildPagePlus(), buildQueryWrapper(bo));
        return PageUtils.buildDataInfo(result);
    }

    @Override
    public List<ApplicationManageMessageVo> queryList(ApplicationManageMessageBo bo) {
        return listVo(buildQueryWrapper(bo));
    }

    private LambdaQueryWrapper<ApplicationManageMessage> buildQueryWrapper(ApplicationManageMessageBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<ApplicationManageMessage> lqw = Wrappers.lambdaQuery();
        lqw.like(StringUtils.isNotBlank(bo.getApplicationName()), ApplicationManageMessage::getApplicationName, bo.getApplicationName());
        lqw.eq(StringUtils.isNotBlank(bo.getApplicationMark()), ApplicationManageMessage::getApplicationMark, bo.getApplicationMark());
        lqw.eq(StringUtils.isNotBlank(bo.getApplicationDesc()), ApplicationManageMessage::getApplicationDesc, bo.getApplicationDesc());
        lqw.eq(StringUtils.isNotBlank(bo.getApplicationType()), ApplicationManageMessage::getApplicationType, bo.getApplicationType());
        return lqw;
    }

    @Override
    public Boolean insertByBo(ApplicationManageMessageBo bo) {
        ApplicationManageMessage add = BeanUtil.toBean(bo, ApplicationManageMessage.class);
        validEntityBeforeSave(add);
        boolean flag = save(add);
        if (flag) {
            bo.setID(add.getID());
        }
        return flag;
    }

    @Override
    public Boolean updateByBo(ApplicationManageMessageBo bo) {
        ApplicationManageMessage update = BeanUtil.toBean(bo, ApplicationManageMessage.class);
        validEntityBeforeSave(update);
        return updateById(update);
    }

    /**
     * 保存前的数据校验
     *
     * @param entity 实体类数据
     */
    private void validEntityBeforeSave(ApplicationManageMessage entity){
        //TODO 做一些数据校验,如唯一约束
    }

    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return removeByIds(ids);
    }

    @Override
    public List<Map<String,Object>> queryByGitGroup(String gitGroup) {
        List<Map<String,Object>> result = new ArrayList<>();
        LambdaQueryWrapper<ApplicationManageMessage> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(gitGroup), ApplicationManageMessage::getGitlabGroup, gitGroup);
        List<ApplicationManageMessage> list = this.baseMapper.selectList(lqw);
        list.stream().forEach(o ->{
            Map<String,Object> map = new HashMap<>();
            map.put("label",o.getApplicationMark());
            map.put("value",o.getApplicationMark());
            result.add(map);
        });
        return result;
    }
}
