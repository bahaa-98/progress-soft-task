package com.progress.dto.response;

public enum CODE {
    OK(200),
    CREATED(201),
    NOT_FOUND(404),
    INTERNAL_SERVER_ERROR(500),
    BAD_REQUEST(400),
    OTP_TOO_MANY_REQUESTS(429);
    final int id;

    CODE(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
