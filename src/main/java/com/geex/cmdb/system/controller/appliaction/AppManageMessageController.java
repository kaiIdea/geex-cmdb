package com.geex.cmdb.system.controller.appliaction;
import java.util.Arrays;

import com.geex.cmdb.common.annotation.RepeatSubmit;
import com.geex.cmdb.common.core.controller.BaseController;
import com.geex.cmdb.common.core.domain.AjaxResult;
import com.geex.cmdb.common.core.page.TableDataInfo;
import com.geex.cmdb.common.core.validate.AddGroup;
import com.geex.cmdb.common.core.validate.EditGroup;
import com.geex.cmdb.common.core.validate.QueryGroup;
import com.geex.cmdb.system.domain.bo.ApplicationManageMessageBo;
import com.geex.cmdb.system.domain.vo.ApplicationManageMessageVo;
import com.geex.cmdb.system.service.IApplicationManageMessageService;
import lombok.RequiredArgsConstructor;
import javax.validation.constraints.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

/**
 * 应用管理Controller
 *
 * @author kk
 * @date 2021-12-13
 */
@Validated
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/manage/manageMessage")
public class AppManageMessageController extends BaseController {

    private final IApplicationManageMessageService iApplicationManageMessageService;

    /**
     * 查询应用管理列表
     */
    @PreAuthorize("@ss.hasPermi('manage:manage:list')")
    @GetMapping("/list")
    public TableDataInfo<ApplicationManageMessageVo> list(@Validated(QueryGroup.class) ApplicationManageMessageBo bo) {
        return iApplicationManageMessageService.queryPageList(bo);
    }

    /**
     * 获取应用管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('manage:manageMessage:query')")
    @GetMapping("/{ID}")
    public AjaxResult<ApplicationManageMessageVo> getInfo(
                                                  @NotNull(message = "主键不能为空")
                                                  @PathVariable("ID") Long ID) {
        return AjaxResult.success(iApplicationManageMessageService.queryById(ID));
    }

    /**
     * 新增应用管理
     */
    @PreAuthorize("@ss.hasPermi('manage:manageMessage:add')")
    @RepeatSubmit()
    @PostMapping()
    public AjaxResult<Void> add(@Validated(AddGroup.class) @RequestBody ApplicationManageMessageBo bo) {
        return toAjax(iApplicationManageMessageService.insertByBo(bo) ? 1 : 0);
    }

    /**
     * 修改应用管理
     */
    @PreAuthorize("@ss.hasPermi('manage:manageMessage:edit')")
    @RepeatSubmit()
    @PutMapping()
    public AjaxResult<Void> edit(@Validated(EditGroup.class) @RequestBody ApplicationManageMessageBo bo) {
        return toAjax(iApplicationManageMessageService.updateByBo(bo) ? 1 : 0);
    }

    /**
     * 删除应用管理
     */
    @PreAuthorize("@ss.hasPermi('manage:manageMessage:remove')")
    @DeleteMapping("/{IDs}")
    public AjaxResult<Void> remove(
                                       @NotEmpty(message = "主键不能为空")
                                       @PathVariable Long[] IDs) {
        return toAjax(iApplicationManageMessageService.deleteWithValidByIds(Arrays.asList(IDs), true) ? 1 : 0);
    }
}
