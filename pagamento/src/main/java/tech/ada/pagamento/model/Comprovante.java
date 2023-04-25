package tech.ada.pagamento.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comprovante {

    public String id;
    private String pagador;
    private String recebedor;
    private Double valor;
    private LocalDateTime data;
    private Boolean ack_usuario;
}