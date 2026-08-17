package br.com.ambidextrous.usuario.business.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class TelefoneDTO {

    private Long id;
    private String numero;
    private String ddd;

}
