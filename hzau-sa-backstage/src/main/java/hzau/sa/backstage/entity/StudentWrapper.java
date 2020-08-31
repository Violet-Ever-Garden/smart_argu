package hzau.sa.backstage.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 *  学生实体类
 * @author wuyihu
 * @date 2020-08-13
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class StudentWrapper {

    @ExcelIgnore
    private static final long serialVersionUID = 1L;

    @ExcelProperty(value = "学号",index = 0)
    private String studentId;


    @ExcelProperty(value = "姓名",index = 1)
    private String studentName;

    @ExcelProperty(value = "年级",index = 2)
    private String gradeName;

    @ExcelProperty(value = "班级",index = 3)
    private String className;

    @ExcelProperty(value = "手机号",index = 4)
    private String phoneNumber;

    @ExcelProperty(value = "是否操纵监控",index =5)
    private String isOperatemonitor;

    @ExcelProperty(value = "是否操纵水肥机",index = 6)
    private String isOperatewfm;

    @ExcelIgnore
    private String password;

    public StudentWrapper(StudentVO studentVO){
        this.studentId=studentVO.getStudentId();
        this.studentName=studentVO.getStudentName();
        this.phoneNumber=studentVO.getPhoneNumber();

        if (studentVO.getIsOperatemonitor()==0){
            this.isOperatemonitor="否";
        }else {
            this.isOperatemonitor="是";
        }

        if (studentVO.getIsOperatewfm()==0){
            this.isOperatewfm="否";
        }else {
            this.isOperatewfm="是";
        }
    }
}