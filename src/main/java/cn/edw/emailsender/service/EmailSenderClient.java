package cn.edw.emailsender.service;

import cn.edw.emailsender.exception.EmailNotSetException;
import cn.edw.emailsender.exception.EmailSendFailedException;
import cn.edw.emailsender.exception.SubjectNotSetException;
import lombok.Data;
import org.apache.http.Consts;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * 邮件发送client
 *
 * @author taoxu.xu
 * @date 8/18/2021 7:26 PM
 */
@Data
public class EmailSenderClient {
    /**
     * 目标邮箱
     */
    private String[] emails;
    /**
     * 主题
     */
    private String subject;
    /**
     * 正文
     */
    private String content;

    /**
     * 附件
     */
    private File[] files;

    /**
     * 发送邮件调用的URI， 这里赋予一个默认值，在autoconfig时应该从获取配置进行覆盖。
     */
    private String uri = "https://edwinxu.xyz:7777/email/send";

    public EmailSenderClient() {
    }

    public EmailSenderClient(String[] emails, String subject, String content, File[] files) {
        this.emails = emails;
        this.subject = subject;
        this.content = content;
        this.files = files;
    }


    private static final ContentType OCTEC_STREAM = ContentType.create("application/octet-stream", StandardCharsets.UTF_8);

    public void send() {
        if (emails == null || emails.length == 0) {
            throw new EmailNotSetException();
        }
        if (subject == null || subject.isEmpty()) {
            throw new SubjectNotSetException();
        }
        send(emails, subject, content, files);
    }

    /**
     * 发送邮件
     * */
    public void send(String[] emails, String subject, String content, File... files) {
        final CloseableHttpClient httpClient = HttpClients.createDefault();

        HttpPost httpPost = new HttpPost(uri);

        // 设置长连接
        httpPost.setHeader("Connection", "keep-alive");
        // 创建 HttpPost 参数
        List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        // 拼接邮箱
        String targetEmails = "";
        for (String email : emails) {
            targetEmails += email + ";";
        }

        final StringBody emailBody = new StringBody(targetEmails, ContentType.create("text/plain", Consts.UTF_8));
        final StringBody subjectBody = new StringBody(subject, ContentType.create("text/plain", Consts.UTF_8));
        final StringBody contentBody = new StringBody(content, ContentType.create("text/plain", Consts.UTF_8));


        final MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.setCharset(StandardCharsets.UTF_8);

        // 依次添加文件
        if (files != null) {
            for (File f : files) {
                if (f != null) {
                    builder.addBinaryBody("files", f, OCTEC_STREAM, f.getName());
                }
            }
        }

        builder.addPart("emails", emailBody);
        builder.addPart("subject", subjectBody);
        builder.addPart("content", contentBody);

        httpPost.setEntity(builder.build());

        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpPost);
            final int statusCode = response.getStatusLine().getStatusCode();
            // 通过状态码判断是否成功
            if (statusCode != 200) {
                throw new EmailSendFailedException();
            }
        } catch (IOException e) {
            throw new EmailSendFailedException(e.getMessage());
        } finally {
            try {
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
