package com.geex.cmdb.system.service;

import com.geex.cmdb.common.core.mybatisplus.core.IServicePlus;
import com.geex.cmdb.common.core.page.TableDataInfo;
import com.geex.cmdb.system.domain.SysOss;
import com.geex.cmdb.system.domain.bo.SysOssBo;
import com.geex.cmdb.system.domain.vo.SysOssVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;

/**
 * 文件上传 服务层
 *
 * @author Lion Li
 */
public interface ISysOssService extends IServicePlus<SysOss, SysOssVo> {

    TableDataInfo<SysOssVo> queryPageList(SysOssBo sysOss);

    SysOss upload(MultipartFile file);

    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
