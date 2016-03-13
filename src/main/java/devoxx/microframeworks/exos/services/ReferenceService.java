package devoxx.microframeworks.exos.services;

import devoxx.microframeworks.exos.models.Wine;
import feign.Param;
import feign.RequestLine;

import java.util.List;

public interface ReferenceService {

    @RequestLine("GET /api/wines")
    List<Wine> findAll();

    @RequestLine("GET /api/wines/{id}")
    Wine findById(@Param("id") String id);

    @RequestLine("GET /api/wines?q={query}")
    List<Wine> search(@Param("query") String query);


}
