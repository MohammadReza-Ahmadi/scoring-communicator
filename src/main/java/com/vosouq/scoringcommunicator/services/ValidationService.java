package com.vosouq.scoringcommunicator.services;

public interface ValidationService {

    void validateUserAccess(Long otherUserId);

    void validateCreateCredentialCheckRequest(Long otherUserId);
}
