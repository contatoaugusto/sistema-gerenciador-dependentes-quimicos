package br.com.sgdq.app.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.joda.time.DateTime;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartSeries;

import br.com.sgdq.app.controller.util.JsfUtil;
import br.com.sgdq.app.entity.Fase;
import br.com.sgdq.app.entity.FaseTratamento;
import br.com.sgdq.app.entity.Tratamento;
import br.com.sgdq.app.entity.TratamentoStatus;
import br.com.sgdq.app.facade.FaseFacade;
import br.com.sgdq.app.facade.FaseTratamentoFacade;
import br.com.sgdq.app.facade.TratamentoFacade;
import br.com.sgdq.app.facade.TratamentoStatusFacade;

/**
*
* @author Antonio Augusto
*/
@ManagedBean
@SessionScoped
public class RelatorioTratamentosFinalizadosController {
	
	private  TratamentoFacade tratamentoFacade;
	private  TratamentoStatusFacade tratamentoStatusFacade;
	private  FaseTratamentoFacade faseTratamentoFacade;
	private  FaseFacade faseFacade;
	
	private Date dataInicial;
	private Date dataFinal;
	
	public RelatorioTratamentosFinalizadosController() {
		
	}
	
	private CartesianChartModel relatorioModel;
	
	public CartesianChartModel getRelatorioModel() {
		return relatorioModel;
	}

	public void setRelatorioModel(CartesianChartModel relatorioModel) {
		this.relatorioModel = relatorioModel;
	}
	
//    @PostConstruct
//    public void init() {
//		
//    	try {
//			emitir();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	
	
	public void emitir() throws IOException{
		
		DateTime dataFinal = new DateTime(this.dataFinal.getTime());
		DateTime dataInicial = new DateTime(this.dataInicial.getTime());
		
		if (dataFinal.getMillis() < dataInicial.getMillis())
			JsfUtil.addErrorMessage(ResourceBundle.getBundle("/message").getString("relatorio.erro.dataFinal_menor_dataInicial"));
		else {
			relatorioModel = new CartesianChartModel();
			
			// Tratamentos
			List<Tratamento> tratamentosFinalizados = new ArrayList<Tratamento>();
			//tratamentos = getFacade().findAll();
			tratamentosFinalizados = getFacade().findTratamentoFinalizadoByPeriodo(this.dataInicial, this.dataFinal);
			
			// Tratamento Status
			List<TratamentoStatus> tratamentoStatus = new ArrayList<TratamentoStatus>();
			tratamentoStatus = getFacadeTratamentoStatus().findAll();
			
			// Fase
			List<Fase> fases = new ArrayList<Fase>();
			fases = getFacadeFase().findAll();
			
			// Fase Tratamento
			List<FaseTratamento> faseTratamento = new ArrayList<FaseTratamento>();
			faseTratamento = getFacadeFaseTratamento().findAll();
			
			
			//Definir loop de fases e dentro dele criar as implementações de relatório
			for (Fase faseEntity : fases ){
			
				ChartSeries faseChartSerie = new ChartSeries();  
		    	faseChartSerie.setLabel(faseEntity.getNmfase()); 
		        
		        // Loop Para Status
				for (TratamentoStatus tratamentoStatusEntity : tratamentoStatus){
					
					// Não contabilizar o status Em Andamento
					//if (tratamentoStatusEntity.getIdtratamentostatus() != 1){
					int qtdeTratamentoPorStatus = 0;
					// Loop Para Tratamentos
					for (Tratamento tratamento : tratamentosFinalizados){
						
						if (tratamento.getIdtratamentostatus() == tratamentoStatusEntity.getIdtratamentostatus() &&
								tratamento.getDttratamentofim() != null){
							
							int idFase = 0;
							for (FaseTratamento faseTratamentoAuxiliar : faseTratamento){
								if (faseTratamentoAuxiliar.getIdtratamento() == tratamento.getIdtratamento() &&
										idFase < faseTratamentoAuxiliar.getIdfase()){
									idFase = faseTratamentoAuxiliar.getIdfase();
								}	
							}
							
							if (idFase == faseEntity.getIdfase())
								qtdeTratamentoPorStatus++;
						} // Fim Teste se o tratament está no status iterado
				        
					} // Fim loop Tratamentos 
				
					faseChartSerie.set(tratamentoStatusEntity.getNmtratamentostatus(), qtdeTratamentoPorStatus);
					//}
				} // Fim loop tratamentoStatus    
			
				relatorioModel.addSeries(faseChartSerie);
			} // Fim loop fases
			
			ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
	        context.redirect(context.getRequestContextPath() + "/pages/relatorios/tratamentoFinalizado/relatorio.xhtml");
		} 
	}
	
	
    private TratamentoFacade getFacade() {
    	if (tratamentoFacade == null)
    		tratamentoFacade = new TratamentoFacade();
    	return tratamentoFacade;
    }
    private FaseFacade getFacadeFase() {
    	if (faseFacade == null)
    		faseFacade = new FaseFacade();
    	return faseFacade;
    }
    private TratamentoStatusFacade getFacadeTratamentoStatus() {
    	if (tratamentoStatusFacade == null)
    		tratamentoStatusFacade = new TratamentoStatusFacade();
    	return tratamentoStatusFacade;
    }
    private FaseTratamentoFacade getFacadeFaseTratamento() {
    	if (faseTratamentoFacade == null)
    		faseTratamentoFacade = new FaseTratamentoFacade();
    	return faseTratamentoFacade;
    }
    
    
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

    
}
