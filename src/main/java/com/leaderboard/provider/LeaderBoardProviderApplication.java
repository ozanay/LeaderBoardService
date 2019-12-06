package com.leaderboard.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication (scanBasePackages = {"com.leaderboard.provider"})
@EnableJpaRepositories ("com.leaderboard.provider")
@EntityScan ("com.leaderboard.provider")
public class LeaderBoardProviderApplication {
    public static void main(String[] args) {
        SpringApplication.run(LeaderBoardProviderApplication.class, args);
    }
}
