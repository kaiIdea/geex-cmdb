package com.geex.cmdb.system.controller.system;

import com.geex.cmdb.common.annotation.Log;
import com.geex.cmdb.common.constant.UserConstants;
import com.geex.cmdb.common.core.controller.BaseController;
import com.geex.cmdb.common.core.domain.AjaxResult;
import com.geex.cmdb.common.core.page.TableDataInfo;
import com.geex.cmdb.common.enums.BusinessType;
import com.geex.cmdb.common.utils.poi.ExcelUtil;
import com.geex.cmdb.system.domain.SysPost;
import com.geex.cmdb.system.service.ISysPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 岗位信息操作处理
 *
 * @author Lion Li
 */
@Validated
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/system/post")
public class SysPostController extends BaseController {

    private final ISysPostService postService;

    /**
     * 获取岗位列表
     */
    //@ApiOperation("获取岗位列表")
    @PreAuthorize("@ss.hasPermi('system:post:list')")
    @GetMapping("/list")
    public TableDataInfo<SysPost> list(SysPost post) {
        return postService.selectPagePostList(post);
    }

    //@ApiOperation("导出岗位列表")
    @Log(title = "岗位管理", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('system:post:export')")
    @PostMapping("/export")
    public void export(SysPost post, HttpServletResponse response) {
        List<SysPost> list = postService.selectPostList(post);
        ExcelUtil.exportExcel(list, "岗位数据", SysPost.class, response);
    }

    /**
     * 根据岗位编号获取详细信息
     */
    //@ApiOperation("根据岗位编号获取详细信息")
    @PreAuthorize("@ss.hasPermi('system:post:query')")
    @GetMapping(value = "/{postId}")
    public AjaxResult<SysPost> getInfo( @PathVariable Long postId) {
        return AjaxResult.success(postService.selectPostById(postId));
    }

    /**
     * 新增岗位
     */
    //@ApiOperation("新增岗位")
    @PreAuthorize("@ss.hasPermi('system:post:add')")
    @Log(title = "岗位管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult<Void> add(@Validated @RequestBody SysPost post) {
        if (UserConstants.NOT_UNIQUE.equals(postService.checkPostNameUnique(post))) {
            return AjaxResult.error("新增岗位'" + post.getPostName() + "'失败，岗位名称已存在");
        } else if (UserConstants.NOT_UNIQUE.equals(postService.checkPostCodeUnique(post))) {
            return AjaxResult.error("新增岗位'" + post.getPostName() + "'失败，岗位编码已存在");
        }
        return toAjax(postService.insertPost(post));
    }

    /**
     * 修改岗位
     */
    //@ApiOperation("修改岗位")
    @PreAuthorize("@ss.hasPermi('system:post:edit')")
    @Log(title = "岗位管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult<Void> edit(@Validated @RequestBody SysPost post) {
        if (UserConstants.NOT_UNIQUE.equals(postService.checkPostNameUnique(post))) {
            return AjaxResult.error("修改岗位'" + post.getPostName() + "'失败，岗位名称已存在");
        } else if (UserConstants.NOT_UNIQUE.equals(postService.checkPostCodeUnique(post))) {
            return AjaxResult.error("修改岗位'" + post.getPostName() + "'失败，岗位编码已存在");
        }
        return toAjax(postService.updatePost(post));
    }

    /**
     * 删除岗位
     */
    //@ApiOperation("删除岗位")
    @PreAuthorize("@ss.hasPermi('system:post:remove')")
    @Log(title = "岗位管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{postIds}")
    public AjaxResult<Void> remove( @PathVariable Long[] postIds) {
        return toAjax(postService.deletePostByIds(postIds));
    }

    /**
     * 获取岗位选择框列表
     */
    //@ApiOperation("获取岗位选择框列表")
    @GetMapping("/optionselect")
    public AjaxResult<List<SysPost>> optionselect() {
        List<SysPost> posts = postService.selectPostAll();
        return AjaxResult.success(posts);
    }
}
