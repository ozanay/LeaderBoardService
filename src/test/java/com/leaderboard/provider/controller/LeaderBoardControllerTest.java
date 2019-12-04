package com.leaderboard.provider.controller;

import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.Collections;
import java.util.List;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.leaderboard.provider.controller.resource.LeaderBoardResource;
import com.leaderboard.provider.service.LeaderBoardUserService;
import com.leaderboard.provider.util.TestUtil;

public class LeaderBoardControllerTest {
    @Mock
    private LeaderBoardUserService leaderBoardUserService;
    
    private LeaderBoardController sut;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        sut = new LeaderBoardController(leaderBoardUserService, TestUtil.conversionService());
    }

    @Test
    public void should_get_empty_board_if_no_user_exists() {
        // Given
        when(leaderBoardUserService.getUsers()).thenReturn(Collections.emptyList());

        // When
        List<LeaderBoardResource> globalLeaderBoard = sut.getGlobalLeaderBoard();

        // Then
        assertTrue(globalLeaderBoard.isEmpty());
    }

    @Test
    public void should_get_leaderboard_according_to_descending_points() {
        // Given
        when(leaderBoardUserService.getUsers()).thenReturn(TestUtil.getOrderedUsers());

        // When
        List<LeaderBoardResource> globalLeaderBoard = sut.getGlobalLeaderBoard();

        // Then
        assertPointsAreDescending(globalLeaderBoard);
    }

    @Test
    public void should_get_leaderboard_according_to_ascending_ranks() {
        // Given
        when(leaderBoardUserService.getUsers()).thenReturn(TestUtil.getOrderedUsers());

        // When
        List<LeaderBoardResource> globalLeaderBoard = sut.getGlobalLeaderBoard();

        // Then
        assertRanksAreAscending(globalLeaderBoard);
    }

    private void assertPointsAreDescending(List<LeaderBoardResource> globalLeaderBoard) {
        for (int i = 0; i < globalLeaderBoard.size(); i++) {
            int nextIndex = i + 1;
            if (nextIndex != globalLeaderBoard.size()) {
                int current = globalLeaderBoard.get(i).getPoints();
                int next = globalLeaderBoard.get(nextIndex).getPoints();
                assertTrue(current > next);
            }
        }
    }
    
    private void assertRanksAreAscending(List<LeaderBoardResource> globalLeaderBoard) {
        for (int i = 0; i < globalLeaderBoard.size(); i++) {
            int expectedRank = i + 1;
            assertEquals(globalLeaderBoard.get(i).getRank(), expectedRank);
        }
    }
}
