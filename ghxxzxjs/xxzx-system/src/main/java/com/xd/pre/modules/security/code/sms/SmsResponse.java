package com.xd.pre.modules.security.code.sms;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @Classname SmsResponse
 * @Description TODO
 */
@Setter
@Getter
@ToString
public class SmsResponse {

    private String smsPhone;
    private String smsTime;
    private String smsCode;


}
