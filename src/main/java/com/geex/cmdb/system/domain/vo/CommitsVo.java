package com.geex.cmdb.system.domain.vo;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @Auther: GEEX1428
 * @Date: 2021/12/14 19:42
 * @Description: TODO
 */
@Data
@Builder
public class CommitsVo implements Serializable {

    private String messageCommit;
    private String messageAllCommit;
    private String authorName;
    private String committedDate;
}
