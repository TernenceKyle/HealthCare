package com.jadenyee.imgService;

import com.arcsoft.face.EngineConfiguration;
import com.arcsoft.face.FaceEngine;
import com.arcsoft.face.FaceInfo;
import com.arcsoft.face.FunctionConfiguration;
import com.arcsoft.face.enums.DetectMode;
import com.arcsoft.face.enums.DetectOrient;
import com.arcsoft.face.enums.ErrorInfo;
import com.arcsoft.face.enums.ImageFormat;
import com.arcsoft.face.toolkit.ImageFactory;
import com.arcsoft.face.toolkit.ImageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.ArrayList;
import java.util.List;


@Service
public class FaceUtils{
    private static final Logger LOGGER = LoggerFactory.getLogger(FaceUtils.class);
    private String appid = "EbeaxoU21kebKUgUNGh1eSZ4VAPdZXXF9yrmcJ6tYSm1";
    private String sdkKey = "8k2J2VxBM9BMZTuT3NDcg14YsMengwpgzFxsF4eEsqDt";
    private String libPath = "C:\\Users\\Ezzra\\Desktop\\ArcSoft_ArcFace_Java_Windows_x64_V3.0\\libs\\WIN64";
    private FaceEngine faceEngine;
    @PostConstruct
    public void init(){
        //激活并且初始化引擎
        FaceEngine faceEngine = new FaceEngine(libPath);
        int activeCode = faceEngine.activeOnline(appid, sdkKey);
        if (activeCode!= ErrorInfo.MOK.getValue()&&activeCode!= ErrorInfo.MERR_ASF_ALREADY_ACTIVATED.getValue()){
            LOGGER.error("引擎激活失败!");
            throw new RuntimeException("引擎激活失败");
        }
        //引擎配置
        EngineConfiguration engineConfiguration = new EngineConfiguration();
        //用于IMAGE 检测模式，用于处理单张的图像数据
        engineConfiguration.setDetectMode(DetectMode.ASF_DETECT_MODE_IMAGE);
        //人脸检测角度，全角度
        engineConfiguration.setDetectFaceOrientPriority(DetectOrient.ASF_OP_ALL_OUT);
        //进行功能配置
        FunctionConfiguration functionConfiguration = new FunctionConfiguration();
        functionConfiguration.setSupportAge(true);
        functionConfiguration.setSupportFace3dAngle(true);
        functionConfiguration.setSupportFaceDetect(true);
        functionConfiguration.setSupportFaceRecognition(true);
        functionConfiguration.setSupportGender(true);
        functionConfiguration.setSupportLiveness(true);
        functionConfiguration.setSupportIRLiveness(true);
        engineConfiguration.setFunctionConfiguration(functionConfiguration);
        //初始化引擎
        int initCode = faceEngine.init(engineConfiguration);
        if (initCode != ErrorInfo.MOK.getValue()) {
            LOGGER.error("初始化引擎出错!");
            throw new RuntimeException("初始化引擎出错!");
        }
        this.faceEngine = faceEngine;
    }

    public List<FaceInfo> checkIsPortrait(ImageInfo imageInfo) {
        //定义人脸列表
        List<FaceInfo> faceInfoList = new ArrayList<FaceInfo>();
        faceEngine.detectFaces(imageInfo.getImageData(), imageInfo.getWidth(), imageInfo.getHeight(), ImageFormat.CP_PAF_BGR24, faceInfoList);
        return faceInfoList;
    }

    public boolean checkIsPortrait(byte[] imageData) {
        return !this.checkIsPortrait(ImageFactory.getRGBData(imageData)).isEmpty();
    }
    public boolean checkIsPortrait(File file) {
        return !this.checkIsPortrait(ImageFactory.getRGBData(file)).isEmpty();
    }
}
