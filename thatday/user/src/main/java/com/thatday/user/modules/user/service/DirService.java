package com.thatday.user.modules.user.service;

import com.thatday.user.modules.user.entity.Dir;
import org.springframework.stereotype.Service;

@Service
public interface DirService {

    Dir save(String id);
}
