package com.gic.utils;

public enum ValidationConst {

    INVALID_START_COORDINATES("Invalid car starting coordinates"),
    INSUFFICIENT_COORDINATES("Insufficient coordinates"),
    INVALID_DIRECTION("Invalid direction"),
    INVALID_COMMAND("Invalid Command");

    private final String msg;

    ValidationConst(String msg) {
        this.msg = msg;
    }

    public String message() {
        return msg;
    }
}
