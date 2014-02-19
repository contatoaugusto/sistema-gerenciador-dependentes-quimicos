package br.com.sgdq.app.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.MonthDay;
import org.joda.time.Months;
import org.joda.time.Years;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartSeries;

import br.com.sgdq.app.controller.util.JsfUtil;
import br.com.sgdq.app.entity.Tratamento;
import br.com.sgdq.app.facade.TratamentoFacade;
//import br.com.sgdq.app.service.TratamentoService;

/**
*
* @author Antonio Augusto
*/
@ManagedBean
@SessionScoped
public class RelatorioMediaPermanenciaController {
	
	private  TratamentoFacade tratamentoFacade;
	
	public RelatorioMediaPermanenciaController() {
		
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
	
	public void emitir() throws IOException{
		
		DateTime dataFinal = new DateTime(this.dataFinal.getTime());
		DateTime dataInicial = new DateTime(this.dataInicial.getTime());
		
		if (dataFinal.getMillis() < dataInicial.getMillis())
			JsfUtil.addErrorMessage(ResourceBundle.getBundle("/message").getString("relatorio.erro.dataFinal_menor_dataInicial"));
		else {
			relatorioModel = new CartesianChartModel();
			
			ChartSeries finalizados = new ChartSeries();
	        
			List<String> ano = new ArrayList<String>();
			List<Tratamento> tratamentosFinalizados = new ArrayList<Tratamento>();
			
			DateTime dataAuxiliar = dataInicial;
			
			DateTime dataDeInclusaoDoTratamentoFinalizado;
			
			Years anos = Years.yearsBetween(dataInicial, dataFinal);
			
			double somaMesesTratamentoPorAno = 0.;
			int somaMesesTratamentoPorAnoAuxiliar = 0;
//			int somaTratamentoFinalizadoPorQuantidadeMes = 0;
			
			tratamentosFinalizados =  getFacade().findTratamentoFinalizadoByPeriodo(this.dataInicial, this.dataFinal);
			
			double quantidadeDeTratamentosFinalizadosPorAno = 0.;
			
			ano.add(String.valueOf(dataInicial.getYear()));
			
			for(int i = 1; i <= anos.getYears(); i++) {
				
				dataAuxiliar = dataAuxiliar.plusYears(1);
				
				if(!ano.contains(String.valueOf(dataAuxiliar.getYear()))) {
					ano.add(String.valueOf(dataAuxiliar.getYear()));
				}
			}
			
			if(!ano.contains(String.valueOf(dataFinal.getYear())))
				ano.add(String.valueOf(dataFinal.getYear()));
			
			finalizados.setLabel("Meses");  
	        
	        for(String anoAuxiliar : ano) {
	        	
	        	// Armazenar a quantidade de tratamentos por meses no anor
		        //HashMap<Integer, Integer> meseQtdeTratamento = new HashMap<Integer, Integer>();
		        
	        	for(Tratamento tratamentoFinalizado : tratamentosFinalizados) {
	        		
	        		dataDeInclusaoDoTratamentoFinalizado = new DateTime(tratamentoFinalizado.getDttratamentofim().getTime());
	        		
	        		if(anoAuxiliar.contains(String.valueOf(dataDeInclusaoDoTratamentoFinalizado.getYear()))){
	        			
	        			// Quantidade de meses dos tratamentos finalizados no ano em questão anoAuxiliar
	        			somaMesesTratamentoPorAnoAuxiliar = Months.monthsBetween(
								new DateTime(tratamentoFinalizado.getDtinclusao().getTime()), 
								new DateTime(tratamentoFinalizado.getDttratamentofim().getTime())).getMonths();
	        			if (somaMesesTratamentoPorAnoAuxiliar >= 7 && somaMesesTratamentoPorAnoAuxiliar <= 12){
	        				quantidadeDeTratamentosFinalizadosPorAno++;
	        				somaMesesTratamentoPorAno = somaMesesTratamentoPorAno + somaMesesTratamentoPorAnoAuxiliar;
	        				
	        				//meseQtdeTratamento.put(somaMesesTratamentoPorAnoAuxiliar, (meseQtdeTratamento.containsKey(somaMesesTratamentoPorAnoAuxiliar) ? meseQtdeTratamento.get(somaMesesTratamentoPorAnoAuxiliar) : 0) + 1);
	        				
	        			}	
	        		}
	        	}
	        	
//	        	Iterator it = meseQtdeTratamento.entrySet().iterator();
//	            while (it.hasNext()) {
//	                Map.Entry permanenciaQuantidade = (Map.Entry)it.next();
//	                somaTratamentoFinalizadoPorQuantidadeMes = somaTratamentoFinalizadoPorQuantidadeMes + (Integer)permanenciaQuantidade.getKey() * (Integer)permanenciaQuantidade.getValue();
//	                it.remove();
//	            }
	        	
	        	
	        	if (quantidadeDeTratamentosFinalizadosPorAno == 0) quantidadeDeTratamentosFinalizadosPorAno = 1;
	        	//finalizados.set(anoAuxiliar, somaTratamentoFinalizadoPorQuantidadeMes/quantidadeDeTratamentosFinalizadosPorAno);
	        	finalizados.set(anoAuxiliar, somaMesesTratamentoPorAno/quantidadeDeTratamentosFinalizadosPorAno);
	        	
	        	somaMesesTratamentoPorAno = 0;
	        	quantidadeDeTratamentosFinalizadosPorAno = 0;
//	        	somaTratamentoFinalizadoPorQuantidadeMes = 0;
	        	
	        }
	        
	        //relatorioModel.addSeries(iniciados);  
	        relatorioModel.addSeries(finalizados);  
		
	        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
	        context.redirect(context.getRequestContextPath() + "/pages/relatorios/mediaPermanencia/relatorio.xhtml");
	        //return "/pages/relatorios/fluxodepacientes/relatorio.xhtml?faces-redirect=true";
		}   
	}
	
	
    private TratamentoFacade getFacade() {
    	if (tratamentoFacade == null)
    		tratamentoFacade = new TratamentoFacade();
    	return tratamentoFacade;
    }
}
