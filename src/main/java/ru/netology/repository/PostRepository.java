package ru.netology.repository;

import ru.netology.model.Post;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

// Stub
public class PostRepository {

    private final List<Post> posts = new CopyOnWriteArrayList<>();
    private static int count = 1;

    public List<Post> all() {
        return posts;
    }

    public Optional<Post> getById(long id) {
        for(Post entry: posts){
            if(entry.getId() == id){
                return Optional.of(entry);
            }
        }
        return Optional.empty();
    }

    public Post save(Post post) {
        if (post.getId() == 0) {
            post.setId(count);
            if(!posts.contains(post)) posts.add(post);
            count++;
            return post;
        }
        if (count == 1){
            posts.add(post);
            count++;
            return post;
        }
        for(Post entry: posts){
            if(entry.getId() == post.getId()){
                entry.setContent(post.getContent());
                return post;
            }
        }
        posts.add(post);
        count++;
        return post;
    }

    public void removeById(long id) {
        for(Post entry: posts){
            if(entry.getId() == id){
                posts.remove(entry);
            }
        }
    }
}
