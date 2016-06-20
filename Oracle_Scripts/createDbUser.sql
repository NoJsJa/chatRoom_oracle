CREATE USER Johnson IDENTIFIED BY yangwei020154
DEFAULT TABLESPACE USERS
TEMPORARY TABLESPACE TEMP;

GRANT "DBA" TO Johnson;

GRANT CREATE SESSION TO Johnson;

ALTER USER Johnson QUOTA 1mb ON EXAMPLE;
ALTER USER Johnson QUOTA 1mb ON USERS;