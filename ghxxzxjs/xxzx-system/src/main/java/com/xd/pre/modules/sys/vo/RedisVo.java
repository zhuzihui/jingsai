package com.xd.pre.modules.sys.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @Classname RedisVo
 * @Description redisVo
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RedisVo implements Serializable {

    /**
     * key
     */
    private String key;
    /**
     * value
     */
    private String value;

    /**
     * 过期时间
     */
    private long expireTime;
}
