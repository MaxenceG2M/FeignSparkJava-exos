package devoxx.microframeworks.exos.routes;

import devoxx.microframeworks.exos.models.CellarEntry;
import devoxx.microframeworks.exos.models.User;
import devoxx.microframeworks.exos.services.AuthenticationService;
import devoxx.microframeworks.exos.services.CellarService;
import devoxx.microframeworks.exos.services.Services;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;

import java.util.List;

public class CellarRoute {
    private static final Logger LOG = LoggerFactory.getLogger(CellarRoute.class);
    private final CellarService cellarService;
    private final AuthenticationService authenticationService;

    public CellarRoute() {
        super();
        this.cellarService = Services.INSTANCE.get(CellarService.class);
        this.authenticationService = Services.INSTANCE.get(AuthenticationService.class);
    }

    public List<CellarEntry> handleMyCellar(Request request, Response response) {
        User user = this.getUser(request);
        LOG.info("Get cellar for {}", user.getEmail());
        return null; // TODO Exercice 2.2: retourner la cave de l'utilisateur
    }

    public int handleDrink(Request request, Response response) {
        User user = this.getUser(request);
        String wid = null; // TODO Exercice 2.2: récupérer le paramètre
        LOG.info("{} drink {}", user, wid);
        return -1; // TODO Exercice 2.2: décrémenter le nombre de bouteilles de l'utilisateur pour ce vin
    }

    public boolean handleFavorite(Request request, Response response) {
        User user = this.getUser(request);
        String wid = null; // TODO Exercice 2.2: récupérer le paramètre
        boolean favorite = false; // TODO Exercice 2.2: récupérer le body
        LOG.info("{} favorite:{} {}", user, favorite, wid);
        return false; // TODO Exercice 2.2: mettre à jour le favorit pour de ce vin pour l'utilisateur
    }

    private User getUser(Request request) {
        String token = Routes.getAuthenticationToken(request);
        return authenticationService.getUser(token);
    }

}
