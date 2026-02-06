package br.com.gustavo.forum_hub.domain.curso;

public enum CategoriaCurso {
    MOBILE("Mobile"),
    PROGRAMACAO("Programação"),
    FRONT_END("Front-end"),
    DEVOPS("DevOps"),
    UX_DESIGN("UX & Design"),
    DATA_SCIENCE("Data Science\n"),
    INOVACAO_GESTAO("Inovação & Gestão"),
    IA("Inteligência Artificial"),
    OFF_TOPIC("Off Topic");

    private final String value;

    CategoriaCurso(String value) {
        this.value = value;
    }
}
