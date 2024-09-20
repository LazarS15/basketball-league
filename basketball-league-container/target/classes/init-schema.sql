CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS teams (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    image_path VARCHAR(255),
    logo_path VARCHAR(255),
    hall VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS players (
    id BIGSERIAL PRIMARY KEY,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    jersey_number INT,
    team_id BIGINT,
    image_path VARCHAR(255),
    birth_date DATE,
    CONSTRAINT unique_player_constraint UNIQUE (first_name, last_name, birth_date),
    FOREIGN KEY (team_id) REFERENCES teams(id)
);

CREATE TABLE IF NOT EXISTS games (
    id UUID PRIMARY KEY,
    round INT,
    home_team_id BIGINT,
    home_team_points INT,
    guest_team_points INT,
    guest_team_id BIGINT,
    is_played BOOLEAN,
    CONSTRAINT unique_game_constraint UNIQUE (home_team_id, guest_team_id),
    FOREIGN KEY (home_team_id) REFERENCES teams(id),
    FOREIGN KEY (guest_team_id) REFERENCES teams(id)
);

CREATE TABLE IF NOT EXISTS player_stats (
    id UUID PRIMARY KEY,
    game_id UUID,
    player_id BIGINT,
    player_points INT,
    FOREIGN KEY (game_id) REFERENCES games(id),
    FOREIGN KEY (player_id) REFERENCES players(id)
);

CREATE TABLE IF NOT EXISTS table_fields (
    id BIGINT PRIMARY KEY,
    team_id BIGINT,
    wins INT,
    losses INT,
    plus_minus INT,
    points INT,
    FOREIGN KEY (team_id) REFERENCES teams(id)
);

CREATE TABLE IF NOT EXISTS _user (
    id BIGSERIAL PRIMARY KEY,
    firstname VARCHAR(255),
    lastname VARCHAR(255),
    email VARCHAR(255),
    password VARCHAR(255),
    role VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS token (
    id BIGSERIAL PRIMARY KEY,
    token VARCHAR(255),
    token_type VARCHAR(255),
    revoked BOOLEAN,
    expired BOOLEAN,
    user_id BIGINT REFERENCES _user(id)
);
