package com.contest.bouldering.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ConflictError extends ResponseStatusException {

    public ConflictError() {
        super(HttpStatus.CONFLICT);
    }

}
