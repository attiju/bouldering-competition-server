package com.contest.bouldering.model;

import lombok.Builder;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
@Builder(toBuilder = true)
public class EventOptions {

    @Builder.Default
    private Integer boulders = 30;

    @Builder.Default
    private Map<String, Object> metadata = new HashMap<>();

}
