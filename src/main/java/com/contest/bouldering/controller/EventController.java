package com.contest.bouldering.controller;

import com.contest.bouldering.model.Event;
import com.contest.bouldering.request.EventRequest;
import com.contest.bouldering.request.EventUpdateRequest;
import com.contest.bouldering.response.EventDetails;
import com.contest.bouldering.response.Leaderboards;
import com.contest.bouldering.service.EventService;
import com.contest.bouldering.service.LeaderboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/events")
public class EventController {

    private final EventService eventService;

    private final LeaderboardService leaderboardService;

    @GetMapping
    public List<Event> getFindAllEvents() {
        return this.eventService.findAllEvents();
    }

    @PostMapping
    public Event postCreateEvent(@RequestBody EventRequest request) {
        return this.eventService.createEvent(request);
    }

    @GetMapping("/{eventId}")
    public EventDetails getFindEvent(@PathVariable String eventId) {
        return this.eventService.getEventDetails(eventId);
    }

    @PutMapping("/{eventId}")
    public EventDetails putUpdateEvent(@PathVariable String eventId, @RequestBody EventUpdateRequest request) {
        return this.eventService.updateEvent(eventId, request);
    }

    @DeleteMapping("/{eventId}")
    public void deleteEvent(@PathVariable String eventId) {
        this.eventService.removeEvent(eventId);
    }

    @GetMapping("/{eventId}/leaderboards")
    public Leaderboards getGenerateLeaderboards(@PathVariable String eventId) {
        return this.leaderboardService.generateLeaderboards(eventId);
    }

}
