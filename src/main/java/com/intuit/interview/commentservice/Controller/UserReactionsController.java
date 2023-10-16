package com.intuit.interview.commentservice.Controller;

import com.intuit.interview.commentservice.Model.Like;
import com.intuit.interview.commentservice.Model.Unlike;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("reaction")
public class UserReactionsController {

    @PutMapping("/like")
    public void updatePost(@RequestBody Like like)
    {
        // update the already present post
    }

    @PutMapping("/unlike")
    public void updatePost(@RequestBody Unlike unlike)
    {
        // update the already present post
    }


    @PutMapping("/undolike")
    public void undoLike(@RequestBody Like like)
    {
        // update the already present post
    }

    @PutMapping("/undounlike")
    public void undoDislike(@RequestBody Unlike unlike)
    {
        // update the already present post
    }
}
