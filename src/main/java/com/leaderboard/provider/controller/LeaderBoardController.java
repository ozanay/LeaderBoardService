package com.leaderboard.provider.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.List;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.leaderboard.provider.config.LeaderBoardConversionService;
import com.leaderboard.provider.controller.resource.LeaderBoardResource;
import com.leaderboard.provider.model.User;
import com.leaderboard.provider.service.LeaderBoardUserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@AllArgsConstructor(onConstructor = @__(@Autowired))
@OpenAPIDefinition(info = @Info(title = "Leaderboard Provider", version = "0.1",
                description = "Leaderboard information provider API."))
public class LeaderBoardController {
    private LeaderBoardUserService leaderBoardUserService;
    private LeaderBoardConversionService conversionService;

    @GetMapping(value = "/leaderboard", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get global leaderboard.")
    public List<LeaderBoardResource> getGlobalLeaderBoard() {
        log.info("Getting global leaderboard...");
        List<User> leaderBoardUsers = leaderBoardUserService.getUsers();
        List<LeaderBoardResource> leaderBoardResources = conversionService.convertToList(leaderBoardUsers,
                        LeaderBoardResource.class);
        log.info("Getting global leaderboard finished successfully.");
        return leaderBoardResources;
    }

}
