package com.intuit.interview.commentservice.Controller;

import com.intuit.interview.commentservice.Model.Reaction;
import com.intuit.interview.commentservice.Repositories.ReactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@RequestMapping("/default")
public interface ReactionController {

    @PostMapping("/")
    public void doneReaction(Reaction reaction);

    @PostMapping("/undone")
    public void undoneReaction(Reaction reaction);
}
