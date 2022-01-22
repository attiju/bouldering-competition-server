package com.contest.bouldering.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Leaderboard {

    List<RankedClimber> climbers;

}
