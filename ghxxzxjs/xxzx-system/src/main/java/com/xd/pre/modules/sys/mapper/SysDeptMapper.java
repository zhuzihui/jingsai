package com.xd.pre.modules.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xd.pre.modules.sys.domain.SysDept;

import java.util.List;

/**
 * @author QCH
 * @version 1.0
 * @description 部门管理 Mapper 接口
 * @date 2021/3/5
 */
public interface SysDeptMapper extends BaseMapper<SysDept> {


    List<SysDept> selectAllDept();
}
