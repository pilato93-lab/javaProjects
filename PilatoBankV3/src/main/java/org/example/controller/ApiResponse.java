package org.example.controller;

public class ApiResponse<T> {
    private String status;
    private String message;
    private T data;

    // How we pack the box
    public ApiResponse(String status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    // How the internet reads the box
    public String getStatus() { return status; }
    public String getMessage() { return message; }
    public T getData() { return data; }
}