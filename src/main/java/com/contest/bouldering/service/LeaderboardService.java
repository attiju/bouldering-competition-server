package com.contest.bouldering.service;

import com.contest.bouldering.model.Climber;
import com.contest.bouldering.model.EventOptionBoulder;
import com.contest.bouldering.model.Gender;
import com.contest.bouldering.response.EventDetails;
import com.contest.bouldering.response.Leaderboard;
import com.contest.bouldering.response.Leaderboards;
import com.contest.bouldering.response.RankedClimber;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class LeaderboardService {

    private final EventService eventService;

    private final ClimberService climberService;

    public Leaderboards generateLeaderboards(String eventId) {
        EventDetails event = this.eventService.getEventDetails(eventId);

        return Leaderboards.builder()
                .maleLeaderboard(this.generateLeaderboard(event, Gender.MALE))
                .femaleLeaderboard(this.generateLeaderboard(event, Gender.FEMALE))
                .build();
    }

    private Leaderboard generateLeaderboard(EventDetails event, Gender gender) {
        List<EventOptionBoulder> boulderOptions = event.getOptions().getBoulders();

        List<Climber> climbers = event.getClimbers().stream()
                .filter(climber -> climber.getGender().equals(gender))
                .toList();

        List<RankedClimber> rankedClimbers = climbers.stream()
                .map(climber -> RankedClimber.builder()
                        .id(climber.getId())
                        .firstname(climber.getFirstname())
                        .lastname(climber.getLastname())
                        .score(this.computeClimberScore(climber, climbers, boulderOptions))
                        .build())
                .sorted(Comparator.comparing(RankedClimber::getScore).reversed())
                .toList();

        return Leaderboard.builder()
                .climbers(rankedClimbers)
                .build();
    }

    private Double computeClimberScore(Climber climber, List<Climber> climbers, List<EventOptionBoulder> boulderOptions) {
        return IntStream.range(0, boulderOptions.size())
                .boxed()
                .map(boulderIndex -> {
                    double score = 0;

                    if (climber.getBoulders().get(boulderIndex).getValidateTop()) {
                        score += (1000. / climbers.stream().filter(c -> c.getBoulders().get(boulderIndex).getValidateTop()).count());
                    }

                    if (boulderOptions.get(boulderIndex).isHasZone() && climber.getBoulders().get(boulderIndex).getValidateZone()) {
                        score += (1000. / climbers.stream().filter(c -> c.getBoulders().get(boulderIndex).getValidateZone()).count());
                    }

                    return score;
                })
                .reduce(0., Double::sum);
    }
}
