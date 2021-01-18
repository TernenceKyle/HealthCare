package com.jadenyee.utils;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;

public class QiniuUtils {
    private static Configuration conf;
    private static final String accessKey = "Ms7rCA1D80i7QsGusi75WQ2v0VIgB4sN71Z6hPAj";
    private static final String secretKey = "jgQFrlidSRIMBouVViZ8oPtetoesiNICABxy424b";
    static Auth auth;
    static {
        auth = Auth.create(accessKey,secretKey);
        //服务器的地域 zone2 为华南
        conf = new Configuration(Zone.zone2());
    }
    /*
    * 上传文件（通过文件路径）
    * bucket 是存储空间名，fileName 是上传文件的名.
    * */
    public static void uploadFile(String bucket,String filePath,String fileName){
        UploadManager uploadManager = new UploadManager(conf);
        String uploadToken = auth.uploadToken(bucket);
        try {
            Response put = uploadManager.put(filePath, fileName, uploadToken);
            DefaultPutRet ret = new Gson().fromJson(put.bodyString(),DefaultPutRet.class);
            System.out.println("上传的文件名:"+ret.key);
            System.out.println("文件Hash:"+ret.hash);
        } catch (QiniuException e) {
            Response resp = e.response;
            System.err.println(resp.toString());
            try {
                System.err.println(resp.bodyString());
            } catch (QiniuException qx) {
                qx.printStackTrace();
            }
        }
    }
    /*
    * 文件上传的二进制版本
    * */
    public static void uploadFile(byte[] bytes,String filePath, String fileName){
        UploadManager um = new UploadManager(conf);
        try{
            Response put = um.put(bytes, filePath, fileName);
            DefaultPutRet ret = new Gson().fromJson(put.bodyString(), DefaultPutRet.class);
            System.out.println("上传的文件名:"+ret.key);
            System.out.println("文件Hash:"+ret.hash);
        } catch (QiniuException e) {
            Response response = e.response;
            System.out.println(response.toString());
            try {
                System.out.println(response.bodyString());
            } catch (QiniuException qiniuException) {
                qiniuException.printStackTrace();
            }
        }
    }
    /*
    * 删除文件，需要给定 bucket 和 文件名
    * */
    public static void deleteFile(String bucket,String fileName){
        BucketManager bm = new BucketManager(auth,conf);
        try {
            bm.delete(bucket,fileName);
        } catch (QiniuException e) {
            System.err.println("删除发生错误!错误代码: "+e.code());
            System.err.println(e.response.toString());
        }
    }
}
