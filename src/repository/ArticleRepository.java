package repository;

import dto.ArticleDto;
import dto.CommentDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ArticleRepository {
    //  게시글 자동 증가 ID (철자 주의: ariticlId)
    public static Long ariticlId = 1L;
    //  댓글 자동 증가 ID
    public static Long commentId = 1L;
    private final Connection conn;

    public ArticleRepository(Connection conn) {
        this.conn = conn;
    }

    public int newArticle(ArticleDto dto) {
        PreparedStatement psmt = null;
        int result = 0;
        try{
            String sql = "INSERT INTO article  (name, title, content, inserted_date) VALUES (?,?,?,?)";
            psmt = conn.prepareStatement(sql);
            psmt.setString(1, dto.getName());
            psmt.setString(2, dto.getTitle());
            psmt.setString(3, dto.getContent());
            psmt.setTimestamp(4 , Timestamp.valueOf(LocalDateTime.now()));
            result = psmt.executeUpdate();
            psmt.close();
        }catch (Exception e){
            System.out.println("newArticle 오류 : " + e.getMessage() );
        }

        return result;
    }

    public List<ArticleDto> all() {
        List<ArticleDto> dtoList = new ArrayList<>();
        PreparedStatement psmt = null;
        ResultSet rs = null;
        try{
            String sql = "SELECT * FROM article";
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
                dtoList.add(dto);
            }
            psmt.close();
            rs.close();

            for(ArticleDto list : dtoList){
                allComment(list.getId(), list.getCommentList());
            }
        }catch (Exception e){
            System.out.println("all Error : " + e.getMessage());
        }

        return dtoList;
    }

    public void allComment(Long article_id, List<CommentDto> list){
        PreparedStatement psmt = null;
        ResultSet rs = null;
        try{
            String sql = "SELECT * FROM comments WHERE article_id = ?";
            psmt = conn.prepareStatement(sql);
            psmt.setLong(1, article_id);
            rs = psmt.executeQuery();
            while(rs.next()){
                CommentDto dto = new CommentDto();
                dto.setCommentId(rs.getLong("comment_id"));
                dto.setArticleId(rs.getLong("article_id"));
                dto.setName(rs.getString("name"));
                dto.setContent(rs.getString("content"));
                list.add(dto);
            }
            psmt.close();
            rs.close();
        }catch (Exception e){
            System.out.println("allComment Error : " + e.getMessage());
        }
    }
}
