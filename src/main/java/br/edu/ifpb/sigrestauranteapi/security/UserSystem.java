package br.edu.ifpb.sigrestauranteapi.security;

import java.util.ArrayList;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import br.edu.ifpb.sigrestauranteapi.model.Usuario;

public class UserSystem extends User{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Usuario usuario;
	
	public UserSystem(Usuario usuario) {
		super(usuario.getLogin(), usuario.getSenha(), new ArrayList<GrantedAuthority>());
		this.usuario = usuario;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	
}
