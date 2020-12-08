package com.thatday.modules;


import com.thatday.base.service.BaseServiceImpl;
import com.thatday.common.utils.IdGen;
import org.springframework.stereotype.Service;

@Service
public class DirServiceImpl extends BaseServiceImpl<Dir, String, DirDao> implements DirService {

    @Override
    public String customDatabaseId() {
        return "DR" + IdGen.getNextTimeCode();
    }

    @Override
    public void addDir(String title) {
        Dir dir = new Dir();
        dir.setTitle(title);
        saveOrUpdate(dir);
    }
}
