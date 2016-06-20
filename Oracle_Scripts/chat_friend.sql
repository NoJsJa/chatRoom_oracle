

CREATE SEQUENCE friendSequence
       increment by 1    -- 每次递增1
       start with 1       -- 从1开始
       nomaxvalue      -- 没有最大值
       minvalue 1       -- 最小值=1
       NOCYCLE;      -- 不循环


CREATE TABLE friend (
    id NUMBER,
    name VARCHAR(45) not null,
	  friend VARCHAR(45) not null,
    status VARCHAR(16),
    PRIMARY KEY (id)
);


INSERT INTO friend
(id,name, friend,status) values
(friendSequence.NEXTVAL,'System', 'Johnson','offline');

INSERT INTO friend
(id,name, friend,status) values
(friendSequence.NEXTVAL,'Johnson', 'System','offline');
