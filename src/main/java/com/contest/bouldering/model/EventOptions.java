package com.contest.bouldering.model;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder(toBuilder = true)
public class EventOptions {

    @Builder.Default
    private List<EventOptionBoulder> boulders = new ArrayList<>();

}
