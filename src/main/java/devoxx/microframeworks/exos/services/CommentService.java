package devoxx.microframeworks.exos.services;

import devoxx.microframeworks.exos.models.Comment;
import feign.Param;
import feign.RequestLine;

import java.util.List;

public interface CommentService {

    @RequestLine("GET /api/wines/{id}/comments")
    List<Comment> findByWine(@Param("id") String wineId);

    @RequestLine("POST /api/wines/{id}/comments")
    Comment addComment(@Param("id") String wineId, Comment comment);
}
