package hzau.sa.trainingReport.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author LvHao
 * @Description : 班级所属学生返回实体
 * @date 2020-08-19 11:08
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class StudentOfClass {

    /**
     * 学号
     */
    private String studentId;

    /**
     * 电话号码
     */
    private String phoneNumber;

    /**
     * 姓名
     */
    private String studentName;

    /**
     * 年级
     */
    private String gradeName;

    /**
     * 班级
     */
    private String className;

}
