package com.xd.pre.modules.sys.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.xd.pre.common.exception.ValidateCodeException;
import com.xd.pre.common.utils.R;
import com.xd.pre.modules.security.social.PreConnectionData;
import com.xd.pre.modules.security.social.SocialRedisHelper;
import com.xd.pre.modules.security.social.SocialUserInfo;
import com.xd.pre.modules.sys.domain.SysRole;
import com.xd.pre.modules.sys.domain.SysUser;
import com.xd.pre.modules.sys.dto.UserDTO;
import com.xd.pre.modules.sys.service.ISysRoleService;
import com.xd.pre.modules.sys.service.ISysUserService;
import com.xd.pre.security.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 主页模块
 * @auther:zlk
 * @date:2021-3-5
 * @description:
 *
 */
@RestController
public class IndexController {

    @Autowired
    private ISysUserService userService;

    @Autowired
    private ProviderSignInUtils providerSignInUtils;

    @Autowired
    private SocialRedisHelper socialRedisHelper;

    @Autowired
    private ISysRoleService sysRoleService;


    @Value("${pre.url.address}")
    private String url;

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;


    @PostMapping("/register")
    public R register(@RequestBody UserDTO userDTO) {
        Object redisCode = redisTemplate.opsForValue().get(userDTO.getPhone());
        if (ObjectUtil.isNull(redisCode)) {
            throw new ValidateCodeException("验证码已失效");
        }
        if (!userDTO.getSmsCode().toLowerCase().equals(redisCode)) {
            throw new ValidateCodeException("短信验证码错误");
        }
        return R.ok(userService.register(userDTO));
    }

    /**
     * 登录
     *
     * @param username
     * @param password
     * @return
     */
    @RequestMapping(value = "/login")
    public R login(String username, String password, HttpServletRequest request) {
        // 社交快速登录
        String token = request.getParameter("token");
        if (StrUtil.isNotEmpty(token)) {
            return R.ok(token);
        }
        return R.ok(userService.login(username, password));
    }

    /**
     * 保存完信息然后跳转到绑定用户信息页面
     *
     * @param request
     * @param response
     * @throws IOException
     */
    @GetMapping("/socialSignUp")
    public void socialSignUp(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String uuid = UUID.randomUUID().toString();
        SocialUserInfo userInfo = new SocialUserInfo();
        Connection<?> connectionFromSession = providerSignInUtils.getConnectionFromSession(new ServletWebRequest(request));
        userInfo.setHeadImg(connectionFromSession.getImageUrl());
        userInfo.setNickname(connectionFromSession.getDisplayName());
        userInfo.setProviderId(connectionFromSession.getKey().getProviderId());
        userInfo.setProviderUserId(connectionFromSession.getKey().getProviderUserId());
        ConnectionData data = connectionFromSession.createData();
        PreConnectionData preConnectionData = new PreConnectionData();
        BeanUtil.copyProperties(data, preConnectionData);
        socialRedisHelper.saveConnectionData(uuid, preConnectionData);
        // 跳转到用户绑定页面
        response.sendRedirect(url + "/bind?key=" + uuid);
    }

    /**
     * 社交登录绑定
     *
     * @param user
     * @return
     */
    @PostMapping("/bind")
    public R register(@RequestBody SysUser user) {
        return R.ok(userService.doPostSignUp(user));
    }


    /**
     * @Description 暂时这样写
     **/
    @RequestMapping("/info")
    public R info() {
        SysUser byUserInfoName = userService.findByUserInfoName(SecurityUtil.getUser().getUsername());
        List<SysRole> rolesByUserId = sysRoleService.findRolesByUserId(byUserInfoName.getUserId());
        List<Integer> list1 = rolesByUserId.parallelStream().map(r -> {
            return r.getRoleId();
        }).collect(Collectors.toList());
        Map<String, Object> map = new HashMap<>();
        map.put("roles", list1);
        map.put("avatar", "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1561394014552&di=17b6c1233048e5276f48309b306c7699&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201804%2F29%2F20180429210111_gtsnf.jpg");
        if (rolesByUserId != null && byUserInfoName.getUsername() != null){
            map.put("name", byUserInfoName.getUsername());
        }else {
            map.put("name", null);
        }
        if (rolesByUserId != null && rolesByUserId.size() > 0){
            map.put("areAdmin", rolesByUserId.get(0).getAreAdmin());
        }else {
            map.put("areAdmin", null);
        }
        return R.ok(map);
    }

    /**
     * @Description 使用jwt前后分离 只需要前端清除token即可 暂时返回success
     **/
    @RequestMapping("/logout")
    public String logout() {
        return "success";
    }
}
