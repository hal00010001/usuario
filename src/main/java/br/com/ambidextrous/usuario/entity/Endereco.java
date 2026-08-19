package br.com.ambidextrous.usuario.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "endereco")
@Builder
@ToString
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String logradouro;
    private String numero;
    @Column(length = 100)
    private String complemento;
    private String bairro;
    @Column(length = 100)
    private String cidade;
    @Column(length = 2)
    private String uf;
    @Column(length = 9)
    private String cep;
    @Column(name = "usuario_id")
    private Long usuarioId;

}
