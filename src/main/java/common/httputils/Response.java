package common.httputils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class Response {

    private String source;
    private String data;

    public Response(@Nonnull String source, @Nullable String data) {
        this.source = source;
        this.data = data;
    }

    @Nonnull
    public String getSource() {
        return source;
    }

    @Nullable
    public String getData() {
        return data;
    }
}
