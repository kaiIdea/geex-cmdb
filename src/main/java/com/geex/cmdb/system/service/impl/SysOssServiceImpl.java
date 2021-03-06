package com.geex.cmdb.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.geex.cmdb.common.core.mybatisplus.core.ServicePlusImpl;
import com.geex.cmdb.common.core.page.PagePlus;
import com.geex.cmdb.common.core.page.TableDataInfo;
import com.geex.cmdb.common.exception.ServiceException;
import com.geex.cmdb.common.utils.PageUtils;
import com.geex.cmdb.common.utils.StringUtils;
import com.geex.cmdb.oss.entity.UploadResult;
import com.geex.cmdb.oss.factory.OssFactory;
import com.geex.cmdb.oss.service.IOssStrategy;
import com.geex.cmdb.system.domain.SysOss;
import com.geex.cmdb.system.domain.bo.SysOssBo;
import com.geex.cmdb.system.domain.vo.SysOssVo;
import com.geex.cmdb.system.mapper.SysOssMapper;
import com.geex.cmdb.system.service.ISysOssService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 文件上传 服务层实现
 *
 * @author Lion Li
 */
@Service
public class SysOssServiceImpl extends ServicePlusImpl<SysOssMapper, SysOss, SysOssVo> implements ISysOssService {

    @Override
    public TableDataInfo<SysOssVo> queryPageList(SysOssBo bo) {
        PagePlus<SysOss, SysOssVo> result = pageVo(PageUtils.buildPagePlus(), buildQueryWrapper(bo));
        return PageUtils.buildDataInfo(result);
    }

    private LambdaQueryWrapper<SysOss> buildQueryWrapper(SysOssBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<SysOss> lqw = Wrappers.lambdaQuery();
        lqw.like(StringUtils.isNotBlank(bo.getFileName()), SysOss::getFileName, bo.getFileName());
        lqw.like(StringUtils.isNotBlank(bo.getOriginalName()), SysOss::getOriginalName, bo.getOriginalName());
        lqw.eq(StringUtils.isNotBlank(bo.getFileSuffix()), SysOss::getFileSuffix, bo.getFileSuffix());
        lqw.eq(StringUtils.isNotBlank(bo.getUrl()), SysOss::getUrl, bo.getUrl());
        lqw.between(params.get("beginCreateTime") != null && params.get("endCreateTime") != null,
                SysOss::getCreateTime, params.get("beginCreateTime"), params.get("endCreateTime"));
        lqw.eq(StringUtils.isNotBlank(bo.getCreateBy()), SysOss::getCreateBy, bo.getCreateBy());
        lqw.eq(StringUtils.isNotBlank(bo.getService()), SysOss::getService, bo.getService());
        return lqw;
    }

    @Override
    public SysOss upload(MultipartFile file) {
        String originalfileName = file.getOriginalFilename();
        String suffix = StringUtils.substring(originalfileName, originalfileName.lastIndexOf("."), originalfileName.length());
        IOssStrategy storage = OssFactory.instance();
        UploadResult uploadResult;
        try {
            uploadResult = storage.uploadSuffix(file.getBytes(), suffix, file.getContentType());
        } catch (IOException e) {
            throw new ServiceException(e.getMessage());
        }
        // 保存文件信息
        SysOss oss = new SysOss()
                .setUrl(uploadResult.getUrl())
                .setFileSuffix(suffix)
                .setFileName(uploadResult.getFilename())
                .setOriginalName(originalfileName)
                .setService(storage.getServiceType());
        save(oss);
        return oss;
    }

    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if (isValid) {
            // 做一些业务上的校验,判断是否需要校验
        }
        List<SysOss> list = listByIds(ids);
        for (SysOss sysOss : list) {
            IOssStrategy storage = OssFactory.instance(sysOss.getService());
            storage.delete(sysOss.getUrl());
        }
        return removeByIds(ids);
    }

}
