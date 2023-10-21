package com.intuit.interview.commentservice;

import com.intuit.interview.commentservice.Reaction.Model.Reaction;

public interface EntityService {

    public void handleLike(Reaction reaction);
    public void handleUndoLike(Reaction reaction);
    public void handleDislike(Reaction reaction);
    public void handleUndoDislike(Reaction reaction);
}
