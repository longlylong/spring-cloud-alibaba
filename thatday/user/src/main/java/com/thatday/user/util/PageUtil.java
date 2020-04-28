package com.thatday.user.util;

import com.thatday.common.model.PageInfo;
import com.thatday.common.utils.TemplateCodeUtil;
import org.springframework.data.domain.Page;

import java.util.List;

//分页数据快速转换
public class PageUtil {

    public static <T> PageInfo<T> setInfo(Integer curPage, Page<T> fromPage) {
        PageInfo<T> pageInfo = new PageInfo<>();
        pageInfo.setCurPage(curPage);
        pageInfo.setTotalCount(fromPage.getTotalElements());
        pageInfo.setTotalPage(fromPage.getTotalPages());
        pageInfo.setDataList(fromPage.getContent());
        return pageInfo;
    }

    public static <T, Y> PageInfo<T> setInfo(Integer curPage, Page<Y> fromPage, Class<T> clazz) {
        return setInfo(curPage, fromPage, clazz, null);
    }

    public static <T, Y> PageInfo<T> setInfo(Integer curPage, Page<Y> fromPage, Class<T> clazz, TemplateCodeUtil.OnTransListener<T, Y> onTransListener) {
        PageInfo<T> pageInfo = new PageInfo<>();
        pageInfo.setCurPage(curPage);
        pageInfo.setTotalCount(fromPage.getTotalElements());
        pageInfo.setTotalPage(fromPage.getTotalPages());
        try {
            List<T> transTo = TemplateCodeUtil.transTo(fromPage.getContent(), clazz, onTransListener);
            pageInfo.setDataList(transTo);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pageInfo;
    }
}
