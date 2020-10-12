package com.liao.system.oss.entity;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 阿里云OSS对象存储
 */
@ConfigurationProperties(prefix = "alioss")
@Component
@Data
public class OssEntity {
    // Endpoint以杭州为例，其它Region请按实际情况填写。
    private String endpoint;
    // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号
    private String accessKeyId;
    private String accessKeySecret;
    //存储空间名称
    private  String bucketName;
}
