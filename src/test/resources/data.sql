-- Inserção de dados na tabela PERFILES
MERGE INTO PERFILES (perfil_id, name) KEY(perfil_id) VALUES (1, 'ADMIN');
MERGE INTO PERFILES (perfil_id, name) KEY(perfil_id) VALUES (2, 'ALUNO');

-- Inserção de dados na tabela aluno
MERGE INTO aluno (id, nome) KEY(id) VALUES (1, 'João da Silva');

-- Inserção de dados na tabela curso
MERGE INTO curso (id, titulo) KEY(id) VALUES (1, 'Curso Teste');

-- Inserção de dados na tabela modulo
MERGE INTO modulo (id, nome, curso_id) KEY(id) VALUES
(1, 'Modulo 1', 1),
(2, 'Modulo 2', 1);

-- Inserção de dados na tabela aula
MERGE INTO aula (id, modulo_id, titulo) KEY(id) VALUES
(1, 1, 'Aula 1 do Modulo 1'),
(2, 1, 'Aula 2 do Modulo 1'),
(3, 2, 'Aula 1 do Modulo 2'),
(4, 2, 'Aula 2 do Modulo 2');

-- Inserção de dados na tabela progresso_aluno
MERGE INTO progresso_aluno (id, aluno_id, aula_id, assistida) KEY(id) VALUES
(1, 1, 1, true),
(2, 1, 2, true);

-- Inserção de dados na tabela resultado_quiz
MERGE INTO resultado_quiz (id, aluno_id, curso_id, nota) KEY(id) VALUES
(1, 1, 1, 8.0);
