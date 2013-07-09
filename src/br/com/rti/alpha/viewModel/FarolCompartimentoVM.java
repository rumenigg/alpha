package br.com.rti.alpha.viewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.zkoss.bind.Converter;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.NotifyChange;

import br.com.rti.alpha.controle.AImageConverter;
import br.com.rti.alpha.controle.FarolImageConverter;
import br.com.rti.alpha.controle.Ordenar;
import br.com.rti.alpha.controle.ToolTipTextConverter;
import br.com.rti.alpha.dao.DaoFactory;
import br.com.rti.alpha.modelo.ativo.Ativo;
import br.com.rti.alpha.modelo.ativo.Compartimento;

public class FarolCompartimentoVM 
{
	private int idAtivo;
	private Ativo selectedAtivo;
	private List<Compartimento> allCompartimento;
	
	private Converter converter = new AImageConverter();
	private Converter farolConverter = new FarolImageConverter();
	private Converter toolTipTextConverter = new ToolTipTextConverter();
	public Ativo getSelectedAtivo() {
		return selectedAtivo;
	}
	public void setSelectedAtivo(Ativo selectedAtivo) {
		this.selectedAtivo = selectedAtivo;
	}
	public List<Compartimento> getAllCompartimento() {
		return allCompartimento;
	}
	public void setAllCompartimento(List<Compartimento> allCompartimento) {
		this.allCompartimento = allCompartimento;
	}
	public Converter getConverter() {
		return converter;
	}
	public void setConverter(Converter converter) {
		this.converter = converter;
	}
	public Converter getFarolConverter() {
		return farolConverter;
	}
	public void setFarolConverter(Converter farolConverter) {
		this.farolConverter = farolConverter;
	}
	public Converter getToolTipTextConverter() {
		return toolTipTextConverter;
	}
	public void setToolTipTextConverter(Converter toolTipTextConverter) {
		this.toolTipTextConverter = toolTipTextConverter;
	}

	@NotifyChange({"allCompartimento", "selectedAtivo"})
	public void atualizaAllCompartimento()
	{
		DaoFactory daof = new DaoFactory();
		daof.beginTransaction();
		
		this.selectedAtivo = daof.getAtivoDAO().procura(idAtivo);
		
		this.allCompartimento = new ArrayList<Compartimento>();
		
		this.allCompartimento.addAll(this.selectedAtivo.getCompartimento());
		Collections.sort(this.allCompartimento, new Ordenar());		
	}

	@AfterCompose
	public void afterCompose(@ExecutionArgParam("ativo") int id)
	{
		this.idAtivo = id;
		
		this.atualizaAllCompartimento();
	}
}
