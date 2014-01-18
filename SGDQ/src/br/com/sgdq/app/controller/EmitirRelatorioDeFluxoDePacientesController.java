package br.com.sgdq.app.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.com.sgdq.app.entity.Tratamento;
//import br.com.sgdq.app.service.TratamentoService;

@ManagedBean
@SessionScoped
public class EmitirRelatorioDeFluxoDePacientesController {
	
	public EmitirRelatorioDeFluxoDePacientesController() {
		
	}
	
	private Date dataInicial;
	private Date dataFinal;
	private CartesianChartModel relatorioModel;
	
//	@Autowired
//	TratamentoService tratamentoService;
//	
	public Date getDataInicial() {
		return dataInicial;
	}
	
	public void setDataInicial(Date dataInicial) {
		this.dataInicial = dataInicial;
	}
	
	public Date getDataFinal() {
		return dataFinal;
	}
	
	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}
	
	public CartesianChartModel getRelatorioModel() {
		return relatorioModel;
	}

	public void setRelatorioModel(CartesianChartModel relatorioModel) {
		this.relatorioModel = relatorioModel;
	}
	
	public String emitir() {
		
		relatorioModel = new CartesianChartModel();
		
		ChartSeries iniciados = new ChartSeries();  
        ChartSeries finalizados = new ChartSeries();
        
		List<String> mesAno = new ArrayList<String>();
		List<Tratamento> tratamentosIniciados = new ArrayList<Tratamento>();
		List<Tratamento> tratamentosFinalizados = new ArrayList<Tratamento>();
		
		DateTime dataFinal = new DateTime(this.dataFinal.getTime());
		DateTime dataInicial = new DateTime(this.dataInicial.getTime());
		DateTime dataAuxiliar = dataInicial;
		DateTime dataDeInclusaoDoTratamentoIniciado;
		DateTime dataDeInclusaoDoTratamentoFinalizado;
		
		Days dias = Days.daysBetween(dataInicial, dataFinal);
		
		int quantidadeDeTratamentosIniciadosPorMes = 0;
		int quantidadeDeTratamentosFinalizadosPorMes = 0;
		
//		tratamentosIniciados = tratamentoService.consultarQuantidadeDeTratamentosIniciadosPorMes(this.dataInicial, this.dataFinal);
//		tratamentosFinalizados = tratamentoService.consultarQuantidadeDeTratamentosFinalizadosPorMes(this.dataInicial, this.dataFinal);
//		
		for(int i = 1; i <= dias.getDays(); i++) {
			
			if(!mesAno.contains(String.valueOf(dataAuxiliar.getMonthOfYear()).concat("/").concat(String.valueOf(dataAuxiliar.getYear())))) {
				mesAno.add(String.valueOf(dataAuxiliar.getMonthOfYear()).concat("/").concat(String.valueOf(dataAuxiliar.getYear())));
			}
			
			dataAuxiliar = dataAuxiliar.plusDays(1);
			
		}
		
		
		iniciados.setLabel("Iniciados");  
        finalizados.setLabel("Finalizados");  
        
        for(String mesAnoAuxiliar : mesAno) {
        	
        	for(Tratamento tratamentoIniciado : tratamentosIniciados) {
        		
        		//dataDeInclusaoDoTratamentoIniciado = new DateTime(tratamentoIniciado.getDataDeInclusao().getTime());
        		
        		//if(mesAnoAuxiliar.contains((String.valueOf(dataDeInclusaoDoTratamentoIniciado.getMonthOfYear()))))
        		//	quantidadeDeTratamentosIniciadosPorMes++;
        		
        	}
        	
        	iniciados.set(mesAnoAuxiliar, quantidadeDeTratamentosIniciadosPorMes);
        	
        	quantidadeDeTratamentosIniciadosPorMes = 0;
        	
        	for(Tratamento tratamentoFinalizado : tratamentosFinalizados) {
        		
        		//dataDeInclusaoDoTratamentoFinalizado = new DateTime(tratamentoFinalizado.getDataDeInclusao().getTime());
        		
        		//if(mesAnoAuxiliar.contains((String.valueOf(dataDeInclusaoDoTratamentoFinalizado.getMonthOfYear()))))
        		//	quantidadeDeTratamentosFinalizadosPorMes++;
        		
        	}
        	
        	finalizados.set(mesAnoAuxiliar, quantidadeDeTratamentosFinalizadosPorMes);
        	
        	quantidadeDeTratamentosFinalizadosPorMes = 0;
        	
        }
        
        relatorioModel.addSeries(iniciados);  
        relatorioModel.addSeries(finalizados);  
	
        return "/pages/usuario/relatorios/fluxodepacientes/relatorio?faces-redirect=true";
        
	}
	
}
