create table conteudos(

    id bigint not null auto_increment,
    nome varchar(100) not null,
    descricao varchar(255) not null,
    duracao varchar(100) not null,
    link varchar(100) not null unique,
    categoria varchar(100) not null,
    ativo tinyint not null,

    primary key(id)

);