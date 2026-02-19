insert into candidate (name) values
                                 ('Alice'),
                                 ('Bob'),
                                 ('Charlie');

insert into voter (name) values
                             ('Voter1'),
                             ('Voter2'),
                             ('Voter3'),
                             ('Voter4'),
                             ('Voter5'),
                             ('Voter6');


insert into vote (candidate_id, voter_id, vote_type) values
                            (1,1,'VALID'),
                            (1,2,'VALID'),
                            (2,3,'VALID'),
                            (2,4,'BLANK'),
                            (null,5,'BLANK'),
                            (3,6,'NULL');

select setval('candidate_id_seq',(select max(id) from candidate));
select setval('vote_id_seq',(select max(id) from vote));
select setval('voter_id_seq',(select max(id) from voter));