package com.contest.bouldering.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class BadRequestError extends ResponseStatusException {

    public BadRequestError() {
        super(HttpStatus.BAD_REQUEST);
    }

}
