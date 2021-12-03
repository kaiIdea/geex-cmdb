package com.geex.cmdb.controller.system;

import com.geex.cmdb.common.core.controller.BaseController;
import com.geex.cmdb.common.core.domain.AjaxResult;
import com.geex.cmdb.common.core.domain.model.RegisterBody;
import com.geex.cmdb.common.utils.StringUtils;
import com.geex.cmdb.system.service.ISysConfigService;
import com.geex.cmdb.system.service.SysRegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 注册验证
 *
 * @author Lion Li
 */
@Validated
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
public class SysRegisterController extends BaseController {

    private final SysRegisterService registerService;
    private final ISysConfigService configService;

    //@ApiOperation("用户注册")
    @PostMapping("/register")
    public AjaxResult<Void> register(@RequestBody RegisterBody user) {
        if (!("true".equals(configService.selectConfigByKey("sys.account.registerUser")))) {
            return error("当前系统没有开启注册功能！");
        }
        String msg = registerService.register(user);
        return StringUtils.isEmpty(msg) ? success() : error(msg);
    }
}
