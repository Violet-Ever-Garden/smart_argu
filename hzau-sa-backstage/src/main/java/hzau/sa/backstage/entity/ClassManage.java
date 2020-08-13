package hzau.sa.backstage.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author LvHao
 * @Description : 班级管理的返回值实体
 * @date 2020-08-12 15:54
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassManage {

    /**
     * ID
     */
    private Integer classId;

    /**
     * 名称
     */
    private String className;

    /**
     * 年级
     */
    private String classGrade;

    /**
     * 班级地块
     */
    private List<String> classFields;

    /**
     * 班级监视器
     */
    private List<String> classMonitor;
}
