package com.vosouq.scoringcommunicator.repositories;

import com.vosouq.scoringcommunicator.controllers.dtos.res.UserProfileRes;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "profile")
public interface UserProfileRepository {

    @GetMapping(value = "/users/{id}")
    UserProfileRes getUserProfile(@PathVariable Long id);


    @GetMapping(value = "/users/{ids}")
    List<UserProfileRes> getUserProfiles(@PathVariable Long[] ids);
}
