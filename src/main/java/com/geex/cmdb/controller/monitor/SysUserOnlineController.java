package com.geex.cmdb.controller.monitor;

import com.geex.cmdb.common.annotation.Log;
import com.geex.cmdb.common.constant.Constants;
import com.geex.cmdb.common.core.controller.BaseController;
import com.geex.cmdb.common.core.domain.AjaxResult;
import com.geex.cmdb.common.core.domain.model.LoginUser;
import com.geex.cmdb.common.core.page.TableDataInfo;
import com.geex.cmdb.common.enums.BusinessType;
import com.geex.cmdb.common.utils.PageUtils;
import com.geex.cmdb.common.utils.RedisUtils;
import com.geex.cmdb.common.utils.StringUtils;
import com.geex.cmdb.system.domain.SysUserOnline;
import com.geex.cmdb.system.service.ISysUserOnlineService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * 在线用户监控
 *
 * @author Lion Li
 */
//@Api(value = "在线用户监控", tags = {"在线用户监控管理"})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/monitor/online")
public class SysUserOnlineController extends BaseController {

    private final ISysUserOnlineService userOnlineService;

    //@ApiOperation("在线用户列表")
    @PreAuthorize("@ss.hasPermi('monitor:online:list')")
    @GetMapping("/list")
    public TableDataInfo<SysUserOnline> list(String ipaddr, String userName) {
        Collection<String> keys = RedisUtils.keys(Constants.LOGIN_TOKEN_KEY + "*");
        List<SysUserOnline> userOnlineList = new ArrayList<SysUserOnline>();
        for (String key : keys) {
            LoginUser user = RedisUtils.getCacheObject(key);
            if (StringUtils.isNotEmpty(ipaddr) && StringUtils.isNotEmpty(userName)) {
                if (StringUtils.equals(ipaddr, user.getIpaddr()) && StringUtils.equals(userName, user.getUsername())) {
                    userOnlineList.add(userOnlineService.selectOnlineByInfo(ipaddr, userName, user));
                }
            } else if (StringUtils.isNotEmpty(ipaddr)) {
                if (StringUtils.equals(ipaddr, user.getIpaddr())) {
                    userOnlineList.add(userOnlineService.selectOnlineByIpaddr(ipaddr, user));
                }
            } else if (StringUtils.isNotEmpty(userName)) {
                if (StringUtils.equals(userName, user.getUsername())) {
                    userOnlineList.add(userOnlineService.selectOnlineByUserName(userName, user));
                }
            } else {
                userOnlineList.add(userOnlineService.loginUserToUserOnline(user));
            }
        }
        Collections.reverse(userOnlineList);
        userOnlineList.removeAll(Collections.singleton(null));
        return PageUtils.buildDataInfo(userOnlineList);
    }

    /**
     * 强退用户
     */
    //@ApiOperation("强退用户")
    @PreAuthorize("@ss.hasPermi('monitor:online:forceLogout')")
    @Log(title = "在线用户", businessType = BusinessType.FORCE)
    @DeleteMapping("/{tokenId}")
    public AjaxResult<Void> forceLogout(@PathVariable String tokenId) {
        RedisUtils.deleteObject(Constants.LOGIN_TOKEN_KEY + tokenId);
        return AjaxResult.success();
    }
}
