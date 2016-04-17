package devoxx.microframeworks.exos.services;

import devoxx.microframeworks.exos.models.User;
import feign.Param;
import feign.RequestLine;

import java.util.Map;

public interface AuthenticationService {

    @RequestLine("GET /api/user/{token}")
    User getUser(@Param("token") String token);

    // XXX for testing purpose
    @RequestLine("POST /api/auth/login")
    Map<String, String> login(Map<String, String> params);

}
