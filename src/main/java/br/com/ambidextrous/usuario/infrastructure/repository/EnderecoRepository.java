package br.com.ambidextrous.usuario.infrastructure.repository;

import br.com.ambidextrous.usuario.entity.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
}
