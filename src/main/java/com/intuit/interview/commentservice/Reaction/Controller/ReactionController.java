package com.intuit.interview.commentservice.Reaction.Controller;

import com.intuit.interview.commentservice.CommonUtility.PaginatedResponse;
import com.intuit.interview.commentservice.Reaction.DTO.ReactionDto;
import com.mongodb.BasicDBObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/default")
public interface ReactionController {

    @PostMapping("/")
    ResponseEntity<String> doneReaction(ReactionDto reaction);

    @PostMapping("/undone")
    ResponseEntity<String> undoneReaction(ReactionDto reaction);

    @GetMapping("/entity/{id}")
    ResponseEntity<PaginatedResponse<BasicDBObject>> getReactions(
            @PathVariable("id") String entityId,
            @RequestParam(required = false) Integer start,
            @RequestParam(required = false) Integer size);
}
