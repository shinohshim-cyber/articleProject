package service;

import crudInterface.CrudInterface;
import dto.CommentDto;
import repository.ArticleRepository;

public class CommentService {
    private final CrudInterface repository;
    public CommentService(CrudInterface repository) {
        this.repository = repository;
    }

    public void commentAdd(Long id, String name, String content) {
        CommentDto dto = new CommentDto(ArticleRepository.commentId, id, name, content);
        repository.insertComment(dto);
        ArticleRepository.commentId++;
        System.out.println("새댓글 저장되었습니다.");
    }
    public void commentUpdate(CommentDto commentDto) {
        repository.updateComment(commentDto);
    }

    public void commentDelete(Long articleId, Long commentId) {
        repository.deleteComment(articleId, commentId);
    }
}
