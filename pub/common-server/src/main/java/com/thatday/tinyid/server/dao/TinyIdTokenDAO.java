package com.thatday.tinyid.server.dao;

import com.thatday.tinyid.server.dao.entity.TinyIdToken;

import java.util.List;

/**
 * @author du_imba
 */
public interface TinyIdTokenDAO {
    /**
     * 查询db中所有的token信息
     *
     * @return
     */
    List<TinyIdToken> selectAll();
}
