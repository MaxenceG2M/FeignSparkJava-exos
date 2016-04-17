package devoxx.microframeworks.exos.routes;

import devoxx.microframeworks.exos.models.Order;
import devoxx.microframeworks.exos.models.OrderEntry;
import devoxx.microframeworks.exos.models.User;
import devoxx.microframeworks.exos.services.AuthenticationService;
import devoxx.microframeworks.exos.services.CellarService;
import devoxx.microframeworks.exos.services.Services;
import devoxx.microframeworks.exos.services.StockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class OrderRoute {
    private static final Logger LOG = LoggerFactory.getLogger(CellarRoute.class);
    private final StockService stockService;
    private final CellarService cellarService;
    private final AuthenticationService authenticationService;
    private final Executor executor;

    public OrderRoute() {
        super();
        this.stockService = Services.INSTANCE.get(StockService.class);
        this.cellarService = Services.INSTANCE.get(CellarService.class);
        this.authenticationService = Services.INSTANCE.get(AuthenticationService.class);
        this.executor = Executors.newCachedThreadPool();

    }

    public List<String> handleOrder(Request request, Response response) {
        User user = this.getUser(request);
        Order order = Routes.parseBody(request, Order.class);
        LOG.info("{} create order {}", user, order);
        // TODO Exercice Bonus 2.5: utiliser le stream parallèle de Java 8 pour exécuter le code de façon asynchrone
        return order.getEntries().stream()
                .map(orderEntry -> applyOrder(orderEntry, user))
                .collect(Collectors.toList());
    }

    private String applyOrder(OrderEntry orderEntry, User user) {
        String result = stockService.createOrder(orderEntry.getWid(), orderEntry.getQuantity());
        cellarService.addQuantity(user.getEmail(), orderEntry.getWid(), orderEntry.getQuantity());
        return result;
    }

    private User getUser(Request request) {
        String token = Routes.getAuthenticationToken(request);
        return authenticationService.getUser(token);
    }
}
