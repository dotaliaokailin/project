package com.liao.util;

import com.alibaba.excel.EasyExcel;
import com.liao.handler.BusinessException;
import com.liao.response.ResultCodeEnum;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

public class ExcelUtil {

    /**
     * 文件下载（失败了会返回一个有部分数据的Excel）
     * <p>
     * 1. 创建excel对应的实体对象 参照{@link com.liao.system.pojo.TbUser}
     * <p>
     * 2. 设置返回的 参数
     * <p>
     * 3. 直接写，这里注意，finish的时候会自动关闭OutputStream,当然你外面再关闭流问题不大
     */
    public static void download(HttpServletResponse response, String fileName, String sheetName, List list, Class clazz) throws IOException {
        ServletOutputStream outputStream = null;
        try {
            // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
            fileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
            response.setHeader("FileName", fileName+".xlsx");
            response.setHeader("Access-Control-Expose-Headers", "FileName");
            outputStream = response.getOutputStream();
            EasyExcel.write(outputStream, clazz).sheet(sheetName).doWrite(list);
        }catch (Exception e){
            throw new BusinessException(ResultCodeEnum.EXPORT_FAIL.getCode(), ResultCodeEnum.EXPORT_FAIL.getMessage());
        } finally {
            if(null != outputStream){
                outputStream.close();
            }
        }
    }
}
