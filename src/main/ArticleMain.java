package main;

import crudInterface.CrudInterface;
import db.DBConn;
import repository.ArticleRepository;
import service.ArticleService;
import service.CommentService;
import view.ArticleView;

import java.util.Scanner;

public class ArticleMain {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        //CrudInterface repository = new ArticleDAO();
         CrudInterface repository = new ArticleRepository(); // 메모리 버전
        ArticleService articleService = new ArticleService(repository);
        CommentService commentService = new CommentService(repository);
        ArticleView articleView = new ArticleView(sc, articleService, commentService);

        int input;
        while(true){
            do{
                System.out.println("게시글 리스트");
                System.out.println("0.전체보기  1.새글  2.자세히보기  3.게시글삭제  4.수정  5.종료");
                input = sc.nextInt();
            }while(input < 0 || input > 5);
            switch (input){
                case 0:
                    articleView.showAll();
                    break;
                case 1:
                    articleView.showNewArticle();
                    break;
                case 2:
                    articleView.showDetail();
                    break;
                case 3:
                    articleView.showDelete();
                    break;
                case 4:
                    articleView.showUpdate();
                    break;
                case 5:
                    DBConn.close();
                    System.out.println("종료합니다.");
                    return;
            }
        }
    }
}
