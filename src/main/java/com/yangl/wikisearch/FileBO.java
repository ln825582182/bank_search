package com.yangl.wikisearch;

import org.apache.solr.client.solrj.beans.Field;

/**
 * Create by cary_may on 2020/6/8.
 */
public class FileBO {
    @Field
    private String id;
    @Field
    private String fileName;
    @Field
    private String content;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    //    private String id;
//    private String id;
//    private String id;
}
