package com.yangl.wikisearch;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Create by cary_may on 2020/6/8.
 */
public class SolrUtil {
    //solr服务器所在的地址，core0为自己创建的文档库目录
    private final static String SOLR_URL = "http://localhost:8983/solr/file_function";
    private static HttpSolrClient solr;
    @Value("${filePath}")
    private static String filePath;
    @Value("${filePath}")
    private String a;
    private static ArrayList<String> fileList = new ArrayList<>();
    private SolrUtil(){}


    /**
     * 获取客户端的连接
     *
     * @return
     */
    private static void createSolrServer() {
        solr = new HttpSolrClient.Builder(SOLR_URL).withConnectionTimeout(10000).withSocketTimeout(60000).build();
    }

    /**
     * 往索引库添加文档
     *
     * @throws SolrServerException
     * @throws IOException
     */
    public static void addDoc(String filePath) throws Exception {
        if (solr==null){
            SolrUtil.createSolrServer();
        }
        String fileName=filePath.substring(filePath.lastIndexOf("\\")+1);
        SolrInputDocument document = new SolrInputDocument();
        String content = null;
        Random random=new Random();
        if (filePath.endsWith(".pdf")){
            content = FileExactUtil.readPDF(filePath);
        }else if (filePath.endsWith(".doc")||filePath.endsWith(".docx")){
            content = FileExactUtil.readWord(filePath);
        }
        if (content!=null){
            document.addField("id",random.nextInt());
            document.addField("fileName", fileName);
            document.addField("filePath", filePath);
            document.addField("content", content);
            solr.add(document);
            solr.commit();
//            solr.close();
            System.out.println(fileName +"添加成功");
        }
    }

    /**
     * 根据ID从索引库删除文档
     *
     * @throws SolrServerException
     * @throws IOException
     */
    public static void deleteDoctById(String id) throws SolrServerException, IOException {
        if (solr==null){
            SolrUtil.createSolrServer();
        }
        solr.deleteById(id);
        solr.commit();
//        solr.close();

    }

    /**
     * 根据ID从索引库删除文档
     *
     * @throws SolrServerException
     * @throws IOException
     */
    public static void deleteAllDoc() throws SolrServerException, IOException {
        if (solr==null){
            SolrUtil.createSolrServer();
        }
        String query="*:*";
        solr.deleteByQuery(query);
        solr.commit();
//        solr.close();
    }

    /**
     * 根据设定的查询条件进行文档字段的查询
     * @throws Exception
     */
    public static List<FileBO> querySolr(String key) throws Exception {
        if (solr==null){
            SolrUtil.createSolrServer();
        }

        SolrQuery query = new SolrQuery();

        //下面设置solr查询参数

        query.set("q", key);// 参数q  查询所有
//        query.set("q", "content:需求");//相关查询，比如某条数据某个字段含有周、星、驰三个字  将会查询出来 ，这个作用适用于联想查询

        //参数fq, 给query增加过滤查询条件
//        query.addFacetQuery("id:[0 TO 9]");
//        query.addFilterQuery("description:一个逗比的码农");

        //参数df,给query设置默认搜索域，从哪个字段上查找
//        query.set("df", "name");

        //参数sort,设置返回结果的排序规则
//        query.setSort("id",SolrQuery.ORDER.desc);

        //设置分页参数
//        query.setStart(0);
//        query.setRows(10);

        //设置高亮显示以及结果的样式
        query.setHighlight(true);
        query.addHighlightField("fileName");
        query.setHighlightSimplePre("<font color='red'>");
        query.setHighlightSimplePost("</font>");

        //执行查询
        QueryResponse response = solr.query(query);

        //获取返回结果
        SolrDocumentList resultList = response.getResults();

        for(SolrDocument document: resultList){
            System.out.println("id:"+document.get("id")+"   document:"+document.get("fileName"));
        }

        //获取实体对象形式
        List<FileBO> fileBOList = response.getBeans(FileBO.class);
        return fileBOList;
    }

    public static  void listAllFile(String path, ArrayList<String> list){
        File filePath=new File(path);
        if (filePath.isDirectory()){
            File[] allFIle = filePath.listFiles();
            for (File file:allFIle){
                listAllFile(file.getPath(),list);
            }
        }else {
            list.add(filePath.getPath());
        }
    }

    public static void init() throws Exception{
        deleteAllDoc();
        String path="C:\\Users\\yl7284\\Desktop\\testDOC";
//        System.out.println(filePath);
        listAllFile(path,fileList);
        for (String filePath :fileList){
            addDoc(filePath);
            Thread.sleep(300);
        }
//        System.out.println(fileList.toString());

    }

    public static void main(String[] args) throws Exception {
//        SolrUtil.addDoc("C:\\Users\\yl7284\\Desktop\\testDOC\\doc2.docx");
//        solr.querySolr();
//        solr.deleteDoctById("1111");
        SolrUtil.init();
//        System.out.println(a);
//        String filePath=" G:\\刷机\\sony解锁工具\\驱动\\32位系统\\i386\\WdfCoInstaller01009.dll";
//        String fileName=filePath.substring(filePath.lastIndexOf("\\")+1);
//        System.out.println(fileName);
//        SolrUtil.deleteAllDoc();
    }

}
