package com.geex.cmdb.system.service;

import com.geex.cmdb.system.domain.harbor.HarborImage;
import com.geex.cmdb.system.domain.harbor.HarborProject;
import com.geex.cmdb.system.domain.harbor.HarborRepositories;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;


/**
 * @Auther: GEEX1428
 * @Date: 2021/12/6 15:42
 * @Description: TODO
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class IDockerRepositoryServiceTest {

    @Resource
    IDockerRepositoryService dockerRepositoryService;

    @Test
    public void queryProject() {
        List<HarborProject> list = dockerRepositoryService.queryProjectList();
        System.out.println();
    }

    @Test
    public void getHarborUser() {
        Integer userId = dockerRepositoryService.getHarborUser("yangkaikai");
        System.out.println();
    }

    @Test
    public void queryProjectByName() {
        List<HarborRepositories> list = dockerRepositoryService.queryRepositories("alpha","check");
        System.out.println();
    }

    @Test
    public void queryImage() {
        List<HarborImage> list = dockerRepositoryService.queryImage("alpha","geex-check-provider_release20211221",null);
        System.out.println();
    }
}
