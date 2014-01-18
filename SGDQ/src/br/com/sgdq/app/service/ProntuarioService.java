package br.com.sgdq.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.sgdq.app.entity.Prontuario;
import br.com.sgdq.app.repository.ProntuarioRepository;

@Service
@Transactional
public class ProntuarioService {

	@Autowired
	ProntuarioRepository prontuarioRepository;
	
	public void incluir(Prontuario prontuario) {
		prontuarioRepository.incluir(prontuario);
	}
	
	public List<Prontuario> consultar(int id, String nome, String cpf) {
		
		if(!(id == 0))
			return prontuarioRepository.consultarPorId(id);
		else
			if(!cpf.isEmpty())
				return prontuarioRepository.consultarPorCpfDoPaciente(cpf);
			else
				return prontuarioRepository.consultarPorNome(nome);
		
	}
	
}
