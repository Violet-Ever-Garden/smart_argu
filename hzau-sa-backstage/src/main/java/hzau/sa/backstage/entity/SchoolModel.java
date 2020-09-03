package hzau.sa.backstage.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import hzau.sa.msg.entity.BaseVO;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 *  VO
 * @author wuyihu
 * @date 2020-08-25
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class SchoolModel{

    private static final long serialVersionUID = 1L;
    @ExcelIgnore
    private Integer schoolId;
    @ExcelProperty("学校名称")
    private String schoolName;
    @ExcelProperty("学校地址")
    private String address;
    @ExcelProperty("法人代表")
    private String legalRepresentative;
    @ExcelProperty("联系人")
    private String contactPerson;
    @ExcelProperty("联系电话")
    private String phoneNumber;
}