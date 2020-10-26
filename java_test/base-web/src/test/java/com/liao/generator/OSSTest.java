package com.liao.generator;

import com.liao.system.oss.entity.OssEntity;
import com.liao.system.oss.service.AliossService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class OSSTest {
    @Autowired
    private OssEntity ossEntity;
    @Autowired
    private AliossService aliossService;

    @Test
    public void contextLoads(){
        System.out.println(ossEntity.toString());
    }


    @Test
    public void listFile(){
        aliossService.listFile();
    };

    @Test
    public void deleteFile(){
        aliossService.deleteFile("2020/10/12/1a961bcf9a2a44e28f94b73453c6d7e5.jpg");
    };
}