package com.geex.cmdb.system.domain.bo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Auther: GEEX1428
 * @Date: 2021/12/16 14:42
 * @Description: TODO
 */
@Data
public class BuildImageBo implements Serializable {

    private String[] selectAppValue;
    private String appBranch;
    private String commit;
    private String subProject;
    private String port;
    private String xxlPort;
    private String profiles;
    private String textareaAlpha;
    private String textareaBeta;
    private String textareaProd;
}
