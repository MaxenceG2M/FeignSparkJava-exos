package devoxx.microframeworks.exos.services;

import devoxx.microframeworks.exos.Configuration;
import devoxx.microframeworks.exos.services.impl.CellarServiceImpl;
import feign.Feign;
import feign.FeignException;
import feign.Response;
import feign.Util;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;

public enum Services {
    INSTANCE;

    private final Map<Class<?>, Object> map;

    Services() {
        map = new ConcurrentHashMap<>();
    }

    public <T> T get(Class<T> aClass) {
        return (T) map.computeIfAbsent(aClass, key -> create(key));
    }

    private Object create(Class<?> aClass) {
        if (CellarService.class.equals(aClass)) {
            return new CellarServiceImpl();
        }
        // Feign service
        String url = Configuration.INSTANCE.getUrl(aClass);
        return createFeignService(aClass, url);
    }

    private <T> T createFeignService(Class<T> aClass, String url) {
        return new Feign.Builder()
                // TODO Exercice Bonus 1.6: Logger les appels via Feign
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .errorDecoder(this::decodeError)
                .target(aClass, url);
    }

    private Exception decodeError(String msg, Response response) {
        try {
            String content = Util.toString(response.body().asReader());
            switch (response.status()) {
                case HttpServletResponse.SC_BAD_REQUEST:
                    return new IllegalArgumentException(content);
                case HttpServletResponse.SC_NOT_FOUND:
                    return new NoSuchElementException(content);
                case HttpServletResponse.SC_FORBIDDEN:
                    return new SecurityException(content);
                default:
                    return FeignException.errorStatus(msg, response);
            }
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
