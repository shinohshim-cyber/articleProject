package service;

import dto.ArticleDto;
import repository.ArticleRepository;

import java.util.List;

public class ArticleService {
    private final ArticleRepository repository;

    public ArticleService(ArticleRepository repository) {
        this.repository = repository;
    }

    public void newArticle(String name, String title, String content) {
        ArticleDto dto = new ArticleDto(ArticleRepository.ariticlId, name, title, content);
        int result = repository.newArticle(dto);
        if(result > 0){
            System.out.println("새글 저장되었습니다.");
        }
    }

    public List<ArticleDto> all() {
        return repository.all();
    }
}
