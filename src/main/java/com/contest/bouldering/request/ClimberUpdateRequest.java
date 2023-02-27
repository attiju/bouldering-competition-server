package com.contest.bouldering.request;

import com.contest.bouldering.model.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClimberUpdateRequest {

    private Boolean paid;

    private Gender gender;

}
