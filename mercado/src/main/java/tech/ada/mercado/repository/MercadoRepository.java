package tech.ada.mercado.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import tech.ada.mercado.model.Mercado;


public interface MercadoRepository extends ReactiveMongoRepository<Mercado, String> {
}
