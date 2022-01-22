package com.contest.bouldering.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RankedClimber {

    private String id;

    private String firstname;

    private String lastname;

    private Double score;

}
