package br.edu.ifpb.sigrestauranteapi.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ifpb.sigrestauranteapi.model.Edital;
import br.edu.ifpb.sigrestauranteapi.repository.EditalRepository;
import br.edu.ifpb.sigrestauranteapi.service.exception.DataInicioAnteriorAHojeException;
import br.edu.ifpb.sigrestauranteapi.service.exception.DataTerminoAnteriorADataInicioException;
import br.edu.ifpb.sigrestauranteapi.service.exception.NumeroJaCadastradoException;

@Service
public class EditalService {
	
	@Autowired
	private EditalRepository editalRepository;
	
	public Edital criar(Edital edital) {
		validarEdital(edital);
		return editalRepository.save(edital);
	}

	private void validarEdital(Edital edital) {
		
		Edital numeroEdital = editalRepository.findByNumero(edital.getNumero());
		
		if(numeroEdital != null) {
			throw new NumeroJaCadastradoException();
		}
		if(edital.getDataInicio().isBefore(LocalDate.now())){
			throw new DataInicioAnteriorAHojeException();
		}
		if(edital.getDataTermino().isBefore(edital.getDataInicio())) {
			throw new DataTerminoAnteriorADataInicioException();
		}
			
		
	}

}
