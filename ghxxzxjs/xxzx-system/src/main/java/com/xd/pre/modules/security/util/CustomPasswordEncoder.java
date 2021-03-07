package com.xd.pre.modules.security.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @auther:zlk
 * @date:2021/3/6
 * @description:
 */
public class CustomPasswordEncoder extends BCryptPasswordEncoder {
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        if (encodedPassword != null && encodedPassword.length() != 0) {
            if(encodedPassword.equals(rawPassword.toString())) {
                return true;
            }
        }
        return super.matches(rawPassword,encodedPassword);
    }


}
