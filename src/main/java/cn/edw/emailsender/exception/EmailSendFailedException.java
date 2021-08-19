package cn.edw.emailsender.exception;

/**
 * TODO 这里声明为非运行时异常更好，显式告诉用户需要处理
 * @author taoxu.xu
 * @date 8/18/2021 7:36 PM
 */
public class EmailSendFailedException extends RuntimeException {
    public EmailSendFailedException(String msg){
        super(msg);
    }
    public EmailSendFailedException(){
        super("Sending the emails failed, please try again!");
    }
}
