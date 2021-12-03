package com.geex.cmdb.system.listener;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.geex.cmdb.common.core.domain.entity.SysUser;
import com.geex.cmdb.common.excel.ExcelListener;
import com.geex.cmdb.common.excel.ExcelResult;
import com.geex.cmdb.common.exception.ServiceException;
import com.geex.cmdb.common.utils.SecurityUtils;
import com.geex.cmdb.common.utils.StringUtils;
import com.geex.cmdb.common.utils.spring.SpringUtils;
import com.geex.cmdb.system.domain.vo.SysUserImportVo;
import com.geex.cmdb.system.service.ISysConfigService;
import com.geex.cmdb.system.service.ISysUserService;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 系统用户自定义导入
 *
 * @author Lion Li
 */
@Slf4j
public class SysUserImportListener extends AnalysisEventListener<SysUserImportVo> implements ExcelListener<SysUserImportVo> {

    private final ISysUserService userService;

    private final String password;

    private final Boolean isUpdateSupport;

    private final String operName;

    private int successNum = 0;
    private int failureNum = 0;
    private final StringBuilder successMsg = new StringBuilder();
    private final StringBuilder failureMsg = new StringBuilder();

    public SysUserImportListener(Boolean isUpdateSupport) {
        this.userService = SpringUtils.getBean(ISysUserService.class);
        this.password = SpringUtils.getBean(ISysConfigService.class)
            .selectConfigByKey("sys.user.initPassword");
        this.isUpdateSupport = isUpdateSupport;
        this.operName = SecurityUtils.getUsername();
    }

    @Override
    public void invoke(SysUserImportVo userVo, AnalysisContext context) {
        SysUser user = this.userService.selectUserByUserName(userVo.getUserName());
        try {
            // 验证是否存在这个用户
            if (StringUtils.isNull(user)) {
                user = BeanUtil.toBean(userVo, SysUser.class);
                user.setPassword(SecurityUtils.encryptPassword(password));
                user.setCreateBy(operName);
                userService.insertUser(user);
                successNum++;
                successMsg.append("<br/>").append(successNum).append("、账号 ").append(user.getUserName()).append(" 导入成功");
            } else if (isUpdateSupport) {
                user.setUpdateBy(operName);
                userService.updateUser(user);
                successNum++;
                successMsg.append("<br/>").append(successNum).append("、账号 ").append(user.getUserName()).append(" 更新成功");
            } else {
                failureNum++;
                failureMsg.append("<br/>").append(failureNum).append("、账号 ").append(user.getUserName()).append(" 已存在");
            }
        } catch (Exception e) {
            failureNum++;
            String msg = "<br/>" + failureNum + "、账号 " + user.getUserName() + " 导入失败：";
            failureMsg.append(msg).append(e.getMessage());
            log.error(msg, e);
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {

    }

    @Override
    public ExcelResult<SysUserImportVo> getExcelResult() {
        return new ExcelResult<SysUserImportVo>() {

            @Override
            public String getAnalysis() {
                if (failureNum > 0) {
                    failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
                    throw new ServiceException(failureMsg.toString());
                } else {
                    successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
                }
                return successMsg.toString();
            }

            @Override
            public List<SysUserImportVo> getList() {
                return null;
            }

            @Override
            public List<String> getErrorList() {
                return null;
            }
        };
    }
}
