package devoxx.microframeworks.exos.routes;

import feign.FeignException;
import spark.Request;
import spark.Response;

import javax.servlet.http.HttpServletResponse;

import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static javax.servlet.http.HttpServletResponse.SC_NOT_FOUND;

public class ErrorRoute {

    public void handleNotFound(Exception exception, Request request, Response response) {
        prepareResponse(response, SC_NOT_FOUND, "Not Found", exception);
    }

    public void handleBadRequest(Exception exception, Request request, Response response) {
        prepareResponse(response, SC_BAD_REQUEST, "Bad Request", exception);
    }

    public void handleForbidden(Exception exception, Request request, Response response) {
        prepareResponse(response, HttpServletResponse.SC_FORBIDDEN, "Forbidden", exception);
    }

    private void prepareResponse(Response response, int statusCode, String defaultMsg, Exception exception) {
        String msg = exception.getMessage();
        msg = (msg == null || msg.isEmpty()) ? defaultMsg : msg;

        response.status(statusCode);
        response.body(msg);
        response.type("plain/text");
    }

    public void handleFeignException(Exception e, Request request, Response response) {
        FeignException feignException = (FeignException) e;
        prepareResponse(response, feignException.status(), e.getMessage(), e);
    }
}
