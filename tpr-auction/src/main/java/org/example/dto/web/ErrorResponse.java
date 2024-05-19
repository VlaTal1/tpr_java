package org.example.dto.web;

import java.time.LocalDateTime;

public record ErrorResponse(LocalDateTime timestamp, Integer status, String errorMessage){

}

