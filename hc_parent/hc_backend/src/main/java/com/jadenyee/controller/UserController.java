package com.jadenyee.controller;

import com.arcsoft.face.FaceInfo;
import com.arcsoft.face.toolkit.ImageFactory;
import com.arcsoft.face.toolkit.ImageInfo;
import com.jadenyee.constant.MessageConstant;
import com.jadenyee.entity.Result;
import com.jadenyee.imgService.FaceUtils;
import com.jadenyee.pojo.User;
import com.jadenyee.service.UserService;
import com.jadenyee.utils.QiniuUtils;
import io.netty.handler.codec.http.HttpRequest;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private FaceUtils faceUtils;
    @Reference
    private UserService userService;

    /**
     * 当用户成功登录后，获取到当前登录的用户的用户名 和 用户的头像
     * @return 返回结果封装
     */
    @GetMapping("/getUsername")
    public Result getUsername(HttpServletRequest request){
        try {
            org.springframework.security.core.userdetails.User user1 = (org.springframework.security.core.userdetails.User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String username = user1.getUsername();
            request.setAttribute("username",username);
            User userByUsername = userService.getUserByUsername(username);
            Map<String,Object> resMap = new HashMap<>();
            resMap.put("username",username);
            resMap.put("avatar",userByUsername.getAvatar());
            resMap.put("id",userByUsername.getId());
            resMap.put("remark",userByUsername.getRemark());
            resMap.put("telephone", userByUsername.getTelephone());
            resMap.put("gender",userByUsername.getGender());
            resMap.put("birthday",userByUsername.getBirthday());
            return new Result(true, MessageConstant.GET_USERNAME_SUCCESS, resMap);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_USERNAME_FAIL);
        }
    }
    /**
     * 用户上传头像 使用虹软人脸识别 SDK 检测上传的头像是否是人类活体
     * @param pic
     * @return
     */
//    appid-key = APP_ID:EbeaxoU21kebKUgUNGh1eSZ4VAPdZXXF9yrmcJ6tYSm1
//    SDK_KEY:8k2J2VxBM9BMZTuT3NDcg14YsMengwpgzFxsF4eEsqDt
//    必要dll路径 C:\Users\Ezzra\Desktop\ArcSoft_ArcFace_Java_Windows_x64_V3.0\libs\WIN64
    @PostMapping("/uploadUserAvatar")
    public Result uploadAvatar(@RequestParam("imgFile")MultipartFile pic){
        try {
            String filename = pic.getOriginalFilename();
            if (!StringUtils.isNotBlank(filename)||!filename.contains("."))
            {
                return new Result(false,MessageConstant.PIC_UPLOAD_FAIL);
            }
            byte[] picBytes = pic.getBytes();
            boolean res = faceUtils.checkIsPortrait(picBytes);
            if (!res) {
                return new Result(res,"非人像图片,上传失败!");
            }
            String suffix = filename.substring(filename.lastIndexOf('.'));
            StringBuilder sb = new StringBuilder(UUID.randomUUID().toString()).append(suffix);
            QiniuUtils.uploadFile("myhealthcare", picBytes,sb.toString());
            return new Result(true,MessageConstant.PIC_UPLOAD_SUCCESS,sb.toString());
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.PIC_UPLOAD_FAIL);
        }
    }

    /**
     * 添加用户的 Controller 方法
     * @param user 更新的用户数据
     * @return 返回操作执行结果封装
     */
    @PostMapping("/uploadUser")
    public Result updateUser(@RequestBody User user){
        try{
            System.out.println(user);
            boolean b = userService.updateUser(user);
            return b ? new Result(b, "成功更新用户信息！") : new Result(false, "更新用户信息失败!");
        }catch (Exception e)
        {
            e.printStackTrace();
            return  new Result(false,"更新用户信息失败！");
        }
    }
}
