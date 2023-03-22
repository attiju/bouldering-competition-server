package com.contest.bouldering.controller;

import com.contest.bouldering.model.Climber;
import com.contest.bouldering.request.BouldersRequest;
import com.contest.bouldering.request.ClimberRequest;
import com.contest.bouldering.request.ClimberUpdateRequest;
import com.contest.bouldering.service.ClimberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/events/{eventId}")
public class ClimberController {

    private final ClimberService climberService;

    @GetMapping("/climbers/{climberId}")
    public Climber getClimberById(@PathVariable String eventId, @PathVariable String climberId) {
        return this.climberService.findClimberById(climberId);
    }

    @PostMapping("/climbers")
    public Climber postRegisterClimbers(@PathVariable String eventId, @RequestBody ClimberRequest request) {
        return this.climberService.registerClimber(eventId, request);
    }

    @PutMapping("/climbers/{climberId}/information")
    public Climber setClimberPaymentStatus(@PathVariable String eventId, @PathVariable String climberId, @RequestBody ClimberUpdateRequest request) {
        return this.climberService.updateClimberInformation(eventId, climberId, request);
    }

    @DeleteMapping("/climbers")
    public void deleteRemoveAllClimbers(@PathVariable String eventId) {
        this.climberService.removeClimbers(eventId);
    }

    @DeleteMapping("/climbers/{climberId}")
    public void deleteRemoveClimber(@PathVariable String eventId, @PathVariable String climberId) {
        this.climberService.removeClimber(eventId, climberId);
    }

    @PutMapping("/climbers/{climberId}")
    public Climber putUpdateClimber(@PathVariable String eventId, @PathVariable String climberId, @RequestBody BouldersRequest request) {
        return this.climberService.updateClimberBoulders(eventId, climberId, request);
    }

}
