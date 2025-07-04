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
       ('청주 야구장', '충청북도 청주시 사직동', 'OUTDOOR'),
       ('울산 문수야구장', '울산광역시 옥동', 'OUTDOOR'),
       ('포항 야구장', '경상북도 포항시', 'OUTDOOR'),
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
VALUES (423, TIMESTAMP '2025-07-04 18:30:00', 1, 'MS_T', 'COMPLETED', 2, 10, 50.0, 50.0, NULL, NULL, 0, 0, NULL),
       (424, TIMESTAMP '2025-07-04 18:30:00', 5, 'KN_T', 'COMPLETED', 6, 1, 50.0, 50.0, NULL, NULL, 0, 0, NULL),
       (425, TIMESTAMP '2025-07-04 18:30:00', 8, 'SS_T', 'COMPLETED', 9, 4, 50.0, 50.0, NULL, NULL, 0, 0, NULL),
       (426, TIMESTAMP '2025-07-04 18:30:00', 9, 'SPO_T', 'COMPLETED', 5, 7, 50.0, 50.0, NULL, NULL, 0, 0, NULL),
       (427, TIMESTAMP '2025-07-04 18:30:00', 2, 'SPO_2T', 'COMPLETED', 3, 8, 50.0, 50.0, NULL, NULL, 0, 0, NULL);


-- 7. today_game_tb
INSERT INTO today_game_tb (id, game_time, stadium_id, broadcast_channel, game_status,
                           home_team_id, away_team_id,
                           home_win_per, away_win_per,
                           home_prediction_score, away_prediction_score,
                           home_result_score, away_result_score,
                           total_prediction_score)
VALUES (423, TIMESTAMP '2025-07-04 18:30:00', 1, 'MS_T', 'COMPLETED', 2, 10, 50.0, 50.0, NULL, NULL, 0, 0, NULL),
       (424, TIMESTAMP '2025-07-04 18:30:00', 5, 'KN_T', 'COMPLETED', 6, 1, 50.0, 50.0, NULL, NULL, 0, 0, NULL),
       (425, TIMESTAMP '2025-07-04 18:30:00', 8, 'SS_T', 'COMPLETED', 9, 4, 50.0, 50.0, NULL, NULL, 0, 0, NULL),
       (426, TIMESTAMP '2025-07-04 18:30:00', 9, 'SPO_T', 'COMPLETED', 5, 7, 50.0, 50.0, NULL, NULL, 0, 0, NULL),
       (427, TIMESTAMP '2025-07-04 18:30:00', 2, 'SPO_2T', 'COMPLETED', 3, 8, 50.0, 50.0, NULL, NULL, 0, 0, NULL);


