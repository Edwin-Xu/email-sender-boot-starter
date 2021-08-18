package cn.edw.emailsender.exception;

/**
 * @author taoxu.xu
 * @date 8/18/2021 7:36 PM
 */
public class EmailNotSetException extends RuntimeException {
    public EmailNotSetException(){
        super("The emails have not be set yet!");
    }
}
