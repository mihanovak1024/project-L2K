package common;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.annotation.Nonnull;

import common.exceptions.InputStreamException;

public class Util {

    public static String getStringFromInputStream(@Nonnull InputStream inputStream) throws InputStreamException {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int len;
            byte[] buffer = new byte[1024];
            while ((len = inputStream.read(buffer)) > 0) {
                baos.write(buffer, 0, len);
            }
            return new String(baos.toByteArray(), "UTF-8");
        } catch (IOException e) {
            throw new InputStreamException(e.getCause());
        }
    }
}
