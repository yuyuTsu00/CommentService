package com.intuit.interview.commentservice.Reaction.Service;

import com.intuit.interview.commentservice.Constants.Emotion;
import com.intuit.interview.commentservice.Reaction.Model.Reaction;


public interface ReactionService {

    void doneReaction(Reaction reaction, Emotion emotion);
    void undoneReaction(Reaction reaction, Emotion emotion);
}
