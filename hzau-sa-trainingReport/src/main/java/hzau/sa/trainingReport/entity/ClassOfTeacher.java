package hzau.sa.trainingReport.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author LvHao
 * @Description :
 * @date 2020-08-19 0:38
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassOfTeacher {

    /**
     * 教师管理班级ID
     */
    private Integer classId;

    /**
     * 班级名称
     */
    private String className;

    /**
     * 班级所属年级
     */
    private String classGrade;

    /**
     * 班级所属地块
     */
    private List<String> classField;

}
