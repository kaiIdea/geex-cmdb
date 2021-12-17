package com.geex.cmdb.system.domain.bo;

import com.geex.cmdb.common.core.domain.BaseEntity;
import com.geex.cmdb.common.core.validate.AddGroup;
import com.geex.cmdb.common.core.validate.EditGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;
import java.util.Date;


/**
 * 应用构建详情业务对象 app_built_details
 *
 * @author kk
 * @date 2021-12-17
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class AppBuiltDetailsBo extends BaseEntity {

    /**
     *
     */
    @NotNull(message = "不能为空", groups = { EditGroup.class })
    private Long id;

    private String buildName;

    private Date buildTime;

    private String imageTag;

    /**
     * 构建任务Id
     */
    @NotNull(message = "构建任务Id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long buildId;

    /**
     * 构建状态，0初始化，1成功，2失败，3异常
     */
    @NotNull(message = "构建状态，0初始化，1成功，2失败，3异常不能为空", groups = { AddGroup.class, EditGroup.class })
    private Integer buildResult;

    /**
     * 是否提交验收，0未提交，1已提交
     */
    @NotNull(message = "是否提交验收，0未提交，1已提交不能为空", groups = { AddGroup.class, EditGroup.class })
    private Integer acceptCheck;

    /**
     * 构建进程ID
     */
    @NotNull(message = "构建进程ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Integer processId;

    /**
     * 提交验收时间
     */
    @NotNull(message = "提交验收时间不能为空", groups = { AddGroup.class, EditGroup.class })
    private Date acceptCheckTime;

    /**
     * 审核状态，0待审核，1审核中，2审核成功，3审核失败
     */
    @NotNull(message = "审核状态，0待审核，1审核中，2审核成功，3审核失败不能为空", groups = { AddGroup.class, EditGroup.class })
    private Integer reviewStatus;

    /**
     * 备注
     */
    @NotBlank(message = "备注不能为空", groups = { AddGroup.class, EditGroup.class })
    private String remark;


    /**
     * 分页大小
     */
    private Integer pageSize;

    /**
     * 当前页数
     */
    private Integer pageNum;

    /**
     * 排序列
     */
    private String orderByColumn;

    /**
     * 排序的方向desc或者asc
     */
    private String isAsc;

}
