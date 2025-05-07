package com.berrakaraman.s19_challenge_backend.util;

import com.berrakaraman.s19_challenge_backend.dto.CommentResponse;
import com.berrakaraman.s19_challenge_backend.entity.Comment;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CommentMapper {
    public static CommentResponse toCommentResponse(Comment comment) {
        return new CommentResponse(
                comment.getId(),
                comment.getContent(),
                comment.getTweet().getId(),
                comment.getUser().getId()
        );
    }

    public static List<CommentResponse> toCommentResponseList(List<Comment> comments) {
        return comments.stream().map(CommentMapper::toCommentResponse).toList();
    }

    public static Set<CommentResponse> toCommentResponseSet(Set<Comment> comments) {
        return comments.stream().map(CommentMapper::toCommentResponse).collect(Collectors.toSet());
    }
}
