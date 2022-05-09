package com.contest.bouldering.response;

import com.contest.bouldering.model.Climber;
import com.contest.bouldering.model.EventOptions;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class EventDetails {

    private String id;

    private String name;

    private String color;

    private Boolean active;

    private EventOptions options;

    private List<Climber> climbers;

}
