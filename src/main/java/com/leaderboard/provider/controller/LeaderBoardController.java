package com.leaderboard.provider.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.List;
import java.util.Locale;

import com.leaderboard.provider.controller.resource.ScoreWorthResource;
import com.leaderboard.provider.controller.resource.UserResource;
import com.leaderboard.provider.exception.BadRequestException;
import com.leaderboard.provider.model.LeaderBoardScore;
import com.leaderboard.provider.model.Score;
import com.leaderboard.provider.model.ScoreWorth;
import com.leaderboard.provider.util.IsoCountyUtil;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.leaderboard.provider.config.LeaderBoardConversionService;
import com.leaderboard.provider.controller.resource.LeaderBoardResource;
import com.leaderboard.provider.service.LeaderBoardScoreService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@AllArgsConstructor(onConstructor = @__(@Autowired))
@OpenAPIDefinition(info = @Info(title = "Leaderboard Provider", version = "0.1",
                description = "Leaderboard information provider API."))
public class LeaderBoardController {
    private LeaderBoardScoreService leaderBoardScoreService;
    private LeaderBoardConversionService conversionService;

    @GetMapping(value = "/leaderboard", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get global leaderboard.")
    public ResponseEntity<List<LeaderBoardResource>> getGlobalLeaderBoard() {
        log.info("Getting global leaderboard...");
        List<LeaderBoardScore> leaderBoardScores = leaderBoardScoreService.getGlobalScores();
        List<LeaderBoardResource> leaderBoardResources = conversionService.convertToList(leaderBoardScores,
                        LeaderBoardResource.class);
        log.info("Getting global leaderboard finished successfully.");
        return ResponseEntity.ok(leaderBoardResources);
    }

    @GetMapping(value = "/leaderboard/{country}", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get country leaderboard.")
    public ResponseEntity<List<LeaderBoardResource>> getCountryLeaderBoard(@PathVariable("country") @Parameter(
                    required = true,
                    description = "Country iso code according to ISO 3166-1 alpha-2 standards.") String country) {
        log.info("Getting country leaderboard...");
        if (!IsoCountyUtil.isValidIsoCountryCode(country)) {
            throw new BadRequestException(String.format("Illegal country code <%S> is given", country));
        }

        List<LeaderBoardScore> countryScores = leaderBoardScoreService
                        .getCountryScores(country.toUpperCase(Locale.getDefault()));
        List<LeaderBoardResource> countryLeaderBoardScores = conversionService.convertToList(countryScores,
                        LeaderBoardResource.class);
        ResponseEntity<List<LeaderBoardResource>> responseEntity = ResponseEntity.ok(countryLeaderBoardScores);
        log.info("Getting country leaderboard finished successfully.");
        return responseEntity;
    }

    @PostMapping(value = "/score/submit", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Submits the score of given player.")
    public ResponseEntity<ScoreWorthResource> submitScore(@RequestBody ScoreWorthResource submitResource) {
        log.info("Submitting score for user <{}>", submitResource.getUserId());
        ScoreWorth scoreWorth = conversionService.convert(submitResource, ScoreWorth.class);
        ScoreWorth updatedScoreWorth = leaderBoardScoreService.addScore(scoreWorth);
        ScoreWorthResource scoreWorthResource = conversionService.convert(updatedScoreWorth, ScoreWorthResource.class);
        ResponseEntity<ScoreWorthResource> responseEntity = ResponseEntity.ok(scoreWorthResource);
        log.info("Submitting score for user <{}> finished successfully.", submitResource.getUserId());
        return responseEntity;
    }

    @PostMapping(value = "/users", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Submits user list.")
    public ResponseEntity<List<UserResource>> submit(@RequestBody List<UserResource> users) {
        log.info("Submitting users...");
        if (users.isEmpty()) {
            throw new BadRequestException("No content to submit users");
        }

        List<Score> scores = conversionService.convertToList(users, Score.class);
        List<Score> submittedScores = leaderBoardScoreService.submit(scores);
        List<UserResource> submittedUsers = conversionService.convertToList(submittedScores, UserResource.class);
        ResponseEntity<List<UserResource>> response = ResponseEntity.ok(submittedUsers);
        log.info("Submitting users finished successfully.");
        return response;
    }

    @PostMapping(value = "/user", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Submits single user.")
    public ResponseEntity<UserResource> submit(@RequestBody UserResource user) {
        log.info("Submitting single user...");
        String country = user.getCountry();
        if (!IsoCountyUtil.isValidIsoCountryCode(country)) {
            throw new BadRequestException(String.format("Illegal country code <%S> for user <%s> is given", country,
                            user.getDisplayName()));
        }

        Score score = conversionService.convert(user, Score.class);
        Score submittedScore = leaderBoardScoreService.submit(score);
        UserResource submittedUser = conversionService.convert(submittedScore, UserResource.class);
        ResponseEntity<UserResource> response = ResponseEntity.ok(submittedUser);
        log.info("Submitting single user finished successfully.");
        return response;
    }
}
