package com.vosouq.scoringcommunicator.repositories;

import com.vosouq.scoringcommunicator.controllers.dtos.res.UserProfileRes;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserProfileRepositoryMOC {

    public UserProfileRes getUserProfile(@PathVariable Long id) {
        return new UserProfileRes(id, "Ali Zoghi", "/v1/images/personal1");
    }

    public List<UserProfileRes> getUserProfiles(@PathVariable Long[] ids) {
        return Arrays.stream(ids)
                .map(i -> new UserProfileRes(i, "Name Family:" + i, "image-url:" + i))
                .collect(Collectors.toList());
    }
}
