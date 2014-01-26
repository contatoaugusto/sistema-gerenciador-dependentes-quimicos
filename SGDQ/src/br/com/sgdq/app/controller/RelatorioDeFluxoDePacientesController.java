package br.com.sgdq.app.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartSeries;

import br.com.sgdq.app.entity.Tratamento;
import br.com.sgdq.app.facade.TratamentoFacade;
//import br.com.sgdq.app.service.TratamentoService;

/**
*
* @author Antonio Augusto
*/
@ManagedBean
@SessionScoped
public class RelatorioDeFluxoDePacientesController {
	
	private  TratamentoFacade tratamentoFacade;
	
	public RelatorioDeFluxoDePacientesController() {
		
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
		
		tratamentosIniciados = getFacade().findTratamentoIniciadoByPeriodo(this.dataInicial, this.dataFinal);
		tratamentosFinalizados =  getFacade().findTratamentoFinalizadoByPeriodo(this.dataInicial, this.dataFinal);
		
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
        		
        		dataDeInclusaoDoTratamentoIniciado = new DateTime(tratamentoIniciado.getDtinclusao().getTime());
        		
        		if(mesAnoAuxiliar.contains((
        					String.valueOf(dataDeInclusaoDoTratamentoIniciado.getMonthOfYear()).concat("/")
        					.concat(String.valueOf(dataDeInclusaoDoTratamentoIniciado.getYear())))))
        			quantidadeDeTratamentosIniciadosPorMes++;
        		
        	}
        	
        	iniciados.set(mesAnoAuxiliar, quantidadeDeTratamentosIniciadosPorMes);
        	
        	quantidadeDeTratamentosIniciadosPorMes = 0;
        	
        	for(Tratamento tratamentoFinalizado : tratamentosFinalizados) {
        		
        		dataDeInclusaoDoTratamentoFinalizado = new DateTime(tratamentoFinalizado.getDttratamentofim().getTime());
        		
        		if(mesAnoAuxiliar.contains((
        				String.valueOf(dataDeInclusaoDoTratamentoFinalizado.getMonthOfYear()).concat("/")
    					.concat(String.valueOf(dataDeInclusaoDoTratamentoFinalizado.getYear())))))
        			quantidadeDeTratamentosFinalizadosPorMes++;
        		
        	}
        	
        	finalizados.set(mesAnoAuxiliar, quantidadeDeTratamentosFinalizadosPorMes);
        	
        	quantidadeDeTratamentosFinalizadosPorMes = 0;
        	
        }
        
        relatorioModel.addSeries(iniciados);  
        relatorioModel.addSeries(finalizados);  
	
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        context.redirect(context.getRequestContextPath() + "/pages/relatorios/fluxodepacientes/relatorio.xhtml");
        //return "/pages/relatorios/fluxodepacientes/relatorio.xhtml?faces-redirect=true";
        
	}
	
	
    private TratamentoFacade getFacade() {
    	if (tratamentoFacade == null)
    		tratamentoFacade = new TratamentoFacade();
    	return tratamentoFacade;
    }
}
