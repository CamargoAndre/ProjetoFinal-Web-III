package tech.ada.mercado.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Cotacao {

    private String code;
    private String codein;
    private String name;

    private Double high;
//    private Double low;
//    private Double varBid;
//    private Double pctChange;
//    private Double bid;
//    private Double ask;
//    private String timestamp;
//    private String createDate;

}
