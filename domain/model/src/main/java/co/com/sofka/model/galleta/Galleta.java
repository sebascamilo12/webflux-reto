package co.com.sofka.model.galleta;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data

public class Galleta {

    private Integer codigo;
    private String tipo;

    public Galleta(Integer codigo, String tipo) {
        this.codigo = codigo;
        this.tipo = tipo;
    }
}
