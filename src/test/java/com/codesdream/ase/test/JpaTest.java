package com.codesdream.ase.test;

import com.codesdream.ase.component.ASESpringUtil;
import com.codesdream.ase.model.student.Comment;
import com.codesdream.ase.repository.student.CommentRepository;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
public class JpaTest {

    @Resource
    ASESpringUtil aseSpringUtil;

    @Test
    public void test() throws InterruptedException {

        CommentRepository commentRepository = aseSpringUtil.getBean(CommentRepository.class);
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
    }
}
