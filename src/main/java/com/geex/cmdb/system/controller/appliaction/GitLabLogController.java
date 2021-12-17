package com.geex.cmdb.system.controller.appliaction;

import com.geex.cmdb.system.domain.vo.BranchesVo;
import com.geex.cmdb.system.domain.vo.CommitsVo;
import com.geex.cmdb.system.service.IGitLabLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @Auther: GEEX1428
 * @Date: 2021/12/14 14:48
 * @Description: TODO
 */
@RestController
@RequestMapping("/gitlab")
@Slf4j
public class GitLabLogController {

    @Autowired
    private IGitLabLogService gitLabLogService;

    @GetMapping(value = "/getNameSpaceAndProject")
    public List<Map<String,Object>> getNameSpaceAndProject(){
        return gitLabLogService.getNameSpaceAndProject();
    }

    @GetMapping(value = "/getBranches/{namespace}/{projectName}")
    public List<BranchesVo> getBranches(@PathVariable("namespace")String namespace, @PathVariable("projectName")String projectName){
        return gitLabLogService.getBranches(namespace,projectName);
    }

    @GetMapping(value = "/getCommits/{namespace}/{projectName}/{branchName}")
    public List<CommitsVo> getCommits(@PathVariable("namespace")String namespace,
                                      @PathVariable("projectName")String projectName,
                                      @PathVariable("branchName")String branchName){
        return gitLabLogService.getCommits(branchName,new String[]{namespace,"/",projectName});
    }
}
