create table if not exists leaderBoardPlayers(
    id character varying(255) primary key,
    display_name character varying(255) not null,
    name character varying(255),
    surname character varying(255),
    country character varying(5) not null
);

create table if not exists leaderboard
(
    id character varying(255) not null primary key,
    points integer not null,
    player_id character varying(255) not null,
    timestamp bigint,
    constraint leaderboard_player_id_fk foreign key (player_id)
        references leaderBoardPlayers (id) match simple
        on update no action on delete no action
);

create index leaderboard_index on leaderboard(points desc nulls last);
cluster leaderboard using leaderboard_index