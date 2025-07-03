-- 1. stadium_tb
INSERT INTO stadium_tb (stadium_name, location, stadium_type)
VALUES ('잠실야구장', '서울특별시 잠실동', 'OUTDOOR'),
       ('고척스카이돔', '서울특별시 고척동', 'INDOOR'),
       ('인천 SSG 랜더스필드', '인천광역시 문학동', 'OUTDOOR'),
       ('광주-기아 챔피언스필드', '광주광역시 임동', 'OUTDOOR'),
       ('대구 삼성라이온즈파크', '대구광역시 연호동', 'OUTDOOR'),
       ('부산 사직야구장', '부산광역시 사직동', 'OUTDOOR'),
       ('대전 한화생명이글스파크', '대전광역시 부사동', 'OUTDOOR'),
       ('창원 NC파크', '경상남도 창원시 양덕동', 'OUTDOOR'),
       ('수원 KT위즈파크', '경기도 수원시 조원동', 'OUTDOOR'),
       ('청주야구장', '충청북도 청주시 사직동', 'OUTDOOR'),
       ('울산 문수야구장', '울산광역시 옥동', 'OUTDOOR'),
       ('포항야구장', '경상북도 포항시', 'OUTDOOR'),
       ('군산 월명야구장', '전라북도 군산시', 'OUTDOOR');


-- 2. stadium_correction_tb (구장 보정 계수)테이블
INSERT INTO stadium_correction_tb (stadium_id, correction, this_year)
VALUES (1, 0.732, 2025);
INSERT INTO stadium_correction_tb (stadium_id, correction, this_year)
VALUES (2, 0.822, 2025);
INSERT INTO stadium_correction_tb (stadium_id, correction, this_year)
VALUES (3, 1.489, 2025);
INSERT INTO stadium_correction_tb (stadium_id, correction, this_year)
VALUES (4, 0.953, 2025);
INSERT INTO stadium_correction_tb (stadium_id, correction, this_year)
VALUES (5, 1.522, 2025);
INSERT INTO stadium_correction_tb (stadium_id, correction, this_year)
VALUES (6, 0.729, 2025);
INSERT INTO stadium_correction_tb (stadium_id, correction, this_year)
VALUES (7, 0.982, 2025);
INSERT INTO stadium_correction_tb (stadium_id, correction, this_year)
VALUES (8, 1.085, 2025);
INSERT INTO stadium_correction_tb (stadium_id, correction, this_year)
VALUES (9, 1.24, 2025);
INSERT INTO stadium_correction_tb (stadium_id, correction, this_year)
VALUES (10, 1.16, 2025);
INSERT INTO stadium_correction_tb (stadium_id, correction, this_year)
VALUES (11, 1.08, 2025);
INSERT INTO stadium_correction_tb (stadium_id, correction, this_year)
VALUES (12, 1.02, 2025);

-- 3. stadium_coordinate_tb 테이블
INSERT INTO stadium_coordinate_tb (stadium_id, longitude, latitude)
VALUES (1, 127.07, 37.51); -- 잠실야구장 (서울특별시 송파구 잠실동)
INSERT INTO stadium_coordinate_tb (stadium_id, longitude, latitude)
VALUES (2, 126.89, 37.50); -- 고척스카이돔 (서울특별시 구로구 고척동)
INSERT INTO stadium_coordinate_tb (stadium_id, longitude, latitude)
VALUES (3, 126.69, 37.45); -- 인천 SSG 랜더스필드 (인천광역시 미추홀구 문학동)
INSERT INTO stadium_coordinate_tb (stadium_id, longitude, latitude)
VALUES (4, 126.89, 35.56); -- 광주-기아 챔피언스필드 (광주광역시 북구 임동)
INSERT INTO stadium_coordinate_tb (stadium_id, longitude, latitude)
VALUES (5, 128.65, 35.32); -- 대구 삼성라이온즈파크 (대구 수성구 연호동)
INSERT INTO stadium_coordinate_tb (stadium_id, longitude, latitude)
VALUES (6, 129.06, 35.52); -- 부산 사직야구장 (부산 동래구 사직동)
INSERT INTO stadium_coordinate_tb (stadium_id, longitude, latitude)
VALUES (7, 127.42, 36.29); -- 대전 한화생명이글스파크 (대전 중구 부사동)
INSERT INTO stadium_coordinate_tb (stadium_id, longitude, latitude)
VALUES (8, 128.57, 35.33); -- 창원 NC파크 (창원 마산회원구 양덕동)
INSERT INTO stadium_coordinate_tb (stadium_id, longitude, latitude)
VALUES (9, 127.01, 37.59); -- 수원 KT위즈파크 (경기 수원시 장안구 조원동)
INSERT INTO stadium_coordinate_tb (stadium_id, longitude, latitude)
VALUES (10, 127.49, 36.64); -- 청주야구장 (청주시 서원구 사직동)
INSERT INTO stadium_coordinate_tb (stadium_id, longitude, latitude)
VALUES (11, 129.27, 35.53); -- 울산 문수야구장 (울산 남구 옥동)
INSERT INTO stadium_coordinate_tb (stadium_id, longitude, latitude)
VALUES (12, 128.80, 35.86); -- 포항야구장 (경북 포항시 남구)
INSERT INTO stadium_coordinate_tb (stadium_id, longitude, latitude)
VALUES (13, 126.50, 35.98);
-- 군산 월명야구장 (전북 군산시)

