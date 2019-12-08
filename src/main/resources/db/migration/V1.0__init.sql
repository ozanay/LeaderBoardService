create table if not exists users
(
    id character varying(255) primary key,
    display_name character varying(255) not null unique,
    name character varying(255),
    surname character varying(255),
    country character varying(5) not null
);

create table if not exists scores
(
    id character varying(255) not null primary key,
    score_value double precision not null,
    user_id character varying(255) not null,
    timestamp bigint,
    constraint leaderboard_user_id_fk foreign key (user_id)
        references users (id) match simple
        on update no action on delete no action
);

create index leaderboard_index on scores(score_value desc nulls last, user_id);
cluster scores using leaderboard_index