package view;

import dto.ArticleDto;
import service.ArticleService;

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
        if(list.isEmpty()){
            System.out.println("게시글이 없습니다.");
            return;
        }
        System.out.println("=============================================");
        System.out.println("id\tname\t\ttitle\t\t작성일");
        System.out.println("=============================================");
       for(ArticleDto dto : list) {
           System.out.println(dto.getId() + "\t" + dto.getName() + "\t\t" + dto.getTitle() + "\t\t"  + dto.getInsertedDate().toLocalDate() + " " + dto.getInsertedDate().toLocalTime());
           dto.getCommentList().forEach(x-> System.out.println(x));
       }
        System.out.println("=============================================");
    }
}
