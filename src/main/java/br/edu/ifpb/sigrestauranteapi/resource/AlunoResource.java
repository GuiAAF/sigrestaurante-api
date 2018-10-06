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
import br.edu.ifpb.sigrestauranteapi.model.Aluno;
import br.edu.ifpb.sigrestauranteapi.service.AlunoService;

@RestController
@RequestMapping("/alunos")
public class AlunoResource {
	
	@Autowired
	private AlunoService alunoService;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@PostMapping
	public ResponseEntity<Aluno> salvar(@Valid @RequestBody Aluno aluno, HttpServletResponse response){
		Aluno novoALuno = alunoService.salvar(aluno);
		publisher.publishEvent(new RecursoCriadoEvent(aluno, response, novoALuno.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(novoALuno);
	}

}
