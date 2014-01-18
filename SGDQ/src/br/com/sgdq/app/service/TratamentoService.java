package br.com.sgdq.app.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.sgdq.app.entity.Tratamento;
import br.com.sgdq.app.repository.TratamentoRepository;

@Service
@Transactional
public class TratamentoService {
	
	@Autowired
	TratamentoRepository tratamentoRepository;
	
	public void incluir(Tratamento tratamento) {
		tratamentoRepository.incluir(tratamento);
	}
	
	public List<Tratamento> consultarQuantidadeDeTratamentosIniciadosPorMes(Date dataInicial, Date dataFinal) {
		return tratamentoRepository.consultarQuantidadeDeTratamentosIniciadosPorMes(dataInicial, dataFinal);
	}
	
	public List<Tratamento> consultarQuantidadeDeTratamentosFinalizadosPorMes(Date dataInicial, Date dataFinal) {
		return tratamentoRepository.consultarQuantidadeDeTratamentosFinalizadosPorMes(dataInicial, dataFinal);
	}
	
}
