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
@ApiModel("老师model")
public class TeacherVO {

        private static final long serialVersionUID = 1L;


        /**
         *
         */
        @ApiModelProperty("id")
        @TableId(type=IdType.AUTO)
        private String teacherId;


        /**
         *
         */
        @ApiModelProperty("密码")
        private String password;



        /**
         *
         */
        @ApiModelProperty("手机号")
        private String phoneNumber;



        /**
         *
         */
        @ApiModelProperty("姓名")
        private String teacherName;


        /**
         *
         */
        @ApiModelProperty("监控权限")
        private Integer isOperatemonitor;



        /**
         *
         */
        @ApiModelProperty("类型，是否为管理员")
        private String type;

}