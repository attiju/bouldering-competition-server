package com.contest.bouldering.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder(toBuilder = true)
@Document
public class Event {

    @Id
    private String id;

    private String name;
    
    @Builder.Default
    private Boolean verifyPayment = false;

    @Builder.Default
    private Boolean active = true;

    @Builder.Default
    private String color = "#000000";

    @Builder.Default
    private EventOptions options = EventOptions.builder().build();

}
