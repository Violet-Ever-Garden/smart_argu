package hzau.sa.backstage.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author wyh17
 * @version 1.0
 * @date 2020/8/20 16:26
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class StudentModel {
    private static final long serialVersionUID = 1L;

    private String studentId;

    private String studentName;

    private String gradeName;

    private String className;

    private String phoneNumber;

    private Integer isOperatemonitor;

    private Integer isOperatewfm;
}
