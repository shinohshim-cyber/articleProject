package view;

import dto.ArticleDto;
import dto.CommentDto;
import service.ArticleService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class ArticleView {
    private final Scanner sc;
    private final ArticleService service;

    public ArticleView(Scanner sc, ArticleService service) {
        this.sc = sc;
        this.service = service;
    }

    public void showNewArticle() {
        System.out.println("새글 입력창입니다.");
        System.out.printf("작성자: ");
        String name = sc.next();
        System.out.printf("제목: ");
        String title = sc.next();
        System.out.printf("내용: ");
        String content = sc.next();

        service.newArticle(name, title, content);
    }

    public void showAll() {
        List<ArticleDto> list = service.all();
        if (list.isEmpty()) {
            System.out.println("게시글이 없습니다.");
            return;
        }
        printArticle(list);
    }

    public void showDetail() {
        String name = "";
        String content = "";
        Long commentId = 0L;
        List<CommentDto> commentDtoList = null;

        System.out.println("게시글 ID : ");
        Long id = sc.nextLong();
        ArticleDto articleDto = service.detail(id);
        if(articleDto == null){
            System.out.println("해당 ID가 없습니다.");
        }else {
            printArticleOne(articleDto);
            int choice = 0;
            while(true) {
                do {
                    System.out.println("1.댓글입력  2.댓글수정  3.댓글삭제  4.돌아가기");
                    choice = sc.nextInt();
                } while (choice < 1 || choice > 4);
                switch (choice) {
                    case 1:
                        System.out.printf("댓글 등록자 이름: ");
                        name = sc.next();
                        System.out.printf("댓글 내용: ");
                        content = sc.next();
                        service.commentAdd(articleDto.getId(), name, content);
                        break;
                    case 2:
                        System.out.println("수정할 댓글 ID : ");
                        commentId = sc.nextLong();
                        commentDtoList = service.detailComment(commentId);
                        if(commentDtoList.isEmpty()){
                            System.out.println("해당 댓글 ID가 없습니다.");
                        }else {
                            System.out.printf("수정할 댓글 내용: ");
                            content = sc.next();
                            CommentDto commentDto = commentDtoList.get(0);
                            commentDto.setContent(content);
                            service.commentUpdate(commentDto);
                        }
                        break;
                    case 3:
                        System.out.println("삭제할 댓글 ID : ");
                        commentId = sc.nextLong();
                        commentDtoList = service.detailComment(commentId);
                        if(commentDtoList.isEmpty()){
                            System.out.println("해당 댓글 ID가 없습니다.");
                        }else {
                            service.commentDelete(commentId);
                        }
                        break;
                    case 4:
                        System.out.println("메인화면으로 돌아갑니다.");
                        return;
                }
            }
        }
    }

    public void printArticle( List<ArticleDto> list){
        System.out.println("=============================================");
        System.out.println("id\tname\t\t\ttitle\t\t\t작성일");
        System.out.println("=============================================");
        for (ArticleDto dto : list) {
            System.out.println(dto.getId() + "\t" + dto.getName() + "\t\t" + dto.getTitle() + "\t\t\t" + dto.getInsertedDate().toLocalDate() + " " + dto.getInsertedDate().toLocalTime());
            List<CommentDto> listComment = dto.getCommentList();
            if(listComment != null) {
                listComment.forEach(x -> System.out.println(x));
            }
        }
        System.out.println("=============================================");
    }

    public void printArticleOne(ArticleDto articleDto){
        System.out.printf("ID: ");
        System.out.println(articleDto.getId());
        System.out.printf("Name: ");
        System.out.println(articleDto.getName());
        System.out.printf("Title: ");
        System.out.println(articleDto.getTitle());
        System.out.printf("Content: ");
        System.out.println(articleDto.getContent());
        System.out.printf("작성일: ");
        System.out.println(articleDto.getInsertedDate().toLocalDate() + " " + articleDto.getInsertedDate().toLocalTime());
        if(articleDto.getUpdatedDate() != null) {
            System.out.printf("수정일: ");
            System.out.println(articleDto.getUpdatedDate().toLocalDate() + " " + articleDto.getUpdatedDate().toLocalTime());
        }
        System.out.println();
        System.out.println("\t댓글 리스트\t");
        List<CommentDto> listComment = articleDto.getCommentList();
        if(listComment != null) {
            listComment.forEach(x -> System.out.println(x));
        }
        System.out.println();
    }

    public void showDelete() {
        System.out.println("삭제할 게시글 ID : ");
        Long id = sc.nextLong();
        service.delete(id);
    }

    public void showUpdate() {
        System.out.println("수정할 게시글 ID : ");
        Long id = sc.nextLong();
        ArticleDto oldData = service.detail(id);
        if(oldData == null){
            System.out.println("해당 ID가 없습니다.");
        }else {
            System.out.printf("수정 전 작성자 : " );
            System.out.println(oldData.getName());
            System.out.println("수정 할 작성자 : " );
            String name = sc.next();
            System.out.printf("수정 전 제목 : " );
            System.out.println(oldData.getTitle());
            System.out.printf("수정 할  제목: ");
            String title = sc.next();
            System.out.printf("수정 전 내용 : " );
            System.out.println(oldData.getContent());
            System.out.printf("수정 할  내용: ");
            String content = sc.next();
            oldData.setName(name);
            oldData.setTitle(title);
            oldData.setContent(content);
            oldData.setUpdatedDate(LocalDateTime.now());
            service.update(oldData);
        }
    }
}
