package br.edu.ifpb.sigrestauranteapi.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import br.edu.ifpb.sigrestauranteapi.model.Usuario;
import br.edu.ifpb.sigrestauranteapi.repository.UsuarioRepository;

public class AppUserDetailsService implements UserDetailsService{

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = usuarioRepository.findByLogin(username);
		if (usuario!=null && usuario.isAtivo()) {
			return new UserSystem(usuario);			
		}

		else {
			throw new UsernameNotFoundException("Usuário e/ou senha incorretos");
		}
	}

}
