package com.xd.pre.modules.sys.service;

import com.xd.pre.modules.sys.domain.SysTenant;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 租户表 服务类
 * </p>
 *
 */
public interface ISysTenantService extends IService<SysTenant> {

    /**
     * 保存租户
     *
     * @param sysTenant
     * @return
     */
    boolean saveTenant(SysTenant sysTenant);


    /**
     * 获取正常租户
     *
     * @return
     */
    List<SysTenant> getNormalTenant();
}
