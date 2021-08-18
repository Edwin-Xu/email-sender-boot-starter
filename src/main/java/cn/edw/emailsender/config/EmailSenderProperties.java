package cn.edw.emailsender.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author taoxu.xu
 * @date 8/18/2021 7:05 PM
 */
@Data
@ConfigurationProperties(prefix = "email.sender")
public class EmailSenderProperties {
    /**
     * 发邮件的URL/API
     */
    private String host = "https://edwinxu.xyz";

    /**
     * port
     * */
    private int port = 7777;

    /**
     * 和host、ip组成起来构成访问接口，默认使用POST提交邮件发送请求
     * */
    private String api = "/email/send";

    /**
     * 和 host:port/api 等价
     * */
    private String url = "https://edwinxu.xyz:7777/email/send";
}
