package hzau.sa.backstage.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 *  Model
 * @author haokai
 * @date 2020-08-13
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class TeacherClassModel {

    private Integer asTeacherclassId;

    private String className;

    private String gradeName;

    private String teacherName;

}
