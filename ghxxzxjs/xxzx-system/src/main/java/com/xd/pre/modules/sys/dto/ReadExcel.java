package com.xd.pre.modules.sys.dto;

import lombok.Data;

/**
 * @ClassName ReadExcel
 * @Description 用于存放excel的相关信息
 * @Author MSI
 * @Date 2020/7/10 15:51
 * @Version 1.0
 **/
@Data
public class ReadExcel {
    /**
     * 总行数
     */
    private int totalRows = 0;
    /**
     * 总条数
     */
    private int totalCells = 0;
    /**
     * 错误信息收集
     */
    private String errorMsg;
}
