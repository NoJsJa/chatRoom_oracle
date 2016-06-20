
CREATE TABLE message (
	id NUMBER,
	name_sendTo VARCHAR(45) not null,
  name_sender VARCHAR(45) not null,
	msg VARCHAR(500),
  imgIndex VARCHAR(16),
  PRIMARY KEY (id)
) ;

INSERT INTO message
(id,name_sendTo, name_sender, msg) values
(friendSequence.NEXTVAL,'System', 'Johnson', 'hello System!');

INSERT INTO message
(id,name_sendTo, name_sender, msg) values
(friendSequence.NEXTVAL,'Johnson', 'System', 'hello Johnson!');
