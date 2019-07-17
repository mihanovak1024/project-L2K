package concurrency.rxjava.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.net.HttpHeaders;

import concurrency.rxjava.network.interfaces.DataProvider;

public class HttpClientDataProvider<T> implements DataProvider<T> {

    private static final int HTTP_RESPONSE_OK = 200;
    private static final int TIMEOUT_MILLIS = 10 * 1000;
    private static final String GET_REQUEST = "GET";

    @Override
    public T getSingleData(String url, Class<T> clazz) {
        HttpURLConnection httpURLConnection = null;
        try {
            URL requestUrl = new URL(url);
            httpURLConnection = (HttpURLConnection) requestUrl.openConnection();
            httpURLConnection.setConnectTimeout(TIMEOUT_MILLIS);
            httpURLConnection.setReadTimeout(TIMEOUT_MILLIS);

            // set headers
            Map<String, String> headers = getHeaderMap();
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                httpURLConnection.setRequestProperty(entry.getKey(), entry.getValue());
            }

            httpURLConnection.setRequestMethod(GET_REQUEST);

            int responseCode = httpURLConnection.getResponseCode();
            if (responseCode != HTTP_RESPONSE_OK) {
                return null;
            }

            String jsonString = transformInputStreamToJsonString(httpURLConnection.getInputStream());
            T desiredObjectResponse = parseJsonStringToClass(jsonString, clazz);

            return desiredObjectResponse;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        }

        return null;
    }

    @Override
    public List<T> getDataList(String url, Class<T> clazz) {
        return null;
    }

    private Map<String, String> getHeaderMap() {
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put(HttpHeaders.ACCEPT, "application/json");

        return headerMap;
    }

    private String transformInputStreamToJsonString(InputStream inputStream) throws IOException {
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            try {
                String str;
                while ((str = br.readLine()) != null) sb.append(str).append('\n');
            } finally {
                br.close();
            }
        } catch (UnsupportedEncodingException e) {
            throw new IOException(e);
        }

        return sb.toString();
    }

    private T parseJsonStringToClass(String jsonString, Class<T> clazz) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        T objectData = mapper.readValue(jsonString, clazz);
        return objectData;
    }
}
