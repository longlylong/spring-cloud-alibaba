package com.thatday.gateway.props;

import lombok.Data;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 权限过滤
 */
@Data
@Component
@RefreshScope
public class AuthProperties {

    /**
     * 放行API集合
     */
    private final List<String> skipUrl = new ArrayList<>();

}
