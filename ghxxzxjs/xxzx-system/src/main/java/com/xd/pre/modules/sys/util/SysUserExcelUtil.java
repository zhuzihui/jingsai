package com.xd.pre.modules.sys.util;


import com.xd.pre.modules.sys.dto.ReadExcel;
import com.xd.pre.modules.sys.dto.UserDTO;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName ExpertExcelUtil
 * @Description 文件导入工具类
 * @Author zhb
 * @Date 2021/3/05 16:17
 * @Version 1.0
 **/
public class SysUserExcelUtil {
    /**
     * 先创建一个实体类
     */
    ReadExcel readExcel = new ReadExcel();
    /**
     * 读取Excel文件，获取信息集合
     * @param mFile
     * @return
     */
    public List<UserDTO> getExcelInfo(MultipartFile mFile) {
        // 获取文件名
        String fileName = mFile.getOriginalFilename();
        List<UserDTO> ilist = null;
        try {
            // 验证文件名是否合格
            if(!validateExcel(fileName)){
                //不合格的话直接return
                return null;
            }
            // 根据文件名判断是2003版本的还是2007版本的
            boolean isExcel2003 = true;
            if(isExcel2007(fileName)){
                isExcel2003 = false;
            }
            ilist= createExcel(mFile.getInputStream(), isExcel2003);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ilist;
    }
    /**
     * 判断是不是2003版本的excel
     * @param filePath
     * @return
     */
    public static boolean isExcel2003(String filePath){
        return filePath.matches("^.+\\.(?i)(xls)$");
    }
    /**
     * 判断是不是2007版本的excel
     * @param filePath
     * @return
     */
    public static boolean isExcel2007(String filePath){
        return filePath.matches("^.+\\.(?i)(xlsx)$");
    }
    /**
     * 判断是不是excel文件格式
     * @param filePath
     * @return
     */
    public boolean validateExcel(String filePath){
        if(filePath == null || !(isExcel2003(filePath) || isExcel2007(filePath))){
            readExcel.setErrorMsg("文件名不是excel格式");
            return false;
        }
        return true;
    }
    /**
     * 读取excel里面的信息
     */
    public List<UserDTO> readExcelValue(Workbook wb){
        List<UserDTO> ilist=new ArrayList<>();
        // 先得到一个sheet
        Sheet sheet = wb.getSheetAt(0);
        // 得到excel里面的行数
        int totalRows = sheet.getPhysicalNumberOfRows();
        readExcel.setTotalRows(totalRows);
        // 得到excel里面的列，前提是有行
        // 大于1是因为我从第二行就是数据了，这个大家看情况而定
        if(totalRows >1 && sheet.getRow(0)!=null){
            int totalCells = sheet.getRow(0).getPhysicalNumberOfCells();
            readExcel.setTotalCells(totalCells);
        }
        for (int r = 1 ; r < totalRows; r++){
            Row row = sheet.getRow(r);
            if(row == null){
                continue;//如果行为空的话直接中断
            }
            UserDTO userDTO = new UserDTO();
            // 循环excel的列
            for(int c = 0; c<readExcel.getTotalCells() ; c++){
                Cell cell = row.getCell(c);
                if(cell != null){
                    // 根据excel需要导入的列数来写
                    if(c == 0){
                        if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
                            // 如果是纯数字,比如你写的是38,
                            // cell.getNumericCellValue()获得是38.0,
                            // 通过截取字符串去掉.0获得25
                            String name = String.valueOf(cell.getNumericCellValue());
                            // 截取如果length()-2为零了，就说明只有一位数，就直接截取0到1就行
                            userDTO.setUsername(name.substring(0,name.length()-2>0?name.length()-2:1));
                        }else{
                            // 如果不是纯数字可以直接获得名称
                            userDTO.setUsername(cell.getStringCellValue());
                        }
                    }else if (c == 1){
                        if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
                            String firstDept = String.valueOf(cell.getNumericCellValue());
                            userDTO.setDept(firstDept.substring(0,firstDept.length()-2>0?firstDept.length()-2:1));
                        }else{
                            userDTO.setDept(cell.getStringCellValue());
                        }
                    }else if (c == 2){
                        if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
                            String firstDept = String.valueOf(cell.getNumericCellValue());
                            userDTO.setEmail(firstDept.substring(0,firstDept.length()-2>0?firstDept.length()-2:1));
                        }else{
                            userDTO.setEmail(cell.getStringCellValue());
                        }
                    }else if (c == 3){
                        if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
                            DecimalFormat df=new DecimalFormat("0");
                            String firstDept = String.valueOf(df.format(cell.getNumericCellValue()));
                            userDTO.setPhone(firstDept);
                        }else{
                            userDTO.setPhone(cell.getStringCellValue());
                        }
                    }else if (c == 4){
                        if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
                            String firstDept = String.valueOf(cell.getNumericCellValue());
                            userDTO.setRole(firstDept.substring(0,firstDept.length()-2>0?firstDept.length()-2:1));
                        }else{
                            userDTO.setRole(cell.getStringCellValue());
                        }
                    }
                }
            }
            // 最后将这些全部添加到ilist中
            ilist.add(userDTO);
        }
        return ilist;
    }
    public List<UserDTO> createExcel(InputStream is , boolean isExcel2003){
        List<UserDTO> ilist = null;
        try {
            Workbook wb = null;
            if(isExcel2003){
                // 如果是2003版本的就new一个2003的wb出来
                wb = new HSSFWorkbook(is);
            }else{
                // 否则就new 一个2007版的出来
                wb = new XSSFWorkbook(is);

            }
            // 再让wb去解析readExcelValue(Workbook wb)方法
            ilist = readExcelValue(wb);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ilist;
    }
}
