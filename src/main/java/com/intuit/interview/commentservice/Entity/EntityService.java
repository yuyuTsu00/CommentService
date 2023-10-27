package com.intuit.interview.commentservice.Entity;

import com.intuit.interview.commentservice.Reaction.Model.Reaction;

public interface EntityService {
    void handleLike(Reaction reaction);
    void handleUndoLike(Reaction reaction);
    void handleDislike(Reaction reaction);
    void handleUndoDislike(Reaction reaction);
}
