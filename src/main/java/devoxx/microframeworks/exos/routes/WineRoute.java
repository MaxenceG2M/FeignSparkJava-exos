package devoxx.microframeworks.exos.routes;

import devoxx.microframeworks.exos.models.Wine;
import devoxx.microframeworks.exos.models.WineDetail;
import devoxx.microframeworks.exos.services.CommentService;
import devoxx.microframeworks.exos.services.ReferenceService;
import devoxx.microframeworks.exos.services.Services;
import devoxx.microframeworks.exos.services.StockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;

import java.util.List;

public class WineRoute {
    private static final Logger LOG = LoggerFactory.getLogger(CellarRoute.class);
    private final ReferenceService referenceService;
    private final CommentService commentService;
    private final StockService stockService;

    public WineRoute() {
        super();
        this.referenceService = Services.INSTANCE.get(ReferenceService.class);
        this.commentService = Services.INSTANCE.get(CommentService.class);
        this.stockService = Services.INSTANCE.get(StockService.class);
    }

    public WineDetail handleFindById(Request request, Response response) {
        String wid = request.params("wid");
        LOG.info("Wine {}", wid);
        WineDetail result = new WineDetail();
        // TODO Exercice Bonus 2.5: utiliser les CompletableFuture de Java 8 pour exécuter le code de façon asynchrone
        result.setWine(referenceService.findById(wid));
        result.setComments(commentService.findByWine(wid));
        result.setStock(stockService.findByWine(wid));
        return result;
    }

    public List<Wine> handleSearch(Request request, Response response) {
        String query = request.queryParams("q");
        LOG.info("Search wines {}", query);
        return referenceService.search(query);
    }
}
