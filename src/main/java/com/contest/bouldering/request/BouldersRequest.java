package com.contest.bouldering.request;

import com.contest.bouldering.model.ClimberBoulder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BouldersRequest {

    private List<ClimberBoulder> boulders;

}
