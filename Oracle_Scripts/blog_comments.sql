CREATE SEQUENCE commentSequence
       increment by 1    -- 每次递增1
       start with 1       -- 从1开始
       nomaxvalue      -- 没有最大值
       minvalue 1       -- 最小值=1
       NOCYCLE;      -- 不循环

CREATE TABLE comments(
  id NUMBER not null,
  title varchar2(45) not null,
  author varchar2(15),
  commentator varchar2(15),
  content varchar2(200),
  dates date,
  primary key(id)
);

INSERT INTO comments(id,title,author,commentator,content,dates) values (
  COMMENTSEQUENCE.NEXTVAL,
  'java Date格式数据',
  'Johnson',
  'System',
  '这篇文章写的不错！',
  to_date('2016-6-23 22:41:14','yyyy-mm-dd hh24:mi:ss')
);

INSERT INTO comments(id,title,author,commentator,content,dates) values (
  COMMENTSEQUENCE.NEXTVAL,
  'NULL',
  'Johnson',
  'System',
  '我来留言啦，Johnson！',
  to_date('2016-6-26 22:41:14','yyyy-mm-dd hh24:mi:ss')
);