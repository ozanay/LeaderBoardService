package com.leaderboard.provider.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.List;

import com.leaderboard.provider.util.IsoCountyUtil;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    public ResponseEntity<List<LeaderBoardResource>> getGlobalLeaderBoard() {
        log.info("Getting global leaderboard...");
        List<User> leaderBoardUsers = leaderBoardUserService.getGlobalUsers();
        List<LeaderBoardResource> leaderBoardResources = conversionService.convertToList(leaderBoardUsers,
                        LeaderBoardResource.class);
        log.info("Getting global leaderboard finished successfully.");
        return ResponseEntity.ok(leaderBoardResources);
    }

    @GetMapping(value = "/leaderboard/{countryIsoCode}", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get country leaderboard.")
    public ResponseEntity<List<LeaderBoardResource>> getCountryLeaderBoard(@PathVariable("countryIsoCode") @Parameter(
                    required = true,
                    description = "Country iso code according to ISO 3166-1 alpha-2 standards.") String countryIsoCode) {
        log.info("Getting country leaderboard...");
        ResponseEntity<List<LeaderBoardResource>> responseEntity = IsoCountyUtil.isValidIsoCountryCode(countryIsoCode)
                        ? getCountryLeaderBoardUsersResponse(countryIsoCode)
                        : badRequestResponse(countryIsoCode);

        log.info("Getting country leaderboard finished successfully.");
        return responseEntity;
    }

    private ResponseEntity<List<LeaderBoardResource>> getCountryLeaderBoardUsersResponse(String countryIsoCode) {
        log.info("Given country iso code <{}> is valid.", countryIsoCode);
        List<User> countryUsers = leaderBoardUserService.getCountryUsers(countryIsoCode);
        List<LeaderBoardResource> countryLeaderBoardResources = conversionService.convertToList(countryUsers,
                        LeaderBoardResource.class);
        log.info("Country users are obtained.");
        return ResponseEntity.ok(countryLeaderBoardResources);
    }

    private ResponseEntity<List<LeaderBoardResource>> badRequestResponse(String countryIsoCode) {
        log.info("Given country iso code <{}> is invalid.", countryIsoCode);
        return ResponseEntity.badRequest().build();
    }
}
