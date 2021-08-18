package cn.edw.emailsender.autoconfig;

import cn.edw.emailsender.config.EmailSenderProperties;
import cn.edw.emailsender.service.EmailSenderClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * @author taoxu.xu
 * @date 8/18/2021 7:21 PM
 */
@EnableConfigurationProperties(EmailSenderProperties.class)
@Configuration
public class EmailSenderAutoConfiguration {
    @Resource
    private EmailSenderProperties properties;

    @Bean
    @ConditionalOnMissingBean
    public EmailSenderClient getEmailSenderClient() {
        EmailSenderClient emailSenderClient = new EmailSenderClient();
        // 设置EmailSenderClient请求发送邮件的URI
        String url = properties.getUrl();
        if (url == null || url.isEmpty()) {
            String api = properties.getApi();
            if (api == null) {
                api = "";
            }
            // 拼接URI
            url = properties.getHost() + ":" + properties.getPort()
                    + (api.startsWith("/") ? api.substring(1) : api);
        }
        emailSenderClient.setUri(url);
        return emailSenderClient;
    }
}
