package com.intuit.interview.commentservice.Reaction.Controller;

import com.intuit.interview.commentservice.CommonUtility.PaginatedResponse;
import com.intuit.interview.commentservice.Constants.Emotion;
import com.intuit.interview.commentservice.Reaction.DTO.ReactionDto;
import com.intuit.interview.commentservice.Reaction.Service.ReactionService;
import com.mongodb.BasicDBObject;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("likes")
public class LikeController implements ReactionController {

    private final ReactionService reactionService;

    LikeController(ReactionService reactionService) {
        this.reactionService = reactionService;
    }

    @Override
    public ResponseEntity<String> doneReaction(ReactionDto reaction) {
        reactionService.doneReaction(reaction, Emotion.LIKE);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> undoneReaction(ReactionDto reaction) {
        reactionService.undoneReaction(reaction, Emotion.LIKE);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @Override
    @Operation(summary = "Get reactions of entity")
    public ResponseEntity<PaginatedResponse<BasicDBObject>> getReactions(
            @PathVariable("id") String entityId,
            @RequestParam(required = false) Integer start,
            @RequestParam(required = false) Integer size) {
        int skip = start == null ? 0 : start;
        int count = size == null ? 100 : size;
        Sort sort = Sort.by(Sort.Direction.DESC, "reactionTime");
        Pageable pageable = PageRequest.of(skip, count, sort);

        return new ResponseEntity<>(
                reactionService.getAllReactions(entityId, Emotion.LIKE.name(), pageable), HttpStatus.OK);
    }
}
