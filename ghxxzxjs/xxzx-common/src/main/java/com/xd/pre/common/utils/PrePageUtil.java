package com.xd.pre.common.utils;

import cn.hutool.core.util.PageUtil;
import lombok.experimental.UtilityClass;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Classname PageUtil
 * @Description TODO
 */
@UtilityClass
public class PrePageUtil extends PageUtil {

    /**
     * List 分页
     * @param page
     * @param size
     * @param list
     * @return
     */
    public List toPage(int page, int size , List list) {
        int fromIndex = page * size;
        int toIndex = page * size + size;

        if(fromIndex > list.size()){
            return new ArrayList();
        } else if(toIndex >= list.size()) {
            return list.subList(fromIndex,list.size());
        } else {
            return list.subList(fromIndex,toIndex);
        }
    }

}
