package service;

import crudInterface.CrudInterface;
import dto.ArticleDto;
import repository.ArticleRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ArticleService {
    private final CrudInterface repository;
    public ArticleService(CrudInterface repository) {
        this.repository = repository;
    }

    public void newArticle(String name, String title, String content, LocalDateTime insertedDate) {
        ArticleDto dto = new ArticleDto(ArticleRepository.ariticlId, name, title, content, insertedDate, null, new ArrayList<>());
        repository.newArticle(dto);
        ArticleRepository.ariticlId++;
        System.out.println("새글 저장되었습니다.");
    }

    public List<ArticleDto> all() {
        return repository.all();
    }

    public ArticleDto detail(Long id) {
        return repository.detail(id);
    }

    public void delete(Long id) {
        if(repository.delete(id)) {
            System.out.println("ID : " + id + " 게시글 삭제되었습니다.");
        }else{
            System.out.println("게시글 삭제 실패했습니다.");
        }
    }

    public void update(ArticleDto oldData) {
        repository.update(oldData);
    }
}
