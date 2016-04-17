package devoxx.microframeworks.exos.exo1;

import devoxx.microframeworks.exos.models.Comment;
import devoxx.microframeworks.exos.services.CommentService;
import devoxx.microframeworks.exos.services.Services;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class Exo_1_3 {

    private static final Logger LOG = LoggerFactory.getLogger(Exo_1_3.class);
    private CommentService service;

    @Before
    public void init() {
        service = Services.INSTANCE.get(CommentService.class);
    }

    @After
    public void clean() {
        this.service = null;
    }

    @Test
    public void testFindComments() throws Exception {
        String wid = "80ce8353-279e-4cf4-800f-7bc3c3ce398e";
        List<Comment> comments;

        comments = service.findByWine(wid);
        LOG.info("Result : {}", comments);

        assertNotNull("User should retrieve comments for a wine", comments);
        assertEquals("The wine should have 2 comments", 2, comments.size());
    }

    @Test
    public void testAddComment() throws Exception {
        String wid = "f1667d0b-9cf4-4d58-8c1a-bbd9d1f46995";
        int initialSize = service.findByWine(wid).size();

        // Create Comment
        Comment comment = new Comment();
        comment.setAuthor("Toto");
        comment.setDate(Instant.now().toString());
        comment.setEmail("toto@plop.fr");
        comment.setMessage("Lorem ipsum dolor sit amet");

        comment = service.addComment(wid, comment);
        LOG.info("Result : {}", comment);

        assertNotNull("User can create a new comment", comment);
        assertNotNull("The comment should have an id", comment.getId());

        int size = service.findByWine(wid).size();
        assertEquals("The comment should be added", initialSize + 1, size);
    }
}
