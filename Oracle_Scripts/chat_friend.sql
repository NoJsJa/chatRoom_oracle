

CREATE SEQUENCE friendSequence
       increment by 1    -- ÿ�ε���1
       start with 1       -- ��1��ʼ
       nomaxvalue      -- û�����ֵ
       minvalue 1       -- ��Сֵ=1
       NOCYCLE;      -- ��ѭ��


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
