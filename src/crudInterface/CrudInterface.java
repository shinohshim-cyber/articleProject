package crudInterface;

import dto.ArticleDto;
import dto.CommentDto;

import java.util.List;

public interface CrudInterface {
    // 게시글
    List<ArticleDto> all();
    void newArticle(ArticleDto article);
    ArticleDto detail(Long id);
    boolean delete(Long id);
    void update(ArticleDto article);

    // 댓글
    void insertComment(CommentDto comment);
    void updateComment(CommentDto comment);
    void deleteComment(Long ariticlId, Long deleteCommentId);
}
