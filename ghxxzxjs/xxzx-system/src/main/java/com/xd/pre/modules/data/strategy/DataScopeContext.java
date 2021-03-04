package com.xd.pre.modules.data.strategy;

import com.xd.pre.modules.data.enums.DataScopeTypeEnum;
import com.xd.pre.modules.sys.dto.RoleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Classname DataScopeContext
 * @Description 创建环境角色Context:
 */
@Service
public class DataScopeContext {

    @Autowired
    private final Map<String, AbstractDataScopeHandler> strategyMap = new ConcurrentHashMap<>();

    /**
     * Component里边的1是指定其名字，这个会作为key放到strategyMap里
     * @param strategyMap
     */
    public DataScopeContext(Map<String, AbstractDataScopeHandler> strategyMap) {
        strategyMap.forEach(this.strategyMap::put);
    }

    public List<Integer> getDeptIdsForDataScope(RoleDTO roleDto, Integer type) {
        return strategyMap.get(String.valueOf(type)).getDeptIds(roleDto, DataScopeTypeEnum.valueOf(type));
    }
}
