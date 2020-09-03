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
 * @author wyh
 * @date 2020-08-26
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class BaseModel{

    private static final long serialVersionUID = 1L;
    @ExcelIgnore
    private Integer baseId;
    @ExcelProperty("基地名称")
    private String baseName;
    @ExcelProperty("基地地址")
    private String address;
    @ExcelProperty("联系人")
    private String contactPerson;
    @ExcelProperty("联系电话")
    private String phoneNumber;
    @ExcelProperty("所属学校")
    private String schoolName;
}