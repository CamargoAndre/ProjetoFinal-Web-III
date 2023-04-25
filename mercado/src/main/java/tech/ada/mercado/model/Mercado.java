package tech.ada.mercado.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.annotation.Collation;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Mercado {

    @Id
    private String id;
    private String nome;
    private String moeda;
    private Double saldo;

    public Mercado update(Mercado mercado){
        this.setNome(mercado.nome);
        this.setMoeda(mercado.moeda);
        this.setSaldo(mercado.saldo);
        return this;

    }

}
