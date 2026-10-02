package org.example.len_lich_chieu.exception;

import lombok.Getter;

@Getter
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String resource, String field, Object value) {
        super(String.format("%s khong tim thay voi %s = '%s'", resource, field, value));
    }
}