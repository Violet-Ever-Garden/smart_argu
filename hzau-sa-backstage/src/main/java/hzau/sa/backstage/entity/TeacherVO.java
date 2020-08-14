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

/**
 *  VO
 * @author wuyihu
 * @date 2020-08-13
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("teacher")
public class TeacherVO {

        private static final long serialVersionUID = 1L;


        /**
         *
         */
        private String teacherId;


        /**
         *
         */
        private String password;


        /**
         *
         */

        private String phoneNumber;



        /**
         *
         */
        private String teacherName;


        /**
         *
         */
        private Integer isOperatemonitor;



        /**
         *
         */
        private String type;

}