package com.vosouq.scoringcommunicator.infrastructures.exceptions;

import com.vosouq.commons.exception.BusinessException;

public class CredentialProfileNotAccessibleException extends BusinessException {

    public CredentialProfileNotAccessibleException(String... parameters) {
        super(parameters);
    }
}
