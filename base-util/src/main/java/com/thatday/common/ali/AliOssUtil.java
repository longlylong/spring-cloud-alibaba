package com.thatday.common.ali;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.DownloadFileRequest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class AliOssUtil {

    // Endpoint以全球加速为例，其它Region请按实际情况填写。
    private static final String endpoint = "http://oss-accelerate.aliyuncs.com";
    // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。
    // 强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
    private static final String accessKeyId = "accessKeyId";
    private static final String accessKeySecret = "accessKeySecret";

    public static void delFile(String bucketName, String objectName) {
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        // 上传内容到指定的存储空间（bucketName）并保存为指定的文件名称（objectName）。
        ossClient.deleteObject(bucketName, objectName);
        // 关闭OSSClient。
        ossClient.shutdown();
    }

    public static void uploadFile(String bucketName, File file, String objectName) throws FileNotFoundException {
        uploadFile(bucketName, new FileInputStream(file), objectName);
    }

    public static void uploadFile(String bucketName, InputStream inputStream, String objectName) {
        // <yourObjectName>上传文件到OSS时需要指定包含文件后缀在内的完整路径，例如abc/efg/123.jpg。
        // String objectName = uuid;

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        try {
            ossClient.getObject(bucketName, objectName);
            System.out.println("AliOssUtil: 已有文件 " + objectName);
        } catch (OSSException oss) {
            System.out.println("AliOssUtil: 需要上传 " + objectName);
            // 上传内容到指定的存储空间（bucketName）并保存为指定的文件名称（objectName）。
            ossClient.putObject(bucketName, objectName, inputStream);
        }

        // 关闭OSSClient。
        ossClient.shutdown();
    }

    public static void downloadFile(String bucketName, File outFile, String objectName) {
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        DownloadFileRequest fileRequest = new DownloadFileRequest(bucketName, objectName, outFile.getAbsolutePath(), 102400L);
        try {
            ossClient.downloadFile(fileRequest);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }
}
