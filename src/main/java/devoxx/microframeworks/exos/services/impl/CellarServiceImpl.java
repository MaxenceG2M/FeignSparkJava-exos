package devoxx.microframeworks.exos.services.impl;

import devoxx.microframeworks.exos.models.CellarEntry;
import devoxx.microframeworks.exos.routes.CellarRoute;
import devoxx.microframeworks.exos.services.CellarService;
import devoxx.microframeworks.exos.services.ReferenceService;
import devoxx.microframeworks.exos.services.Services;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class CellarServiceImpl implements CellarService {
    private static final Logger LOG = LoggerFactory.getLogger(CellarRoute.class);
    private final Map<String, Map<String, WineInfo>> data;
    private final ReferenceService referenceService;

    public CellarServiceImpl() {
        super();
        this.data = new ConcurrentHashMap<>();
        this.referenceService = Services.INSTANCE.get(ReferenceService.class);
    }

    @Override
    public List<CellarEntry> getCellar(String email) {
        Map<String, WineInfo> cellar = data.getOrDefault(email, Collections.emptyMap());
        LOG.info("Get cellar for {} : {}", email, cellar);
        return cellar.entrySet().stream()
                .map(this::createCellarEntry)
                .collect(Collectors.toList());
    }

    private CellarEntry createCellarEntry(Entry<String, WineInfo> entry) {
        CellarEntry result = new CellarEntry();
        result.setWine(referenceService.findById(entry.getKey()));
        result.setFavorite(entry.getValue().favorite);
        result.setQuantity(entry.getValue().quantity);
        return result;
    }

    @Override
    public boolean setFavorite(String email, String wineId, boolean favorite) {
        Map<String, WineInfo> cellar = data.getOrDefault(email, Collections.emptyMap());
        WineInfo wineInfo = cellar.get(wineId);
        if (wineInfo == null) {
            throw new IllegalArgumentException(String.format("Wine '%s' not found into '%s' cellar", wineId, email));
        }
        LOG.info("{} set {} favorite:{}", email, wineId, favorite);
        wineInfo.favorite = favorite;
        return favorite;
    }

    @Override
    public int addQuantity(String email, String wineId, int quantity) {
        Map<String, WineInfo> cellar = data.computeIfAbsent(email, key -> new ConcurrentHashMap<>()); //
        WineInfo wineInfo = cellar.computeIfAbsent(wineId, wid -> new WineInfo());
        int newQuantity = wineInfo.quantity + quantity;
        if (newQuantity < 0) {
            throw new IllegalArgumentException(
                    String.format("Cannot drink %s for Wine '%s' into '%s' cellar", -quantity, wineId, email));
        }
        LOG.info("{} add {} to {}", email, quantity, wineId);
        wineInfo.quantity = newQuantity;
        return newQuantity;
    }

    private class WineInfo {
        private int quantity;
        private boolean favorite;

        public WineInfo() {
            super();
            this.quantity = 0;
            this.favorite = false;
        }
    }
}
