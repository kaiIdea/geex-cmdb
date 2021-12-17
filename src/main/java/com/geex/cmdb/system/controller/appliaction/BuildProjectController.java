package com.geex.cmdb.system.controller.appliaction;

import com.geex.cmdb.common.core.domain.AjaxResult;
import com.geex.cmdb.system.domain.bo.AppBuildMissionBo;
import com.geex.cmdb.system.domain.bo.AppBuiltDetailsBo;
import com.geex.cmdb.system.domain.bo.BuildImageBo;
import com.geex.cmdb.system.service.IAppBuiltDetailsService;
import com.geex.cmdb.system.service.IBuildImageService;
import com.geex.cmdb.system.service.IJenkinsApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;



/**
 * @Auther: GEEX1428
 * @Date: 2021/12/16 14:37
 * @Description: TODO
 */
@RestController
@RequestMapping("/build")
@Slf4j
public class BuildProjectController {

    @Autowired
    IBuildImageService buildImageService;

    @Autowired
    IAppBuiltDetailsService appBuiltDetailsService;

    @Autowired
    IJenkinsApiService jenkinsApiService;


    @PostMapping(value = "/create")
    public AjaxResult image(@RequestBody BuildImageBo imageBo){
        System.out.println(imageBo);
        buildImageService.createBuild(imageBo);
        return AjaxResult.success();
    }


    @PostMapping(value = "/image")
    public AjaxResult buildImage(@RequestBody AppBuildMissionBo missionBo){
        System.out.println(missionBo);
        buildImageService.buildImage(missionBo);
        return AjaxResult.success();
    }



    @GetMapping(value = "/getLastBranch/{nameSpace}/{projectName}")
    public AjaxResult getLastBranch(@PathVariable("nameSpace")String nameSpace,@PathVariable("projectName")String projectName){
        return AjaxResult.success("SUCCESS",buildImageService.getLastBuildMessage(nameSpace,projectName));
    }

    @PostMapping(value = "/queryBuildResult")
    public AjaxResult queryBuildResult(@RequestBody AppBuiltDetailsBo detailsBo){
        System.out.println(detailsBo);
        try {
            buildImageService.queryBuildResult(detailsBo);
        } catch (Exception e) {
            return AjaxResult.error(e.getMessage());
        }
        return AjaxResult.success();
    }


    @GetMapping(value = "/getBuildDetailList/{id}")
    public AjaxResult getBuildDetailList(@PathVariable("id")String id){
        AppBuiltDetailsBo bo = new AppBuiltDetailsBo();
        bo.setBuildId(Long.parseLong(id));
        return AjaxResult.success(appBuiltDetailsService.queryList(bo));
    }

    @GetMapping(value = "/getLookLog/{id}/{jobName}")
    public AjaxResult getLookLog(@PathVariable("id")String id,@PathVariable("jobName")String jobName){
        try {
            String log = jenkinsApiService.buildConsoleOutput(Long.parseLong(id),jobName);
            return AjaxResult.success(null,log);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return AjaxResult.success();
    }
}
