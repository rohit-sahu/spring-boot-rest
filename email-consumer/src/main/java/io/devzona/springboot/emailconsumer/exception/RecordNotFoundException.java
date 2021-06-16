package io.devzona.springboot.emailconsumer.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RecordNotFoundException extends Exception {

    private String message;
}
