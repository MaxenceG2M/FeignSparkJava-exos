package devoxx.microframeworks.exos.services;

import devoxx.microframeworks.exos.models.Comment;

import java.util.List;

// TODO Exercice 1.3: ajouter les annotations feign
public interface CommentService {

    List<Comment> findByWine(String wineId);

    Comment addComment(String wineId, Comment comment);
}
