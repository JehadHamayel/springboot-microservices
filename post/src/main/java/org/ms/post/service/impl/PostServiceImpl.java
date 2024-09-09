package org.ms.post.service.impl;

import lombok.RequiredArgsConstructor;
import org.ms.post.model.entity.PostEntity;
import org.ms.post.repository.PostRepository;
import org.ms.post.service.PostService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    @Override
    public List<PostEntity> getPosts() {
        return postRepository.findAll();
    }

    @Override
    public PostEntity createPost(PostEntity post) {
        return postRepository.save(post);
    }

    @Override
    public PostEntity getPost(Long id) {
        return postRepository.findById(id).orElse(null);
    }

    @Override
    public PostEntity getPostByUserIdAndPostId(Long userId, Long postId) {
        Optional<PostEntity> postEntityOptional = postRepository.getPostByUserIdAndPostId(userId, postId);
        if (postEntityOptional.isPresent()) {
            return postEntityOptional.get();
        }else {
            return null;
        }
    }

    @KafkaListener(topics = "post_user", groupId = "post_user_group")
    public void listen(String message) {
        Long userId = Long.parseLong(message);
        postRepository.deletePostByUserId(userId);

    }

}
