-- Cria a tabela se não existir (ajuste o nome conforme seu modelo)
CREATE TABLE IF NOT EXISTS perfiles (
    perfil_id BIGINT PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE  -- Adicione UNIQUE para evitar duplicatas
);

-- Garante que o perfil ADMIN exista (usando MERGE ou INSERT IGNORE)
MERGE INTO perfiles (perfil_id, name) KEY(perfil_id) VALUES (1, 'ADMIN');  -- Nome deve ser igual ao enum em Java
MERGE INTO perfiles (perfil_id, name) KEY(perfil_id) VALUES (2, 'ALUNO');

-- Opcional: Inserir outros perfis necessários
-- MERGE INTO perfiles (perfil_id, name) KEY(perfil_id) VALUES (3, 'PROFESSOR');
