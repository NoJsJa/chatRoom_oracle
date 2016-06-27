CREATE SEQUENCE BLOGSEQUENCE
       increment by 1    -- 每次递增1
       start with 1       -- 从1开始
       nomaxvalue      -- 没有最大值
       minvalue 1       -- 最小值=1
       NOCYCLE;      -- 不循环

CREATE TABLE blog(
  id NUMBER,
  title varchar2(45) not null,
  abstract varchar2(200),
  author varchar2(15),
  content varchar2(4000),
  dates date,
  tag varchar2(15),
  primary key(id)
);

insert into blog(id,title,abstract,author,content,dates,tag) values (
  BLOGSEQUENCE.NEXTVAL,
  'java Date格式数据',
  '今天来讲讲java中怎样创建不同格式的date数据...',
  'Johnson',
  '今天来讲讲java中怎样创建不同格式的date数据，时间的格式可以精确到时分秒。',
  to_date('2000-11-26 00:04:47','yyyy-mm-dd hh24:mi:ss'),
  'java'
);