package devoxx.microframeworks.exos.exo2;

import devoxx.microframeworks.exos.Main;
import devoxx.microframeworks.exos.TestUtils;
import devoxx.microframeworks.exos.models.Comment;
import feign.*;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static spark.Spark.stop;

public class Exo_2_3 {

    private static final Logger LOG = LoggerFactory.getLogger(Exo_2_3.class);
    private AddComment addComment;

    @Before
    public void init() throws Exception {
        Main.main("0");
        int port = TestUtils.awaitRunningPort();

        addComment = new Feign.Builder()
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .target(AddComment.class, "http://localhost:" + port + "/");
    }

    @After
    public void clean() {
        stop();
        addComment = null;
    }

    @Test
    public void testAddComment() throws Exception {
        String email = "toto@plop.fr";
        String token = TestUtils.createToken(email, "admin");
        String wid = "0617b04b-c156-4f5f-92a8-5d033c75a948";
        String txt = "Lorem Ipsum dolor";
        Comment result;

        result = addComment.addComment(token, wid, txt);

        LOG.info("User {} add comment {}", email, result);
        assertNotNull("Comment should exists", result);
        assertNotNull("Comment should have a new id", result.getId());
        assertNotNull("Comment should have a date", result.getDate());
        assertEquals("Comment should have the same message", txt, result.getMessage());
    }

    private interface AddComment {
        @RequestLine("POST /api/wine/{wid}/comments")
        @Headers("Authorization: Bearer {token}")
        @Body("{message}")
        Comment addComment(@Param("token") String token, @Param("wid") String wid, @Param("message") String message);
    }

}
