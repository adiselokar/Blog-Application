package com.blog.services.impl;

import com.blog.entities.Category;
import com.blog.entities.Post;
import com.blog.entities.User;
import com.blog.exceptions.ResourseNotFoundException;
import com.blog.payloads.PostDto;
import com.blog.payloads.PostResponse;
import com.blog.repositories.CategoryRepository;
import com.blog.repositories.PostRepository;
import com.blog.repositories.UserRepository;
import com.blog.services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public PostDto createPost( PostDto postDto, Long userId, Long categoryId ) {

        User user = userRepository.findById(userId).orElseThrow(() -> new ResourseNotFoundException("User Not Found with Id : " + userId, HttpStatus.NOT_FOUND));
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourseNotFoundException("Category Not Found with Id : " + categoryId, HttpStatus.NOT_FOUND));

        Post post = modelMapper.map(postDto, Post.class);
        post.setImageName("default.png");
        post.setAddedDate(new Date());
        post.setUser(user);
        post.setCategory(category);

        Post savedPost = postRepository.save(post);
        return modelMapper.map(savedPost, PostDto.class);
    }

    @Override
    public PostResponse getAllPosts( Integer pageNumber, Integer pageSize, String sortBy, String sortDirection)
    {
        Sort sort = (sortDirection.equalsIgnoreCase("asc")) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
//        if (sortDirection.equalsIgnoreCase("asc"))
//        {
//            sort = Sort.by(sortBy).ascending();
//        }else {
//            sort = Sort.by(sortBy).descending();
//        }
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page <Post> posts = postRepository.findAll(pageable);
//        List <Post> posts = postRepository.findAll();
        List <PostDto> postDtos = posts.stream()
                .map(post -> modelMapper.map(post, PostDto.class))
                .collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(postDtos);
        postResponse.setPageNumber(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setLastPage(posts.isLast());

        return postResponse;
    }

    @Override
    public PostDto getPostById( Long postId )
    {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourseNotFoundException("Post Not found with Id : " + postId, HttpStatus.NOT_FOUND));
        PostDto postDto = modelMapper.map(post, PostDto.class);
        return postDto;
    }

    @Override
    public PostDto updatePost( Long postId, PostDto postDto )
    {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourseNotFoundException("Post Not Found With Id : " + postId, HttpStatus.NOT_FOUND));
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setImageName(postDto.getImageName());

        Post updatedPost = postRepository.save(post);
        PostDto updatedPostDto = modelMapper.map(updatedPost, PostDto.class);
        return updatedPostDto;
    }

    @Override
    public void deletePost( Long postId )
    {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourseNotFoundException("Post Not found with Id : " + postId, HttpStatus.NOT_FOUND));
        postRepository.delete(post);
    }

    @Override
    public List <PostDto> getPostsByCategory( Long categoryId )
    {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourseNotFoundException("Category Not Found With Id : " + categoryId, HttpStatus.NOT_FOUND));
        List <Post> posts = postRepository.findByCategory(category);
        List <PostDto> postDtos = posts.stream()
                .map(post -> modelMapper.map(post, PostDto.class))
                .collect(Collectors.toList());
        return postDtos;
    }

    @Override
    public List <PostDto> getPostsByUser( Long userId )
    {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourseNotFoundException("User Not Found With Id : " + userId, HttpStatus.NOT_FOUND));
        List <Post> posts = postRepository.findByUser(user);
        List <PostDto> postDtos = posts.stream()
                .map(post -> modelMapper.map(post, PostDto.class))
                .collect(Collectors.toList());
        return postDtos;
    }

    @Override
    public List <PostDto> searchPosts( String keyword )
    {
        List <Post> posts = postRepository.findByTitleContaining(keyword);
        List <PostDto> postDtos = posts.stream()
                .map(post -> modelMapper.map(post, PostDto.class))
                .collect(Collectors.toList());
        return postDtos;
    }
}
