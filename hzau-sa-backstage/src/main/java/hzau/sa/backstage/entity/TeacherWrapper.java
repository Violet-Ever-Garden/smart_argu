package hzau.sa.backstage.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

/**
 *  VO
 * @author wuyihu
 * @date 2020-08-13
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class TeacherWrapper {

    private static final long serialVersionUID = 1L;

    private String teacherId;

    private String phoneNumber;

    private String teacherName;

    /**
     * 是对应1，否对应0
     */
    private String isOperatemonitor;

    private String type;

    private String password;

    private MultipartFile multipartFile;

}