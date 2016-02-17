# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table ingredient (
  id                            bigint not null,
  name                          varchar(255),
  frozen                        boolean,
  category                      varchar(255),
  base_type                     varchar(255),
  photo                         varchar(255),
  constraint pk_ingredient primary key (id)
);
create sequence ingredient_seq;

create table measure_ingredient (
  id                            bigint not null,
  measure                       varchar(255),
  quantity                      float,
  recipe_id                     bigint,
  ingredient_id                 bigint,
  constraint pk_measure_ingredient primary key (id)
);
create sequence measure_ingredient_seq;

create table recipe (
  id                            bigint not null,
  name                          varchar(255),
  score                         integer,
  author                        varchar(255),
  difficulty                    integer,
  photo                         varchar(255),
  portions                      integer,
  constraint pk_recipe primary key (id)
);
create sequence recipe_seq;

create table task (
  id                            bigint not null,
  name                          varchar(255),
  description                   varchar(255),
  seconds                       integer,
  recipe_id                     bigint,
  photo                         varchar(255),
  constraint pk_task primary key (id)
);
create sequence task_seq;

alter table measure_ingredient add constraint fk_measure_ingredient_recipe_id foreign key (recipe_id) references recipe (id) on delete restrict on update restrict;
create index ix_measure_ingredient_recipe_id on measure_ingredient (recipe_id);

alter table measure_ingredient add constraint fk_measure_ingredient_ingredient_id foreign key (ingredient_id) references ingredient (id) on delete restrict on update restrict;
create index ix_measure_ingredient_ingredient_id on measure_ingredient (ingredient_id);

alter table task add constraint fk_task_recipe_id foreign key (recipe_id) references recipe (id) on delete restrict on update restrict;
create index ix_task_recipe_id on task (recipe_id);


# --- !Downs

alter table measure_ingredient drop constraint if exists fk_measure_ingredient_recipe_id;
drop index if exists ix_measure_ingredient_recipe_id;

alter table measure_ingredient drop constraint if exists fk_measure_ingredient_ingredient_id;
drop index if exists ix_measure_ingredient_ingredient_id;

alter table task drop constraint if exists fk_task_recipe_id;
drop index if exists ix_task_recipe_id;

drop table if exists ingredient;
drop sequence if exists ingredient_seq;

drop table if exists measure_ingredient;
drop sequence if exists measure_ingredient_seq;

drop table if exists recipe;
drop sequence if exists recipe_seq;

drop table if exists task;
drop sequence if exists task_seq;

