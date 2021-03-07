package com.xd.pre.modules.security.controller;

import cn.hutool.core.util.ObjectUtil;
import com.wf.captcha.ArithmeticCaptcha;
import com.xd.pre.common.constant.PreConstant;
import com.xd.pre.common.exception.ValidateCodeException;
import com.xd.pre.common.utils.R;
import com.xd.pre.modules.sys.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @Classname AuthController
 * @Description TODO
 */
@RequestMapping("/auth")
@RestController
public class AuthController {

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    /**
     * 生成验证码
     *
     * @throws ServletException
     * @throws IOException
     */
    @GetMapping("/captcha.jpg")
    public R captcha() throws IOException {
        // 算术类型
        ArithmeticCaptcha captcha = new ArithmeticCaptcha(111, 36);
        // 几位数运算，默认是两位
        captcha.setLen(2);
        // 获取运算的结果
        String result = captcha.text();

        String key = UUID.randomUUID().toString();
        redisTemplate.opsForValue().set(PreConstant.PRE_IMAGE_KEY + key, result, 2, TimeUnit.MINUTES);
        Map map = new HashMap();
        map.put("key", key);
        map.put("img", captcha.toBase64());
        return R.ok(map);
    }
}
