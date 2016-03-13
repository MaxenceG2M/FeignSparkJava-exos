package devoxx.microframeworks.exos.exo2;

import devoxx.microframeworks.exos.Main;
import devoxx.microframeworks.exos.TestUtils;
import devoxx.microframeworks.exos.models.CellarEntry;
import devoxx.microframeworks.exos.services.CellarService;
import devoxx.microframeworks.exos.services.Services;
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

public class Exo_2_2 {

    private Cellar cellar;
    private static final Logger LOG = LoggerFactory.getLogger(Exo_2_2.class);

    @Before
    public void init() throws Exception {
        Main.main("0");
        int port = TestUtils.awaitRunningPort();

        cellar = new Feign.Builder()
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .target(Cellar.class, "http://localhost:" + port + "/");

        // Ensure toto has something in his cellar
        CellarService service = Services.INSTANCE.get(CellarService.class);
        service.addQuantity("toto@plop.fr", "0617b04b-c156-4f5f-92a8-5d033c75a948", 1);
    }

    @After
    public void clean() {
        stop();
        cellar = null;
    }

    @Test
    public void testMyCellar() throws Exception {
        String email = "toto@plop.fr";
        String token = TestUtils.createToken(email, "admin");
        List<CellarEntry> result = cellar.getMyCellar(token);
        LOG.info("Cellar for {}: {}", email, result);

        assertNotNull("A user can retrieve his cellar", result);
        assertFalse("Toto should have wine in his cellar", result.isEmpty());
    }

    @Test
    public void testDrink() throws Exception {
        String email = "toto@plop.fr";
        String token = TestUtils.createToken(email, "admin");
        String wid = "0617b04b-c156-4f5f-92a8-5d033c75a948";

        // Ensure we can drink a bottle
        CellarService service = Services.INSTANCE.get(CellarService.class);
        service.addQuantity(email, wid, 1);

        int result = cellar.drink(token, wid, -1);
        assertTrue("The number of remaining bottle should be >= 0", result >= 0);
    }

    @Test
    public void testFavorite() throws Exception {
        String email = "toto@plop.fr";
        String token = TestUtils.createToken(email, "admin");
        String wid = "0617b04b-c156-4f5f-92a8-5d033c75a948";
        boolean result = cellar.setFavorite(token, wid, true);

        assertTrue("The wine should be set to favorite", result);
    }

    private interface Cellar {
        @RequestLine("GET /api/cellar")
        @Headers("Authorization: Bearer {token}")
        List<CellarEntry> getMyCellar(@Param("token") String token);

        @RequestLine("POST /api/cellar/drink/{wid}")
        @Headers("Authorization: Bearer {token}")
        @Body("{qty}")
        int drink(@Param("token") String token, @Param("wid") String wid, @Param("qty") int qty);

        @RequestLine("POST /api/cellar/favorite/{wid}")
        @Headers("Authorization: Bearer {token}")
        @Body("{bool}")
        boolean setFavorite(@Param("token") String token, @Param("wid") String wid, @Param("bool") boolean bool);
    }
}
