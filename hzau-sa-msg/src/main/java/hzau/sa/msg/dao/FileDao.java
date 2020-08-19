package hzau.sa.msg.dao;

import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import hzau.sa.msg.entity.FileVO;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * File Mapper 接口
 * @author lvhao
 * @date 2020-08-18
 */
@Repository
@Mapper
public interface FileDao extends BaseMapper<FileVO> {

}