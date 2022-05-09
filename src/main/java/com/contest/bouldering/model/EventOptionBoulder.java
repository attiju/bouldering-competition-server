package com.contest.bouldering.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class EventOptionBoulder {

    private String label;

    @Builder.Default
    private boolean hasZone = false;

}
