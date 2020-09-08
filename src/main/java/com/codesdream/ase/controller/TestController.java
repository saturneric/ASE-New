package com.codesdream.ase.controller;

import com.codesdream.ase.model.student.Comment;
import com.codesdream.ase.repository.student.CommentRepository;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController("test")
public class TestController {

    @Resource
    CommentRepository commentRepository;

    @RequestMapping
    @ResponseStatus(HttpStatus.OK)
    public boolean test() throws InterruptedException {

        int userId = 1;
        for (int i = 0;i < 10; i++){
            Comment comment = new Comment();
            comment.setUserId(userId);
            comment.setContext(new Integer(i).toString());
            commentRepository.save(comment);
            Thread.sleep(20);
        }
        Thread.sleep(1000);
        List<Comment> comments = commentRepository.findByUserId(new Integer(userId).toString(),
                Sort.by(Sort.Direction.ASC, "date"));
        return true;
    }
}
