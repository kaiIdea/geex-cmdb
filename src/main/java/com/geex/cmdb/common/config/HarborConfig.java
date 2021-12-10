package com.geex.cmdb.common.config;

import org.springframework.context.annotation.Configuration;

/**
 * @Auther: GEEX1428
 * @Date: 2021/12/6 15:01
 * @Description: TODO
 */
@Configuration
public class HarborConfig {

    private static String BASE_URL = "http://192.168.4.50/api/v2.0/";

    private static String QUERY_PROJECT_LIST = BASE_URL+"projects?page=1&page_size=100";

    private static String QUERY_REPOSITORIES = BASE_URL+"projects/%s/repositories?page_size=100&page=1";

    private static String QUERY_USER = BASE_URL+"users?username=%s";

    private static String QUERY_IMAGE = BASE_URL+"projects/%s/repositories/%s/artifacts";


    public String getQueryProjectList(){
        return QUERY_PROJECT_LIST;
    }

    public String getQueryRepositories(String projectName){
        return String.format(QUERY_REPOSITORIES,projectName);
    }

    public String getQueryImage(String projectName,String repositories){
        return String.format(QUERY_IMAGE,projectName,repositories);
    }
    public String getQueryUser(String userName){
        return String.format(QUERY_USER,userName);
    }
}
