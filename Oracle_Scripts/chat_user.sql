
CREATE TABLE chat_user (
    name VARCHAR2(45) not null,
	  password VARCHAR2(45) not null,
    sex VARCHAR2(16),
    motto VARCHAR2(500),
    activity VARCHAR2(16),
    PRIMARY KEY (name)
) ;

INSERT INTO chat_user
(name, password, sex, motto, activity) values
('System', 'root', '��', '����Ϊ�˽�', '0');

INSERT INTO chat_user
(name, password, sex, motto, activity) values
('Johnson', '123456', '��', '����Ϊ����', '50');
