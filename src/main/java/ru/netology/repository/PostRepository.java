package ru.netology.repository;

import org.springframework.stereotype.Repository;
import ru.netology.model.Post;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class PostRepository {

    private final Map<Long, Post> posts = new ConcurrentHashMap<>();
    private static AtomicLong count = new AtomicLong(1);

    public Map<Long, Post> all() {
        Map<Long, Post> resp = new HashMap<>();
        for (Map.Entry<Long, Post> entry : posts.entrySet()){
            if (!entry.getValue().isRemoved()){
                resp.put(entry.getKey(), entry.getValue());
            }
        }
            return resp;
    }

    public Optional<Post> getById(long id) {
        var flag = posts.get(id).isRemoved();
        if(flag) return null;
        return Optional.of(posts.get(id));
    }

    public Post save(Post post) {
        if (post.getId() == 0) {
            post.setId(count.get());
            if (!posts.containsValue(post)) posts.put(post.getId(), post);
            count.incrementAndGet();
            return post;
        }
        if (count.get() == 1) {
            posts.put(post.getId(), post);
            count.incrementAndGet();
            return post;
        }
        posts.put(post.getId(), post);
        count.incrementAndGet();
        return post;
    }

    public void removeById(long id) {
        posts.get(id).setRemoved(true);
    }
}
