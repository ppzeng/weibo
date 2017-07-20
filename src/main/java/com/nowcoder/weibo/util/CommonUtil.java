package com.nowcoder.weibo.util;


import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.dom4j.*;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.List;

/**
 * Created by now on 2017/7/19.
 */
class User{
    int age;
    String name;
    public User(int age,String name){
        this.age=age;
        this.name=name;
    }
}
public class CommonUtil {

    public static void print(int index,Object obj) {
        System.out.println(String.format("{%d},{%s}",index,String.valueOf(obj)));
    }
    public static void StringUtil() {
        print(1, StringUtils.isAllBlank(new String[]{" ","\t"}));
        print(2, StringUtils.compare("hello","hai"));
        print(3, StringUtils.countMatches("hello","l"));
        print(4, StringUtils.join(new int[]{3,4,5},'-'));
        print(5,StringUtils.join(new String[]{"hello","little","girl"},"**"));
        print(6,StringUtils.remove("sunshine","s"));
        print(7,StringUtils.leftPad("0.01",7,"#"));
        print(8,StringUtils.deleteWhitespace("hello little girl"));
        print(9, ToStringBuilder.reflectionToString(new User(11,"lili"), ToStringStyle.JSON_STYLE));

    }

    public static void DatetimeUtil() throws Exception {
        Date date= DateUtils.parseDate("2017-7-19","yyyy-MM-dd");
        print(1,date);
        print(2, DateFormatUtils.format(date,"yyyy:MM:dd"));
        String str="2017-7-19 15:20";
        date=DateUtils.parseDate(str,"yyyy-MM-dd hh:mm","yyyy-MM-dd hh-mm");
        print(3,date);
        print(4,DateFormatUtils.format(date,"yyyy**MM**dd mm@@ss"));
        print(5,DateFormatUtils.format(999999669,"yyyy$$MM$$dd mm@@ss"));
        DateUtils.addDays(date,7);
        print(6,DateFormatUtils.format(date,"yyyy##MM##dd"));

    }
    public static void printNode(Element node,int num){
        for(int i=0;i<num;i++) System.out.print("    ");
        System.out.print("<" + node.getName());
        List<Attribute> listAttr = node.attributes();
        for (Attribute attr : listAttr) {
            String name = attr.getName();
            String value = attr.getValue();
            System.out.print(" " + name + ":" + value);
        }
        System.out.print(">");

        if(node.getText().trim().isEmpty())
            System.out.println("");
        else
                System.out.print(node.getText());
        List<Element> listElement = node.elements();
        for (Element e : listElement) {
            printNode(e,num+1);// 递归
        }
        //for(int i=0;i<num;i++) System.out.print("    ");
        System.out.print("</" + node.getName());
        System.out.println(">");

    }


    public static void XMLUtil(){
        File file=new File("C:\\pom.xml");
        FileInputStream fis = null;
        try {
            fis  = new FileInputStream(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        SAXReader saxReader = new SAXReader();
        Document doc =null;
        try {
            doc = saxReader.read(fis);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        Element node=doc.getRootElement();
        //printNode(node,0);

        Element subNode=DocumentHelper.createElement("subNode");
        subNode.setText("\nsubValue\n\t");
        node.add(subNode);
        printNode(node,0);

        }


    public static void OfficeUtil() throws Exception{
        Workbook workbook=new HSSFWorkbook();
        Sheet sheet=workbook.createSheet("test");
        Row row=sheet.createRow(3);
        Cell cell= row.createCell(3);
        cell.setCellValue(3.1);
        cell.setCellType(CellType.NUMERIC);

        row = sheet.createRow(5);
        cell = row.createCell(2);
        cell.setCellValue("we");

        workbook.write(new FileOutputStream("camp.xls"));




    }
    public static void PDFUtil() {

    }

    public static void main(String[] args) throws Exception {
        //StringUtil();
        //DatetimeUtil();
        //XMLUtil();
        OfficeUtil();
    }
}
