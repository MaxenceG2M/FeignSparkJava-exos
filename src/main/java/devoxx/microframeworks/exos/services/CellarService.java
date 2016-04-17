package devoxx.microframeworks.exos.services;

import devoxx.microframeworks.exos.models.CellarEntry;

import java.util.List;

// Ceci est un service local, il n'y a pas d'annotation feign.
public interface CellarService {

    List<CellarEntry> getCellar(String email);

    boolean setFavorite(String email, String wineId, boolean favorite);

    int addQuantity(String email, String wineId, int quantity);

}
