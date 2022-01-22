package com.contest.bouldering.service;

import com.contest.bouldering.model.Climber;
import com.contest.bouldering.model.Gender;
import com.contest.bouldering.response.EventDetails;
import com.contest.bouldering.response.Leaderboard;
import com.contest.bouldering.response.Leaderboards;
import com.contest.bouldering.response.RankedClimber;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
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
        List<Climber> climbers = event.getClimbers().stream()
                .filter(climber -> climber.getGender().equals(gender))
                .toList();

        List<Double> bouldersScores = IntStream.range(0, event.getOptions().getBoulders())
                .boxed()
                .map(boulder -> climbers
                        .stream()
                        .filter(user -> user.getBoulders().contains(boulder))
                        .count()
                )
                .map(count -> count == 0 ? 1000.0 : 1000.0 / count)
                .toList();

        List<RankedClimber> rankedClimbers = climbers.stream()
                .map(climber -> RankedClimber.builder()
                        .id(climber.getId())
                        .firstname(climber.getFirstname())
                        .lastname(climber.getLastname())
                        .score(climber.getBoulders().stream().map(bouldersScores::get).reduce(0.0, Double::sum))
                        .build())
                .sorted(Comparator.comparing(RankedClimber::getScore).reversed())
                .toList();

        return Leaderboard.builder()
                .climbers(rankedClimbers)
                .build();
    }
}
