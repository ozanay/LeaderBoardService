package com.leaderboard.provider.exception;

public class ApiException extends RuntimeException {
    public ApiException(String message) {
        super(message);
    }
}
