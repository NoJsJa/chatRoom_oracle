
CREATE TABLE message (
	id NUMBER,
	name_sendTo VARCHAR(45) not null,
  name_sender VARCHAR(45) not null,
	msg VARCHAR(500),
  imgIndex VARCHAR(16),
  PRIMARY KEY (id)
) ;

--����������
CREATE SEQUENCE MESSAGESEQUENCE
       increment by 1    -- ÿ�ε���1
       start with 1       -- ��1��ʼ
       nomaxvalue      -- û�����ֵ
       minvalue 1       -- ��Сֵ=1
       NOCYCLE;      -- ��ѭ��

INSERT INTO message
(id,name_sendTo, name_sender, msg) values
(MESSAGESEQUENCE.NEXTVAL,'System', 'Johnson', 'hello System!');

INSERT INTO message
(id,name_sendTo, name_sender, msg) values
(MESSAGESEQUENCE.NEXTVAL,'Johnson', 'System', 'hello Johnson!');