-- 4. team_tb
INSERT INTO team_tb (stadium_id1, stadium_id2, team_name, logo_url, ticket_link)
VALUES (1, NULL, 'LG 트윈스', 'https://upload.wikimedia.org/wikipedia/ko/4/4f/LG_Twins.png',
        'https://www.lgtwins.com/service/html.ncd?baRs=OUT_DS&view=%2Fpc_twins%2Fticket%2Fticketbuy&actID=BR_RetrieveTicketing'),
       (1, NULL, '두산 베어스', 'https://upload.wikimedia.org/wikipedia/ko/0/09/Doosan_Bears.png',
        'https://ticket.interpark.com/Contents/Sports/GoodsInfo?SportsCode=07001&TeamCode=PB004'),
       (2, NULL, '키움 히어로즈', 'https://upload.wikimedia.org/wikipedia/ko/5/52/Kiwoom_Heroes.png',
        'https://ticket.interpark.com/Contents/Sports/GoodsInfo?SportsCode=07001&TeamCode=PB003'),
       (3, NULL, 'SSG 랜더스', 'https://upload.wikimedia.org/wikipedia/ko/0/02/SSG_Landers.png',
        'https://www.ticketlink.co.kr/sports#reservation'),
       (4, 13, 'KIA 타이거즈', 'https://upload.wikimedia.org/wikipedia/ko/6/6e/KIA_Tigers.png',
        'https://tigers.co.kr/ticket/reservation'),
       (5, 11, '삼성 라이온즈', 'https://upload.wikimedia.org/wikipedia/ko/9/95/Samsung_Lions.png',
        'https://www.ticketlink.co.kr/sports#reservation'),
       (6, 12, '롯데 자이언츠', 'https://upload.wikimedia.org/wikipedia/ko/9/91/Lotte_Giants.png',
        'https://ticket.giantsclub.com/loginForm.do'),
       (7, 10, '한화 이글스', 'https://upload.wikimedia.org/wikipedia/ko/9/99/Hanwha_Eagles.png',
        'https://www.hanwhaeagles.co.kr/ticketInfo.do'),
       (8, NULL, 'NC 다이노스', 'https://upload.wikimedia.org/wikipedia/ko/b/bd/NC_Dinos.png',
        'https://ticket.ncdinos.com/login'),
       (9, NULL, 'KT 위즈', 'https://upload.wikimedia.org/wikipedia/ko/b/b3/KT_Wiz.png',
        'https://www.ktwiz.co.kr/ticket/reservation');

