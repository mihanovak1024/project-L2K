package common.httputils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.annotation.Nonnull;

import common.httputils.exceptions.HttpRequestException;

public class HttpRequestHelper {
    private static final int REQUEST_TIMEOUT_MILLIS = 10 * 1000; // 10 seconds

    public InputStream makeRequestForUrl(@Nonnull String url) throws HttpRequestException {
        try {
            HttpURLConnection urlConnection;

            URL requestUrl = new URL(url);
            urlConnection = (HttpURLConnection) requestUrl.openConnection();
            urlConnection.setReadTimeout(REQUEST_TIMEOUT_MILLIS);
            urlConnection.setConnectTimeout(REQUEST_TIMEOUT_MILLIS);
            urlConnection.setRequestMethod(RequestType.GET.name());

            InputStream inputStream = urlConnection.getInputStream();
            return new BufferedInputStream(inputStream);
        } catch (IOException e) {
            throw new HttpRequestException(e.getCause());
        }
    }
}
