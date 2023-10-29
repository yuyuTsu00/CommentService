package com.intuit.interview.commentservice.Reaction.Controller;

import com.intuit.interview.commentservice.Reaction.DTO.ReactionDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/default")
public interface ReactionController {

    @PostMapping("/")
    ResponseEntity<String> doneReaction(ReactionDto reaction);

    @PostMapping("/undone")
    ResponseEntity<String> undoneReaction(ReactionDto reaction);
}
