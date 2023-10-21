package com.intuit.interview.commentservice;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class PaginatedResponse<T> {
    private int start;
    private int count;
    private List<T> items;
}