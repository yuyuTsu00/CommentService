package com.intuit.interview.commentservice.Reaction.Service;

import com.intuit.interview.commentservice.Constants.Emotion;
import com.intuit.interview.commentservice.Reaction.DTO.ReactionDto;


public interface ReactionService {

    void doneReaction(ReactionDto reaction, Emotion emotion);
    void undoneReaction(ReactionDto reaction, Emotion emotion);
}