-- 5.user_tb
INSERT INTO user_tb
(password, name, nickname, team_id, email, birth_date, gender, profile_url, user_role)
VALUES ('1234', '쌀', 'ssar', 1, 'ssar@nate.com', '1999-09-09', 'MALE', '/img/profile.png', 'USER'),
       ('1234', '코스', 'cos', 2, 'cos@nate.com', '2000-01-01', 'FEMALE', '/img/profile.png', 'USER'),
       ('1234', '러브', 'love', 3, 'love@nate.com', '1998-08-08', 'MALE', '/img/profile.png', 'USER'),
       ('1234', '하하', 'haha', NULL, 'haha@nate.com', '1997-07-07', 'FEMALE', '/img/profile.png', 'ADMIN'),
       ('1234', '백하림', 'harim', 4, 'harim@example.com', '1995-05-05', 'MALE', '/img/profile.png', 'USER'),
       ('1234', '김정원', 'jungwon', 5, 'jungwon@example.com', '1996-06-06', 'MALE', '/img/profile.png', 'USER'),
       ('1234', '김미숙', 'misook', 6, 'misook@example.com', '1994-04-04', 'FEMALE', '/img/profile.png', 'USER');

-- 6.game_tb
INSERT INTO game_tb (id, game_time, stadium_id, broadcast_channel, game_status,
                     home_team_id, away_team_id,
                     home_win_per, away_win_per,
                     home_prediction_score, away_prediction_score,
                     home_result_score, away_result_score,
                     total_prediction_score)
VALUES (423, TIMESTAMP '2025-06-27 18:30:00', 1, 'MS_T', 'COMPLETED', 2, 4, 50.0, 50.0, NULL, NULL, 0, 0, NULL),
       (424, TIMESTAMP '2025-06-27 18:30:00', 5, 'KN_T', 'COMPLETED', 6, 8, 50.0, 50.0, NULL, NULL, 0, 0, NULL),
       (425, TIMESTAMP '2025-06-27 18:30:00', 8, 'SS_T', 'COMPLETED', 9, 7, 50.0, 50.0, NULL, NULL, 0, 0, NULL),
       (426, TIMESTAMP '2025-06-27 18:30:00', 9, 'SPO_T', 'COMPLETED', 10, 1, 50.0, 50.0, NULL, NULL, 0, 0, NULL),
       (427, TIMESTAMP '2025-06-27 18:30:00', 2, 'SPO_2T', 'COMPLETED', 3, 5, 50.0, 50.0, NULL, NULL, 0, 0, NULL);


-- 7. today_game_tb
INSERT INTO today_game_tb (id, game_time, stadium_id, broadcast_channel, game_status,
                           home_team_id, away_team_id,
                           home_win_per, away_win_per,
                           home_prediction_score, away_prediction_score,
                           home_result_score, away_result_score,
                           total_prediction_score)
VALUES (423, TIMESTAMP '2025-06-27 18:30:00', 1, 'MS_T', 'COMPLETED', 2, 4, 50.0, 50.0, NULL, NULL, 0, 0, NULL),
       (424, TIMESTAMP '2025-06-27 18:30:00', 5, 'KN_T', 'COMPLETED', 6, 8, 50.0, 50.0, NULL, NULL, 0, 0, NULL),
       (425, TIMESTAMP '2025-06-27 18:30:00', 8, 'SS_T', 'COMPLETED', 9, 7, 50.0, 50.0, NULL, NULL, 0, 0, NULL),
       (426, TIMESTAMP '2025-06-27 18:30:00', 9, 'SPO_T', 'COMPLETED', 10, 1, 50.0, 50.0, NULL, NULL, 0, 0, NULL),
       (427, TIMESTAMP '2025-06-27 18:30:00', 2, 'SPO_2T', 'COMPLETED', 3, 5, 50.0, 50.0, NULL, NULL, 0, 0, NULL);

8. player_tb
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (51867, 4, '김건우'),
       (55257, 2, '콜어빈'),
       (52701, 8, '문동주'),
       (55460, 6, '가라비토'),
       (55532, 7, '감보아'),
       (66920, 9, '최성영'),
       (61101, 1, '임찬규'),
       (50030, 10, '소형준'),
       (76225, 5, '김건국'),
       (64350, 3, '하영민');

-- 9. 선발투수
INSERT INTO starting_pitcher_tb (game_id, player_id, profile_url,
                                 era, game_count, result, qs, whip)
