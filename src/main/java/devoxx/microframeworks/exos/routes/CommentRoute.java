package devoxx.microframeworks.exos.routes;

import devoxx.microframeworks.exos.models.Comment;
import devoxx.microframeworks.exos.models.User;
import devoxx.microframeworks.exos.services.AuthenticationService;
import devoxx.microframeworks.exos.services.CommentService;
import devoxx.microframeworks.exos.services.Services;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;

import java.time.Instant;

public class CommentRoute {
    private static final Logger LOG = LoggerFactory.getLogger(CommentRoute.class);
    private final CommentService commentService;
    private final AuthenticationService authenticationService;

    public CommentRoute() {
        super();
        this.commentService = Services.INSTANCE.get(CommentService.class);
        this.authenticationService = Services.INSTANCE.get(AuthenticationService.class);
    }

    public Comment handleAddComment(Request request, Response response) {
        User user = getUser(request);
        String wid = request.params("wid");

        Comment comment = new Comment();
        comment.setEmail(user.getEmail());
        comment.setAuthor(user.getName());
        comment.setMessage(request.body());
        comment.setDate(Instant.now().toString());

        return commentService.addComment(wid, comment);
    }

    private User getUser(Request request) {
        String token = Routes.getAuthenticationToken(request);
        return authenticationService.getUser(token);
    }
}
