create database election;

\c election;

create table candidate(
    id serial primary key,
    name text not null
);

create table voter (
    id serial primary key,
    name text not null
);

create type vote_type as enum ('VALID','BLANK','NULL');

create table vote (
    id serial primary key ,
    candidate_id int references candidate(id),
    voter_id int not null references voter(id),
    vote_type vote_type not null
)