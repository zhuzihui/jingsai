package com.xd.pre.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xd.pre.modules.sys.domain.SysRoleDept;
import com.xd.pre.modules.sys.mapper.SysRoleDeptMapper;
import com.xd.pre.modules.sys.service.ISysRoleDeptService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author QCH
 * @version 1.0
 * @description 角色与部门对应关系 服务实现类
 * @date 2021/3/5
 */
@Service
public class SysRoleDeptServiceImpl extends ServiceImpl<SysRoleDeptMapper, SysRoleDept> implements ISysRoleDeptService {


    @Override
    public List<SysRoleDept> getRoleDeptIds(int roleId) {
        return baseMapper.selectList(Wrappers.<SysRoleDept>lambdaQuery().select(SysRoleDept::getDeptId).eq(SysRoleDept::getRoleId,roleId));
    }


}
