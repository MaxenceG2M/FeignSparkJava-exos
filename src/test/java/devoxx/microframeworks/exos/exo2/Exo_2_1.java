package devoxx.microframeworks.exos.exo2;

import devoxx.microframeworks.exos.Main;
import devoxx.microframeworks.exos.TestUtils;
import devoxx.microframeworks.exos.models.Wine;
import devoxx.microframeworks.exos.models.WineDetail;
import feign.*;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.junit.Assert.*;
import static spark.Spark.stop;

public class Exo_2_1 {

    private WineAPI wineAPI;
    private static final Logger LOG = LoggerFactory.getLogger(Exo_2_1.class);

    @Before
    public void init() throws Exception {
        Main.main("0");
        int port = TestUtils.awaitRunningPort();

        wineAPI = new Feign.Builder()
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .target(WineAPI.class, "http://localhost:" + port + "/");

    }

    @After
    public void clean() {
        stop();
        wineAPI = null;
    }
    @Test
    public void testFindById() throws Exception {
        String wid = "0617b04b-c156-4f5f-92a8-5d033c75a948";
        String email = "toto@plop.fr";
        String token = TestUtils.createToken(email, "admin");
        WineDetail result = wineAPI.findById(token, wid);
        LOG.info("find wine {}: {}", wid, result);
        assertNotNull("A user can retrieve a wine", result);
        assertEquals("The wine id should match", wid, result.getWine().getId());
    }

    @Test
    public void testFindByIdError() throws Exception {
        String wid = "plop";
        String email = "toto@plop.fr";
        String token = TestUtils.createToken(email, "admin");

        try {
            wineAPI.findById(token, wid);
            fail();
        } catch (FeignException e) {
            // Status is 500 until error are properly handled in Exo 2.4
            assertTrue(e.status() == 404 || e.status() == 500);
        }
   }

    @Test
    public void testSearch() throws Exception {
        String search = "poulet";
        String email = "toto@plop.fr";
        String token = TestUtils.createToken(email, "admin");
        List<Wine> result = wineAPI.search(token, search);

        LOG.info("find wine matching {}: {}", search, result);

        assertNotNull("A user could search wines", result);
        assertEquals("There is 15 wine matching 'poulet' search", 15, result.size());
    }

    private interface WineAPI {
        @RequestLine("GET /api/wine/{wid}")
        @Headers("Authorization: Bearer {token}")
        WineDetail findById(@Param("token") String token, @Param("wid") String wid);

        @RequestLine("GET /api/wine?q={query}")
        @Headers("Authorization: Bearer {token}")
        List<Wine> search(@Param("token") String token, @Param("query") String query);
    }
}
