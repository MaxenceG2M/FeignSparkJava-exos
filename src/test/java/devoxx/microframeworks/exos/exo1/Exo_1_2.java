package devoxx.microframeworks.exos.exo1;

import devoxx.microframeworks.exos.models.Wine;
import devoxx.microframeworks.exos.services.ReferenceService;
import devoxx.microframeworks.exos.services.Services;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class Exo_1_2 {

    private ReferenceService service;
    private static final Logger LOG = LoggerFactory.getLogger(Exo_1_2.class);

    @Before
    public void init() {
        service = Services.INSTANCE.get(ReferenceService.class);
    }

    @After
    public void clean() {
        this.service = null;
    }

    @Test
    public void testFindById() throws Exception {
        String wid = "0617b04b-c156-4f5f-92a8-5d033c75a948";
        Wine wine;

        wine = service.findById(wid);
        LOG.info("Result : {}", wine);

        assertNotNull("User can find an existing wine", wine);
        assertEquals("The wine should match the id", wid, wine.getId());
    }


    @Test
    public void testFindAll() throws Exception {
        List<Wine> wines;
        wines = service.findAll();

        LOG.info("Result : {}", wines);
        assertNotNull("User can retrieve all wines", wines);
        assertEquals("The list should be completed", 323, wines.size());
    }

    @Test
    public void testSearch() throws Exception {
        List<Wine> wines;

        wines = service.search("poulet");

        LOG.info("Result : {}", wines);
        assertNotNull("User can search wine", wines);
        assertEquals("The list should have 15 wine matching 'poulet'",15, wines.size());
    }

}

