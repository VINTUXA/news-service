package com.example.demo.web.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpsetUserRequest {
    @NotBlank(message = "Client name should be specified in the request")
    private String userName;
}
