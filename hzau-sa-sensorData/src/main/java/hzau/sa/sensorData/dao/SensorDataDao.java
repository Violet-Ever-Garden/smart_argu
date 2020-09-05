package hzau.sa.sensorData.dao;

import hzau.sa.sensorData.entity.GatewayNameAddress;
import org.apache.ibatis.annotations.Param;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface SensorDataDao {
    List<String> selectRegionByStudentId(@Param("studentId")String sutentId);

    List<String> selectRegionByTeacherId(@Param("teacherId") String teacherId);

    List<GatewayNameAddress> selectGatewayByRegionName(@Param("regionName") String regionName);
}
