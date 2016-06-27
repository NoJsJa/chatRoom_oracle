CREATE SEQUENCE BLOGSEQUENCE
       increment by 1    -- ÿ�ε���1
       start with 1       -- ��1��ʼ
       nomaxvalue      -- û�����ֵ
       minvalue 1       -- ��Сֵ=1
       NOCYCLE;      -- ��ѭ��

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
  'java Date��ʽ����',
  '����������java������������ͬ��ʽ��date����...',
  'Johnson',
  '����������java������������ͬ��ʽ��date���ݣ�ʱ��ĸ�ʽ���Ծ�ȷ��ʱ���롣',
  to_date('2000-11-26 00:04:47','yyyy-mm-dd hh24:mi:ss'),
  'java'
);