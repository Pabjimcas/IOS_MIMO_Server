# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table ingredient (
  id                            bigint not null,
  name                          varchar(255),
  frozen                        boolean,
  category                      varchar(255),
  base_type                     varchar(255),
  constraint pk_ingredient primary key (id)
);
create sequence ingredient_seq;

create table ingredient_recipe (
  ingredient_id                 bigint not null,
  recipe_id                     bigint not null,
  constraint pk_ingredient_recipe primary key (ingredient_id,recipe_id)
);

create table ingredient_task (
  id                            bigint not null,
  task_id                       bigint,
  ingredient_id                 bigint,
  measure                       varchar(255),
  quantity                      float,
  constraint pk_ingredient_task primary key (id)
);
create sequence ingredient_task_seq;

create table recipe (
  id                            bigint not null,
  name                          varchar(255),
  score                         integer,
  author                        varchar(255),
  difficulty                    integer,
  portions                      integer,
  constraint pk_recipe primary key (id)
);
create sequence recipe_seq;

create table task (
  id                            bigint not null,
  description                   varchar(255),
  seconds                       integer,
  recipe_id                     bigint,
  constraint pk_task primary key (id)
);
create sequence task_seq;

alter table ingredient_recipe add constraint fk_ingredient_recipe_ingredient foreign key (ingredient_id) references ingredient (id) on delete restrict on update restrict;
create index ix_ingredient_recipe_ingredient on ingredient_recipe (ingredient_id);

alter table ingredient_recipe add constraint fk_ingredient_recipe_recipe foreign key (recipe_id) references recipe (id) on delete restrict on update restrict;
create index ix_ingredient_recipe_recipe on ingredient_recipe (recipe_id);

alter table ingredient_task add constraint fk_ingredient_task_task_id foreign key (task_id) references task (id) on delete restrict on update restrict;
create index ix_ingredient_task_task_id on ingredient_task (task_id);

alter table ingredient_task add constraint fk_ingredient_task_ingredient_id foreign key (ingredient_id) references ingredient (id) on delete restrict on update restrict;
create index ix_ingredient_task_ingredient_id on ingredient_task (ingredient_id);

alter table task add constraint fk_task_recipe_id foreign key (recipe_id) references recipe (id) on delete restrict on update restrict;
create index ix_task_recipe_id on task (recipe_id);


# --- !Downs

alter table ingredient_recipe drop constraint if exists fk_ingredient_recipe_ingredient;
drop index if exists ix_ingredient_recipe_ingredient;

alter table ingredient_recipe drop constraint if exists fk_ingredient_recipe_recipe;
drop index if exists ix_ingredient_recipe_recipe;

alter table ingredient_task drop constraint if exists fk_ingredient_task_task_id;
drop index if exists ix_ingredient_task_task_id;

alter table ingredient_task drop constraint if exists fk_ingredient_task_ingredient_id;
drop index if exists ix_ingredient_task_ingredient_id;

alter table task drop constraint if exists fk_task_recipe_id;
drop index if exists ix_task_recipe_id;

drop table if exists ingredient;
drop sequence if exists ingredient_seq;

drop table if exists ingredient_recipe;

drop table if exists ingredient_task;
drop sequence if exists ingredient_task_seq;

drop table if exists recipe;
drop sequence if exists recipe_seq;

drop table if exists task;
drop sequence if exists task_seq;

