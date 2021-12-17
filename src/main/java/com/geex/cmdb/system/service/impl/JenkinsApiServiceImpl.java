package com.geex.cmdb.system.service.impl;

import com.geex.cmdb.common.utils.BaseResult;
import com.geex.cmdb.system.service.IJenkinsApiService;
import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.model.BuildWithDetails;
import com.offbytwo.jenkins.model.QueueItem;
import com.offbytwo.jenkins.model.QueueReference;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

/**
 * @Auther: GEEX1428
 * @Date: 2021/12/15 15:44
 * @Description: TODO
 */
@Service
@Slf4j
public class JenkinsApiServiceImpl implements IJenkinsApiService {

    private static JenkinsServer jenkinsServer = null;

    public static String JENKINS_URL = "http://192.168.113.180:30080/";

    private static String USER_NAME = "yangkaikai";

    private static String TOKEN = "1170c3b7f41cf19817db67fad9ebe89890";

    @Override
    public BaseResult buildProject(Map<String, String> params) throws Exception {
        if(!params.containsKey("jobName")){
            return BaseResult.fail("0001","任务名称不能为空！");
        }
        if(!params.containsKey("branch")){
            return BaseResult.fail("0001","分支名称不能为空！");
        }
        String jobName = params.get("jobName");
        params.remove("jobName");
        QueueItem queueItem = apiBuild(params,jobName);
        if(null == queueItem || null == queueItem.getId()){
            return BaseResult.fail("0002","构建失败，请稍后再试");
        }
        return BaseResult.success("0000","处理中",queueItem.getExecutable().getNumber());
    }


    public QueueItem apiBuild(Map<String, String> params,String jobName) throws Exception {
        try {
            createClient();
        } catch (URISyntaxException e) {
            throw e;
        }
        QueueReference queueReference = jenkinsServer.getJob(jobName).build(params);

        System.out.println("queueItem.getId()=" +jenkinsServer.getQueueItem(queueReference).getId());
        QueueItem queueItem = jenkinsServer.getQueueItem(queueReference);
        System.out.println("queueItem.getId()=" + queueItem.getId());
        while (null != queueItem && queueItem.getExecutable() == null) {
            Thread.sleep(1000);
            queueItem = jenkinsServer.getQueueItem(queueReference);
        }
        return queueItem;
    }

    //组装jenkins请求连接信息
    public static void createClient() throws URISyntaxException {
        if(null == jenkinsServer){
            jenkinsServer =  new JenkinsServer(new URI(JENKINS_URL),USER_NAME,TOKEN);
        }
    }
    @Override
    public String buildConsoleOutput(Long buildnumber,String jobName) throws Exception {
        return getBuildInfo(buildnumber, jobName).getConsoleOutputText();
    }

    public String getConsoleOutputHtml(Long buildnumber,String jobName) throws Exception {
        return getBuildInfo(buildnumber, jobName).getConsoleOutputHtml();
    }

    //获取构建详情信息
    public BuildWithDetails getBuildInfo(Long buildnumber,String jobName) throws Exception {
        createClient();
        return jenkinsServer.getJob(jobName).getBuildByNumber(buildnumber.intValue()).details();
    }
}
