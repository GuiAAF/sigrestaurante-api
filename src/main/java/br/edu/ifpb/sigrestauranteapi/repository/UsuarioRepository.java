package br.edu.ifpb.sigrestauranteapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifpb.sigrestauranteapi.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

	Usuario findByLogin(String login);

}
