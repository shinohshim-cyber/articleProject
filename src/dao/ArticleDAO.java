package dao;

import crudInterface.CrudInterface;
import db.DBConn;
import dto.ArticleDto;
import dto.CommentDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ArticleDAO implements CrudInterface {
    private Connection conn;

    public ArticleDAO() {
        this.conn = DBConn.getConnection();
    }

    @Override
    public void newArticle(ArticleDto dto) {
        PreparedStatement psmt = null;
        int result = 0;
        try{
            String sql = "INSERT INTO article  (name, title, content, inserted_date) VALUES (?,?,?,?)";
            psmt = conn.prepareStatement(sql);
            psmt.setString(1, dto.getName());
            psmt.setString(2, dto.getTitle());
            psmt.setString(3, dto.getContent());
            psmt.setTimestamp(4 , Timestamp.valueOf(dto.getInsertedDate()));
            result = psmt.executeUpdate();
            psmt.close();
        }catch (Exception e){
            System.out.println("newArticle 오류 : " + e.getMessage() );
        }
    }

    @Override
    public ArticleDto detail(Long id) {
        ArticleDto dto = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;
        try{
            String sql = "SELECT * FROM article WHERE id=?";
            psmt = conn.prepareStatement(sql);
            psmt.setLong(1, id);
            rs = psmt.executeQuery();
            if(rs.next()){
                dto = new ArticleDto();
                dto.setId(rs.getLong("id"));
                dto.setName(rs.getString("name"));
                dto.setTitle(rs.getString("title"));
                dto.setContent(rs.getString("content"));
                dto.setInsertedDate(rs.getTimestamp("inserted_date").toLocalDateTime());
                dto.setCommentList(new ArrayList<>());
                if(rs.getTimestamp("updated_date") != null)
                    dto.setUpdatedDate(rs.getTimestamp("updated_date").toLocalDateTime());

            }
            psmt.close();
            rs.close();

            if(dto != null) {
                getArticleComments(dto);
            }
        }catch (Exception e){
            System.out.println("detail Error : " + e.getMessage());
        }

        return dto;
    }

    @Override
    public boolean delete(Long id) {
        PreparedStatement psmt = null;
        boolean result = false;
        try{
            String sql = "DELETE  FROM article WHERE id = ?";
            psmt = conn.prepareStatement(sql);
            psmt.setLong(1, id);
            if(psmt.executeUpdate() > 0)
                result = true;
            psmt.close();
        }catch (Exception e){
            System.out.println("delete 오류 : " + e.getMessage() );
        }
        return result;
    }

    @Override
    public List<ArticleDto> all() {
        List<ArticleDto> dtoList = new ArrayList<>();
        PreparedStatement psmt = null;
        ResultSet rs = null;
        try{
            String sql = "SELECT * FROM article ORDER BY id DESC";
            psmt = conn.prepareStatement(sql);
            rs = psmt.executeQuery();
            while(rs.next()){
                //  읽어온 레코드를 담을 빈 DTO 생성
                ArticleDto dto = new ArticleDto();
                dto.setId(rs.getLong("id"));
                dto.setName(rs.getString("name"));
                dto.setTitle(rs.getString("title"));
                dto.setContent(rs.getString("content"));
                dto.setInsertedDate(rs.getTimestamp("inserted_date").toLocalDateTime());
                dto.setCommentList(new ArrayList<>());
                dtoList.add(dto);
            }
            psmt.close();
            rs.close();
            for (ArticleDto list : dtoList) {
                getArticleComments(list);
            }
        }catch (Exception e){
            System.out.println("all Error : " + e.getMessage());
        }

        return dtoList;
    }

    private  void getArticleComments(ArticleDto dtoArticle) {
        PreparedStatement psmt = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT * FROM comments WHERE article_id=?";
            psmt = conn.prepareStatement(sql);
            psmt.setLong(1, dtoArticle.getId());
            rs = psmt.executeQuery();
            while (rs.next()) {
                CommentDto dto = new CommentDto();
                dto.setCommentId(rs.getLong("comment_id"));
                dto.setArticleId(rs.getLong("article_id"));
                dto.setName(rs.getString("name"));
                dto.setContent(rs.getString("content"));
                dtoArticle.getCommentList().add(dto);
            }
            psmt.close();
            rs.close();
        } catch (Exception e) {
            System.out.println("allComment Error : " + e.getMessage());
        }
    }

    @Override
    public void insertComment(CommentDto dto) {
        PreparedStatement psmt = null;
        int result = 0;
        try{
            String sql = "INSERT INTO comments  (name, content, article_id) VALUES (?,?,?)";
            psmt = conn.prepareStatement(sql);
            psmt.setString(1, dto.getName());
            psmt.setString(2, dto.getContent());
            psmt.setLong(3, dto.getArticleId());
            result = psmt.executeUpdate();
            psmt.close();
        }catch (Exception e){
            System.out.println("insertComment 오류 : " + e.getMessage() );
        }
    }

    @Override
    public void updateComment(CommentDto comment) {
        PreparedStatement psmt = null;
        try{
            String sql = "UPDATE comments ";
            sql += "set content = ? ";
            sql += "WHERE comment_id = ?";
            psmt = conn.prepareStatement(sql);
            psmt.setString(1, comment.getContent());
            psmt.setLong(2, comment.getCommentId());
            psmt.executeUpdate();
            psmt.close();
        }catch (Exception e){
            System.out.println("commentUpdate 오류 : " + e.getMessage() );
        }
    }

    @Override
    public void deleteComment(Long ariticlId,  Long deleteCommentId) {
        PreparedStatement psmt = null;
        try{
            String sql = "DELETE  FROM comments WHERE comment_id = ?";
            psmt = conn.prepareStatement(sql);
            psmt.setLong(1, deleteCommentId);
            psmt.executeUpdate();
            psmt.close();
        }catch (Exception e){
            System.out.println("commentDelete 오류 : " + e.getMessage() );
        }
    }

    @Override
    public void update(ArticleDto updateData) {
        PreparedStatement psmt = null;
        try{
            String sql = "UPDATE article ";
            sql += "set name = ?, ";
            sql += "title = ?, ";
            sql += "content = ?, ";
            sql += "updated_date = ? ";
            sql += "WHERE ID = ?";
            psmt = conn.prepareStatement(sql);
            psmt.setString(1, updateData.getName());
            psmt.setString(2, updateData.getTitle());
            psmt.setString(3, updateData.getContent());
            psmt.setTimestamp(4, Timestamp.valueOf(updateData.getUpdatedDate()));
            psmt.setLong(5, updateData.getId());
            psmt.executeUpdate();
            psmt.close();
        }catch (Exception e){
            System.out.println("UPDATE 오류 : " + e.getMessage() );
        }
    }
}

