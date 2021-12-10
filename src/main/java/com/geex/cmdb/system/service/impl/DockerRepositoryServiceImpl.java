package com.geex.cmdb.system.service.impl;

import com.geex.cmdb.common.config.HarborConfig;
import com.geex.cmdb.common.utils.StringUtils;
import com.geex.cmdb.system.domain.harbor.HarborImage;
import com.geex.cmdb.system.domain.harbor.HarborProject;
import com.geex.cmdb.system.domain.harbor.HarborRepositories;
import com.geex.cmdb.system.domain.harbor.HarborUser;
import com.geex.cmdb.system.service.IDockerRepositoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

/**
 * @Auther: GEEX1428
 * @Date: 2021/12/6 10:36
 * @Description: TODO
 */
@Service
@Slf4j
public class DockerRepositoryServiceImpl implements IDockerRepositoryService {

    private static String harborUserName = "admin";
    private static String harborPassWord = "Harbor12345";

    private static RestTemplate restTemplate = new RestTemplate();

    @Autowired
    HarborConfig harborConfig;

    public List<HarborProject> queryProjectList(){
        //提交请求
        ResponseEntity<String> resp = restTemplate.exchange(harborConfig.getQueryProjectList(), HttpMethod.GET, getHttpEntity(), new ParameterizedTypeReference<String>(){});
        if(null == resp || 200!= resp.getStatusCodeValue()){
            log.info("获取项目列表失败："+harborUserName);
            return Collections.emptyList();
        }
        log.info("项目列表body:"+resp.getBody());
        return HarborProject.getHarborProject(resp.getBody());
    }

    /**
     * 创建用户请求head
     * @return
     */
    public HttpHeaders createHeaders() {
        return new HttpHeaders() {
            {
                String auth = harborUserName + ":" + harborPassWord;
                String authHeader = "Basic " + Base64.getEncoder().encodeToString(auth.getBytes());
                set("Authorization", authHeader);
            }
        };
    }

    public HttpEntity<String> getHttpEntity(){
        HttpHeaders headers = createHeaders();
        //拼接参数和header
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);
        return httpEntity;
    }

    public Integer getHarborUser(String userName){
        //提交请求
        ResponseEntity<String> resp = restTemplate.exchange(harborConfig.getQueryUser(userName), HttpMethod.GET, getHttpEntity(), new ParameterizedTypeReference<String>(){});
        if(null == resp || 200!= resp.getStatusCodeValue()){
            log.info("获取Harbor用户失败："+harborUserName);
            return null;
        }
        log.info("Harbor用户body:"+resp.getBody());
        HarborUser user = HarborUser.getHarborUserMessage(resp.getBody());
        return null != user?user.getUserId():null;
    }

    @Override
    public List<HarborRepositories> queryRepositories(String projectName,String blurMatch) {
        String url = harborConfig.getQueryRepositories(projectName);
        if(StringUtils.isNotEmpty(blurMatch)){
            url = url+"&q=name=~"+blurMatch;
            log.info("queryRepositories模糊匹配Url:"+url);
        }
        //提交请求
        ResponseEntity<String> resp = restTemplate.exchange(url, HttpMethod.GET, getHttpEntity(), new ParameterizedTypeReference<String>(){});
        if(null == resp || 200!= resp.getStatusCodeValue()){
            log.info("根据projectName获取项目失败："+projectName);
        }
        log.info("根据projectName获取项目body:"+resp.getBody());
        return HarborRepositories.getHarborRepositories(resp.getBody());
    }

    @Override
    public List<HarborImage> queryImage(String projectName,String repositoriesName,String blurMatch) {
        String url = harborConfig.getQueryImage(projectName,repositoriesName);
        if(StringUtils.isNotEmpty(blurMatch)){
            url = url+"?q=tags=~"+blurMatch;
            log.info("queryImage模糊匹配Url:"+url);
        }
        //提交请求
        ResponseEntity<String> resp = restTemplate.exchange(url, HttpMethod.GET, getHttpEntity(), new ParameterizedTypeReference<String>(){});
        if(null == resp || 200!= resp.getStatusCodeValue()){
            log.info("获取镜像列表失败："+projectName);
        }
        log.info("获取镜像列表body:"+resp.getBody());
        return HarborImage.getHarborImage(resp.getBody());
    }
}
