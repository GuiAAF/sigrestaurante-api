package br.edu.ifpb.sigrestauranteapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.edu.ifpb.sigrestauranteapi.model.Edital;

public interface EditalRepository extends JpaRepository<Edital, Long>{
		
	public Edital findByNumero(String numero);
}
