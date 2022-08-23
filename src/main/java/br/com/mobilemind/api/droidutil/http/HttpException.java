package br.com.mobilemind.api.droidutil.http;


public class HttpException extends RuntimeException {

    private final  int httpStatus;

    public HttpException(int httpStatus, String message, Exception e) {
        super(message, e);
        this.httpStatus = httpStatus;
    }

    public HttpException(int httpStatus, Exception e) {
        super(e);
        this.httpStatus = httpStatus;
    }

    public HttpException(int httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpException(int httpStatus) {
        super("unknown exception");
        this.httpStatus = httpStatus;
    }

    public int getHttpSatatus() {
        return httpStatus;
    }
}

