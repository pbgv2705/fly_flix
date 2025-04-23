-- Criação do banco
CREATE DATABASE IF NOT EXISTS flyflix CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Criação do usuário
CREATE USER IF NOT EXISTS 'flyflix_prod_user'@'%' IDENTIFIED BY 'flyflix123';

-- Concessão de permissões
GRANT ALL PRIVILEGES ON flyflix.* TO 'flyflix_prod_user'@'%';

-- Aplicar as permissões
FLUSH PRIVILEGES;
