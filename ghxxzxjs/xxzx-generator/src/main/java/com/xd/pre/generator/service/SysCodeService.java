package com.xd.pre.generator.service;

import com.xd.pre.generator.domain.CodeGenConfig;
import com.xd.pre.generator.domain.SysColumnEntity;
import com.xd.pre.generator.domain.SysTableEntity;

import java.util.List;

/**
 * @Classname SysCodeService
 * @Description 代码生成服务类
 */
public interface SysCodeService {


    List<SysTableEntity> findTableList(String tableSchema);

    List<SysColumnEntity> findColumnList(String tableName, String tableSchema);

    /**
     * 代码生成
     * @param codeGenConfig
     * @return
     */
    boolean generatorCode(CodeGenConfig codeGenConfig);;
}
