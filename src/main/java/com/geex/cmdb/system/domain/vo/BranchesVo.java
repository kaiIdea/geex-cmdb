package com.geex.cmdb.system.domain.vo;

import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;

/**
 * @Auther: GEEX1428
 * @Date: 2021/12/14 18:31
 * @Description: TODO
 */
@Data
@Builder
public class BranchesVo implements Serializable {

    private String branchName;

    private String message;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    private String createDate;



    @DateTimeFormat(pattern="yyyy-MM-dd")
    private String updateDate;
}
