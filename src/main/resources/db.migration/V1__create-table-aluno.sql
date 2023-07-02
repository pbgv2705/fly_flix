create table aluno(

    id bigint not null auto_increment,
    nome varchar(100) not null,
    email varchar(100) not null unique,
    endereco varchar(100) not null,
    cep varchar(9) not null,
    identidade_genero varchar(100) not null,
    orientacao_sexual varchar(100) not null,
    cor_raca varchar(20) not null,
    data_nascimento date not null,

    primary key(id)

);