VALUES (423, 1, 'https://6ptotvmi5753.edge.naverncp.com/KBO_IMAGE/person/kbo/2025/51867.png', 4.58, 27, '2승 3패', 0,
        1.53),
       (423, 2, 'https://6ptotvmi5753.edge.naverncp.com/KBO_IMAGE/person/kbo/2025/55257.png', 4.86, 14, '5승 7패', 8,
        1.38),
       (424, 3, 'https://6ptotvmi5753.edge.naverncp.com/KBO_IMAGE/person/kbo/2025/52701.png', 4.09, 11, '5승 2패', 4,
        1.2),
       (424, 4, 'https://6ptotvmi5753.edge.naverncp.com/KBO_IMAGE/person/kbo/2025/55460.png', NULL, NULL, '없음', NULL,
        NULL),
       (425, 5, 'https://6ptotvmi5753.edge.naverncp.com/KBO_IMAGE/person/kbo/2025/55532.png', 2.37, 5, '4승 1패', 4,
        1.02),
       (425, 6, 'https://6ptotvmi5753.edge.naverncp.com/KBO_IMAGE/person/kbo/2025/66920.png', 5.34, 17, '2승 2패', 0,
        1.71),
       (426, 7, 'https://6ptotvmi5753.edge.naverncp.com/KBO_IMAGE/person/kbo/2025/61101.png', 2.61, 14, '8승 2패', 9,
        1.18),
       (426, 8, 'https://6ptotvmi5753.edge.naverncp.com/KBO_IMAGE/person/kbo/2025/50030.png', 3.13, 13, '5승 2패', 10,
        1.18),
       (427, 9, 'https://6ptotvmi5753.edge.naverncp.com/KBO_IMAGE/person/kbo/2025/76225.png', 7.53, 11, '없음', 0,
        1.88),
       (427, 10, 'https://6ptotvmi5753.edge.naverncp.com/KBO_IMAGE/person/kbo/2025/64350.png', 4.88, 15, '6승 7패', 8,
        1.51);

-- 10. 오늘의 선발투수
INSERT INTO today_starting_pitcher_tb (game_id, player_id, profile_url,
                                       era, game_count, qs, whip, result)
VALUES (423, 1, 'https://6ptotvmi5753.edge.naverncp.com/KBO_IMAGE/person/kbo/2025/51867.png', 4.58, 27, 0, 1.53,
        '2승 3패'),
       (423, 2, 'https://6ptotvmi5753.edge.naverncp.com/KBO_IMAGE/person/kbo/2025/55257.png', 4.86, 14, 8, 1.38,
        '5승 7패'),
       (424, 3, 'https://6ptotvmi5753.edge.naverncp.com/KBO_IMAGE/person/kbo/2025/52701.png', 4.09, 11, 4, 1.2,
        '5승 2패'),
       (424, 4, 'https://6ptotvmi5753.edge.naverncp.com/KBO_IMAGE/person/kbo/2025/55460.png', NULL, NULL, NULL, NULL,
        '없음'),
       (425, 5, 'https://6ptotvmi5753.edge.naverncp.com/KBO_IMAGE/person/kbo/2025/55532.png', 2.37, 5, 4, 1.02,
        '4승 1패'),
       (425, 6, 'https://6ptotvmi5753.edge.naverncp.com/KBO_IMAGE/person/kbo/2025/66920.png', 5.34, 17, 0, 1.71,
        '2승 2패'),
       (426, 7, 'https://6ptotvmi5753.edge.naverncp.com/KBO_IMAGE/person/kbo/2025/61101.png', 2.61, 14, 9, 1.18,
        '8승 2패'),
       (426, 8, 'https://6ptotvmi5753.edge.naverncp.com/KBO_IMAGE/person/kbo/2025/50030.png', 3.13, 13, 10, 1.18,
        '5승 2패'),
       (427, 9, 'https://6ptotvmi5753.edge.naverncp.com/KBO_IMAGE/person/kbo/2025/76225.png', 7.53, 11, 0, 1.88,
        '없음'),
       (427, 10, 'https://6ptotvmi5753.edge.naverncp.com/KBO_IMAGE/person/kbo/2025/64350.png', 4.88, 15, 8, 1.51,
        '6승 7패');