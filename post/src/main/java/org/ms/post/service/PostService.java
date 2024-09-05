package org.ms.post.service;

import org.ms.post.model.entity.PostEntity;

import java.util.List;

public interface PostService {

    List<PostEntity> getPosts();

    PostEntity createPost(PostEntity post);

    PostEntity getPost(Long id);

    PostEntity getPostByUserIdAndPostId(Long userId, Long postId);
}
