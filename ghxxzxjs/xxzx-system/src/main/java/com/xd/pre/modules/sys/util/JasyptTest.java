package com.xd.pre.modules.sys.util;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.EnvironmentPBEConfig;
import org.junit.Test;

/**
 * ClassName test
 * Description
 * Create by zhb
 * Date 2021/3/5 11:01
 */
public class JasyptTest {

    // 先运行这个测试生成一个密文
    @Test
    public void testEncrypt() throws Exception {
        StandardPBEStringEncryptor standardPBEStringEncryptor = new StandardPBEStringEncryptor();
        EnvironmentPBEConfig config = new EnvironmentPBEConfig();

        config.setAlgorithm("PBEWithMD5AndDES");          // 加密的算法，这个算法是默认的
        config.setPassword("EbfYkitulv73I2p0mXI50JMXoaxZTKJ1");    // 加密的密钥   配置文件处以固定
        standardPBEStringEncryptor.setConfig(config);
        String plainText = "root";   // 此处内容输入自己的账号或者密码生成密文
        String encryptedText = standardPBEStringEncryptor.encrypt(plainText);
        System.out.println(encryptedText);
    }

    // 次测试方法可以检测密码是否转义正确
    @Test
    public void testDe() throws Exception {
        StandardPBEStringEncryptor standardPBEStringEncryptor = new StandardPBEStringEncryptor();
        EnvironmentPBEConfig config = new EnvironmentPBEConfig();

        config.setAlgorithm("PBEWithMD5AndDES");
        config.setPassword("EbfYkitulv73I2p0mXI50JMXoaxZTKJ1");
        standardPBEStringEncryptor.setConfig(config);
        String encryptedText = "wRQNoM+KoPM6VSmg8QIKcA==";  // 输入第一个测试生成的密文
        String plainText = standardPBEStringEncryptor.decrypt(encryptedText);
        System.out.println(plainText);
    }
}