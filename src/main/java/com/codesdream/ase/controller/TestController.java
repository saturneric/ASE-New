package com.codesdream.ase.controller;

import com.codesdream.ase.model.student.Comment;
import com.codesdream.ase.repository.student.CommentRepository;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("test")
public class TestController {

    @Resource
    private CommentRepository commentRepository;

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public boolean test() throws InterruptedException {

        String userId = "1";
        for (int i = 0;i < 10; i++){
            Comment comment = new Comment();
            comment.setUserId(userId);
            comment.setContext(new Integer(i).toString());
            commentRepository.save(comment);
            Thread.sleep(20);
        }
        Thread.sleep(1000);
        List<Comment> comments = commentRepository.findByUserId(userId,
                Sort.by(Sort.Direction.DESC, "date"));
        if(!comments.isEmpty()){
            System.out.println(comments.toString());
        }
        return true;
    }
}
