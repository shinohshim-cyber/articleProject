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
    static List<ArticleDto> articleList = new ArrayList<>() ;

    public ArticleRepository() {}

    @Override
    public void newArticle(ArticleDto dto) {
        articleList.add(dto);
    }

    @Override
    public ArticleDto detail(Long id) {
        ArticleDto result = null;
        for(ArticleDto dto : articleList){
            if(dto.getId().equals(id)) {
                result = dto;
                break;
            }
        }

        return result;
    }

    @Override
    public boolean delete(Long id) {
        boolean result = false;

        for(ArticleDto dto : articleList){
            if(dto.getId().equals(id)) {
                dto.getCommentList().clear();
                articleList.remove(dto);
                result = true;
                break;
            }
        }
        return result;
    }

    @Override
    public List<ArticleDto> all() {
        return articleList;
    }

    @Override
    public void insertComment(CommentDto dtoComment) {
        ArticleDto dto = detail(dtoComment.getArticleId());
        if(dto != null) {
            dto.getCommentList().add(dtoComment);
        }
    }

    @Override
    public void updateComment(CommentDto dtoComment) {
    }

    @Override
    public void deleteComment(Long ariticlId, Long deleteCommentId) {
        ArticleDto dto = detail(ariticlId);
        if(dto != null) {
            List<CommentDto> list = dto.getCommentList();
            for(CommentDto dtoComment : list) {
                if(dtoComment.getCommentId().equals(deleteCommentId)) {
                    list.remove(dtoComment);
                    break;
                }
            }
        }
    }

    @Override
    public void update(ArticleDto updateData) {
    }
}
