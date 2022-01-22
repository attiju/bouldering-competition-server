package com.contest.bouldering.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventUpdateRequest {

    private Boolean active;

    private Integer boulders;

}
