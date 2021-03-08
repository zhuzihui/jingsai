package com.xd.pre.modules.sys.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xd.pre.common.exception.PreBaseException;
import com.xd.pre.modules.data.datascope.DataScope;
import com.xd.pre.modules.security.social.SocialRedisHelper;
import com.xd.pre.modules.security.util.JwtUtil;
import com.xd.pre.modules.sys.domain.*;
import com.xd.pre.modules.sys.dto.UserDTO;
import com.xd.pre.modules.sys.mapper.SysUserMapper;
import com.xd.pre.modules.sys.service.*;
import com.xd.pre.modules.sys.util.PreUtil;
import com.xd.pre.modules.sys.util.SysUserExcelUtil;
import com.xd.pre.security.PreSecurityUser;
import com.xd.pre.security.util.SecurityUtil;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 用户表 服务实现类
 * @auther:zlk
 * @date:2021-3-5
 * @description:
 *
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    @Autowired
    private ISysUserRoleService userRoleService;
    @Autowired
    private ISysDeptService deptService;
    @Autowired
    private ISysRoleDeptService roleDeptService;
    @Autowired
    private ISysMenuService menuService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    private SocialRedisHelper socialRedisHelper;
    @Autowired
    private SysDeptServiceImpl sysDeptService;
    @Resource
    private ISysRoleService roleService;

    @Override
    public IPage<SysUser> getUsersWithRolePage(Page page, UserDTO userDTO) {
        PreSecurityUser user = SecurityUtil.getUser();
        List<SysRole> rolesByUserId = roleService.findRolesByUserId(user.getUserId());
        SysRole sysRole = rolesByUserId.get(0);
        if ("0".equals(sysRole.getAreAdmin())){
            //普通用户，只返回他自己的信息
            userDTO.setUsername(user.getUsername());
        }
        if ( ObjectUtils.anyNotNull(userDTO) && userDTO.getDeptId() != null) {
            List<SysDept> sysDepts = deptService.selectByParentId(userDTO.getDeptId());
            if (sysDepts != null && sysDepts.size() > 0){
                List<Integer> collect = sysDepts.parallelStream().map(s -> {
                    return s.getDeptId();
                }).collect(Collectors.toList());
                userDTO.setDeptList(collect);
                userDTO.setDeptId(null);
            }else {
                userDTO.setDeptId(userDTO.getDeptId());
            }
//            userDTO.setDeptList(deptService.selectDeptIds(userDTO.getDeptId()));
        }else {
            //为空则查该角色下的所有部门
            List<SysRoleDept> roleDeptIds = roleDeptService.getRoleDeptIds(sysRole.getRoleId());
            if (roleDeptIds != null && roleDeptIds.size() > 0){
                List<Integer> deptIds = roleDeptIds.parallelStream().map(r -> {
                    return r.getDeptId();
                }).collect(Collectors.toList());
                userDTO.setDeptList(deptIds);
            }
        }
        return baseMapper.getUserVosPage(page, userDTO, new DataScope());
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean insertUser(UserDTO userDto) {

        SysUser oldUser = findByUserInfoName(userDto.getUsername());
        if (ObjectUtil.isNotNull(oldUser)) {
            throw new PreBaseException("用户名已被添加");
        }
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(userDto, sysUser);
        // 默认密码 xxzx@#123
        sysUser.setPassword(PreUtil.sm4EncryptECB("xxzx@#123"));
        baseMapper.insertUser(sysUser);
        List<SysUserRole> userRoles = userDto.getRoleList().stream().map(item -> {
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setRoleId(item);
            sysUserRole.setUserId(sysUser.getUserId());
            return sysUserRole;
        }).collect(Collectors.toList());

        return userRoleService.saveBatch(userRoles);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateUser(UserDTO userDto) {
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(userDto, sysUser);
        baseMapper.updateById(sysUser);
        userRoleService.remove(Wrappers.<SysUserRole>lambdaQuery().eq(SysUserRole::getUserId, sysUser.getUserId()));
        List<SysUserRole> userRoles = userDto.getRoleList().stream().map(item -> {
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setRoleId(item);
            sysUserRole.setUserId(sysUser.getUserId());
            return sysUserRole;
        }).collect(Collectors.toList());

        return userRoleService.saveBatch(userRoles);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean removeUser(Integer userId) {
        baseMapper.deleteById(userId);
        return userRoleService.remove(Wrappers.<SysUserRole>lambdaQuery().eq(SysUserRole::getUserId, userId));
    }

    @Override
    public boolean restPass(Integer userId) {
        SysUser sysUser = new SysUser();
        sysUser.setPassword(PreUtil.sm4EncryptECB("xxzx@#123"));
        sysUser.setUserId(userId);
        return baseMapper.updateById(sysUser) > 0;
    }

    @Override
    public SysUser findByUserInfoName(String username) {
        SysUser sysUser = baseMapper.selectOne(Wrappers.<SysUser>lambdaQuery()
                .select(SysUser::getUserId, SysUser::getUsername, SysUser::getPhone, SysUser::getEmail, SysUser::getPassword, SysUser::getDeptId, SysUser::getJobId, SysUser::getAvatar)
                .eq(SysUser::getUsername, username));
        // 获取部门
        if (sysUser == null){
            return null;
        }
        sysUser.setDeptName(deptService.selectDeptNameByDeptId(sysUser.getDeptId()));
        // 获取岗位
//        sysUser.setJobName(jobService.selectJobNameByJobId(sysUser.getJobId()));
        return sysUser;
    }

    @Override
    public Set<String> findPermsByUserId(Integer userId) {
        return menuService.findPermsByUserId(userId).stream().filter(StringUtils::isNotEmpty).collect(Collectors.toSet());
    }

    @Override
    public Set<String> findRoleIdByUserId(Integer userId) {
        return userRoleService
                .selectUserRoleListByUserId(userId)
                .stream()
                .map(sysUserRole -> "ROLE_" + sysUserRole.getRoleId())
                .collect(Collectors.toSet());
    }


    @Override
    public String login(String username, String password) {
        //用户验证
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        //存储认证信息
        SecurityContextHolder.getContext().setAuthentication(authentication);
        //生成token
        PreSecurityUser userDetail = (PreSecurityUser) authentication.getPrincipal();
        return JwtUtil.generateToken(userDetail);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean register(UserDTO userDTO) {
        // 查询用户名是否存在
        SysUser byUserInfoName = findSecurityUser(userDTO.getUsername());
        if (ObjectUtil.isNotNull(byUserInfoName)) {
            throw new PreBaseException("账户名已被注册");
        }
        SysUser securityUser = findSecurityUser(userDTO.getPhone());
        if (ObjectUtil.isNotNull(securityUser)) {
            throw new PreBaseException("手机号已被注册");
        }
        userDTO.setDeptId(6);
        userDTO.setLockFlag("0");
        SysUser sysUser = new SysUser();
        // 对象拷贝
        BeanUtil.copyProperties(userDTO, sysUser);
        // 加密后的密码
        sysUser.setPassword(PreUtil.encode(userDTO.getPassword()));
        baseMapper.insertUser(sysUser);
        SysUserRole sysUserRole = new SysUserRole();
        sysUserRole.setRoleId(14);
        sysUserRole.setUserId(sysUser.getUserId());
        return userRoleService.save(sysUserRole);
    }

    @Override
    public boolean updateUserInfo(SysUser sysUser) {
        return baseMapper.updateById(sysUser) > 0;
    }

    @Override
    public SysUser findSecurityUserByUser(SysUser sysUser) {
        LambdaQueryWrapper<SysUser> select = Wrappers.<SysUser>lambdaQuery()
                .select(SysUser::getUserId, SysUser::getUsername, SysUser::getPassword);
        if (StrUtil.isNotEmpty(sysUser.getUsername())) {
            select.eq(SysUser::getUsername, sysUser.getUsername());
        } else if (StrUtil.isNotEmpty(sysUser.getPhone())) {
            select.eq(SysUser::getPhone, sysUser.getPhone());
        } else if (ObjectUtil.isNotNull(sysUser.getUserId()) && sysUser.getUserId() != 0) {
            select.eq(SysUser::getUserId, sysUser.getUserId());
        }
        return baseMapper.selectOne(select);
    }

    @Override
    public boolean doPostSignUp(SysUser user) {
        // 进行账号校验
        SysUser sysUser = findSecurityUserByUser(new SysUser().setUsername(user.getUsername()));
        if (ObjectUtil.isNull(sysUser)) {
            throw new PreBaseException("账号不存在");
        }
        Integer userId = sysUser.getUserId();
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        //将业务系统的用户与社交用户绑定
        socialRedisHelper.doPostSignUp(user.getKey(), userId);
        return true;
    }

    private SysUser findSecurityUser(String userIdOrUserNameOrPhone) {
        LambdaQueryWrapper<SysUser> select = Wrappers.<SysUser>lambdaQuery()
                .select(SysUser::getUserId, SysUser::getUsername, SysUser::getPassword)
                .eq(SysUser::getUsername, userIdOrUserNameOrPhone)
                .or()
                .eq(SysUser::getPhone, userIdOrUserNameOrPhone)
                .or()
                .eq(SysUser::getUserId, userIdOrUserNameOrPhone);
        return baseMapper.selectOne(select);
    }

    /**
     * 文件导入
     * @param file
     * @Author zhb
     * @return
     */
    @Override
    public String readExcelFile(MultipartFile file) {
        String result = "";
        SysUserExcelUtil excelUtil = new SysUserExcelUtil();
        List<UserDTO> ilist = excelUtil.getExcelInfo(file);
        if (ilist != null && !ilist.isEmpty()){
            // 不为空  加入数据库
            for (UserDTO userDTO:ilist){
                SysUser sysUser = new SysUser();
                BeanUtils.copyProperties(userDTO, sysUser);
                // 查询用户名是否存在
                SysUser byUserInfoName = findSecurityUser(sysUser.getUsername());
                if (ObjectUtil.isNull(byUserInfoName)) {
                    // 默认密码 123456
                    sysUser.setPassword(PreUtil.sm4EncryptECB("xxzx@#123"));
                    sysUser.setLockFlag("0");  // 默认账号正常
                    sysUser.setDelFlag("0");
                    List<SysDept> sysDepts = sysDeptService.selectAllDept();
                    // 如果存在该部门与该部门关联
                    if (sysDepts.stream().anyMatch(w->w.getName().equals(sysUser.getDept()))){
                        List<SysDept> collect = sysDepts.stream().filter(d -> Objects.equals(d.getName(), sysUser.getDept())).collect(Collectors.toList());
                        for (SysDept sy:collect){
                            sysUser.setDeptId(sy.getDeptId());
                        }
                    }else {
                        // 不存在此处需要新建一个部门
                        SysDept sysDept = new SysDept();
                        sysDept.setParentId(0);
                        sysDept.setName(sysUser.getDept());
                        sysDept.setSort(0);
                        deptService.insertDpet(sysDept);
                        sysUser.setDeptId(sysDept.getDeptId());
                    }
                    baseMapper.insertUser(sysUser);
                    userRoleService.remove(Wrappers.<SysUserRole>lambdaQuery().eq(SysUserRole::getUserId, sysUser.getUserId()));
                    SysUserRole sysUserRole = new SysUserRole();
                    switch (sysUser.getRole()) {
                        case "一般用户":
                            sysUserRole.setRoleId(3);
                            break;
                        case "部门管理员":
                            sysUserRole.setRoleId(2);
                            break;
                        case "超级管理员":
                            sysUserRole.setRoleId(1);
                            break;
                    }
                    sysUserRole.setUserId(sysUser.getUserId());
                    userRoleService.save(sysUserRole);
                }else {
                    BeanUtils.copyProperties(userDTO, sysUser);
                    baseMapper.updateByUseName(sysUser);
                }
            }
            result = "上传成功";
        }else {
            result = "上传失败";
        }
        return result;
    }
//    // 判断部门是否存在  不存在则新增部门
//    public static void decideDept(SysUser sysUser){
//        // 获取所有部门信息
//        List<SysDept> sysDepts = sysDeptService.selectDeptList();
//        if (sysDepts.stream().anyMatch(w->w.getName().equals(sysUser.getDept()))){
//            sysDepts.forEach(SysDept::getDeptId);
//        }
//
//    }
}
