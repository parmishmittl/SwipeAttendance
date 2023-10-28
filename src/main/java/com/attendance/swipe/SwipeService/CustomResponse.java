package com.attendance.swipe.SwipeService;

class CustomResponse {
    private final int code;
    private final String message;

    public CustomResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    // Getters
    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
