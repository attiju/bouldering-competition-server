package com.contest.bouldering.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class ClimberBoulder {

    @Builder.Default
    private Boolean validateTop = false;

    @Builder.Default
    private Boolean validateZone = false;

}
