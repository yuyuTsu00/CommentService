package com.intuit.interview.commentservice.Reaction.Controller;

import com.intuit.interview.commentservice.Constants.Emotion;
import com.intuit.interview.commentservice.Entity.EntityService;
import com.intuit.interview.commentservice.Reaction.Model.Reaction;
import com.intuit.interview.commentservice.Reaction.Repository.ReactionRepository;
import com.intuit.interview.commentservice.Reaction.Service.ReactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("dislike")
public class DislikeController implements ReactionController {
    private final ReactionService reactionService;

    DislikeController(ReactionService reactionService)
    {
        this.reactionService = reactionService;
    }

    @Override
    public ResponseEntity<String> doneReaction(Reaction reaction)
    {
        reactionService.doneReaction(reaction, Emotion.DISLIKE);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> undoneReaction(Reaction reaction) {
        reactionService.undoneReaction(reaction, Emotion.DISLIKE);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }
}