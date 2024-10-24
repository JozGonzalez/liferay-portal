alter table WikiNode add status INTEGER;
alter table WikiNode add statusByUserId LONG;
alter table WikiNode add statusByUserName VARCHAR(75) null;
alter table WikiNode add statusDate DATE null;

COMMIT_TRANSACTION;

update WikiNode set status = 0, statusByUserId = userId, statusByUserName = userName, statusDate = modifiedDate;