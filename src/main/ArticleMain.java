package main;

import db.DBConn;
import repository.ArticleRepository;
import service.ArticleService;
import view.ArticleView;

import java.sql.Connection;
import java.util.Scanner;

public class ArticleMain {
    public static void main(String[] args) {
        Connection connection = DBConn.getConnection();

        Scanner sc = new Scanner(System.in);
        ArticleRepository repository = new ArticleRepository(connection);
        ArticleService service = new ArticleService(repository);
        ArticleView articleView = new ArticleView(sc, service);

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
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    DBConn.close();
                    System.out.println("종료합니다.");
                    return;
            }
        }
    }
}
