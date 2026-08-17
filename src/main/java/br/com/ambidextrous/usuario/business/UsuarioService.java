package br.com.ambidextrous.usuario.business;

import br.com.ambidextrous.usuario.business.converter.UsuarioConverter;
import br.com.ambidextrous.usuario.business.dto.EnderecoDTO;
import br.com.ambidextrous.usuario.business.dto.TelefoneDTO;
import br.com.ambidextrous.usuario.business.dto.UsuarioDTO;
import br.com.ambidextrous.usuario.entity.Endereco;
import br.com.ambidextrous.usuario.entity.Telefone;
import br.com.ambidextrous.usuario.entity.Usuario;
import br.com.ambidextrous.usuario.exception.ConflictException;
import br.com.ambidextrous.usuario.exception.ResourceNotFoundException;
import br.com.ambidextrous.usuario.infrastructure.repository.EnderecoRepository;
import br.com.ambidextrous.usuario.infrastructure.repository.TelefoneRepository;
import br.com.ambidextrous.usuario.infrastructure.repository.UsuarioRepository;
import br.com.ambidextrous.usuario.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService {

	private final UsuarioRepository usuarioRepository;
    private final UsuarioConverter usuarioConverter;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final EnderecoRepository enderecoRepository;
    private final TelefoneRepository telefoneRepository;
    
    public UsuarioDTO create(UsuarioDTO usuarioDTO) {
        emailExiste(usuarioDTO.getEmail());
        usuarioDTO.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));
        Usuario usuario = usuarioConverter.paraUsuario(usuarioDTO);
        return usuarioConverter.paraUsuarioDTO(usuarioRepository.save(usuario));
    }

    public void emailExiste(String email) {
        try {
            boolean existe = verificaEmailExistente(email);
            if(existe){
                throw new ConflictException("Email já cadastrado " + email);
            }
        } catch (Exception e) {
            throw new ConflictException("Email já cadastrado " + e.getCause());
        }
    }

    public boolean verificaEmailExistente(String email) {
        return usuarioRepository.existsByEmail(email);
    }

    public UsuarioDTO buscaUsuarioPorEmail(String email) {
        try {
            return usuarioConverter.paraUsuarioDTO(
                    usuarioRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("Email não encontrado " + email))
            );
        } catch (ResourceNotFoundException e){
            throw new ResourceNotFoundException("Email não encontrado " +  email);
        }
    }

    public void deletaUsuarioPorEmail(String email) {
        usuarioRepository.deleteByEmail(email);
    }

    public UsuarioDTO atualizaDadosUsuario(String token, UsuarioDTO usuarioDTO) {
        // Aqui buscamos o email do usuário através do token (tirar a obrigatoriedade do email)
        String email = jwtUtil.extractUsername(token.substring(7));

        // Criptografia de senha
        usuarioDTO.setSenha(usuarioDTO.getSenha() != null ? passwordEncoder.encode(usuarioDTO.getSenha()) : null);

        // Busca os dados do usuário no banco de dados
        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("Email não encontrado"));

        // Mesclou os dados que recebemos na requisição DTO com os dados do banco de dados
        Usuario updateUsuario = usuarioConverter.updateUsuario(usuarioDTO, usuario);
        updateUsuario.setSenha(passwordEncoder.encode(updateUsuario.getSenha()));

        return usuarioConverter.paraUsuarioDTO(usuarioRepository.save(updateUsuario));
    }

    public EnderecoDTO atualizaEndereco(Long idEndereco, EnderecoDTO  enderecoDTO) {
        Endereco endereco = enderecoRepository.findById(idEndereco).orElseThrow(() -> new ResourceNotFoundException("ID não encontrado"));
        Endereco updateEndereco = usuarioConverter.updateEndereco(enderecoDTO, endereco);
        return usuarioConverter.paraEnderecoDTO(enderecoRepository.save(updateEndereco));
    }

    public TelefoneDTO atualizaTelefone(Long idTelefone, TelefoneDTO telefoneDTO) {
        Telefone telefone = telefoneRepository.findById(idTelefone).orElseThrow(() -> new ResourceNotFoundException("ID não encontrado"));
        Telefone updateTelefone =  usuarioConverter.updateTelefone(telefoneDTO, telefone);
        return usuarioConverter.paraTelefoneDTO(telefoneRepository.save(updateTelefone));
    }

    public EnderecoDTO cadastrarEndereco(String token, EnderecoDTO enderecoDTO) {
        String email = jwtUtil.extractUsername(token.substring(7));
        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("Email não localizado " + email));
        Endereco endereco = usuarioConverter.paraEnderecoEntity(enderecoDTO, usuario.getId());
        return usuarioConverter.paraEnderecoDTO(enderecoRepository.save(endereco));
    }

    public TelefoneDTO cadastrarTelefone(String token, TelefoneDTO telefoneDTO) {
        String email = jwtUtil.extractUsername(token.substring(7));
        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("Email não cadastrado " + email));
        Telefone telefone = usuarioConverter.paraTelefoneEntity(telefoneDTO, usuario.getId());
        return usuarioConverter.paraTelefoneDTO(telefoneRepository.save(telefone));
    }

}
