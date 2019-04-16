package com.thatday.tinyid.server.factory.impl;

import com.thatday.tinyid.base.factory.AbstractIdGeneratorFactory;
import com.thatday.tinyid.base.generator.IdGenerator;
import com.thatday.tinyid.base.generator.impl.CachedIdGenerator;
import com.thatday.tinyid.base.service.SegmentIdService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author du_imba
 */
@Component
public class IdGeneratorFactoryServer extends AbstractIdGeneratorFactory {

    private static final Logger logger = LoggerFactory.getLogger(CachedIdGenerator.class);
    @Autowired
    private SegmentIdService tinyIdService;

    @Override
    public IdGenerator createIdGenerator(String bizType) {
        logger.info("createIdGenerator :{}", bizType);
        return new CachedIdGenerator(bizType, tinyIdService);
    }
}
