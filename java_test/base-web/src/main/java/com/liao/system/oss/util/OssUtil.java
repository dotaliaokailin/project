package com.liao.system.oss.util;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.*;
import com.liao.system.oss.entity.OssEntity;
import org.joda.time.DateTime;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.UUID;

/**
 * oss工具
 */
@Component
public class OssUtil implements InitializingBean {
    @Autowired
    private OssEntity ossEntity;

    // Endpoint以杭州为例，其它Region请按实际情况填写。
    private String endpoint;
    // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号
    private String accessKeyId;
    private String accessKeySecret;
    //存储空间名称
    private  String bucketName;

    /**
     * 初始化操作
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        endpoint = ossEntity.getEndpoint();
        accessKeyId = ossEntity.getAccessKeyId();
        accessKeySecret = ossEntity.getAccessKeySecret();
        bucketName = ossEntity.getBucketName();
    }
    /**
     * 创建存储空间
     */
    public void createBucket(OSS ossClient) {
        //判断是否存在存储空间
        if(ossClient.doesBucketExist(bucketName)){
            throw new RuntimeException("已存在OSS存储空间:" + bucketName);
        }
        CreateBucketRequest createBucketRequest = new CreateBucketRequest(bucketName);
        // 设置存储空间的权限为公共读，默认是私有。
        createBucketRequest.setCannedACL(CannedAccessControlList.PublicRead);
        // 设置存储空间的存储类型为低频访问类型，默认是标准类型。
        createBucketRequest.setStorageClass(StorageClass.IA);
        ossClient.createBucket(createBucketRequest);
    }
    /**
     * 通过文件名判断并获取OSS服务文件上传时文件的contentType
     *
     * @param fileName 文件名
     * @return 文件的contentType
     */
    public static final String getContentType(String fileName) {
        String FilenameExtension = fileName.substring(fileName.lastIndexOf("."));
        if (FilenameExtension.equalsIgnoreCase(".bmp")) {
            return "application/x-bmp";
        }
        if (FilenameExtension.equalsIgnoreCase(".gif")) {
            return "image/gif";
        }
        if (FilenameExtension.equalsIgnoreCase(".jpeg") ||
                FilenameExtension.equalsIgnoreCase(".jpg") ||
                FilenameExtension.equalsIgnoreCase(".png")) {
            return "image/jpg";
        }
        if (FilenameExtension.equalsIgnoreCase(".html")) {
            return "text/html";
        }
        if (FilenameExtension.equalsIgnoreCase(".txt")) {
            return "text/plain";
        }
        if (FilenameExtension.equalsIgnoreCase(".vsd")) {
            return "application/vnd.visio";
        }
        if (FilenameExtension.equalsIgnoreCase(".pptx") ||
                FilenameExtension.equalsIgnoreCase(".ppt")) {
            return "application/vnd.ms-powerpoint";
        }
        if (FilenameExtension.equalsIgnoreCase(".docx") ||
                FilenameExtension.equalsIgnoreCase(".doc")) {
            return "application/msword";
        }
        if (FilenameExtension.equalsIgnoreCase(".xla") ||
                FilenameExtension.equalsIgnoreCase(".xlc")||
                FilenameExtension.equalsIgnoreCase(".xlm")||
                FilenameExtension.equalsIgnoreCase(".xls")||
                FilenameExtension.equalsIgnoreCase(".xlt")||
                FilenameExtension.equalsIgnoreCase(".xlw")) {
            return "application/vnd.ms-excel";
        }
        if (FilenameExtension.equalsIgnoreCase(".xml")) {
            return "text/xml";
        }
        if (FilenameExtension.equalsIgnoreCase(".pdf")) {
            return "application/pdf";
        }
        if (FilenameExtension.equalsIgnoreCase(".zip")) {
            return "application/zip";
        }
        if (FilenameExtension.equalsIgnoreCase(".tar")) {
            return "application/x-tar";
        }
        if (FilenameExtension.equalsIgnoreCase(".avi")) {
            return "video/avi";
        }
        if (FilenameExtension.equalsIgnoreCase(".mp4")) {
            return "video/mpeg4";
        }
        if (FilenameExtension.equalsIgnoreCase(".mp3")) {
            return "audio/mp3";
        }
        if (FilenameExtension.equalsIgnoreCase(".mp2")) {
            return "audio/mp2";
        }
        return "application/octet-stream";
    }
    /**
     * 上传文件
     *
     * @param file
     */
    public String upload(MultipartFile file) {
        //上传地址
        String uploadUrl = null;
        OSS ossClient = null;
        String fileUrl = "";
        try {
            // 创建OSSClient实例。
            ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
            //判断是否存在存储空间
            if(!ossClient.doesBucketExist(bucketName)){
                createBucket(ossClient);
            }
            //获取上传的文件流
            InputStream inputStream = file.getInputStream();
            //获取日期
            String datePath = new DateTime().toString("yyyy/MM/dd");
            //获取文件名
            String originalFilename = file.getOriginalFilename();
            //获取文件后缀.
            String fileType = originalFilename.substring(originalFilename.lastIndexOf("."));
            //uuid / redis分布式ID / 雪花算法
            String uuid = UUID.randomUUID().toString().replaceAll("-","");
            //文件全路径
            originalFilename = datePath + "/" + uuid + fileType;
            //实现图片预览 必须设置文本类型和ACL权限
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setObjectAcl(CannedAccessControlList.PublicRead);
            objectMetadata.setContentType(getContentType(fileType));
            // 上传文件
            ossClient.putObject(bucketName, originalFilename, inputStream, objectMetadata);
            //默认十年不过期
            Date expiration = new Date(System.currentTimeMillis() + 3600L * 1000 * 24 * 365 * 10);
            fileUrl = ossClient.generatePresignedUrl(bucketName, originalFilename, expiration).toString();
        } catch (IOException e) {
            e.printStackTrace();
            return "上传失败";
        } finally {
            if(null != ossClient){
                // 关闭OSSClient。
                ossClient.shutdown();
            }
            return fileUrl.substring(0, fileUrl.lastIndexOf("?"));
        }
    }

