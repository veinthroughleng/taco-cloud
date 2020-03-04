package veinthrough.taco.service.rest.security.exception;

class BaseException extends RuntimeException {

    BaseException(String message) {
        super(message);
    }

    BaseException(String message, Throwable cause) {
        super(message, cause);
    }
}

