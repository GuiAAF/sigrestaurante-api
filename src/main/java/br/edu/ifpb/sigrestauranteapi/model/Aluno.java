package br.edu.ifpb.sigrestauranteapi.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@PrimaryKeyJoinColumn(name = "id")
@Table(name = "aluno")
public class Aluno extends Usuario{

	private String Urlfoto;
	
	@NotNull
	private Integer creditos;
	
	@ManyToOne
	@NotNull
	@JoinColumn(name="id_edital")
	private Edital edital;

	public String getUrlfoto() {
		return Urlfoto;
	}

	public void setUrlfoto(String urlfoto) {
		Urlfoto = urlfoto;
	}

	public Integer getCreditos() {
		return creditos;
	}

	public void setCreditos(Integer creditos) {
		this.creditos = creditos;
	}

	public Edital getEdital() {
		return edital;
	}

	public void setEdital(Edital edital) {
		this.edital = edital;
	}
}
