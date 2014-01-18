package br.com.sgdq.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.sgdq.app.entity.Responsavel;
import br.com.sgdq.app.repository.ResponsavelRepository;

@Service
@Transactional
public class ResponsavelService {

	@Autowired
	ResponsavelRepository responsavelRepository;
	
	public void incluir(Responsavel responsavel) {
		responsavelRepository.incluir(responsavel);
	}
	
}
