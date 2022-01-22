package com.contest.bouldering.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Leaderboards {

    Leaderboard maleLeaderboard;

    Leaderboard femaleLeaderboard;

}
