package cn.edw.emailsender.exception;

/**
 * @author taoxu.xu
 * @date 8/18/2021 7:36 PM
 */
public class SubjectNotSetException extends RuntimeException {
    public SubjectNotSetException(){
        super("The subject have not be set yet!");
    }
}
