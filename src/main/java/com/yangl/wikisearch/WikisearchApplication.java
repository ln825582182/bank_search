package com.yangl.wikisearch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class WikisearchApplication {

	public static void main(String[] args) {
		try {
			SolrUtil.init();
		} catch (Exception e) {
			e.printStackTrace();
		}
		SpringApplication.run(WikisearchApplication.class, args);
	}



}
