package com.yangl.wikisearch;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.ooxml.extractor.POIXMLTextExtractor;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.*;




/**
 * Create by cary_may on 2020/6/8.
 */
public class FileExactUtil {

    public static String readPDF(String fileName) throws Exception {
        File file = new File(fileName);
        PDDocument document = PDDocument.load(file);
        PDFTextStripper pdfStripper = new PDFTextStripper();
        String content = pdfStripper.getText(document);
        return content;
    }

    public static String readWord(String filename) {
//         String path = "D:\\temp\\temp\\test.doc";
        String path = filename;
        String content = null;
        File file = new File(path);
        if (file.exists() && file.isFile()) {
            InputStream is = null;
            HWPFDocument doc = null;
            XWPFDocument docx = null;
            POIXMLTextExtractor extractor = null;
            WordExtractor wordExtractor=null;
            try {
                is = new FileInputStream(file);
                if (path.endsWith(".doc")) {
                    doc = new HWPFDocument(is);
                    wordExtractor=new WordExtractor(doc);
                    content=wordExtractor.getText();

                    // 文档文本内容
//                    System.out.println(content);

                    // 文档图片内容
//                    PicturesTable picturesTable = doc.getPicturesTable();
//                    List<Picture> pictures = picturesTable.getAllPictures();
//                    for (Picture picture : pictures) {
//                        // 输出图片到磁盘
//                        OutputStream out = new FileOutputStream(
//                                new File("D:\\temp\\" + UUID.randomUUID() + "." + picture.suggestFileExtension()));
//                        picture.writeImageContent(out);
//                        out.close();
//                    }
                } else if (path.endsWith("docx")) {
                    docx = new XWPFDocument(is);
                    extractor = new XWPFWordExtractor(docx);

                    // 文档文本内容
                    content = extractor.getText();

//                    // 文档图片内容
//                    List<XWPFPictureData> pictures = docx.getAllPictures();
//                    for (XWPFPictureData picture : pictures) {
//                        byte[] bytev = picture.getData();
//                        // 输出图片到磁盘
//                        FileOutputStream out = new FileOutputStream(
//                                "D:\\temp\\temp\\" + UUID.randomUUID() + picture.getFileName());
//                        out.write(bytev);
//                        out.close();
//                    }
                } else {
                    System.out.println("此文件不是word文件！");
                }
            } catch (FileNotFoundException e) {
            } catch (IOException e) {
            } finally {
                try {
                    if (doc != null) {
                        doc.close();
                    }
                    if (extractor != null) {
                        extractor.close();
                    }
                    if (docx != null) {
                        docx.close();
                    }
                    if (is != null) {
                        is.close();
                    }
                } catch (IOException e) {
                }
            }
        }
        System.out.println(content);
        return content;

    }



    /**
     * 测试pdf文件的创建
     * @param args
     */
    public static void main(String[] args) throws Exception{

//        String fileName = "F:\\开发\\solr\\04.参考复习篇\\01.solr精选学习资料\\J2EE武功秘籍.pdf";  //这里先手动把绝对路径的文件夹给补上。
//        FileExactUtil.readPDF(fileName);
        String file="C:\\Users\\yl\\Desktop\\工作\\会议纪要-P2001批次“出行平台”性能测试阶段工作总结.doc";
        System.out.println(FileExactUtil.readWord(file));
    }
}
