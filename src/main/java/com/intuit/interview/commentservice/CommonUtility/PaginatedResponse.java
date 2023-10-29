package com.intuit.interview.commentservice.CommonUtility;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Builder
@Data
public class PaginatedResponse<T> implements Serializable {
    private int start;
    private int count;
    private List<T> items;
}