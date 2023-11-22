package com.rentalhive.rentalhive.exceptions;

public class InvalidEquipmentStatusException extends RuntimeException {

    public InvalidEquipmentStatusException(String message) {
        super(message);
    }
}