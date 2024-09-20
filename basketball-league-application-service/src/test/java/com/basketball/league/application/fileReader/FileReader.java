package com.basketball.league.application.fileReader;

import com.basketball.league.domain.entity.*;
import com.basketball.league.domain.valueobject.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class FileReader {
    public static Game readGameFromFile(String filePath, boolean isPlayed) {
        Game playedGame = null;
        Game unplayedGame = null;

        try {
            List<String> lines = readLinesFromFile(filePath);
            for (String line : lines) {
                String[] parts = line.split(",");
                GameId gameId = new GameId(UUID.fromString(parts[0]));
                int round = Integer.parseInt(parts[1]);
                TeamId homeTeamId = new TeamId(Long.parseLong(parts[2]));
                int homeTeamPoints = Integer.parseInt(parts[3]);
                int guestTeamPoints = Integer.parseInt(parts[4]);
                TeamId guestTeamId = new TeamId(Long.parseLong(parts[5]));
                boolean played = Boolean.parseBoolean(parts[6]);

                if (played) {
                   playedGame = buildGame(gameId, round, homeTeamId,
                            homeTeamPoints, guestTeamPoints, guestTeamId, played);
                } else {
                    unplayedGame = buildGame(gameId, round, homeTeamId,
                            homeTeamPoints, guestTeamPoints, guestTeamId, played);
                }
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
        return isPlayed ? playedGame : unplayedGame;
    }
    public static List<Player> readPlayersFromFile(String filePath, long teamId) {
        List<Player> players = new ArrayList<>();
        try {
            List<String> lines = readLinesFromFile(filePath);
            for (String line : lines) {
                String[] parts = line.split(",");
                long id = Long.parseLong(parts[0]);
                String firstName = parts[1];
                String lastName = parts[2];
                int jerseyNumber = Integer.parseInt(parts[3]);
                TeamId fileTeamId = new TeamId(Long.parseLong(parts[4]));
                String imagePath = parts[5];
                LocalDate birthDate = LocalDate.parse(parts[6]);
                if (fileTeamId.getValue() == teamId) {
                    Player player = Player.builder()
                            .id(new PlayerId(id))
                            .firstName(firstName)
                            .lastName(lastName)
                            .jerseyNumber(jerseyNumber)
                            .imagePath(imagePath)
                            .teamId(fileTeamId)
                            .birthDate(birthDate)
                            .build();
                    players.add(player);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return players;
    }

    public static List<PlayerStats> readPlayerStatsFromFile(String filePath, boolean isPlayerPointsGreaterThanZeroList) {
        List<PlayerStats> playerStatsLessThanZeroPointsList = new ArrayList<>();
        List<PlayerStats> playerStatsGreaterThanZeroPointsList = new ArrayList<>();
        try {
            List<String> lines = readLinesFromFile(filePath);
            for (String line : lines) {
                String[] parts = line.split(",");
                UUID id = UUID.fromString(parts[0]);
                UUID gameId = UUID.fromString(parts[1]);
                long playerId = Long.parseLong(parts[2]);
                int points = Integer.parseInt(parts[3]);

                if (points > 0) {
                    playerStatsGreaterThanZeroPointsList.add(buildPlayerStats(id, playerId, gameId, points));
                } else {
                    playerStatsLessThanZeroPointsList.add(buildPlayerStats(id, playerId, gameId, points));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return isPlayerPointsGreaterThanZeroList
                ? playerStatsGreaterThanZeroPointsList : playerStatsLessThanZeroPointsList;
    }

    public static TableField readTableFieldFromFile(String filePath, long teamId) {
        TableField tableField = null;
        try {
            List<String> lines = readLinesFromFile(filePath);
            for (String line : lines) {
                String[] parts = line.split(",");
                Long id = Long.parseLong(parts[0]);
                TeamId fileTeamId = new TeamId(Long.parseLong(parts[1]));
                int wins = Integer.parseInt(parts[2]);
                int losses = Integer.parseInt(parts[3]);
                int plusMinus = Integer.parseInt(parts[4]);
                int points = Integer.parseInt(parts[5]);

                if (fileTeamId.getValue() == teamId) {
                    tableField = buildTableField(id, fileTeamId, wins, losses, plusMinus, points);
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
        return tableField;
    }

    public static List<Team> readTeamsFromFile(String filePath) {
        List<Team> teams = new ArrayList<>();
        try {
            List<String> lines = readLinesFromFile(filePath);
            for (String line : lines) {
                String[] parts = line.split(",");
                TeamId teamId = new TeamId(Long.parseLong(parts[0]));
                String name = parts[1];
                String imagePath = parts[2];
                String logoPath = parts[3];
                String hall = parts[4];

                teams.add(buildTeam(teamId, name, imagePath, logoPath, hall));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return teams;
    }

    private static List<String> readLinesFromFile(String filePath) throws IOException {
        List<String> lines = new ArrayList<>();
        try {
            File file = new File(filePath);
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine().trim();
                if (!data.isEmpty()) {
                    lines.add(data);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return lines;
    }
    private static Team buildTeam(TeamId teamId, String name,
                                  String imagePath, String logoPath, String hall) {
        return Team.builder()
                .id(teamId)
                .name(name)
                .imagePath(imagePath)
                .logoPath(logoPath)
                .hall(hall)
                .build();
    }

    public static TableField buildTableField(Long id, TeamId teamId, int wins,
                                             int losses, int plusMinus, int points) {
        return TableField.builder()
                .id(new TableFieldId(id))
                .teamId(teamId)
                .wins(wins)
                .losses(losses)
                .plusMinus(plusMinus)
                .points(points)
                .build();
    }
    private static PlayerStats buildPlayerStats(UUID id, Long playerId, UUID gameId, int points) {
        return PlayerStats.builder()
                .id(new PlayerStatsId(id))
                .playerId(new PlayerId(playerId))
                .gameId(new GameId(gameId))
                .playerPoints(points)
                .build();
    }

    private static Game buildGame(GameId id, int round, TeamId homeTeamId,
                                  int homeTeamPoints, int guestTeamPoints,TeamId guestTeamId, boolean isPlayed) {
        return Game.builder()
                .id(id)
                .round(round)
                .homeTeamId(homeTeamId)
                .homeTeamPoints(homeTeamPoints)
                .guestTeamPoints(guestTeamPoints)
                .guestTeamId(guestTeamId)
                .isPlayed(isPlayed)
                .build();
    }
}
