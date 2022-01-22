package com.contest.bouldering.service;

import com.contest.bouldering.error.ConflictError;
import com.contest.bouldering.error.NotFoundError;
import com.contest.bouldering.model.Climber;
import com.contest.bouldering.model.Event;
import com.contest.bouldering.model.EventOptions;
import com.contest.bouldering.repository.ClimberRepository;
import com.contest.bouldering.repository.EventRepository;
import com.contest.bouldering.request.EventRequest;
import com.contest.bouldering.request.EventUpdateRequest;
import com.contest.bouldering.response.EventDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;

    private final ClimberRepository climberRepository;

    public List<Event> findAllEvents() {
        return this.eventRepository.findAll();
    }

    public Event createEvent(EventRequest request) {
        Event event = Event.builder()
                .name(request.getName().toLowerCase().trim())
                .options(EventOptions.builder()
                        .boulders(request.getBoulders())
                        .metadata(request.getMetadata())
                        .build())
                .build();

        if (this.eventRepository.existsByName(event.getName())) {
            throw new ConflictError();
        }

        return this.eventRepository.save(event);
    }

    public EventDetails getEventDetails(String eventId) {
        Event event = this.eventRepository.findById(eventId)
                .orElseThrow(NotFoundError::new);

        return EventDetails.builder()
                .id(eventId)
                .name(event.getName())
                .options(event.getOptions())
                .active(event.getActive())
                .climbers(this.climberRepository.findAllByEventId(eventId))
                .build();
    }

    public void removeEvent(String eventId) {
        if (!this.eventRepository.existsById(eventId)) {
            throw new NotFoundError();
        }

        this.climberRepository.deleteAllByEventId(eventId);
        this.eventRepository.deleteById(eventId);
    }

    public EventDetails updateEvent(String eventId, EventUpdateRequest request) {
        Event event = this.eventRepository.findById(eventId)
                .orElseThrow(NotFoundError::new);
        Event.EventBuilder eventBuilder = event.toBuilder();

        if (Objects.nonNull(request.getActive())) {
            eventBuilder.active(request.getActive());
        }
        if (Objects.nonNull(request.getBoulders())) {
            eventBuilder.options(event.getOptions().toBuilder().boulders(request.getBoulders()).build());

            this.updateClimberBoulders(eventId, request.getBoulders());
        }
        Event updatedEvent = this.eventRepository.save(eventBuilder.build());

        return EventDetails.builder()
                .id(eventId)
                .name(updatedEvent.getName())
                .options(updatedEvent.getOptions())
                .active(updatedEvent.getActive())
                .climbers(this.climberRepository.findAllByEventId(eventId))
                .build();
    }

    private void updateClimberBoulders(String eventId, Integer boulders) {
        List<Climber> climbers = this.climberRepository.findAllByEventId(eventId);

        climbers.forEach(climber -> {
            climber.setBoulders(climber.getBoulders().stream().filter(boulder -> boulder < boulders).collect(Collectors.toSet()));
        });

        this.climberRepository.saveAll(climbers);
    }
}
