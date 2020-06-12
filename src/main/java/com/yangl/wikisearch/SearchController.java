package com.yangl.wikisearch;

import com.alibaba.fastjson.JSON;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Create by cary_may on 2020/6/6.
 * //    https://www.jianshu.com/p/d1a4a4a1716a
 */
@SpringBootApplication
@RestController
//@RequestMapping(value = "/search")
public class SearchController {

    @RequestMapping(value = "/list" )
    public String searchByKey(String key)throws Exception{
        if (key==null ||key.equals("")){
            key="*:*";
        }
        List<FileBO> fileBOList = SolrUtil.querySolr(key);
        return JSON.toJSONString(fileBOList);
    }

}
