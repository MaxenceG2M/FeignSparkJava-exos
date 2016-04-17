package devoxx.microframeworks.exos.exo1;

import devoxx.microframeworks.exos.TestUtils;
import devoxx.microframeworks.exos.models.User;
import devoxx.microframeworks.exos.services.AuthenticationService;
import devoxx.microframeworks.exos.services.Services;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class Exo_1_1 {

    private AuthenticationService service;
    private static final Logger LOG = LoggerFactory.getLogger(Exo_1_1.class);

    @Before
    public void init() {
        service = Services.INSTANCE.get(AuthenticationService.class);
    }

    @After
    public void clean() {
        this.service = null;
    }

    @Test
    public void testGetUser() throws Exception {
        String email = "toto@plop.fr";
        String password = "admin";
        String token = TestUtils.createToken(email, password);
        User user = service.getUser(token);
        LOG.info("Token : {} \n User: {}", token, user);
        assertNotNull("User can authenticate",user);
        assertEquals("User can retrieve is data", email, user.getEmail());
    }


}
