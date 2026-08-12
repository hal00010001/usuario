package br.com.ambidextrous.usuario.business;

import br.com.ambidextrous.usuario.business.converter.UsuarioConverter;
import br.com.ambidextrous.usuario.business.dto.UsuarioDTO;
import br.com.ambidextrous.usuario.entity.Usuario;
import br.com.ambidextrous.usuario.infrastructure.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

	private final UsuarioRepository usuarioRepository;
    private final UsuarioConverter usuarioConverter;
    
    public UsuarioDTO create(UsuarioDTO usuarioDTO) {
        Usuario usuario = usuarioConverter.paraUsuario(usuarioDTO);
        return usuarioConverter.paraUsuarioDTO(usuarioRepository.save(usuario));
    }

}
