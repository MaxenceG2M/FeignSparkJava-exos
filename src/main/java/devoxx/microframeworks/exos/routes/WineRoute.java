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
import java.util.NoSuchElementException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static java.util.concurrent.CompletableFuture.allOf;
import static java.util.concurrent.CompletableFuture.completedFuture;

public class WineRoute {
    private static final Logger LOG = LoggerFactory.getLogger(CellarRoute.class);
    private final ReferenceService referenceService;
    private final CommentService commentService;
    private final StockService stockService;
    private final Executor executor;

    public WineRoute() {
        super();
        this.referenceService = Services.INSTANCE.get(ReferenceService.class);
        this.commentService = Services.INSTANCE.get(CommentService.class);
        this.stockService = Services.INSTANCE.get(StockService.class);
        this.executor = Executors.newCachedThreadPool();
    }

    public WineDetail handleFindById(Request request, Response response) {
        String wid = request.params("wid");
        LOG.info("Wine {}", wid);
        WineDetail result = new WineDetail();
        try {
            allOf(
                    // get wine
                    completedFuture(wid)
                            .thenApplyAsync(referenceService::findById, executor)
                            .thenAccept(result::setWine),
                    // get comments
                    completedFuture(wid)
                            .thenApplyAsync(commentService::findByWine, executor)
                            .thenAccept(result::setComments),
                    // get stock
                    completedFuture(wid)
                            .thenApplyAsync(stockService::findByWine, executor)
                            .thenAccept(result::setStock)
            ).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new NoSuchElementException("Wine not found: " + wid);
        }
        return result;
    }

    public List<Wine> handleSearch(Request request, Response response) {
        String query = request.queryParams("q");
        LOG.info("Search wines {}", query);
        return referenceService.search(query);
    }
}
