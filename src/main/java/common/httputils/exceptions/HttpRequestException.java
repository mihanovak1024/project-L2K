package common.httputils.exceptions;

import javax.annotation.Nonnull;

public class HttpRequestException extends Exception {

    public HttpRequestException() {
        super();
    }

    public HttpRequestException(@Nonnull String detailMessage) {
        super(detailMessage);
    }

    public HttpRequestException(@Nonnull String detailMessage, @Nonnull Throwable exceptionCause) {
        super(detailMessage, exceptionCause);
    }

    public HttpRequestException(@Nonnull Throwable exceptionCause) {
        super(exceptionCause);
    }
}
