package ru.returtless.blog.repo;

import org.springframework.data.repository.CrudRepository;
import ru.returtless.blog.models.Post;

public interface PostRepository extends CrudRepository<Post, Long> {
}
