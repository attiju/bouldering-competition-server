package com.contest.bouldering.service;

import com.contest.bouldering.error.BadRequestError;
import com.contest.bouldering.error.ConflictError;
import com.contest.bouldering.error.NotFoundError;
import com.contest.bouldering.model.Climber;
import com.contest.bouldering.model.ClimberBoulder;
import com.contest.bouldering.repository.ClimberRepository;
import com.contest.bouldering.request.BouldersRequest;
import com.contest.bouldering.request.ClimberRequest;
import com.contest.bouldering.response.EventDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClimberService {

    private final EventService eventService;

    private final ClimberRepository climberRepository;

    public Climber registerClimber(String eventId, ClimberRequest request) {
        EventDetails event = this.eventService.getEventDetails(eventId);

        Climber climber = Climber.builder()
                .eventId(eventId)
                .firstname(request.getFirstname().toLowerCase().trim())
                .lastname(request.getLastname().toLowerCase().trim())
                .gender(request.getGender())
                .boulders(event.getOptions().getBoulders().stream().map(boulderOption -> ClimberBoulder.builder().build()).collect(Collectors.toList()))
                .build();

        if (event.getClimbers().stream().anyMatch(c -> c.getFirstname().equals(climber.getFirstname()) && c.getLastname().equals(climber.getLastname()))) {
            throw new ConflictError();
        }

        return this.climberRepository.save(climber);
    }

    public void removeClimber(String eventId, String climberId) {
        this.climberRepository.deleteById(climberId);
    }

    public Climber updateClimberBoulders(String eventId, String climberId, BouldersRequest request) {
        EventDetails event = this.eventService.getEventDetails(eventId);

        Climber climber = event.getClimbers()
                .stream()
                .filter(c -> c.getId().equals(climberId))
                .findFirst()
                .orElseThrow(NotFoundError::new);

        if (!event.getActive()) {
            return climber;
        }

        if (request.getBoulders().size() != event.getOptions().getBoulders().size()) {
            throw new BadRequestError();
        }

        return this.climberRepository.save(climber.toBuilder().boulders(request.getBoulders()).build());
    }
}
