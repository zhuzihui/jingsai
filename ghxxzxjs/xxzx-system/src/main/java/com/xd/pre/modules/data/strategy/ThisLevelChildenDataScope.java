package com.xd.pre.modules.data.strategy;

import com.xd.pre.modules.data.enums.DataScopeTypeEnum;
import com.xd.pre.security.util.SecurityUtil;
import com.xd.pre.modules.sys.dto.RoleDTO;
import com.xd.pre.modules.sys.service.ISysDeptService;
import com.xd.pre.modules.sys.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Classname ThisLevelChildenDataScope
 * @Description 本级以及子级
 */
@Component("3")
public class ThisLevelChildenDataScope implements AbstractDataScopeHandler {

    @Autowired
    private ISysUserService userService;

    @Autowired
    private ISysDeptService deptService;


    @Override
    public List<Integer> getDeptIds(RoleDTO roleDto, DataScopeTypeEnum dataScopeTypeEnum) {
        Integer deptId = userService.findByUserInfoName(SecurityUtil.getUser().getUsername()).getDeptId();
        return deptService.selectDeptIds(deptId);
    }
}
