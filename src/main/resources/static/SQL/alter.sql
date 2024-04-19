alter table `users` alter column 'user_password' set default 'password';
-- set varchar(255) on user_password
alter table `users` alter column 'user_password' set type varchar(255);



