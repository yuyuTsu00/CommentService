package com.intuit.interview.commentservice.Reaction.Controller;

import com.intuit.interview.commentservice.Constants.Emotion;
import com.intuit.interview.commentservice.Entity.EntityService;
import com.intuit.interview.commentservice.Reaction.DTO.ReactionDto;
import com.intuit.interview.commentservice.Reaction.Model.Reaction;
import com.intuit.interview.commentservice.Reaction.Repository.ReactionRepository;
import com.intuit.interview.commentservice.Reaction.Service.ReactionService;
import com.intuit.interview.commentservice.Reaction.Service.ReactionServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("like")
public class LikeController implements ReactionController {

    private final ReactionService reactionService;

    LikeController(ReactionService reactionService)
    {
        this.reactionService = reactionService;
    }

    @Override
    public ResponseEntity<String> doneReaction(ReactionDto reaction)
    {
        reactionService.doneReaction(reaction, Emotion.LIKE);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> undoneReaction(ReactionDto reaction) {
        reactionService.undoneReaction(reaction, Emotion.LIKE);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }
}
