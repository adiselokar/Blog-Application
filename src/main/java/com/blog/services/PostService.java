package com.blog.services;

import com.blog.entities.Post;
import com.blog.payloads.PostDto;
import com.blog.payloads.PostResponse;

import java.util.List;

public interface PostService {

    PostDto createPost( PostDto postDto, Long userId, Long categoryId );
    PostResponse getAllPosts( Integer pageNumber, Integer pageSize, String sortBy, String sortDirection);
    PostDto getPostById(Long postId);
    PostDto updatePost(Long postId, PostDto postDto);
    void deletePost(Long postId);
    List<PostDto> getPostsByCategory(Long categoryId);
    List<PostDto> getPostsByUser(Long userId);
    List<PostDto> searchPosts(String keyword);

}
