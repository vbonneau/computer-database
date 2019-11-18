  create table company (
    id                        bigint not null auto_increment,
    name                      varchar(255),
    constraint pk_company primary key (id))
  ;

  create table computer (
    id                        bigint not null auto_increment,
    name                      varchar(255),
    introduced                timestamp NULL,
    discontinued              timestamp NULL,
    company_id                bigint default NULL,
    constraint pk_computer primary key (id))
  ;

  alter table computer add constraint fk_computer_company_1 foreign key (company_id) references company (id) on delete restrict on update restrict;
  create index ix_computer_company_1 on computer (company_id);


insert into company (id,name) values (  1,'Apple Inc.');
insert into company (id,name) values (  2,'Thinking Machines');
insert into company (id,name) values (  3,'RCA');
insert into company (id,name) values (  4,'Netronics');

insert into computer (id,name,introduced,discontinued,company_id) values (  1,'MacBook Pro 15.4 inch',null,null,null);
insert into computer (id,name,introduced,discontinued,company_id) values ( 2,'CM-5','1991-01-01',null,2);
insert into computer (id,name,introduced,discontinued,company_id) values ( 3,'Apple III','1980-05-01','1984-04-01',1);
insert into computer (id,name,introduced,discontinued,company_id) values (  6,'MacBook Pro',null,'2006-01-10',1);
