package common.exceptions;

import javax.annotation.Nonnull;

public class InputStreamException extends Exception {

    public InputStreamException() {
        super();
    }

    public InputStreamException(@Nonnull String detailMessage) {
        super(detailMessage);
    }

    public InputStreamException(@Nonnull String detailMessage, @Nonnull Throwable exceptionCause) {
        super(detailMessage, exceptionCause);
    }

    public InputStreamException(@Nonnull Throwable exceptionCause) {
        super(exceptionCause);
    }
}
