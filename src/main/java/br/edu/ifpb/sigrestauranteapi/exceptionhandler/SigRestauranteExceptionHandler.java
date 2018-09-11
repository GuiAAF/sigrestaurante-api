package br.edu.ifpb.sigrestauranteapi.exceptionhandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.edu.ifpb.sigrestauranteapi.service.exception.DataInicioAnteriorAHojeException;
import br.edu.ifpb.sigrestauranteapi.service.exception.DataTerminoAnteriorADataInicioException;
import br.edu.ifpb.sigrestauranteapi.service.exception.NumeroJaCadastradoException;


public class SigRestauranteExceptionHandler extends ResponseEntityExceptionHandler {
	
	@Autowired
	private MessageSource messageSource;
	
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		String userMessage = messageSource.getMessage("invalid.message", null, LocaleContextHolder.getLocale());
		String developerMessage = ex.getCause() != null ? ex.getCause().toString() : ex.toString();
		List<Erro> erros = Arrays.asList(new Erro(userMessage, developerMessage));
		return handleExceptionInternal(ex, erros, headers, HttpStatus.BAD_REQUEST, request);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		List<Erro> erros = criarListaDeErros(ex.getBindingResult());
		return handleExceptionInternal(ex, erros, headers, HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler({ EmptyResultDataAccessException.class })
	public ResponseEntity<Object> handleEmptyResultDataAccessException(EmptyResultDataAccessException ex, WebRequest request) {
		String userMessage = messageSource.getMessage("resource.not-found", null, LocaleContextHolder.getLocale());
		String developerMessage = ex.toString();
		List<Erro> erros = Arrays.asList(new Erro(userMessage, developerMessage));
		return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	}

	@ExceptionHandler({ DataIntegrityViolationException.class } )
	public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException ex, WebRequest request) {
		String userMessage = messageSource.getMessage("resource.operation-not-allowed", null, LocaleContextHolder.getLocale());
		String developerMessage = ExceptionUtils.getRootCauseMessage(ex);
		List<Erro> erros = Arrays.asList(new Erro(userMessage, developerMessage));
		return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}
	
	@ExceptionHandler({ DataInicioAnteriorAHojeException.class })
	public ResponseEntity<Object> handleDataInicioAnteriorAHojeException(DataInicioAnteriorAHojeException ex, WebRequest request) {
		String mensagemUsuario = messageSource.getMessage("edital.data.anterior.a.de.hoje-cliente", null,
				LocaleContextHolder.getLocale());
		String mensagemDesenvolvedor = messageSource.getMessage("edital.data.anterior.a.de.hoje-desenvolvedor", null,
				LocaleContextHolder.getLocale());
		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));
		return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}
	
	@ExceptionHandler({ DataTerminoAnteriorADataInicioException.class })
	public ResponseEntity<Object> handleDataTerminoAnteriorADataInicioException(DataTerminoAnteriorADataInicioException ex, WebRequest request) {
		String mensagemUsuario = messageSource.getMessage("edital.data.termino.anterior.a.data.inicio-cliente", null,
				LocaleContextHolder.getLocale());
		String mensagemDesenvolvedor = messageSource.getMessage("edital.data.termino.anterior.a.data.inicio-desenvolvedor", null,
				LocaleContextHolder.getLocale());
		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));
		return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}
	
	@ExceptionHandler({ NumeroJaCadastradoException.class})
	public ResponseEntity<Object> handleNumeroJaCadastradoException(NumeroJaCadastradoException ex, WebRequest request) {
		String mensagemUsuario = messageSource.getMessage("edital.numero.ja.cadastrado-cliente", null,
				LocaleContextHolder.getLocale());
		String mensagemDesenvolvedor = messageSource.getMessage("edital.numero.ja.cadastrado-desenvolvedor", null,
				LocaleContextHolder.getLocale());

		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));

		return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}
	
	private List<Erro> criarListaDeErros(BindingResult bindingResult) {
		List<Erro> erros = new ArrayList<>();

		for (FieldError fieldError : bindingResult.getFieldErrors()) {
			String userMessage = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
			String developerMessage = fieldError.toString();
			erros.add(new Erro(userMessage, developerMessage));
		}

		return erros;
	}

	public static class Erro {

		private String userMessage;
		private String developerMessage;

		public Erro(String userMessage, String developerMessage) {
			this.userMessage = userMessage;
			this.developerMessage = developerMessage;
		}

		public String getuserMessage() {
			return userMessage;
		}

		public String getdeveloperMessage() {
			return developerMessage;
		}

	}

}
