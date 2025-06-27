-- 1. stadium_tb
INSERT INTO stadium_tb (stadium_name, location, stadium_type)
VALUES ('잠실야구장', '서울특별시 잠실동', 'OUTDOOR');

INSERT INTO stadium_tb (stadium_name, location, stadium_type)
VALUES ('고척스카이돔', '서울특별시 고척동', 'INDOOR');

INSERT INTO stadium_tb (stadium_name, location, stadium_type)
VALUES ('인천 SSG 랜더스필드', '인천광역시 문학동', 'OUTDOOR');

INSERT INTO stadium_tb (stadium_name, location, stadium_type)
VALUES ('광주-기아 챔피언스필드', '광주광역시 임동', 'OUTDOOR');

INSERT INTO stadium_tb (stadium_name, location, stadium_type)
VALUES ('대구 삼성라이온즈파크', '대구광역시 연호동', 'OUTDOOR');

INSERT INTO stadium_tb (stadium_name, location, stadium_type)
VALUES ('부산 사직야구장', '부산광역시 사직동', 'OUTDOOR');

INSERT INTO stadium_tb (stadium_name, location, stadium_type)
VALUES ('대전 한화생명이글스파크', '대전광역시 부사동', 'OUTDOOR');

INSERT INTO stadium_tb (stadium_name, location, stadium_type)
VALUES ('창원 NC파크', '경상남도 창원시 양덕동', 'OUTDOOR');

INSERT INTO stadium_tb (stadium_name, location, stadium_type)
VALUES ('수원 KT위즈파크', '경기도 수원시 조원동', 'OUTDOOR');

INSERT INTO stadium_tb (stadium_name, location, stadium_type)
VALUES ('청주야구장', '충청북도 청주시 사직동', 'OUTDOOR');

INSERT INTO stadium_tb (stadium_name, location, stadium_type)
VALUES ('울산 문수야구장', '울산광역시 옥동', 'OUTDOOR');

INSERT INTO stadium_tb (stadium_name, location, stadium_type)
VALUES ('포항야구장', '경상북도 포항시', 'OUTDOOR');

INSERT INTO stadium_tb (stadium_name, location, stadium_type)
VALUES ('군산 월명야구장', '전라북도 군산시', 'OUTDOOR');

-- 1-1. stadium_correction_tb (구장 보정 계수)테이블
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


-- 1-2. stadium_coordinate_tb 테이블
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

-- 2. team_tb
INSERT INTO team_tb (stadium_id1, stadium_id2, team_name, logo_url, ticket_link)
VALUES (1, NULL, 'LG 트윈스', '/img/logo/LG로고.png',
        'https://www.lgtwins.com/service/html.ncd?baRs=OUT_DS&view=%2Fpc_twins%2Fticket%2Fticketbuy&actID=BR_RetrieveTicketing'),
       (1, NULL, '두산 베어스', '/img/logo/두산로고.png',
        'https://ticket.interpark.com/Contents/Sports/GoodsInfo?SportsCode=07001&TeamCode=PB004'),
       (2, NULL, '키움 히어로즈', '/img/logo/키움로고.png',
        'https://ticket.interpark.com/Contents/Sports/GoodsInfo?SportsCode=07001&TeamCode=PB003'),
       (3, NULL, 'SSG 랜더스', '/img/logo/SSG로고.png',
        'https://www.ticketlink.co.kr/sports#reservation'),
       (4, 13, 'KIA 타이거즈', '/img/logo/KIA로고.png',
        'https://tigers.co.kr/ticket/reservation'),
       (5, 11, '삼성 라이온즈', '/img/logo/삼성로고.png',
        'https://www.ticketlink.co.kr/sports#reservation'),
       (6, 12, '롯데 자이언츠', '/img/logo/롯데로고.png',
        'https://ticket.giantsclub.com/loginForm.do'),
       (7, 10, '한화 이글스', '/img/logo/한화로고.png',
        'https://www.hanwhaeagles.co.kr/ticketInfo.do'),
       (8, NULL, 'NC 다이노스', '/img/logo/NC로고.png',
        'https://ticket.ncdinos.com/login'),
       (9, NULL, 'KT 위즈', '/img/logo/KT로고.png',
        'https://www.ktwiz.co.kr/ticket/reservation');