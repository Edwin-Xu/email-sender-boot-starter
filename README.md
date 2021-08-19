# Email Sender Boot Starter
Using my email sending tool, develop a spring boot starter, which
can be imported easily!

## Usage
build a Spring Boot project, then import the starter:
```xml
    <dependency>
        <groupId>cn.edw</groupId>
        <artifactId>email-sender-boot-starter</artifactId>
        <version>1.0</version>
    </dependency>
```
Define a service class, which using EmailSenderClient as a bean. Then call EmailSenderClient's methods to send emails.
```java
@Service
public class EmailSenderService {
    /**
     * EmailSenderService只能作为一个Bean使用，不能脱离Spring环境
     * */
    @Resource
    private EmailSenderClient emailSenderClient;

    public void sendEmail(String []emails, String subject, String content, File...files){
        emailSenderClient.send(emails, subject, content, files);
    }
}
```
Test the function in a test case:
```java
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class EmailSenderServiceTest {
    @Autowired
    private EmailSenderService emailSenderService;

    @Test
    public void sendEmailTest(){
        String[]emails = new String[]{
                "youremail01@XX.com",
                "youremail02@XX.com"
        };
        String subject = "Email Sender Boot Start Test";
        String content = "Just some test, nothing else!";
        
        // add files if you want.
        File file = null;
        emailSenderService.sendEmail(emails, subject, content, file);
    }
}
```
A few seconds later, you can receive message in your email accounts.
