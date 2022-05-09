package com.contest.bouldering.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder(toBuilder = true)
@Document
public class Climber {

    @Builder.Default
    List<ClimberBoulder> boulders = new ArrayList<>();
    @Id
    private String id;
    private String eventId;
    private String firstname;
    private String lastname;
    private Gender gender;

}
