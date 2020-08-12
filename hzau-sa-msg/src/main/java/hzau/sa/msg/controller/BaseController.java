package hzau.sa.msg.controller;

import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import hzau.sa.msg.common.PageConstant;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

/**
 * @author LvHao
 * @Description : 基础控制器
 * @date 2020-08-09 15:06
 */
public class BaseController {

    @Autowired
    protected HttpServletRequest request;

    /**
     * 获取分页对象
     *
     * @param <T>
     * @return
     */
    protected <T> Page<T> getPage() {
        int index = 1;
        // 页数
        Integer cursor = Convert.toInt(request.getParameter(PageConstant.PAGE_PAGE), index);
        // 分页大小
        Integer limit = Convert.toInt(request.getParameter(PageConstant.PAGE_ROWS), PageConstant.DEFAULT_LIMIT);
        // 是否查询分页
        Boolean searchCount = Convert.toBool(request.getParameter(PageConstant.SEARCH_COUNT), true);
        limit = limit > PageConstant.MAX_LIMIT ? PageConstant.MAX_LIMIT : limit;
        Page<T> page = new Page<>(cursor, limit, searchCount);
        return page;
    }
}