-- 8. player_tb
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (52769, 8, '권광민');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55764, 8, '권민규');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (69702, 8, '김건');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (52703, 8, '김겸재');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (53701, 8, '김관우');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (51713, 8, '김규연');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (51715, 8, '김기중');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (54705, 8, '김도빈');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (65764, 8, '김민우');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (65769, 8, '김범수');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (50710, 8, '김범준');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (53754, 8, '김서현');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (50711, 8, '김승일');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (53708, 8, '김예준');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (66715, 8, '김인환');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (63765, 8, '김종수');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (66704, 8, '김태연');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (52717, 8, '노석진');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (69737, 8, '노시환');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (76715, 8, '류현진');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55703, 8, '리베라토');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (52701, 8, '문동주');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (51705, 8, '문승진');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (53764, 8, '문현빈');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (52711, 8, '민승기');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55711, 8, '박부성');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (66707, 8, '박상언');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (67703, 8, '박상원');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55705, 8, '박상현');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (68703, 8, '박성웅');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (50709, 8, '박정현');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (52731, 8, '박준영');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (51761, 8, '배동현');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (69974, 8, '배민서');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55798, 8, '배승수');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (53711, 8, '성지훈');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (51767, 8, '송호정');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (53717, 8, '신우재');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (64006, 8, '심우준');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (51704, 8, '안진');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (79608, 8, '안치홍');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (50813, 8, '양선률');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (65056, 8, '엄상백');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55707, 8, '엄상현');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55708, 8, '엄요셉');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (54755, 8, '와이스');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (54702, 8, '원종혁');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (69766, 8, '유로결');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (52765, 8, '유민');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (63464, 8, '윤대경');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (69213, 8, '윤산흠');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (54703, 8, '이기창');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (65703, 8, '이도윤');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55704, 8, '이동영');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (65616, 8, '이민우');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55710, 8, '이민재');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (65117, 8, '이상규');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (52704, 8, '이상혁');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (52714, 8, '이성민');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55712, 8, '이승현');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (68700, 8, '이원석');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (76812, 8, '이재원');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55706, 8, '이지성');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (66657, 8, '이진영');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (63749, 8, '이충호');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (60768, 8, '이태양');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (50704, 8, '임종찬');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (51762, 8, '장규현');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (79764, 8, '장민재');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (77318, 8, '장시환');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (69645, 8, '장지수');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (51764, 8, '정민규');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (54794, 8, '정안석');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55743, 8, '정우주');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (69767, 8, '정이황');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (54768, 8, '조동욱');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (69705, 8, '조한민');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (65707, 8, '주현상');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (79192, 8, '채은성');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (50707, 8, '최인호');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (78288, 8, '최재훈');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55709, 8, '최주원');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (54710, 8, '최준서');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55730, 8, '폰세');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55734, 8, '플로리얼');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (62700, 8, '하주석');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (52710, 8, '한경빈');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (53703, 8, '한서구');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (61666, 8, '한승혁');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55767, 8, '한지윤');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (69706, 8, '허관회');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (52764, 8, '허인서');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (54795, 8, '황영묵');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (54729, 8, '황준서');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (50105, 1, '강민');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (53103, 1, '강민균');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55116, 1, '고영웅');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (53104, 1, '곽민호');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (69100, 1, '구본혁');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (53168, 1, '권동혁');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (77263, 1, '김강률');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (66145, 1, '김대현');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (54107, 1, '김도윤');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (67504, 1, '김민수');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (53144, 1, '김범석');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (52105, 1, '김성우');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (69105, 1, '김성진');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (50115, 1, '김수인');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55167, 1, '김영우');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (68130, 1, '김영준');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55129, 1, '김웅');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (64596, 1, '김유영');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (68109, 1, '김의준');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55122, 1, '김종운');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (66162, 1, '김주성');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (65411, 1, '김주온');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (62558, 1, '김준태');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (51104, 1, '김지용');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (75867, 1, '김진성');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (51154, 1, '김진수');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (76290, 1, '김현수');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (54166, 1, '김현종');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (51103, 1, '김형욱');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (69102, 1, '문보경');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (68119, 1, '문성주');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (52121, 1, '문정빈');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (54123, 1, '박건우');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55164, 1, '박관우');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (79365, 1, '박동원');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (53139, 1, '박명근');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (51112, 1, '박민호');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55121, 1, '박시원');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (62415, 1, '박해민');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (63145, 1, '배재준');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (68096, 1, '백선기');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (65109, 1, '백승현');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55160, 1, '서영준');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (68159, 1, '성동현');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55118, 1, '성준서');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (54156, 1, '손용준');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (67143, 1, '손주영');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (53105, 1, '송대현');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (51111, 1, '송승기');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (68110, 1, '송찬의');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (65207, 1, '신민재');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (54108, 1, '심규빈');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (61411, 1, '심창민');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55109, 1, '안시후');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (65115, 1, '안익훈');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (52123, 1, '양진혁');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (52124, 1, '엄태경');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (54119, 1, '에르난데스');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (53123, 1, '오스틴');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (79109, 1, '오지환');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (51526, 1, '우강훈');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55111, 1, '우정안');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (53101, 1, '원상훈');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (50106, 1, '유영찬');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (51110, 1, '이믿음');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (51100, 1, '이영빈');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (61145, 1, '이우찬');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (69134, 1, '이정용');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (96153, 1, '이정준');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (50904, 1, '이종준');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (52154, 1, '이주헌');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (53160, 1, '이준서');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (69108, 1, '이지강');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55165, 1, '이태훈');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55100, 1, '이한림');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (53100, 1, '임정균');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (61101, 1, '임찬규');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (63950, 1, '장현식');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (68869, 1, '전경원');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (67123, 1, '전준호');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (69159, 1, '정우영');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (54149, 1, '정지헌');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (51163, 1, '조건희');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (52149, 1, '조원태');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (54148, 1, '진우영');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (50054, 1, '천성호');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (65905, 1, '최승민');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (52163, 1, '최용하');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (52103, 1, '최원영');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (68419, 1, '최채흥');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55163, 1, '추세현');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55146, 1, '치리노스');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55138, 1, '코엔 윈');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (50107, 1, '하영진');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (63248, 1, '함덕주');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (50108, 1, '함창건');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (53102, 1, '허용주');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (52119, 1, '허준혁');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (66108, 1, '홍창기');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55532, 7, '감보아');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (54567, 7, '강성우');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (54506, 7, '강승구');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (69517, 7, '고승민');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (63543, 7, '구승민');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (65522, 7, '김강현');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (53540, 7, '김기준');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (54511, 7, '김대현');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (68585, 7, '김도규');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (69592, 7, '김동규');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (52504, 7, '김동혁');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55502, 7, '김동현');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (77564, 7, '김민성');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (76430, 7, '김상수');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (52514, 7, '김세민');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (62528, 7, '김원중');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (51516, 7, '김진욱');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55507, 7, '김태균');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55566, 7, '김태현');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55501, 7, '김현우');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (67539, 7, '나균안');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (51551, 7, '나승엽');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (62931, 7, '노진혁');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55536, 7, '데이비슨');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (54529, 7, '레이예스');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (53597, 7, '박건');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55509, 7, '박건우');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (50563, 7, '박로건');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (64021, 7, '박세웅');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (66047, 7, '박세진');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55568, 7, '박세현');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (62802, 7, '박승욱');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (60559, 7, '박시영');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (69507, 7, '박영완');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55563, 7, '박재엽');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (54503, 7, '박준우');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55508, 7, '박지훈');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (69594, 7, '박진');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (63512, 7, '박진형');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55530, 7, '박찬형');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (52528, 7, '반즈');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (54504, 7, '배세종');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (53598, 7, '배인혁');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (54507, 7, '백두산');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (53569, 7, '서동욱');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (51528, 7, '손성빈');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (50150, 7, '손호영');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (51594, 7, '송재영');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (68067, 7, '신병률');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (69508, 7, '신윤후');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (64017, 7, '심재민');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (52565, 7, '엄장윤');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55514, 7, '오창현');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (61102, 7, '유강남');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55512, 7, '유태웅');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (52591, 7, '윤동희');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (67588, 7, '윤성빈');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (53505, 7, '윤수녕');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (52530, 7, '이민석');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (51592, 7, '이병준');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55513, 7, '이상화');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (68529, 7, '이승헌');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55503, 7, '이영재');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (50608, 7, '이인한');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (51560, 7, '이주찬');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55511, 7, '이태경');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (54598, 7, '이호준');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (68507, 7, '장두성');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (68205, 7, '전민재');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (78513, 7, '전준우');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (68518, 7, '정보근');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55515, 7, '정선우');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (68556, 7, '정성종');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (51546, 7, '정우준');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (68242, 7, '정철원');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (54537, 7, '정현수');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (60523, 7, '정훈');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (53504, 7, '조경민');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (52516, 7, '조세진');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55504, 7, '조영우');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (52558, 7, '진승현');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (76650, 7, '진해수');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55505, 7, '최민규');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (68036, 7, '최이준');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (50556, 7, '최준용');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (62884, 7, '최항');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (52560, 7, '하혜성');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55506, 7, '한승현');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (52568, 7, '한태양');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (62363, 7, '한현희');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (68260, 7, '현도훈');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (50596, 7, '홍민기');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (50500, 7, '황성빈');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (54607, 5, '강동훈');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (54613, 5, '강민제');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (52659, 5, '강병우');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (67604, 5, '강이준');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (61353, 5, '고종욱');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (53609, 5, '곽도규');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (51640, 5, '권다결');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (76225, 5, '김건국');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55618, 5, '김경묵');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (66614, 5, '김규성');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (69620, 5, '김기훈');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (60337, 5, '김대유');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (52605, 5, '김도영');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (69745, 5, '김도현');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (54614, 5, '김두현');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (69627, 5, '김민수');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (54608, 5, '김민재');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (54645, 5, '김민주');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (63894, 5, '김사윤');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (67610, 5, '김석환');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (78603, 5, '김선빈');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (51604, 5, '김선우');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (53637, 5, '김세일');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (66492, 5, '김승현');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (50613, 5, '김양수');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (53619, 5, '김재현');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55646, 5, '김정엽');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (78122, 5, '김태군');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55610, 5, '김태형');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (69516, 5, '김현수');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (65653, 5, '김호령');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (62947, 5, '나성범');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55629, 5, '나연우');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (54640, 5, '네일');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (51645, 5, '박건우');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (50657, 5, '박민');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (52634, 5, '박상준');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55636, 5, '박재현');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (67609, 5, '박정우');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (63638, 5, '박준표');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (64646, 5, '박찬호');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55637, 5, '박헌');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (69727, 5, '변우혁');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (78168, 5, '서건창');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (54610, 5, '성영탁');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (52642, 5, '신명승');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55659, 5, '양수호');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (77637, 5, '양현종');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55631, 5, '엄준현');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (68330, 5, '예진원');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (50622, 5, '오규석');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (69636, 5, '오선우');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (68614, 5, '오정환');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55633, 5, '올러');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55645, 5, '위즈덤');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (67640, 5, '유승철');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (50604, 5, '유지성');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (52667, 5, '윤도현');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (53613, 5, '윤영철');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (68619, 5, '윤중현');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (53615, 5, '이도현');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (54644, 5, '이상준');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55632, 5, '이성원');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (51668, 5, '이승재');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (51605, 5, '이영재');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (63260, 5, '이우성');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (51648, 5, '이의리');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (51602, 5, '이준범');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (65665, 5, '이준영');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (64560, 5, '이창진');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (62951, 5, '이형범');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55663, 5, '이호민');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (62754, 5, '임기영');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55630, 5, '임다온');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (51665, 5, '장민기');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (51603, 5, '장시현');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (50641, 5, '장재혁');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (66609, 5, '전상현');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (50662, 5, '정해영');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (53659, 5, '정해원');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (54667, 5, '조대현');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (63342, 5, '조상우');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (66354, 5, '주효상');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55628, 5, '최건희');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (66606, 5, '최원준');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (65464, 5, '최정용');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (52639, 5, '최지민');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (72443, 5, '최형우');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (52628, 5, '한승연');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (63722, 5, '한승택');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (68646, 5, '한준수');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (69643, 5, '홍원빈');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (50600, 5, '홍종표');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (65610, 5, '황대인');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (52641, 5, '황동하');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (53006, 10, '강건');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (69064, 10, '강민성');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (68050, 10, '강백호');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (50066, 10, '강현우');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (64001, 10, '고영표');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (51003, 10, '권동진');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (52003, 10, '권성준');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (51005, 10, '김건형');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55040, 10, '김동현');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (54097, 10, '김민석');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (65048, 10, '김민수');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (64004, 10, '김민혁');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (52002, 10, '김병준');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (79402, 10, '김상수');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55043, 10, '김재원');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (51299, 10, '김주완');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (68906, 10, '김철호');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (66064, 10, '김태오');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (67025, 10, '로하스');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (50007, 10, '문상준');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (64007, 10, '문상철');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (67419, 10, '문용익');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55046, 10, '박건우');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55004, 10, '박민석');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (69056, 10, '박민석');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (52060, 10, '박영현');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55002, 10, '박준혁');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (54009, 10, '박태완');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (64166, 10, '배정대');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (65516, 10, '배제성');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55012, 10, '서영준');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (50109, 10, '성재헌');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (50030, 10, '소형준');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (69041, 10, '손동현');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (64012, 10, '송민섭');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (51057, 10, '신범준');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (54007, 10, '신호준');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (67006, 10, '안치영');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (52001, 10, '안현민');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55005, 10, '오서진');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (50859, 10, '오원석');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (64504, 10, '오윤석');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (75334, 10, '오재일');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (73117, 10, '우규민');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (52006, 10, '우종휘');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (54063, 10, '원상현');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (51013, 10, '유준규');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (68801, 10, '유호식');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (54064, 10, '육청명');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55007, 10, '윤상인');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (50092, 10, '윤준혁');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (69054, 10, '이상동');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (52063, 10, '이상우');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (69068, 10, '이선우');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (54012, 10, '이승언');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55009, 10, '이승준');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (54011, 10, '이승현');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55006, 10, '이용현');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (67048, 10, '이정현');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55003, 10, '이정환');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (67644, 10, '이정훈');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (67066, 10, '이종혁');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (53005, 10, '이준명');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (68896, 10, '이채호');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (69623, 10, '이태규');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (54014, 10, '이현민');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (68504, 10, '이호연');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (69113, 10, '임준형');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55010, 10, '장민호');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (78548, 10, '장성우');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (64115, 10, '장준원');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (66706, 10, '장진혁');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (69047, 10, '전용주');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55008, 10, '정영웅');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55011, 10, '정운교');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (52007, 10, '정정우');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (68089, 10, '조대현');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (64768, 10, '조이현');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (65060, 10, '주권');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (51060, 10, '지명성');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (79140, 10, '최동환');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (52008, 10, '최동희');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (51007, 10, '최성민');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (50650, 10, '최용준');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (69032, 10, '쿠에바스');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (52065, 10, '한지웅');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (51058, 10, '한차현');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (79240, 10, '허경민');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (54354, 10, '헤이수스');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (76313, 10, '황재균');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (51868, 4, '고명준');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (71837, 4, '김강민');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (51867, 4, '김건우');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (77829, 4, '김광현');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (54810, 4, '김규민');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (52844, 4, '김도현');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (68043, 4, '김민');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (62864, 4, '김민식');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (50848, 4, '김성민');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (62934, 4, '김성욱');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (76802, 4, '김성현');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (67900, 4, '김수윤');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (53865, 4, '김정민');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (53805, 4, '김준영');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (66917, 4, '김찬형');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (69825, 4, '김창평');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (52803, 4, '김태윤');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (65343, 4, '김택형');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55895, 4, '김현재');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (73211, 4, '노경은');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55802, 4, '도재현');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (50868, 4, '류효승');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55844, 4, '맥브룸');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (62869, 4, '문승원');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (54802, 4, '박기호');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (52864, 4, '박상후');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (54807, 4, '박성빈');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (67893, 4, '박성한');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (50812, 4, '박시후');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (51860, 4, '박정빈');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (60841, 4, '박종훈');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (54805, 4, '박지환');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (69839, 4, '백승건');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (54814, 4, '백준서');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (61895, 4, '서진용');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (52804, 4, '석정우');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (53898, 4, '송영진');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (66662, 4, '신범수');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55860, 4, '신지환');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (52873, 4, '신헌민');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (66864, 4, '안상현');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (53897, 4, '안현서');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (54833, 4, '앤더슨');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (53827, 4, '에레디아');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (60558, 4, '오태곤');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (52867, 4, '윤태현');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (64861, 4, '이건욱');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55896, 4, '이도우');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (53892, 4, '이로운');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (54806, 4, '이승민');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (53809, 4, '이승훈');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55893, 4, '이원준');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55832, 4, '이율예');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (67807, 4, '이정범');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (79456, 4, '이지영');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (52807, 4, '임근우');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (52802, 4, '임성준');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (51895, 4, '장지훈');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55899, 4, '장현진');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (52809, 4, '전영준');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (66858, 4, '정동윤');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (54812, 4, '정준재');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (54815, 4, '정현승');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (51897, 4, '조병현');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (51809, 4, '조요한');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (51865, 4, '조형우');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (69804, 4, '채현우');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55890, 4, '천범석');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (68856, 4, '최민준');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (65157, 4, '최민창');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (68805, 4, '최상민');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (69893, 4, '최수호');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55894, 4, '최윤석');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (75847, 4, '최정');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (68868, 4, '최준우');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (50854, 4, '최지훈');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (54803, 4, '최현석');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (69813, 4, '하재훈');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (68092, 4, '한두솔');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (62895, 4, '한유섬');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55801, 4, '한지헌');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (54813, 4, '허진');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (50840, 4, '현원회');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55897, 4, '홍대인');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55855, 4, '화이트');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55460, 6, '가라비토');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55427, 6, '강민성');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (74540, 6, '강민호');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (53460, 6, '강준서');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (64610, 6, '강한울');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (68407, 6, '공민규');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (62404, 6, '구자욱');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55441, 6, '권현우');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (61365, 6, '김대우');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (54401, 6, '김대호');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (69442, 6, '김도환');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (54402, 6, '김동현');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (68403, 6, '김무신');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (64793, 6, '김민수');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55420, 6, '김백산');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (52402, 6, '김상민');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55425, 6, '김상준');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (54403, 6, '김성경');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (67449, 6, '김성윤');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (53464, 6, '김시온');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (52430, 6, '김영웅');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55429, 6, '김유현');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (65132, 6, '김재성');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (65062, 6, '김재윤');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (52424, 6, '김재혁');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (54407, 6, '김재형');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (50458, 6, '김지찬');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (69207, 6, '김태근');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (62360, 6, '김태훈');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (65040, 6, '김태훈');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (61404, 6, '김헌곤');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (54460, 6, '김호진');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (54400, 6, '디아즈');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (54443, 6, '레예스');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (62234, 6, '류지혁');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (75125, 6, '박병호');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (69418, 6, '박승규');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (53416, 6, '박장민');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (50411, 6, '박주혁');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (54466, 6, '박준용');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (53411, 6, '박진우');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55455, 6, '배찬승');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (77446, 6, '백정현');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (73801, 6, '송은범');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (54405, 6, '신경민');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55438, 6, '심재훈');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (66406, 6, '안주형');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (54408, 6, '양도근');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (69403, 6, '양우현');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (68415, 6, '양창섭');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (61268, 6, '양현');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (75421, 6, '오승환');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (51466, 6, '오현석');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55428, 6, '우승완');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (69446, 6, '원태인');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (54406, 6, '유병선');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (54404, 6, '육선엽');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (68463, 6, '윤정빈');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (69428, 6, '이병헌');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (63960, 6, '이상민');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (66409, 6, '이성규');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (50464, 6, '이승민');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (60146, 6, '이승현');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (51454, 6, '이승현');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (63492, 6, '이재익');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (52415, 6, '이재현');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (51460, 6, '이재희');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55417, 6, '이진용');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (51407, 6, '이창용');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (69416, 6, '이해승');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (54411, 6, '이현준');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (53455, 6, '이호성');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (78352, 6, '임창민');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (65586, 6, '전병우');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (54467, 6, '정민성');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (52405, 6, '조민성');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (51468, 6, '주한울');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55415, 6, '진희성');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (52400, 6, '차동영');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55435, 6, '차승준');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55414, 6, '천겸');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (62146, 6, '최성훈');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55419, 6, '최예한');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (65320, 6, '최원태');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (67421, 6, '최지광');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (66451, 6, '최충연');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (68501, 6, '최하늘');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55440, 6, '함수호');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (50449, 6, '허윤동');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (50430, 6, '홍원표');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55412, 6, '홍준영');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (67005, 6, '홍현빈');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (50441, 6, '황동재');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (53375, 6, '후라도');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (50905, 9, '강태경');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (54908, 9, '고승완');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (65933, 9, '구창모');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (63963, 9, '권희동');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (52995, 9, '김녹원');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55933, 9, '김동현');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (54913, 9, '김민규');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (54901, 9, '김민균');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (69980, 9, '김범준');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (54905, 9, '김세훈');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (68928, 9, '김시훈');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (68900, 9, '김영규');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (64580, 9, '김재열');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (51903, 9, '김정호');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (51907, 9, '김주원');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (54910, 9, '김준원');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (51901, 9, '김진우');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (67954, 9, '김진호');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (50992, 9, '김태경');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (67956, 9, '김태현');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (54912, 9, '김태호');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55995, 9, '김태훈');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (50902, 9, '김한별');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (68912, 9, '김형준');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (54959, 9, '김휘건');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (51344, 9, '김휘집');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (53914, 9, '노재원');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (54944, 9, '데이비슨');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (66965, 9, '도태훈');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55903, 9, '라일리');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55912, 9, '로건');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (65949, 9, '류진욱');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (53973, 9, '목지훈');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (79215, 9, '박건우');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (52994, 9, '박동수');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (62907, 9, '박민우');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (52980, 9, '박성재');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (62244, 9, '박세혁');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (50996, 9, '박시원');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (50906, 9, '박영빈');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (54911, 9, '박인우');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (69900, 9, '박주찬');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (68097, 9, '박주현');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (69994, 9, '박지한');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (53992, 9, '박한결');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (64995, 9, '배재환');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (53999, 9, '서동욱');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (66068, 9, '서의태');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (52906, 9, '서준교');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (69995, 9, '서호철');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (67962, 9, '소이현');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (77532, 9, '손아섭');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (54904, 9, '손주환');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (69203, 9, '송승환');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55917, 9, '신민우');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (68902, 9, '신민혁');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (53989, 9, '신성호');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (53919, 9, '신영우');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (53991, 9, '신용석');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (50901, 9, '안인산');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (64022, 9, '안중열');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55920, 9, '양가온솔');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (68904, 9, '오영수');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (51991, 9, '오장한');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (51996, 9, '오태양');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (54906, 9, '원종해');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55925, 9, '유재현');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55997, 9, '이세민');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (77211, 9, '이용찬');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (60263, 9, '이재학');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (52992, 9, '이준혁');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (52907, 9, '이한');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (54900, 9, '임상현');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (63959, 9, '임정호');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (52902, 9, '임지민');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (50900, 9, '임형원');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55993, 9, '장창훈');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (69204, 9, '전루건');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (69969, 9, '전사민');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (53995, 9, '정주영');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55926, 9, '정현창');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (52903, 9, '조민석');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55927, 9, '조창연');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (54907, 9, '조현민');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (52908, 9, '조효원');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (67905, 9, '천재환');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (68909, 9, '최보성');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (66920, 9, '최성영');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (54903, 9, '최우석');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55913, 9, '최윤혁');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (69992, 9, '최정원');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (64101, 9, '한석현');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (51994, 9, '한재승');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (50903, 9, '한재환');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (54902, 9, '홍유원');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55996, 9, '홍재문');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (52210, 2, '강동형');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (63123, 2, '강승호');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (51269, 2, '강현구');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (72523, 2, '고효준');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (68220, 2, '곽빈');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (50203, 2, '권휘');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (66112, 2, '김기연');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (69238, 2, '김대한');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (51294, 2, '김도윤');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (52267, 2, '김동준');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (67246, 2, '김명신');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (54215, 2, '김무빈');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (68200, 2, '김민규');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (53554, 2, '김민석');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (65269, 2, '김민혁');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55225, 2, '김민호');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55216, 2, '김성재');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (53262, 2, '김유성');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (63257, 2, '김인태');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (78224, 2, '김재환');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (68830, 2, '김정우');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55226, 2, '김준상');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55222, 2, '김지윤');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (54217, 2, '김태완');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (54263, 2, '김택연');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55221, 2, '김한중');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (68269, 2, '김호준');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (69104, 2, '남호');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (54219, 2, '류현준');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (64468, 2, '박계범');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55223, 2, '박민제');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (53204, 2, '박민준');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (51298, 2, '박성재');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (68249, 2, '박신지');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55220, 2, '박연준');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (50205, 2, '박웅');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (65639, 2, '박정수');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55252, 2, '박준순');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (66928, 2, '박준영');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (54213, 2, '박지호');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (50204, 2, '박지훈');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (67266, 2, '박치국');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (54216, 2, '손율기');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (52294, 2, '신민철');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (64153, 2, '양석환');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (76232, 2, '양의지');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55215, 2, '양재훈');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (51200, 2, '양현진');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (54205, 2, '여동건');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55218, 2, '연서준');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (50208, 2, '오명진');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (52202, 2, '윤태호');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (69205, 2, '이교훈');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (52209, 2, '이민석');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (52204, 2, '이병헌');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55224, 2, '이선우');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (64805, 2, '이승진');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (66291, 2, '이영하');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (67207, 2, '이유찬');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (50262, 2, '이주엽');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55991, 2, '이한별');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (54236, 2, '임종성');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (50244, 2, '장규빈');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (63202, 2, '장승현');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (53267, 2, '장우진');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55239, 2, '잭로그');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (54214, 2, '전다민');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (79231, 2, '정수빈');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (64202, 2, '정은재');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (50211, 2, '제환유');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (66209, 2, '조수행');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (50296, 2, '조제영');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55217, 2, '주양준');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (69093, 2, '지강혁');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (52208, 2, '천현재');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55268, 2, '최민석');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (50298, 2, '최세창');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (51264, 2, '최승용');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (51564, 2, '최우인');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55219, 2, '최우혁');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (67263, 2, '최원준');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (50206, 2, '최종인');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (53259, 2, '최준호');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (52206, 2, '최지강');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (68362, 2, '추재현');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55208, 2, '케이브');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55257, 2, '콜어빈');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55214, 2, '한다현');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (61643, 2, '홍건희');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55267, 2, '홍민규');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (66233, 2, '홍성호');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55258, 2, '황희천');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (62925, 3, '강진성');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (54344, 3, '고영우');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55371, 3, '권혁빈');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (53312, 3, '김건희');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (53164, 3, '김동규');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (66838, 3, '김동엽');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (53344, 3, '김동헌');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (50304, 3, '김병휘');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55359, 3, '김서준');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (66018, 3, '김선기');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (67828, 3, '김성민');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (54368, 3, '김연주');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (65898, 3, '김웅빈');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (54319, 3, '김윤하');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (69367, 3, '김인범');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (62332, 3, '김재현');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (64984, 3, '김태진');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55322, 3, '로젠버그');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (54304, 3, '박범준');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (53305, 3, '박성빈');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (52305, 3, '박수종');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (53301, 3, '박윤성');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55394, 3, '박정훈');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (69328, 3, '박주성');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (50357, 3, '박주홍');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (68305, 3, '변상권');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (53309, 3, '서유신');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (54363, 3, '손현기');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55300, 3, '손힘찬');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (65357, 3, '송성문');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (54394, 3, '송지후');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55326, 3, '스톤');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (69045, 3, '알칸타라');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (67365, 3, '양지율');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55370, 3, '양현종');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55392, 3, '어준서');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55393, 3, '여동욱');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (67116, 3, '오석주');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (78756, 3, '오선진');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (54307, 3, '원성준');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (76118, 3, '원종현');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55348, 3, '웰스');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (52395, 3, '윤석원');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55396, 3, '윤현');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (50036, 3, '이강준');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (53302, 3, '이승원');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (75566, 3, '이원석');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (54305, 3, '이재상');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (50354, 3, '이종민');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (50167, 3, '이주형');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (51302, 3, '이주형');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55301, 3, '이준우');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (78135, 3, '이형종');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (64346, 3, '임병욱');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (64340, 3, '임지열');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55382, 3, '임진묵');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (51359, 3, '장재영');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (77927, 3, '장필준');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (54362, 3, '전준표');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55397, 3, '전태현');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55377, 3, '정세영');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (55313, 3, '정현우');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (69360, 3, '조영건');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (69332, 3, '주성원');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (52330, 3, '주승우');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (76267, 3, '최주환');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (54444, 3, '카디네스');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (52366, 3, '푸이그');
INSERT INTO player_tb (kbo_player_id, team_id, name)
VALUES (64350, 3, '하영민');
