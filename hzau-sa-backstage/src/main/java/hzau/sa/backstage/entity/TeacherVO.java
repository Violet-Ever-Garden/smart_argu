package hzau.sa.backstage.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import hzau.sa.msg.entity.BaseVO;
import hzau.sa.msg.util.ShiroKit;
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
public class TeacherVO extends BaseVO{

        private static final long serialVersionUID = 1L;
        public static final String DEFAULT_PASSWORD="123456";

        private String teacherId;
        private String teacherName;
        private String phoneNumber;
        private Integer isOperatemonitor;
        private String type;
        private String password;


        public TeacherVO(TeacherWrapper teacherWrapper){
                //转换
                if (teacherWrapper.getIsOperatemonitor().equals("否")){
                        this.isOperatemonitor=0;
                }else {
                        this.isOperatemonitor=1;
                }

                //转换
                if (teacherWrapper.getPassword()==null){
                        this.password= ShiroKit.md5(TeacherVO.DEFAULT_PASSWORD);
                }else {
                        this.password=ShiroKit.md5(teacherWrapper.getPassword());
                }

                this.phoneNumber=teacherWrapper.getPhoneNumber();
                this.teacherId=teacherWrapper.getTeacherId();
                this.type=teacherWrapper.getType();
                this.teacherName=teacherWrapper.getTeacherName();
        }
}