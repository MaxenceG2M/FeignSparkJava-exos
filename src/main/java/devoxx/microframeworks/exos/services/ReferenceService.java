package devoxx.microframeworks.exos.services;

import devoxx.microframeworks.exos.models.Wine;

import java.util.List;

// TODO Exercice 1.2: ajouter les annotations feign
public interface ReferenceService {

    List<Wine> findAll();

    Wine findById(String id);

    List<Wine> search(String query);


}
