package hzau.sa.trainingReport.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import hzau.sa.msg.entity.BaseVO;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 *  VO
 * @author wuyihu
 * @date 2020-08-19
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("trainingReport")
public class TrainingReportVO extends BaseVO{

	private static final long serialVersionUID = 1L;

	@TableId(type=IdType.AUTO)
	private Integer trainingReportId;
	private String studentId;
	private Integer cropId;
	private String trainingReportName;
	private Integer score;
	private String reviewStatus;
	private String teacherId;
	private Integer batch;
	private String comments;

	public TrainingReportVO(Integer cropId,String studentId,Integer batch,String trainingReportName,String teacherId){
		this.cropId=cropId;
		this.studentId=studentId;
		this.batch=batch;
		this.trainingReportName=trainingReportName;
		this.teacherId=teacherId;
		this.comments="";
		this.reviewStatus="待评语";
	}
}