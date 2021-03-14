package com.vosouq.scoringcommunicator.infrastructures.exceptions;

import com.vosouq.commons.exception.BusinessException;

public class CreateCredentialCheckRequestIsNotAllowedException extends BusinessException {

    public CreateCredentialCheckRequestIsNotAllowedException(String... parameters) {
        super(parameters);
    }
}
