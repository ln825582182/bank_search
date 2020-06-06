package com.yangl.wikisearch;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Create by cary_may on 2020/6/6.
 */
@SpringBootApplication
@RestController
//@RequestMapping(value = "/search")
public class SearchController {

    @RequestMapping(value = "/list")
    public String searchByKey(){
        return "hello";
    }
//    https://www.jianshu.com/p/d1a4a4a1716a
}
