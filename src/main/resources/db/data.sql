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

-- 3. user_tb
INSERT INTO user_tb
(password, name, nickname, team_id, email, birth_date, gender, profile_url, user_role)
VALUES ('1234', '쌀', 'ssar', 1, 'ssar@nate.com', '1999-09-09', 'MALE', '/img/profile.png', 'USER'),
       ('1234', '코스', 'cos', 2, 'cos@nate.com', '2000-01-01', 'FEMALE', '/img/profile.png', 'USER'),
       ('1234', '러브', 'love', 3, 'love@nate.com', '1998-08-08', 'MALE', '/img/profile.png', 'USER'),
       ('1234', '하하', 'haha', NULL, 'haha@nate.com', '1997-07-07', 'FEMALE', '/img/profile.png', 'ADMIN'),
       ('1234', '백하림', 'harim', 4, 'harim@example.com', '1995-05-05', 'MALE', '/img/profile.png', 'USER'),
       ('1234', '김정원', 'jungwon', 5, 'jungwon@example.com', '1996-06-06', 'MALE', '/img/profile.png', 'USER'),
       ('1234', '김미숙', 'misook', 6, 'misook@example.com', '1994-04-04', 'FEMALE', '/img/profile.png', 'USER');


-- 4. board_tb
INSERT INTO board_tb (user_id, team_id, title, content, delete_status, created_at, updated_at)
VALUES (1, 3, '속보! 롯데 드디어 가을야구 가나요??',
        '아니 매년 이맘때 기대하다 쳐 발렸는데... 이번엔 뭔가 다르다 ㄹㅇ. 7승 3패 페이스 절대 못 막음. 불펜만 좀만 더 버티면 진짜 가능성 보인다. 아 그때 그 송승준이 던지던 낭만 야구 생각나네. 제발 올해는 ㅠㅠ',
        'NOT_DELETED', '2025-06-24 12:40:00', NULL),
       (2, 10, '크트 이기고도 혈압 터지는 사람 나뿐이냐? 미친 불펜 개빡치네',
        '이긴 건 맞는데 이게 맞냐고? 선발 내려가면 불펜이 그냥 자살쇼하고... 9회까지 고혈압 유발 오지게 하고 겨우 이김. 아니 진짜 야구 보면서 스트레스 받는 게 정상임? 팬들 명 줄이려 작정했냐.',
        'NOT_DELETED', '2025-06-24 12:42:00', NULL),
       (3, 4, '광주 직관 후기: 갸 응원단 미쳤음',
        '광주 갸 응원석 갔는데 진짜 미쳤다. 북소리+응원가+타자 나올 때 분위기 뭐냐ㅋㅋㅋㅋㅋ 나 분명 원정 팬인데 반쯤 갸팬 될 뻔함. 현장감 오지는 직관 무조건 가라. 이건 약이다!',
        'NOT_DELETED', '2025-06-24 12:44:00', NULL),
       (5, 1, '쓱 응원 진짜 힘 빠진다... 이대로 챔필 위엄 다 버릴 거냐고?',
        '직관 갔다가 대실망... 경기 내용도 내용인데 응원 분위기가 완전 뒤져있음. 몇 년 전 챔피언 맞냐고 이 팀? 외야석 너무 조용해서 잠 올 뻔했다. 정신 좀 차려라 쫌!',
        'DELETED', '2025-06-24 12:46:00', NULL),
       (5, 7, '칰 1위 실화냐ㅋㅋㅋ 이걸 말로만 듣던 ''가을 칰'' 드디어 오냐?',
        '아니 이걸 말로만 듣던 가을 칰인가? 요즘 친구들한테 한화 팬이라고 어깨 뽕 지린다. 근데 칰은 8월부터 무너지는 과학이 있으니 아직 방심하면 안 됨. (feat. 콩가루 주의보)',
        'NOT_DELETED', '2025-06-24 12:48:00', NULL),
       (6, 2, '잠실 더비 (두산vs엘지) 직관, 진짜 역대급 레전드',
        '더위도 날려버릴 정도로 경기 내용 미쳤고, 외야에서 싸우는 두 팬덤도 ㅋㅋㅋ 야구장 가면 현피 구경도 보너스임. 응원 열기랑 분위기 역대급이었다. 이건 직관 안 한 사람 개손해.',
        'NOT_DELETED', '2025-06-24 12:50:00', NULL),
       (7, 5, '삼팬인데 요즘 마음이 너무 아프다... 제발 사고만 치지 마라',
        '경기 보면서 이기길 바라기보단, 사고만 안 났으면 좋겠다는 생각이 먼저 든다. 무기력한 타선, 불안한 마운드. 그래도 응원한다, 내 팀이니까... (오늘도 눈물 한 바가지)',
        'NOT_DELETED', '2025-06-24 12:52:00', NULL),
       (1, 6, '굿즈 덕후들 주목! 라팍 굿즈 퀄리티 미쳤음',
        '최근에 산 썬캡이랑 유니폼 재질 무슨 일... 진짜 질 개좋아짐. 디자인도 예뻐서 길에서 입어도 민망함 1도 없음. 팬심으로 사서 실사용도 가능. 지갑 털릴 준비해라.',
        'NOT_DELETED', '2025-06-24 12:54:00', NULL),
       (2, 8, '엔씨 요즘 분위기 왜 이래? 타선 침묵에 팬들도 조용... (이번주 직관 간다)',
        '타선이 뭐 하나만 걸려도 점수를 못 뽑는다... 순위도 계속 내려가고 팬들도 조용해졌고. 이럴 때일수록 직관 가서 응원하는 게 진짜 팬이지. 다음 주 홈경기 간다. 너희도 와라!',
        'NOT_DELETED', '2025-06-24 12:56:00', NULL),
       (3, 9, '속터짐 주의! 두산 불펜... 이거 불 지르는 거 실화냐??',
        '와 진짜 8회 9회만 되면 무조건 불안하다. 불펜만 보면 심장이 쪼그라들고 손에 땀이 남. 유망주도 좋지만 지금은 제발 경험자도 써줘라 좀. 이러다 팬들 다 쓰러진다!',
        'NOT_DELETED', '2025-06-24 12:58:00', NULL);


-- 5. board_image_tb
INSERT INTO board_image_tb (board_id, img_url, delete_status)
VALUES (1, '/img/board/1.jpg', 'NOT_DELETED'),
       (2, '/img/board/2.jpg', 'NOT_DELETED'),
       (3, '/img/board/3.jpg', 'NOT_DELETED'),
       (4, '/img/board/4.jpg', 'DELETED'),
       (5, '/img/board/3.jpg', 'NOT_DELETED'),
       (6, '/img/board/5.jpg', 'NOT_DELETED'),
       (7, '/img/board/6.jpg', 'NOT_DELETED'),
       (8, '/img/board/4.jpg', 'NOT_DELETED'),
       (8, '/img/board/5.jpg', 'NOT_DELETED'),
       (9, '/img/board/6.jpg', 'NOT_DELETED'),
       (9, '/img/board/7.jpg', 'NOT_DELETED'),
       (10, '/img/board/8.jpg', 'NOT_DELETED'),
       (10, '/img/board/9.jpg', 'NOT_DELETED'),
       (10, '/img/board/10.jpg', 'NOT_DELETED');


