package com.vosouq.scoringcommunicator.services.impl;

import com.vosouq.commons.model.OnlineUser;
import com.vosouq.scoringcommunicator.services.UserBusinessService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import static com.vosouq.scoringcommunicator.infrastructures.utils.CreditScoringUtil.isNull;

@Slf4j
@Service
public class UserBusinessServiceImpl implements UserBusinessService {

    //    private final OnlineUser onlineUser;
    //todo: DEMO
    private final OnlineUser onlineUser = new OnlineUser();

    @Value("${app.run.mode.local}")
    private boolean isLocalRun;

    //todo: DEMO
//    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
//    public UserBusinessServiceImpl(OnlineUser onlineUser) {
//        this.onlineUser = onlineUser.clone();
//    }

    @Override
    public Long getOnlineUserId() {
        //todo: DEMO
        return isLocalRun ? 1L : onlineUser.getUserId();
    }

    @Override
    public boolean isOnlineUser(Long userId) {
        return getOnlineUserId().equals(userId);
    }

    @Override
    public boolean isNotOnlineUser(Long userId) {
        return !isOnlineUser(userId);
    }

    @Override
    public Long resolveUserId(Long userId) {
        return isNull(userId) ? getOnlineUserId() : userId;
    }
}
