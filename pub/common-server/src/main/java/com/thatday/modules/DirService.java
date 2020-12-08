package com.thatday.modules;


import com.thatday.base.service.BaseService;

public interface DirService extends BaseService<Dir, String, DirDao> {

    void addDir( String title);

}
