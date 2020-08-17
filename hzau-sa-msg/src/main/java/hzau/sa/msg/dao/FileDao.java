package hzau.sa.msg.dao;

import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import hzau.sa.msg.entity.FileVO;

/**
 * File Mapper 接口
 * @author lvhao
 * @date 2020-08-18
 */
@Mapper
public interface FileDao extends BaseMapper<FileVO> {

}