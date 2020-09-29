package hzau.sa.sensorData.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import hzau.sa.sensorData.entity.GatewayNameAddress;
import hzau.sa.sensorData.entity.SensorDataRecord;
import org.apache.ibatis.annotations.Param;
import org.mapstruct.Mapper;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@Mapper
public interface SensorDataRecordDao extends BaseMapper<SensorDataRecord> {
    List<String> selectRegionByStudentId(@Param("studentId")String sutentId);

    List<String> selectRegionByTeacherId(@Param("teacherId") String teacherId);

    List<GatewayNameAddress> selectGatewayByRegionName(@Param("regionName") String regionName);

    /**
     * 获取某一传感器的最新数据
     * @param logo
     * @return
     */
    List<SensorDataRecord> getNowSensorData(@Param("logo") String logo);

    /**
     * 查询某一网关的历史数据
     * @param logo 网关
     * @param startTime 起始时间
     * @param endTime 终止时间
     * @return
     */
    List<SensorDataRecord> getOneGatewayHistoryData(@Param("logo") String logo, @Param("startTime") LocalDateTime  startTime , @Param("endTime") LocalDateTime endTime);

    List<SensorDataRecord> getOneGatewayHistoryDataPage(IPage<SensorDataRecord> page,@Param("logo") String logo, @Param("startTime") LocalDateTime startTime , @Param("endTime") LocalDateTime endTime);

}
