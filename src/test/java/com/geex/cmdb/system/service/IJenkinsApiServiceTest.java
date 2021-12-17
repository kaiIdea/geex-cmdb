package com.geex.cmdb.system.service;

import com.geex.cmdb.common.utils.BaseResult;
import com.geex.cmdb.system.domain.bo.AppBuiltDetailsBo;
import com.offbytwo.jenkins.model.QueueItem;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Auther: GEEX1428
 * @Date: 2021/12/15 16:06
 * @Description: TODO
 */
@RunWith(SpringRunner.class)
@SpringBootTest
class IJenkinsApiServiceTest {


    @Autowired
    IJenkinsApiService jenkinsApiService;

    @Autowired
    IBuildImageService buildImageService;


    @Test
    public void buildProject() throws Exception {
        Map<String, String> params = new HashMap<>();
        params.put("jobName","geex-check-provider");
        params.put("branch","origin/release20211221");
        BaseResult result = jenkinsApiService.buildProject(params);
        System.out.println();
    }

    @Test
    public void buildConsoleOutput() throws Exception {
        String result = jenkinsApiService.buildConsoleOutput(277L,"geex-check-provider");
        System.out.println();
    }


    @Test
    public void queryBuildResult() throws Exception {
        AppBuiltDetailsBo appBuiltDetailsBo = new AppBuiltDetailsBo();
        appBuiltDetailsBo.setProcessId(277);
        appBuiltDetailsBo.setBuildName("geex-check-provider");
        buildImageService.queryBuildResult(appBuiltDetailsBo);
    }
}
