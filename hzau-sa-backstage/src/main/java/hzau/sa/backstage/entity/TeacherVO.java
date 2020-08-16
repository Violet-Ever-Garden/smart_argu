package hzau.sa.backstage.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import hzau.sa.msg.entity.BaseVO;
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
        private static final String DEFAULT_PASSWORD="123456";

        private String teacherId;

        private String password;

        private String phoneNumber;

        private String teacherName;

        private Integer isOperatemonitor;

        private String type;

        public TeacherVO(TeacherWrapper teacherWrapper){
                if (teacherWrapper.getIsOperatemonitor().equals("否")){
                        this.isOperatemonitor=0;
                }else {
                        this.isOperatemonitor=1;
                }
                this.password=TeacherVO.DEFAULT_PASSWORD;
                this.phoneNumber=teacherWrapper.getPhoneNumber();
                this.teacherId=teacherWrapper.getTeacherId();
                this.type=teacherWrapper.getType();
                this.teacherName=teacherWrapper.getTeacherName();
        }
}