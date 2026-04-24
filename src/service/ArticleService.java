package service;

import dto.ArticleDto;
import dto.CommentDto;
import repository.ArticleRepository;

import java.time.LocalDateTime;
import java.util.List;

public class ArticleService {
    private final ArticleRepository repository;

    public ArticleService(ArticleRepository repository) {
        this.repository = repository;
    }

    public void newArticle(String name, String title, String content) {
        ArticleDto dto = new ArticleDto(ArticleRepository.ariticlId, name, title, content, LocalDateTime.now());
        int result = repository.newArticle(dto);
        if(result > 0){
            System.out.println("새글 저장되었습니다.");
        }
    }

    public List<ArticleDto> all() {
        return repository.all();
    }

    public List<ArticleDto> detail(int id) {
        return repository.detail(id);
    }

    public void commentAdd(Long id, String name, String content) {
        CommentDto dto = new CommentDto(null, id, name, content);
        int result = repository.insertComment(dto);
        if(result > 0){
            System.out.println("새댓글 저장되었습니다.");
        }
    }

    public List<CommentDto> detailComment(int commentId) {
       return repository.getComments(commentId);
    }

    public void commentUpdate(CommentDto commentDto) {
        repository.commentUpdate(commentDto);
    }

    public void commentDelete(int commentId) {
        int result = repository.commentDelete(commentId);
        if(result > 0){
            System.out.println("댓글이 삭제되었습니다.");
        }
    }

    public void delete(int id) {
        int result = repository.delete(id);
        if(result > 0) {
            System.out.println("ID : " + id + " 게시글 삭제되었습니다.");
        }
    }

    public void update(ArticleDto oldData) {
        repository.update(oldData);
    }
}
