CREATE SEQUENCE commentSequence
       increment by 1    -- ÿ�ε���1
       start with 1       -- ��1��ʼ
       nomaxvalue      -- û�����ֵ
       minvalue 1       -- ��Сֵ=1
       NOCYCLE;      -- ��ѭ��

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
  'java Date��ʽ����',
  'Johnson',
  'System',
  '��ƪ����д�Ĳ���',
  to_date('2016-6-23 22:41:14','yyyy-mm-dd hh24:mi:ss')
);

INSERT INTO comments(id,title,author,commentator,content,dates) values (
  COMMENTSEQUENCE.NEXTVAL,
  'NULL',
  'Johnson',
  'System',
  '������������Johnson��',
  to_date('2016-6-26 22:41:14','yyyy-mm-dd hh24:mi:ss')
);