package br.com.ambidextrous.usuario.business.converter;

import br.com.ambidextrous.usuario.business.dto.EnderecoDTO;
import br.com.ambidextrous.usuario.business.dto.TelefoneDTO;
import br.com.ambidextrous.usuario.business.dto.UsuarioDTO;
import br.com.ambidextrous.usuario.entity.Endereco;
import br.com.ambidextrous.usuario.entity.Telefone;
import br.com.ambidextrous.usuario.entity.Usuario;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UsuarioConverter {

    public Usuario paraUsuario(UsuarioDTO usuarioDTO) {
        return Usuario.builder()
                .nome(usuarioDTO.getNome())
                .email(usuarioDTO.getEmail())
                .senha(usuarioDTO.getSenha())
                .enderecos(paraListaEndereco(usuarioDTO.getEnderecos()))
                .telefones(paraListaTelefone(usuarioDTO.getTelefones()))
                .build();
    }
    public List<Endereco> paraListaEndereco(List<EnderecoDTO> enderecoDTOS) {
        return enderecoDTOS.stream().map(this::paraEndereco).toList();
    }

    public Endereco paraEndereco(EnderecoDTO enderecoDTO) {
        return Endereco.builder()
                .logradouro(enderecoDTO.getLogradouro())
                .numero(enderecoDTO.getNumero())
                .complemento(enderecoDTO.getComplemento())
                .cidade(enderecoDTO.getCidade())
                .uf(enderecoDTO.getUf())
                .cep(enderecoDTO.getCep())
                .build();
    }

    public List<Telefone> paraListaTelefone(List<TelefoneDTO>  telefoneDTOS) {
        return  telefoneDTOS.stream().map(this::paraTelefone).toList();
    }

    public Telefone paraTelefone(TelefoneDTO telefoneDTO) {
        return Telefone.builder()
                .numero(telefoneDTO.getNumero())
                .ddd(telefoneDTO.getDdd())
                .build();
    }

    public UsuarioDTO paraUsuarioDTO(Usuario usuario) {
        return UsuarioDTO.builder()
                .nome(usuario.getNome())
                .email(usuario.getEmail())
                .senha(usuario.getSenha())
                .enderecos(paraListaEnderecoDTO(usuario.getEnderecos()))
                .telefones(paraListaTelefoneDTO(usuario.getTelefones()))
                .build();
    }
    public List<EnderecoDTO> paraListaEnderecoDTO(List<Endereco> enderecos) {
        return enderecos.stream().map(this::paraEnderecoDTO).toList();
    }

    public EnderecoDTO paraEnderecoDTO(Endereco endereco) {
        return EnderecoDTO.builder()
                .id(endereco.getId())
                .logradouro(endereco.getLogradouro())
                .numero(endereco.getNumero())
                .complemento(endereco.getComplemento())
                .cidade(endereco.getCidade())
                .uf(endereco.getUf())
                .cep(endereco.getCep())
                .build();
    }

    public List<TelefoneDTO> paraListaTelefoneDTO(List<Telefone>  telefones) {
        return  telefones.stream().map(this::paraTelefoneDTO).toList();
    }

    public TelefoneDTO paraTelefoneDTO(Telefone telefone) {
        return TelefoneDTO.builder()
                .id(telefone.getId())
                .numero(telefone.getNumero())
                .ddd(telefone.getDdd())
                .build();
    }

    public Usuario updateUsuario(UsuarioDTO usuarioDTO, Usuario usuario) {
        return Usuario.builder()
                .nome(usuarioDTO.getNome() != null ? usuarioDTO.getNome() : usuario.getNome())
                .id(usuario.getId())
                .senha(usuarioDTO.getSenha() != null ? usuarioDTO.getSenha() : usuario.getSenha())
                .email(usuarioDTO.getEmail() != null ? usuarioDTO.getEmail() : usuario.getEmail())
                .enderecos(usuario.getEnderecos())
                .telefones(usuario.getTelefones())
                .build();
    }

    public Endereco updateEndereco(EnderecoDTO enderecoDTO, Endereco endereco) {
        return Endereco.builder()
                .id(enderecoDTO.getId() != null ? enderecoDTO.getId() : endereco.getId())
                .logradouro(enderecoDTO.getLogradouro() != null ?  enderecoDTO.getLogradouro() : endereco.getLogradouro())
                .numero(enderecoDTO.getNumero() != null ? enderecoDTO.getNumero() :  endereco.getNumero())
                .complemento(enderecoDTO.getComplemento() != null ?  enderecoDTO.getComplemento() : endereco.getComplemento())
                .cidade(enderecoDTO.getCidade() != null ?   enderecoDTO.getCidade() : endereco.getCidade())
                .cep(enderecoDTO.getCep()  != null ?  enderecoDTO.getCep() : endereco.getCep())
                .uf(enderecoDTO.getUf()  != null ?  enderecoDTO.getUf() : endereco.getUf())
                .build();
    }

    public Telefone updateTelefone(TelefoneDTO telefoneDTO,  Telefone telefone) {
        return Telefone.builder()
                .id(telefoneDTO.getId() != null  ? telefoneDTO.getId() : telefone.getId())
                .numero(telefoneDTO.getNumero() != null ? telefoneDTO.getNumero() : telefone.getNumero())
                .ddd(telefoneDTO.getDdd() != null ?  telefoneDTO.getDdd() : telefone.getDdd())
                .build();
    }

}
