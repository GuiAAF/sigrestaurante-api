package br.edu.ifpb.sigrestauranteapi.resource;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifpb.sigrestauranteapi.event.RecursoCriadoEvent;
import br.edu.ifpb.sigrestauranteapi.model.Edital;
import br.edu.ifpb.sigrestauranteapi.service.EditalService;

@RestController
@RequestMapping("/editais")
public class EditalResource {
	
	@Autowired
	private EditalService editalService;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@PostMapping
	public ResponseEntity<Edital> criar(@Valid @RequestBody Edital edital, HttpServletResponse response) {
		Edital novoEdital = editalService.criar(edital);
		publisher.publishEvent(new RecursoCriadoEvent(edital, response, novoEdital.getCodigo()));
		return ResponseEntity.status(HttpStatus.CREATED).body(novoEdital);
	}

}
