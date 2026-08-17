package br.com.ambidextrous.usuario.infrastructure.repository;

import br.com.ambidextrous.usuario.entity.Telefone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TelefoneRepository extends JpaRepository<Telefone, Long> {
}
