package com.liao.system.oss.service.impl;

import com.liao.system.oss.service.AliossService;
import com.liao.system.oss.util.OssUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class AliossServiceImpl implements AliossService {
    @Autowired
    private OssUtil OssUtil;

    /**
     * 上传文件
     *
     * @param file
     */
    @Override
    public String upload(MultipartFile file) {
        return OssUtil.upload(file);
    }

    /**
     * 下载文件
     *
     * @param fileName
     */
    @Override
    public void download(String fileName) throws IOException {
        OssUtil.download(fileName);
    }

    /**
     * 列举文件
     */
    @Override
    public void listFile() {
        OssUtil.listFile();
    }

    /**
     * 删除文件
     *
     * @param fileName
     */
    @Override
    public Boolean deleteFile(String fileName) {
        return OssUtil.deleteFile(fileName);
    }


}
