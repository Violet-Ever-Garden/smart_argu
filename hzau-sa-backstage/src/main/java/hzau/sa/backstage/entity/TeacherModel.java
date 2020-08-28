package hzau.sa.backstage.entity;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 *  VO
 * @author wuyihu
 * @date 2020-08-13
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class TeacherModel {

    private static final long serialVersionUID = 1L;

    private String teacherId;

    private String phoneNumber;

    private String teacherName;

    /**
     * 是对应1，否对应0
     */
    private String isOperatemonitor;

    private String type;

}
