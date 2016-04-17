package devoxx.microframeworks.exos;

import devoxx.microframeworks.exos.routes.CellarRoute;
import devoxx.microframeworks.exos.routes.CommentRoute;
import devoxx.microframeworks.exos.routes.OrderRoute;
import devoxx.microframeworks.exos.routes.WineRoute;
import devoxx.microframeworks.exos.services.AuthenticationService;
import devoxx.microframeworks.exos.services.ReferenceService;
import spark.ResponseTransformer;

import static spark.Spark.*;

public class Main {
    public static void main(String... args) {
        Configuration configuration = Configuration.INSTANCE;
        ResponseTransformer encoder = object -> configuration.getGson().toJson(object);

        // Port
        int port = args.length > 0 ? Integer.parseInt(args[0]) : configuration.getPort();
        port(port);

        // Frontend
        staticFileLocation("/public");

        // XXX to configure frontend
        get("api/config.js", (request, response) -> {
            String loginUrl = configuration.getUrl(AuthenticationService.class);
            String imgUrl = configuration.getUrl(ReferenceService.class);
            return String.format("angular.module('webFrontend')" +
                    ".constant('config', {loginUrl: \"%s\", imgUrl: \"%s\"})", loginUrl, imgUrl);
        });

        // Wine
        WineRoute wineRoute = new WineRoute();
        get("api/wine/:wid", wineRoute::handleFindById, encoder);
        get("api/wine", wineRoute::handleSearch, encoder);

        // TODO Exercice 2.2: déclarer les routes pour la cave
        CellarRoute cellarRoute = new CellarRoute();
        // get("api/cellar", ...);
        // post("api/cellar/drink/:wid", ...);
        // post("api/cellar/favorite/:wid", ...);

        // TODO Exercice 2.3: ajouter la route pour le commentaire
        CommentRoute commentRoute = new CommentRoute();

        // Order
        OrderRoute orderRoute = new OrderRoute();
        post("api/cart/order", orderRoute::handleOrder, encoder);

        // TODO Exercice 2.4: gèrer les errueurs

        // CORS
        options("/*", (request, response) -> "");
        after((request, response) -> {
            response.header("Access-Control-Allow-Methods", "GET,PUT,POST,DELETE,OPTIONS");
            response.header("Access-Control-Allow-Origin", "*");
            response.header("Access-Control-Allow-Headers", "Content-Type,Authorization,X-Requested-With,Content-Length,Accept,Origin,");
            response.header("Access-Control-Allow-Credentials", "true");
        });
    }

}
