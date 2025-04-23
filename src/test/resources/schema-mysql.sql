CREATE TABLE PERFILES (
    perfil_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL
);
CREATE TABLE usuarios (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    cpf VARCHAR(20),
    login VARCHAR(255) NOT NULL,
    senha VARCHAR(255)
);

CREATE TABLE usuario_perfil (
    usuario_id BIGINT NOT NULL,
    perfil_id BIGINT NOT NULL,
    PRIMARY KEY (usuario_id, perfil_id),
    CONSTRAINT fk_usuario FOREIGN KEY (usuario_id) REFERENCES usuarios(id),
    CONSTRAINT fk_perfil FOREIGN KEY (perfil_id) REFERENCES perfiles(perfil_id)
);



CREATE TABLE aluno (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(255) NOT NULL
);

CREATE TABLE curso (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    titulo VARCHAR(255) NOT NULL
);

CREATE TABLE modulo (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(255) NOT NULL,
    curso_id BIGINT,
    CONSTRAINT fk_modulo_curso FOREIGN KEY (curso_id) REFERENCES curso(id)
);

CREATE TABLE aula (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    modulo_id BIGINT,
    titulo VARCHAR(255) NOT NULL,
    CONSTRAINT fk_aula_modulo FOREIGN KEY (modulo_id) REFERENCES modulo(id)
);

CREATE TABLE progresso_aluno (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    aluno_id BIGINT,
    aula_id BIGINT,
    assistida BOOLEAN,
    CONSTRAINT fk_progresso_aluno FOREIGN KEY (aluno_id) REFERENCES aluno(id),
    CONSTRAINT fk_progresso_aula FOREIGN KEY (aula_id) REFERENCES aula(id)
);

CREATE TABLE resultado_quiz (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    aluno_id BIGINT,
    curso_id BIGINT,
    nota DOUBLE,
    CONSTRAINT fk_resultado_aluno FOREIGN KEY (aluno_id) REFERENCES aluno(id),
    CONSTRAINT fk_resultado_curso FOREIGN KEY (curso_id) REFERENCES curso(id)
);
