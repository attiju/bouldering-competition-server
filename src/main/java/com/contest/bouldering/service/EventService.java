package com.contest.bouldering.service;

import com.contest.bouldering.error.ConflictError;
import com.contest.bouldering.error.NotFoundError;
import com.contest.bouldering.model.Climber;
import com.contest.bouldering.model.ClimberBoulder;
import com.contest.bouldering.model.Event;
import com.contest.bouldering.model.EventOptions;
import com.contest.bouldering.repository.ClimberRepository;
import com.contest.bouldering.repository.EventRepository;
import com.contest.bouldering.request.EventUpdateRequest;
import com.contest.bouldering.request.NewEventRequest;
import com.contest.bouldering.response.EventDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;

    private final ClimberRepository climberRepository;

    public List<Event> findAllEvents() {
        return this.eventRepository.findAll();
    }

    public Event createEvent(NewEventRequest request) {
        Event event = Event.builder()
                .name(request.getName().toLowerCase().trim())
                .color(request.getColor())
                .active(true)
                .options(EventOptions.builder()
                        .boulders(request.getBoulders())
                        .build())
                .build();

        if (this.eventRepository.existsByName(event.getName())) {
            throw new ConflictError();
        }

        return this.eventRepository.save(event);
    }

    public void removeEvent(String eventId) {
        if (!this.eventRepository.existsById(eventId)) {
            throw new NotFoundError();
        }

        this.climberRepository.deleteAllByEventId(eventId);
        this.eventRepository.deleteById(eventId);
    }

    public EventDetails getEventDetails(String eventId) {
        Event event = this.eventRepository.findById(eventId)
                .orElseThrow(NotFoundError::new);

        return EventDetails.builder()
                .id(eventId)
                .name(event.getName())
                .color(event.getColor())
                .options(event.getOptions())
                .active(event.getActive())
                .verifyPayment(event.getVerifyPayment())
                .climbers(this.climberRepository.findAllByEventId(eventId))
                .build();
    }

    public EventDetails updateEvent(String eventId, EventUpdateRequest request) {
        Event event = this.eventRepository.findById(eventId)
                .orElseThrow(NotFoundError::new);
        Event.EventBuilder eventBuilder = event.toBuilder();

        if (Objects.nonNull(request.getActive())) {
            eventBuilder.active(request.getActive());
        }

        if (Objects.nonNull(request.getColor())) {
            eventBuilder.color(request.getColor());
        }

        if (Objects.nonNull(request.getBoulders())) {
            eventBuilder.options(event.getOptions().toBuilder().boulders(request.getBoulders()).build());

            this.updateClimberBoulders(eventId, request.getBoulders().size());
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

    private void updateClimberBoulders(String eventId, Integer boulderCount) {
        List<Climber> climbers = this.climberRepository.findAllByEventId(eventId);

        climbers.forEach(climber -> {
            List<ClimberBoulder> boulders = climber.getBoulders();

            if (boulders.size() > boulderCount) {
                climber.setBoulders(boulders.subList(0, boulderCount));
            } else {
                boulders.addAll(IntStream.range(0, boulderCount - boulders.size())
                        .boxed()
                        .map(index -> ClimberBoulder.builder().build())
                        .toList());
            }
        });

        this.climberRepository.saveAll(climbers);
    }
}
