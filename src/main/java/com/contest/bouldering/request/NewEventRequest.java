package com.contest.bouldering.request;

import com.contest.bouldering.model.EventOptionBoulder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewEventRequest {

    private String name;

    private String color;

    private List<EventOptionBoulder> boulders;

}