    /**
     * 下载文件
     *
     * @param fileName
     */
    public void download(String fileName) throws IOException {
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        //判断是否存在存储空间
        if(!ossClient.doesBucketExist(bucketName)){
            createBucket(ossClient);
        }
        // 调用ossClient.getObject返回一个OSSObject实例，该实例包含文件内容及文件元信息。
        OSSObject ossObject = ossClient.getObject(bucketName, fileName);
        // 调用ossObject.getObjectContent获取文件输入流，可读取此输入流获取其内容。
        InputStream content = ossObject.getObjectContent();
        if (content != null) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(content));
            while (true) {
                String line = reader.readLine();
                if (line == null) break;
                System.out.println("\n" + line);
            }
            // 数据读取完成后，获取的流必须关闭，否则会造成连接泄漏，导致请求无连接可用，程序无法正常工作。
            content.close();
        }
        // 关闭OSSClient。
        ossClient.shutdown();
    }

    /**
     * 列举文件
     */
    public void listFile() {
        try{
            // 创建OSSClient实例。
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
            //判断是否存在存储空间
            if(!ossClient.doesBucketExist(bucketName)){
                createBucket(ossClient);
            }
            // ossClient.listObjects返回ObjectListing实例，包含此次listObject请求的返回结果。
            ObjectListing objectListing = ossClient.listObjects(bucketName);
            // objectListing.getObjectSummaries获取所有文件的描述信息。
            for (OSSObjectSummary objectSummary : objectListing.getObjectSummaries()) {
                System.out.println(" - " + objectSummary.getKey() + "  " +
                        "(size = " + objectSummary.getSize() + ")");
            }
            // 关闭OSSClient。
            ossClient.shutdown();
        }catch (Exception e){
            throw new RuntimeException("文件查询失败");
        }
    }

    /**
     * 删除文件
     *
     * @param fileName
     */
    public Boolean deleteFile(String fileName) {
        try{
            // 创建OSSClient实例。
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
            //判断是否存在存储空间
            if(!ossClient.doesBucketExist(bucketName)){
                createBucket(ossClient);
            }
            // 删除文件。
            ossClient.deleteObject(bucketName, fileName);

            // 关闭OSSClient。
            ossClient.shutdown();
        }catch (Exception e){
            return false;
        }
        return true;
    }
}
