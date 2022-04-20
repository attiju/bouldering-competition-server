package com.contest.bouldering.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventRequest {

    private String name;

    private Integer boulders;

    private Map<String, Object> metadata;

}
