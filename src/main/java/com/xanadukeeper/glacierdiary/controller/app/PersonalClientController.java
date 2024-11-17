package com.xanadukeeper.glacierdiary.controller.app;

import com.xanadukeeper.glacierdiary.common.result.Result;
import com.xanadukeeper.glacierdiary.model.entity.PersonalInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Mr-Glacier
 * @version 1.0
 * @apiNote 个人信息-客户端-控制类
 * @since 2024/10/7 21:44
 */
@RestController
@RequestMapping("/api/personal")
public class PersonalClientController {

    /**
     * 获取 博主个人信息
     */
    @GetMapping("/getPersonalInfo")
    public Result<PersonalInfo> getPersonalInfo(){
        PersonalInfo personalInfo = new PersonalInfo();
        personalInfo.setNickname("Mr-Glacier");
        personalInfo.setAvatar("https://zedata-bucket.oss-cn-hongkong.aliyuncs.com/GlacierDiary/myBlogAvatar.png");
        personalInfo.setIntroduction("浮舟沧海 立马昆仑");
        personalInfo.setEmail("mrglacier@yeah.net");
        personalInfo.setGithub("https://github.com/Mr-Glacier");
        personalInfo.setBlogCount(0);
        personalInfo.setCategoryCount(0);
        personalInfo.setTagCount(0);
        return Result.success(personalInfo);
    }

}
