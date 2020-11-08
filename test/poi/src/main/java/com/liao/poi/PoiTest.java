/*
package com.liao.poi;

import com.sun.org.apache.bcel.internal.generic.NEW;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.format.CellDateFormatter;
import org.apache.poi.ss.format.CellNumberFormatter;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellUtil;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFFormulaEvaluator;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.helpers.XSSFFormulaUtils;
import org.joda.time.DateTime;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class PoiTest {
    static final String PATH = "D:/git_project/test/poi/";

    @Test
    public void test(){
        PoiTest poiTest = new PoiTest();
        //03版最大写入 65535  java.lang.IllegalArgumentException: Invalid row number (65536) outside allowable range (0..65535)
        //poiTest.write(new HSSFWorkbook(), "我的03版.xls");
        //poiTest.write(new XSSFWorkbook(), "我的07版.xlsx");
        //poiTest.write(new SXSSFWorkbook(), "我的升级版07版.xlsx");
        //poiTest.read("测试读.xlsx");
        add("求和.xlsx");
    }

    */
/**
     * 写
     * @param book HSSFWorkbook   XSSFWorkbook
     * @param fileName  文件名称
     *//*

    public void write(Workbook book, String fileName){
        long begin = System.currentTimeMillis();
        FileOutputStream fileOutputStream = null;
        Workbook workbook = null;
        try{
            //工作簿
            workbook = book;
            //sheet
            Sheet sheet = workbook.createSheet("我的工作本");
            for (int i = 0; i < 100000; i++) {
                //行
                Row row = sheet.createRow(i);
                for (int y = 0; y < 10; y++) {
                    //列
                    Cell cell = row.createCell(y);
                    cell.setCellValue(y + "----" + new DateTime().toString("yyyy-MM-dd HH:mm:ss"));
                }
            }
            //文件输出流
            fileOutputStream = new FileOutputStream(PATH + fileName);
            //写入excel
            workbook.write(fileOutputStream);
        }catch (Exception e){
            e.printStackTrace();
        } finally {
            // 关闭输出流
            if(null != fileOutputStream){
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            long end = System.currentTimeMillis();
            //关闭临时文件，SXSSF需要注意
            if(null != workbook && book instanceof SXSSFWorkbook){
                ((SXSSFWorkbook)workbook).dispose();
            }
            System.out.println(fileName + "消耗时间：" + ((double)(end - begin)/1000));
        }
    }


    */
/**
     * 读
     *//*

    public void read(String fileName){
        FileInputStream fileInputStream = null;
        Workbook workbook = null;
        try{
            fileInputStream = new FileInputStream(PATH + fileName);
            workbook = new XSSFWorkbook(fileInputStream);
            //拿到所有sheet的长度
            int sheets = workbook.getNumberOfSheets();
            for (int sheetNum = 0; sheetNum < sheets; sheetNum++) {
                Sheet sheet = workbook.getSheetAt(sheetNum);
                if(null == sheet)
                    continue;
                System.out.println("sheet:" + sheet.getSheetName());
                //拿到所有的row
                int rows = sheet.getPhysicalNumberOfRows();
                for (int rowNum = 0; rowNum < rows; rowNum++) {
                    Row row = sheet.getRow(rowNum);
                    if(null == row)
                        continue;
                    //拿到每一行的列
                    int cells = row.getPhysicalNumberOfCells();
                    for (int cellNum = 0; cellNum < cells; cellNum++) {
                        Cell cell = row.getCell(cellNum);
                        if(null == cell)
                            continue;
                        System.out.print(getCellValue(cell,workbook) + " | ");
                    }
                    System.out.println();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        } finally {
            if(null != fileInputStream){
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void add(String fileName){
        FileInputStream fileInputStream = null;
        Workbook workbook = null;
        try{
            fileInputStream = new FileInputStream(PATH + fileName);
            workbook = new XSSFWorkbook(fileInputStream);
            Sheet sheet = workbook.getSheetAt(0);
            Row row = sheet.getRow(2);
            Cell cell = row.getCell(0);
            getCellValue(cell, workbook);
        }catch (Exception e){
            e.printStackTrace();
        } finally {
            if(null != fileInputStream){
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public static String getCellValue(Cell cell,Workbook workbook){
        String cellValue = "";
        switch (cell.getCellType()){
            case STRING:
            case BLANK:
                cellValue = cell.getStringCellValue();
                break;
            case BOOLEAN:
                cellValue = String.valueOf(cell.getBooleanCellValue());
                break;
            case NUMERIC:
                if(HSSFDateUtil.isCellDateFormatted(cell)){
                    cellValue = new DateTime(cell.getDateCellValue()).toString("yyyy-MM-dd HH:mm:ss");
                }else{
                    cell.setCellType(CellType.STRING);
                    cellValue = cell.toString();
                }
                break;
            case _NONE:
                System.out.println("空");
            case ERROR:
                System.out.println("error");
            case FORMULA:
                //公式
                String cellFormula = cell.getCellFormula();
                System.out.println(cellFormula);
                FormulaEvaluator formulaEvaluator = null;
                if(workbook instanceof HSSFWorkbook){
                    formulaEvaluator = new HSSFFormulaEvaluator((HSSFWorkbook) workbook);
                }else if(workbook instanceof XSSFWorkbook){
                    formulaEvaluator = new XSSFFormulaEvaluator((XSSFWorkbook) workbook);
                }else if(workbook instanceof SXSSFWorkbook){
                    formulaEvaluator = new SXSSFFormulaEvaluator((SXSSFWorkbook) workbook);
                }
                //转成string
                System.out.println(formulaEvaluator.evaluate(cell).formatAsString());
        }
        return cellValue;
    }
}
*/
