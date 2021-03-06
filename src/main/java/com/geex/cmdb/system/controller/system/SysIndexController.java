package com.geex.cmdb.system.controller.system;

import com.geex.cmdb.common.config.RuoYiConfig;
import com.geex.cmdb.common.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 首页
 *
 * @author Lion Li
 */
//@Api(value = "首页控制器", tags = {"首页管理"})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
public class SysIndexController {

    /**
     * 系统基础配置
     */
    private final RuoYiConfig geexConfig;

    /**
     * 访问首页，提示语
     */
    //@ApiOperation("访问首页，提示语")
    @GetMapping("/")
    public String index() {
        return StringUtils.format("欢迎使用{}后台管理框架，当前版本：v{}，请通过前端地址访问。", geexConfig.getName(), geexConfig.getVersion());
    }
}
