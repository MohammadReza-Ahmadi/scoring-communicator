package com.vosouq.scoringcommunicator.infrastructures.exceptions;

import com.vosouq.commons.exception.BusinessException;

public class CredentialProfileNotFoundException extends BusinessException {

    public CredentialProfileNotFoundException(String... parameters) {
        super(parameters);
    }
}
