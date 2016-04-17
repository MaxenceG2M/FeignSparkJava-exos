package devoxx.microframeworks.exos.routes;

import com.google.gson.JsonParseException;
import devoxx.microframeworks.exos.Configuration;
import spark.Request;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

public interface Routes {


    static <T> T parseBody(Request request, Class<T> aClass) {
        try (ByteArrayInputStream input = new ByteArrayInputStream(request.bodyAsBytes());
             Reader reader = new InputStreamReader(input)) {
            return Configuration.INSTANCE.getGson().fromJson(reader, aClass);
        } catch (JsonParseException | IOException e) {
            throw new IllegalArgumentException("Invalid body", e);
        }
    }

    static String getAuthenticationToken(Request request) {
        String BEARER = "Bearer";
        String authorization = request.headers("Authorization");
        if (!authorization.startsWith(BEARER)) {
            throw new SecurityException("Expected bearer");
        }
        return authorization.substring(BEARER.length() + 1);
    }
}
