package com.eindproject.YogaShare.exceptions;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class RecordNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

}
