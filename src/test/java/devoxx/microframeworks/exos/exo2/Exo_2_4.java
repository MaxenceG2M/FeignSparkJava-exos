package devoxx.microframeworks.exos.exo2;

import devoxx.microframeworks.exos.Main;
import devoxx.microframeworks.exos.TestUtils;
import devoxx.microframeworks.exos.models.CellarEntry;
import devoxx.microframeworks.exos.models.Comment;
import feign.*;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static spark.Spark.stop;

public class Exo_2_4 {
    private static final Logger LOG = LoggerFactory.getLogger(Exo_2_4.class);
    private ErrorTester errorTester;

    @Before
    public void init() throws Exception {
        Main.main("0");
        int port = TestUtils.awaitRunningPort();

        errorTester = new Feign.Builder()
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .target(ErrorTester.class, "http://localhost:" + port + "/");
    }

    @After
    public void clean() {
        stop();
        errorTester = null;
    }

    @Test
    public void testForbidden() throws Exception {
        String token = "plop.plaf.plouf";
        LOG.info("Try with an invalid token: {}", token);

        try {
            errorTester.getMyCellar(token);
            fail("Should send a 403");
        } catch (FeignException fe) {
            assertEquals("Expected a 403 status", 403, fe.status());
        }
    }

    @Test
    public void testBadRequest() throws Exception {
        String token = TestUtils.createToken("toto@plop.fr", "admin");
        try {
            errorTester.createOrder(token, "plop");
            fail("Should send a 400");
        } catch (FeignException fe) {
            assertEquals("Expected a 400 status", 400, fe.status());
        }
    }

    @Test
    public void testNotFound() throws Exception {
        String token = TestUtils.createToken("toto@plop.fr", "admin");
        String wid = "plop";

        try {
            errorTester.addComment(token, wid, "Lorem Ipsum");
            fail("Should send a 404");
        } catch (FeignException fe) {
            assertEquals("Expected a 404 status", 404, fe.status());
        }
    }

    private interface ErrorTester {
        @RequestLine("GET /api/cellar")
        @Headers("Authorization: Bearer {token}")
        List<CellarEntry> getMyCellar(@Param("token") String token);

        @RequestLine("POST /api/cart/order")
        @Headers("Authorization: Bearer {token}")
        void createOrder(@Param("token") String token, Object body);

        @RequestLine("POST /api/wine/{wid}/comments")
        @Headers("Authorization: Bearer {token}")
        @Body("{message}")
        Comment addComment(@Param("token") String token, @Param("wid") String wid, @Param("message") String message);
    }

}
