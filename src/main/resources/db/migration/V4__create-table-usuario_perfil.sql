CREATE TABLE usuario_perfil (
    usuario_id BIGINT NOT NULL,
    perfil_id BIGINT NOT NULL,

    PRIMARY KEY (usuario_id, perfil_id),

    CONSTRAINT fk_usuario_perfil_usuario
        FOREIGN KEY (usuario_id)
        REFERENCES usuarios(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_usuario_perfil_perfil
        FOREIGN KEY (perfil_id)
        REFERENCES perfis(id)
        ON DELETE CASCADE
);