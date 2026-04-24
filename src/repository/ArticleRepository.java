package repository;

import crudInterface.CrudInterface;
import dto.ArticleDto;
import dto.CommentDto;
import java.util.ArrayList;
import java.util.List;

public class ArticleRepository implements CrudInterface {
    //  게시글 자동 증가 ID (철자 주의: ariticlId)
    public static Long ariticlId = 1L;
    //  댓글 자동 증가 ID
    public static Long commentId = 1L;
    private final List<ArticleDto> articles = new ArrayList<>();

    public ArticleRepository() {}

    @Override
    public void newArticle(ArticleDto dto) {
        articles.add(dto);
    }

    @Override
    public ArticleDto detail(Long id) {
        ArticleDto result = null;
        for(ArticleDto dto : articles){
            if(dto.getId() == id) {
                result = dto;
                break;
            }
        }

        return result;
    }

    @Override
    public boolean delete(Long id) {
        boolean result = false;

        for(ArticleDto dto : articles){
            if(dto.getId() == id) {
                dto.getCommentList().clear();
                articles.remove(dto);
                result = true;
                break;
            }
        }
        return result;
    }

    @Override
    public List<ArticleDto> all() {
        return articles;
    }

    @Override
    public void insertComment(CommentDto dtoComment) {
        for(ArticleDto dto : articles){
            if(dto.getId() == dtoComment.getArticleId()) {
                dto.getCommentList().add(dtoComment);
                break;
            }
        }
    }

    @Override
    public void updateComment(CommentDto dtoComment) {
    }

    @Override
    public void deleteComment(Long ariticlId, Long deleteCommentId) {
        List<CommentDto> list = null;
        for(ArticleDto dto : articles){
            if(dto.getId() == ariticlId) {
                list = dto.getCommentList();
                break;
            }
        }
        if(list != null){
            for(CommentDto dto : list){
                if(dto.getCommentId() == deleteCommentId) {
                    list.remove(dto);
                    break;
                }
            }
        }
    }

    @Override
    public void update(ArticleDto updateData) {

    }
}
