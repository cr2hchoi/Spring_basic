
-- items table 생성
create table items (
                       id        int auto_increment comment '아이디' primary key,
                       name      varchar(50) not null comment  '상품 이름',
                       img_path varchar(50) not null comment '상품 사진 경로',
                       price   int not null comment '상품 가격',
                       discount_per int not null comment '상품 할인율',
                       created   datetime default current_timestamp() not null comment '생성 일시'
) comment '장바구니';

-- carts table 생성
create table carts (
                       id        int auto_increment comment '아이디' primary key,
                       member_id int                                  not null comment '회원 아이디',
                       item_id   int                                  not null comment '상품 아이디',
                       created   datetime default current_timestamp() not null comment '생성 일시'
) comment '장바구니';

-- members table 생성
create table members(
                        id        int auto_increment comment '아이디' primary key,
                        name       varchar(50) not null comment '회원명',
                        login_id   varchar(50) not null unique comment '로그인 아이디',
                        login_pw varchar(100) not null comment '로그인 패스워드',
                        created   datetime default current_timestamp() not null comment '생성 일시'
) comment '장바구니';

