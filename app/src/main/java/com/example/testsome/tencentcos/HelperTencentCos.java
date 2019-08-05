package com.example.testsome.tencentcos;

import android.content.Context;
import android.util.Log;
import com.tencent.cos.xml.CosXmlService;
import com.tencent.cos.xml.CosXmlServiceConfig;
import com.tencent.cos.xml.exception.CosXmlClientException;
import com.tencent.cos.xml.exception.CosXmlServiceException;
import com.tencent.cos.xml.listener.CosXmlProgressListener;
import com.tencent.cos.xml.listener.CosXmlResultListener;
import com.tencent.cos.xml.model.CosXmlRequest;
import com.tencent.cos.xml.model.CosXmlResult;
import com.tencent.cos.xml.transfer.*;
import com.tencent.qcloud.core.auth.QCloudCredentialProvider;
import com.tencent.qcloud.core.auth.ShortTimeCredentialProvider;

/**
 * Created by philer on 2019/7/18.
 * Describe:腾讯对象存储
 */
public class HelperTencentCos {
    private static final HelperTencentCos ourInstance = new HelperTencentCos();

    public static HelperTencentCos getInstance() {
        return ourInstance;
    }

    private HelperTencentCos() {
    }

    CosXmlService cosXmlService;
    public void init(Context context) {
        String region = "ap-chengdu";

//创建 CosXmlServiceConfig 对象，根据需要修改默认的配置参数
        CosXmlServiceConfig serviceConfig = new CosXmlServiceConfig.Builder()
                .setRegion(region)
                .isHttps(true) // 使用 https 请求, 默认 http 请求
                .setDebuggable(true)
                .builder();


        String secretId = "AKIDQmxHUaKWF9vFSWtBmBOwMadVBf1CfyY1"; //永久密钥 secretId
        String secretKey = "SjpjS3Vn7GDyXu53rX3NXWKyQhmhT5Vj"; //永久密钥 secretKey

/**
 * 初始化 {@link QCloudCredentialProvider} 对象，来给 SDK 提供临时密钥。
 */
        QCloudCredentialProvider credentialProvider = new ShortTimeCredentialProvider(secretId,
                secretKey, 300);

        cosXmlService = new CosXmlService(context, serviceConfig, credentialProvider);
    }

    public void updateFile(String filePath) {
        // 初始化 TransferConfig
        TransferConfig transferConfig = new TransferConfig.Builder().build();
/**
 若有特殊要求，则可以如下进行初始化定制。如限定当对象 >= 2M 时，启用分片上传，且分片上传的分片大小为 1M, 当源对象大于 5M 时启用分片复制，且分片复制的大小为 5M。
 TransferConfig transferConfig = new TransferConfig.Builder()
 .setDividsionForCopy(5 * 1024 * 1024) // 是否启用分片复制的最小对象大小
 .setSliceSizeForCopy(5 * 1024 * 1024) //分片复制时的分片大小
 .setDivisionForUpload(2 * 1024 * 1024) // 是否启用分片上传的最小对象大小
 .setSliceSizeForUpload(1024 * 1024) //分片上传时的分片大小
 .build();
 */

//初始化 TransferManager
        TransferManager transferManager = new TransferManager(cosXmlService, transferConfig);

        String bucket = "menjin-1251461298";
        String cosPath = "fff"; //即对象到 COS 上的绝对路径, 格式如 cosPath = "text.txt";
        String srcPath = filePath; // 如 srcPath=Environment.getExternalStorageDirectory().getPath() + "/text.txt";
        String uploadId = null; //若存在初始化分片上传的 UploadId，则赋值对应 uploadId 值用于续传，否则，赋值 null。
//上传对象
        COSXMLUploadTask cosxmlUploadTask = transferManager.upload(bucket, cosPath, srcPath, uploadId);

/**
 * 若是上传字节数组，则可调用 TransferManager 的 upload(string, string, byte[]) 方法实现;
 * byte[] bytes = "this is a test".getBytes(Charset.forName("UTF-8"));
 * cosxmlUploadTask = transferManager.upload(bucket, cosPath, bytes);
 */

/**
 * 若是上传字节流，则可调用 TransferManager 的 upload(String, String, InputStream) 方法实现；
 * InputStream inputStream = new ByteArrayInputStream("this is a test".getBytes(Charset.forName("UTF-8")));
 * cosxmlUploadTask = transferManager.upload(bucket, cosPath, inputStream);
 */


//设置上传进度回调
        cosxmlUploadTask.setCosXmlProgressListener(new CosXmlProgressListener() {
            @Override
            public void onProgress(long complete, long target) {
                float progress = 1.0f * complete / target * 100;
                Log.d("TEST", String.format("progress = %d%%", (int) progress));
            }
        });
//设置返回结果回调
        cosxmlUploadTask.setCosXmlResultListener(new CosXmlResultListener() {
            @Override
            public void onSuccess(CosXmlRequest request, CosXmlResult result) {
                COSXMLUploadTask.COSXMLUploadTaskResult cOSXMLUploadTaskResult = (COSXMLUploadTask.COSXMLUploadTaskResult) result;
                Log.d("TEST", "Success: " + cOSXMLUploadTaskResult.printResult());
            }

            @Override
            public void onFail(CosXmlRequest request, CosXmlClientException exception, CosXmlServiceException serviceException) {
                Log.d("TEST", "Failed: " + (exception == null ? serviceException.getMessage() : exception.toString()));
            }
        });
//设置任务状态回调, 可以查看任务过程
        cosxmlUploadTask.setTransferStateListener(new TransferStateListener() {
            @Override
            public void onStateChanged(TransferState state) {
                Log.d("TEST", "Task state:" + state.name());
            }
        });

    }
}
