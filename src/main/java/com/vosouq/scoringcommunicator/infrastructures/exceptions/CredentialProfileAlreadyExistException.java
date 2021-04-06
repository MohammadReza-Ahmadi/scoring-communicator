package com.vosouq.scoringcommunicator.infrastructures.exceptions;

import com.vosouq.commons.exception.BusinessException;

public class CredentialProfileAlreadyExistException extends BusinessException {

    public CredentialProfileAlreadyExistException(String... parameters) {
        super(parameters);
    }
}
