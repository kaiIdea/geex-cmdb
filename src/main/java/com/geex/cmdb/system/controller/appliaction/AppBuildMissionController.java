package com.geex.cmdb.system.controller.appliaction;

import java.util.Arrays;

import com.geex.cmdb.common.annotation.RepeatSubmit;
import com.geex.cmdb.common.core.controller.BaseController;
import com.geex.cmdb.common.core.domain.AjaxResult;
import com.geex.cmdb.common.core.page.TableDataInfo;
import com.geex.cmdb.common.core.validate.AddGroup;
import com.geex.cmdb.common.core.validate.EditGroup;
import com.geex.cmdb.common.core.validate.QueryGroup;
import com.geex.cmdb.system.domain.bo.AppBuildMissionBo;
import com.geex.cmdb.system.domain.vo.AppBuildMissionVo;
import com.geex.cmdb.system.service.IAppBuildMissionService;
import lombok.RequiredArgsConstructor;
import javax.validation.constraints.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

/**
 * 应用构建任务Controller
 *
 * @author kk
 * @date 2021-12-16
 */
@Validated
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/build/buildMission")
public class AppBuildMissionController extends BaseController {

    private final IAppBuildMissionService iAppBuildMissionService;

    /**
     * 查询应用构建任务列表
     */
    @PreAuthorize("@ss.hasPermi('build:buildMission:list')")
    @GetMapping("/list")
    public TableDataInfo<AppBuildMissionVo> list(@Validated(QueryGroup.class) AppBuildMissionBo bo) {
        return iAppBuildMissionService.queryPageList(bo);
    }


    /**
     * 获取应用构建任务详细信息
     */
    @PreAuthorize("@ss.hasPermi('build:buildMission:query')")
    @GetMapping("/{ID}")
    public AjaxResult<AppBuildMissionVo> getInfo(
                                                  @NotNull(message = "主键不能为空")
                                                  @PathVariable("ID") Long ID) {
        return AjaxResult.success(iAppBuildMissionService.queryById(ID));
    }

    /**
     * 新增应用构建任务
     */
    @PreAuthorize("@ss.hasPermi('build:buildMission:add')")
    @RepeatSubmit()
    @PostMapping()
    public AjaxResult<Void> add(@Validated(AddGroup.class) @RequestBody AppBuildMissionBo bo) {
        return toAjax(iAppBuildMissionService.insertByBo(bo) ? 1 : 0);
    }

    /**
     * 修改应用构建任务
     */
    @PreAuthorize("@ss.hasPermi('build:buildMission:edit')")
    @RepeatSubmit()
    @PutMapping()
    public AjaxResult<Void> edit(@Validated(EditGroup.class) @RequestBody AppBuildMissionBo bo) {
        return toAjax(iAppBuildMissionService.updateByBo(bo) ? 1 : 0);
    }

    /**
     * 删除应用构建任务
     */
    @PreAuthorize("@ss.hasPermi('build:buildMission:remove')")
    @DeleteMapping("/{IDs}")
    public AjaxResult<Void> remove(
                                       @NotEmpty(message = "主键不能为空")
                                       @PathVariable Long[] IDs) {
        return toAjax(iAppBuildMissionService.deleteWithValidByIds(Arrays.asList(IDs), true) ? 1 : 0);
    }
}
