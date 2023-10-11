package com.attendance.swipe.SwipeService;

 class CustomResponse {
    private int code;
    private String message;

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
