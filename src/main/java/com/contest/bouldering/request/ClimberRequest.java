package com.contest.bouldering.request;

import com.contest.bouldering.model.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClimberRequest {

    private String firstname;

    private String lastname;

    private Gender gender;

}
