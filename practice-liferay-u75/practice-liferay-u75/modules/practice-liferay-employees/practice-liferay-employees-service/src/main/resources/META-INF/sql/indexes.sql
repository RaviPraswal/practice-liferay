create index IX_833B6543 on PRACTICE_LIFERAY_Employee (email[$COLUMN_LENGTH:75$]);
create index IX_9466CCC2 on PRACTICE_LIFERAY_Employee (firstName[$COLUMN_LENGTH:75$]);
create index IX_528B779D on PRACTICE_LIFERAY_Employee (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_64D7415F on PRACTICE_LIFERAY_Employee (uuid_[$COLUMN_LENGTH:75$], groupId);