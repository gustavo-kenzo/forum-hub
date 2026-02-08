CREATE TYPE categoria_curso AS ENUM (
    'MOBILE',
    'PROGRAMACAO',
    'FRONT_END',
    'DEVOPS',
    'UX_DESIGN',
    'DATA_SCIENCE',
    'INOVACAO_GESTAO',
    'IA',
    'OFF_TOPIC'
);

ALTER TABLE cursos
ALTER COLUMN categoria
TYPE categoria_curso
USING categoria::categoria_curso;

