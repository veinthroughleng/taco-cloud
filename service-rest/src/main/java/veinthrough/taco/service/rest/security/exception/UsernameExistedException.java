package veinthrough.taco.service.rest.security.exception;

public class UsernameExistedException extends BaseException {

    public UsernameExistedException(String msg) {
        super(msg);
    }

    public UsernameExistedException(String msg, Throwable t) {
        super(msg, t);
    }
}