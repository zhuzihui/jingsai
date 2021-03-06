package com.xd.pre.modules.sys.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xd.pre.modules.sys.domain.SysDept;
import com.xd.pre.modules.sys.domain.SysRole;
import com.xd.pre.modules.sys.domain.SysRoleDept;
import com.xd.pre.modules.sys.dto.DeptDTO;
import com.xd.pre.modules.sys.mapper.SysDeptMapper;
import com.xd.pre.modules.sys.service.ISysDeptService;
import com.xd.pre.modules.sys.service.ISysRoleDeptService;
import com.xd.pre.modules.sys.service.ISysRoleService;
import com.xd.pre.modules.sys.util.PreUtil;
import com.xd.pre.modules.sys.vo.DeptTreeVo;
import com.xd.pre.security.PreSecurityUser;
import com.xd.pre.security.util.SecurityUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author QCH
 * @version 1.0
 * @description 部门管理 服务实现类
 * @date 2021/3/5
 */
@Service
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDept> implements ISysDeptService {

    @Resource
    private ISysRoleService roleService;
    @Resource
    private ISysRoleDeptService roleDeptService;

    @Override
    public List<SysDept> selectDeptList() {
        PreSecurityUser user = SecurityUtil.getUser();
        List<SysRole> rolesByUserId = roleService.findRolesByUserId(user.getUserId());
        SysRole sysRole = rolesByUserId.get(0);
        List<SysRoleDept> roleDeptIds = roleDeptService.getRoleDeptIds(sysRole.getRoleId());
        List<Integer> deptIds = roleDeptIds.parallelStream().map(r -> {
            return r.getDeptId();
        }).collect(Collectors.toList());
        List<SysDept> depts = baseMapper.selectList(Wrappers.<SysDept>lambdaQuery().select(SysDept::getDeptId, SysDept::getName, SysDept::getParentId, SysDept::getSort, SysDept::getCreateTime));
        List<SysDept> collect = depts.parallelStream().filter(d -> deptIds.contains(d.getDeptId()) || d.getParentId() == 0).collect(Collectors.toList());
        /*List<SysDept> sysDepts = collect.stream()
                .filter(sysDept -> sysDept.getParentId() == 0 || ObjectUtil.isNull(sysDept.getParentId()))
                .peek(sysDept -> sysDept.setLevel(0))
                .collect(Collectors.toList());
        PreUtil.findChildren(sysDepts, depts);*/
        collect.parallelStream().forEach(c->{
            for (SysDept sysDept : collect){
                if (c.getParentId().toString().equals(sysDept.getDeptId().toString())){
                    if (sysDept.getChildren() == null){
                        sysDept.setChildren(new ArrayList<>());
                    }
                    sysDept.getChildren().add(c);
                    break;
                }
            }
        });
        List<SysDept> sysDepts = collect.parallelStream().filter(c -> c.getParentId() == 0 || ObjectUtil.isNull(c.getParentId()))
                .peek(sysDept -> sysDept.setLevel(0))
                .collect(Collectors.toList());
        PreUtil.findChildren(sysDepts, collect);
        return sysDepts;
    }


    @Override
    public boolean updateDeptById(DeptDTO entity) {
        SysDept sysDept = new SysDept();
        BeanUtils.copyProperties(entity, sysDept);
        sysDept.setUpdateTime(LocalDateTime.now());
        return this.updateById(sysDept);
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean removeById(Serializable id) {
        // 部门层级删除
        List<Integer> idList = this.list(Wrappers.<SysDept>query().lambda().eq(SysDept::getParentId, id)).stream().map(SysDept::getDeptId).collect(Collectors.toList());
        // 删除自己
        idList.add((Integer) id);
        return super.removeByIds(idList);
    }

    @Override
    public List<SysDept> selectByParentId(int pid) {
        List<SysDept> depts = baseMapper.selectList(Wrappers.<SysDept>query().lambda().select(SysDept::getDeptId, SysDept::getName, SysDept::getParentId, SysDept::getSort, SysDept::getCreateTime).eq(SysDept::getParentId,pid));
        return depts;
    }

    @Override
    public String selectDeptNameByDeptId(int deptId) {
        return baseMapper.selectOne(Wrappers.<SysDept>query().lambda().select(SysDept::getName).eq(SysDept::getDeptId, deptId)).getName();
    }

    @Override
    public List<SysDept> selectDeptListBydeptName(String deptName) {
        return null;
    }

    @Override
    public List<Integer> selectDeptIds(int deptId) {
        SysDept department = this.getDepartment(deptId);
        List<Integer> deptIdList = new ArrayList<>();
        if (department != null) {
            deptIdList.add(department.getDeptId());
            addDeptIdList(deptIdList, department);
        }
        return deptIdList;
    }

    @Override
    public List<DeptTreeVo> getDeptTree() {
        List<SysDept> depts = baseMapper.selectList(Wrappers.<SysDept>query().select("dept_id", "name", "parent_id", "sort", "create_time"));
        List<DeptTreeVo> collect = depts.stream().filter(sysDept -> sysDept.getParentId() == 0 || ObjectUtil.isNull(sysDept.getParentId()))
                .map(sysDept -> {
                    DeptTreeVo deptTreeVo = new DeptTreeVo();
                    deptTreeVo.setId(sysDept.getDeptId());
                    deptTreeVo.setLabel(sysDept.getName());
                    return deptTreeVo;

                }).collect(Collectors.toList());

        PreUtil.findChildren1(collect,depts);
        return collect;
    }


    /**
     * 根据部门ID获取该部门及其下属部门树
     */
    private SysDept getDepartment(Integer deptId) {
        List<SysDept> departments = baseMapper.selectList(Wrappers.<SysDept>query().select("dept_id", "name", "parent_id", "sort", "create_time"));
        Map<Integer, SysDept> map = departments.stream().collect(
                Collectors.toMap(SysDept::getDeptId, department -> department));

        for (SysDept dept : map.values()) {
            SysDept parent = map.get(dept.getParentId());
            if (parent != null) {
                List<SysDept> children = parent.getChildren() == null ? new ArrayList<>() : parent.getChildren();
                children.add(dept);
                parent.setChildren(children);
            }
        }
        return map.get(deptId);
    }
    private void addDeptIdList(List<Integer> deptIdList, SysDept department) {
        List<SysDept> children = department.getChildren();
        if (children != null) {
            for (SysDept d : children) {
                deptIdList.add(d.getDeptId());
                addDeptIdList(deptIdList, d);
            }
        }
    }


}
