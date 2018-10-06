package br.edu.ifpb.sigrestauranteapi.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.edu.ifpb.sigrestauranteapi.model.Aluno;
import br.edu.ifpb.sigrestauranteapi.repository.AlunoRepository;

@Service
public class AlunoService {
	
	@Autowired
	private AlunoRepository alunoRepository;
	
	@Value("${contato.disco.raiz}")
	private String raiz;
	
	@Value("${contato.disco.diretorio-fotos}")
	private String diretorioFotos;
	
	public Aluno salvar(Aluno aluno) {
		
		return alunoRepository.save(aluno);
	}

	public void salvarFoto(String diretorioFotos, MultipartFile foto, Aluno aluno) {
		
		Path diretorioPath = Paths.get(this.raiz, diretorioFotos);
		Path arquivoPath = diretorioPath.resolve(foto.getOriginalFilename());
		
		try {
			Files.createDirectories(diretorioPath);
			foto.transferTo(arquivoPath.toFile());
			aluno.setUrlfoto(arquivoPath.toString());
		} catch (IOException e) {
			throw new RuntimeException("Problemas na tentativa de salvar arquivo.", e);
		}
		
	}

}
