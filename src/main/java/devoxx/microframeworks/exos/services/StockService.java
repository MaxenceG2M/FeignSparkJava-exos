package devoxx.microframeworks.exos.services;

import devoxx.microframeworks.exos.models.Stock;
import feign.Param;
import feign.RequestLine;

public interface StockService {

    @RequestLine("GET /api/wines/{id}/qty")
    Stock findByWine(@Param("id") String wid);

    @RequestLine("POST /api/wines/{id}/order?qty={qty}")
    String createOrder(@Param("id")String wid, @Param("qty") int quantity);

}
