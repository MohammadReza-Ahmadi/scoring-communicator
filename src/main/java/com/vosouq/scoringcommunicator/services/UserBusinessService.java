package com.vosouq.scoringcommunicator.services;


public interface UserBusinessService {

    Long getOnlineUserId();

    boolean isOnlineUser(Long userId);

    boolean isNotOnlineUser(Long userId);

    Long resolveUserId(Long userId);
}
