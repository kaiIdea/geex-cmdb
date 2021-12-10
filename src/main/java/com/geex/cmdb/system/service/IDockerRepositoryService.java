package com.geex.cmdb.system.service;

import com.geex.cmdb.system.domain.harbor.HarborImage;
import com.geex.cmdb.system.domain.harbor.HarborProject;
import com.geex.cmdb.system.domain.harbor.HarborRepositories;

import java.util.List;

/**
 * @Auther: GEEX1428
 * @Date: 2021/12/6 10:32
 * @Description: docker 仓库业务操作接口
 */
public interface IDockerRepositoryService {


    //获取项目列表
    List<HarborProject> queryProjectList();

    //用户信息
    Integer getHarborUser(String userName);

    /**
     * 根据项目名称，获取仓库列表，支持模糊匹配
     * @param projectName
     * @param blurMatch 仓库名称模糊匹配
     * @return
     */
    List<HarborRepositories> queryRepositories(String projectName,String blurMatch);

    /**
     * 查询镜像列表，支持模糊匹配
     * @param projectName
     * @param repositoriesName
     * @param blurMatch tag
     * @return
     */
    List<HarborImage> queryImage(String projectName,String repositoriesName,String blurMatch);


}
