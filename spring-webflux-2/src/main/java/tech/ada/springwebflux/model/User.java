package tech.ada.springwebflux.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import tech.ada.springwebflux.controller.Comprovante;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor

@Document
public class User {
    @Id
    private String id;

    private String name;
    private String username;
    private List<String> roles;
    private Long birth;
    private LocalDate since;
    private Double balance;

    public User pay(User user, Comprovante comprovante) {
        this.balance -= comprovante.getValor();
        user.balance += comprovante.getValor();
        comprovante.setAck_usuario(true);
        return this;
    }

    public User() {
       this.balance = 0.0;
    }


    public User update(User usuario){
        // id, username and since not permited changing
        this.setName(usuario.name);
        this.setBirth(usuario.birth);
        this.setRoles(usuario.getRoles());
        this.setSince(usuario.since);
        return this;
    }


}
