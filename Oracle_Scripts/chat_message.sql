
CREATE TABLE message (
	id NUMBER,
	name_sendTo VARCHAR(45) not null,
  name_sender VARCHAR(45) not null,
	msg VARCHAR(500),
  imgIndex VARCHAR(16),
  PRIMARY KEY (id)
) ;

--序列自增长
CREATE SEQUENCE MESSAGESEQUENCE
       increment by 1    -- 每次递增1
       start with 1       -- 从1开始
       nomaxvalue      -- 没有最大值
       minvalue 1       -- 最小值=1
       NOCYCLE;      -- 不循环

INSERT INTO message
(id,name_sendTo, name_sender, msg) values
(MESSAGESEQUENCE.NEXTVAL,'System', 'Johnson', 'hello System!');

INSERT INTO message
(id,name_sendTo, name_sender, msg) values
(MESSAGESEQUENCE.NEXTVAL,'Johnson', 'System', 'hello Johnson!');
