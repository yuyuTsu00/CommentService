package com.intuit.interview.commentservice.Reaction.Controller;

import com.intuit.interview.commentservice.Reaction.Model.Reaction;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@RequestMapping("/default")
public interface ReactionController {

    @PostMapping("/")
    public void doneReaction(Reaction reaction);

    @PostMapping("/undone")
    public void undoneReaction(Reaction reaction);
}
