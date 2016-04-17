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
        // TODO Exercice 2.3: creer un commentaire, on doit retourner le commentaire qui est créer.
        // Note: la date doit correspondre au format ISO-8601, utilisé java.time.Instant#toString()

        return null;
    }

    private User getUser(Request request) {
        String token = Routes.getAuthenticationToken(request);
        return authenticationService.getUser(token);
    }
}
