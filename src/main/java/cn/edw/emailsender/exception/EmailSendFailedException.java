package cn.edw.emailsender.exception;

/**
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
