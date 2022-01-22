package com.contest.bouldering.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class NotFoundError extends ResponseStatusException {

    public NotFoundError() {
        super(HttpStatus.NOT_FOUND);
    }

}
