package com.blog.services.impl;

import com.blog.entities.Comment;
import com.blog.entities.Post;
import com.blog.entities.User;
import com.blog.exceptions.ResourseNotFoundException;
import com.blog.payloads.CommentDto;
import com.blog.repositories.CommentRepository;
import com.blog.repositories.PostRepository;
import com.blog.repositories.UserRepository;
import com.blog.services.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CommentDto createComment( CommentDto commentDto, Long postId, Long userId )
    {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourseNotFoundException("User Not Found With Id : " + userId, HttpStatus.NOT_FOUND));
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourseNotFoundException("Post Not Found With Id : " + postId, HttpStatus.NOT_FOUND));

        Comment comment = modelMapper.map(commentDto, Comment.class);
        comment.setUser(user);
        comment.setPost(post);

        Comment savedComment = commentRepository.save(comment);
        return modelMapper.map(savedComment, CommentDto.class);
    }

    @Override
    public void deleteComment( Integer commentId )
    {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourseNotFoundException("Comment Not Found With Id : " + commentId, HttpStatus.NOT_FOUND));
        commentRepository.delete(comment);
    }
}
