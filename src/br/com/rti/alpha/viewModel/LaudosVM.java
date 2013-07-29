package br.com.rti.alpha.viewModel;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.xml.crypto.Data;

import net.sf.jasperreports.engine.export.data.DateTextValue;

import org.jfree.ui.action.DowngradeActionMap;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.Converter;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Calendar;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Toolbarbutton;

import com.lowagie.text.pdf.AcroFields.Item;

import br.com.rti.alpha.controle.ArquivoConverter;
import br.com.rti.alpha.controle.CompartimentoAtivoListGroupRenderer;
import br.com.rti.alpha.controle.Ordenar;
import br.com.rti.alpha.dao.DaoFactory;
import br.com.rti.alpha.modelo.amostra.Amostra;
import br.com.rti.alpha.modelo.amostra.Laudos;
import br.com.rti.alpha.modelo.ativo.Ativo;
import br.com.rti.alpha.modelo.ativo.Compartimento;

public class LaudosVM {
	
	@Wire
	private Toolbarbutton tbtnNovoLaudo;
	@Wire
	private Combobox cbxAtivo;
	@Wire
	private Listbox lbCompartimentoList;
	@Wire
	private Combobox cbxCompartimento;
	@Wire
	private Listbox lbdescricao;
	@Wire
	private Listbox lbvistoriado;
	@Wire
	private Listbox lbobs;
	@Wire
	private Listbox lbArquivo;
	
	public Listbox getLbArquivo() {
		return lbArquivo;
	}
	public void setLbArquivo(Listbox lbArquivo) {
		this.lbArquivo = lbArquivo;
	}
	private Media media;
	public Media getMedia() {
		return media;
	}
	public void setMedia(Media media) {
		this.media = media;
	}
	private int navegador = 0;
	
	private Laudos selectedLaudos;
	private List<Laudos> allLaudos;
	
	private Ativo selectedAtivo;
	private List<Ativo> allAtivo;
	
	private Compartimento selectedCompartimento;
	private List<Compartimento> allCompartimento = new ArrayList<Compartimento>();
		
	private boolean desativado = true;	
	private boolean readOnly = false;

	private Converter arquivoConverter = new ArquivoConverter();
	
	private Object objeto;
	
	public Listbox getLbdescricao() {
		return lbdescricao;
	}
	public void setLbdescricao(Listbox lbdescricao) {
		this.lbdescricao = lbdescricao;
	}
	public Listbox getLbvistoriado() {
		return lbvistoriado;
	}
	public void setLbvistoriado(Listbox lbvistoriado) {
		this.lbvistoriado = lbvistoriado;
	}
	public Listbox getLbobs() {
		return lbobs;
	}
	public void setLbobs(Listbox lbobs) {
		this.lbobs = lbobs;
	}

	public Combobox getCbxAtivo() {
		return cbxAtivo;
	}
	public void setCbxAtivo(Combobox cbxAtivo) {
		this.cbxAtivo = cbxAtivo;
	}
	public Listbox getLbCompartimentoList() {
		return lbCompartimentoList;
	}
	public void setLbCompartimentoList(Listbox lbCompartimentoList) {
		this.lbCompartimentoList = lbCompartimentoList;
	}
	public Combobox getCbxCompartimento() {
		return cbxCompartimento;
	}
	public void setCbxCompartimento(Combobox cbxCompartimento) {
		this.cbxCompartimento = cbxCompartimento;
	}
	public Laudos getSelectedLaudos() {
		return selectedLaudos;
	}
	public void setSelectedLaudos(Laudos selectedLaudos) {
		this.selectedLaudos = selectedLaudos;
	}
	public List<Laudos> getAllLaudos() {
		return allLaudos;
	}
	public void setAllLaudos(List<Laudos> allLaudos) {
		this.allLaudos = allLaudos;
	}
	public Ativo getSelectedAtivo() {
		return selectedAtivo;
	}
	public void setSelectedAtivo(Ativo selectedAtivo) {
		this.selectedAtivo = selectedAtivo;
	}
	public List<Ativo> getAllAtivo() {
		return allAtivo;
	}
	public void setAllAtivo(List<Ativo> allAtivo) {
		this.allAtivo = allAtivo;
	}
	public Compartimento getSelectedCompartimento() {
		return selectedCompartimento;
	}
	public void setSelectedCompartimento(Compartimento selectedCompartimento) {
		this.selectedCompartimento = selectedCompartimento;
	}
	public List<Compartimento> getAllCompartimento() {
		return allCompartimento;
	}
	public void setAllCompartimento(List<Compartimento> allCompartimento) {
		this.allCompartimento = allCompartimento;
	}
	public boolean isDesativado() {
		return desativado;
	}
	public void setDesativado(boolean desativado) {
		this.desativado = desativado;
	}	
	public boolean isReadOnly() {
		return readOnly;
	}
	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}
	public Converter getArquivoConverter() {
		return arquivoConverter;
	}
	public void setArquivoConverter(Converter arquivoConverter) {
		this.arquivoConverter = arquivoConverter;
	}
	/*
 *  METODOS DE ATUALIZA��O
 */
	@Command
	@NotifyChange("allLaudos")
	public void atualizaLaudos()
	{
		this.allLaudos = null;
		DaoFactory daof = new DaoFactory();
		daof.beginTransaction();
		
		if ( this.objeto != null )
		{
			if ( this.objeto instanceof Ativo )
			{
				Ativo ativo = (Ativo) objeto;
				ativo = daof.getAtivoDAO().procura(ativo.getId());
				this.allLaudos = ativo.getLaudos();
			}
			if ( this.objeto instanceof Compartimento )
			{
				Compartimento compartimento = (Compartimento) objeto;
				compartimento = daof.getCompartimentoDAO().procura(compartimento.getId());
				this.allLaudos = compartimento.getLaudos();
			}
		}
		else
			this.allLaudos = daof.getLaudosDAO().listaTudo();
		
		Ordenar o = new Ordenar();
		o.setDescending(true);
		Collections.sort(this.allLaudos, o);
		
		o = null;
		daof = null;
	}
	@Command
	@NotifyChange("allAtivo")
	public void atualizaAtivo()
	{
		DaoFactory daof = new DaoFactory();
		daof.beginTransaction();
		this.allAtivo = daof.getAtivoDAO().listaTudo();
		
		//Ordenar o = new Ordenar();
		Collections.sort(this.allAtivo, new Ordenar());
		//o = null;
		daof = null;
	}
	
	@Command
	@NotifyChange("allCompartimento")
	public void atualizaCompartimento()
	{
		DaoFactory daof = new DaoFactory();
		daof.beginTransaction();
							
		this.selectedAtivo = daof.getAtivoDAO().procura(this.selectedLaudos.getAtivoLaudos().getId());
		
		this.allCompartimento.clear();
		this.allCompartimento.addAll(this.selectedAtivo.getCompartimento());
				
		Collections.sort(this.allCompartimento, new Ordenar());		
	}
	
/*
 * INIT 
 */
	@Init
	public void init(){
		this.selectedLaudos = new Laudos();
		this.selectedAtivo = new Ativo();
		this.selectedCompartimento = new Compartimento();
		this.atualizaAtivo();
		this.atualizaLaudos();

	}
	
	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view)
	{
		Selectors.wireComponents(view, this, false);
		Selectors.wireEventListeners(view, this);			
		Clients.showNotification("Clique aqui para adicionar um novo Laudo", "info", this.tbtnNovoLaudo, "end_center", 3000);
		//init();
	}
	
/*
 * FUN��ES -- TOOLBAR
 */
	@Command
	@NotifyChange({"selectedLaudos","selectedAtivo", "selectedCompartimento", "desativado"})
	public void novo(){
		
		this.desativado = false;
				
		this.selectedLaudos =null;
		this.selectedLaudos = new Laudos();	
		
		this.selectedAtivo = null;
		this.selectedAtivo = new Ativo();
		
		this.selectedCompartimento = null;
		this.selectedCompartimento = new Compartimento();		
	}
	
	@Command
	@NotifyChange({"selectedAtivo","desativado","selectedLaudos"})
	public void submit() throws IOException{
		DaoFactory daof = new DaoFactory();
		daof.beginTransaction();
		
		if ( this.readOnly )
		{
			daof.getLaudosDAO().adiciona(this.selectedLaudos);
			
			Messagebox.show("Laudo vistoriado com sucesso",	"Portal Hydro", Messagebox.OK, Messagebox.INFORMATION);
			
			this.atualizaLaudos();
			
			//M�todos usados para atualizar as janelas de far�is anteriores, Supervis�o, Frota, Ativo e Compartimento
			BindUtils.postGlobalCommand(null, null, "atualizaAllSupervisao", null);
			BindUtils.postGlobalCommand(null, null, "atualizaAllFrota", null);	
			BindUtils.postGlobalCommand(null, null, "atualizaAllAtivo", null);
			BindUtils.postGlobalCommand(null, null, "atualizaAllCompartimento", null);			
			BindUtils.postGlobalCommand(null, null, "atualizaFarolAllLaudos", null);
			
			this.selectedLaudos = null;
			this.selectedAtivo = null;
			this.selectedCompartimento = null;
		}
		else
		{
			this.selectedLaudos.setVistoriado("n");
		
			daof.getLaudosDAO().adiciona(this.selectedLaudos);
		
			this.criarDiretorio();

			Messagebox.show("O Laudo " + this.selectedLaudos.getDescricao().toUpperCase() + " foi \nadicionado ou atualizado com sucesso",
					"Portal Hydro", Messagebox.OK, Messagebox.INFORMATION);
		
			this.desativado = true;
			this.selectedLaudos = null;
			this.selectedAtivo = null;
			this.selectedCompartimento = null;		
			//this.selectedAtivo.setCompartimento(null);
		}
	}
	
	@Command
	@NotifyChange({"selectedLaudos"})
	public void excluir(){
		try
		{
			Messagebox.show("Voc� realmente deseja excluir o Laudo selecionado?", 
					"Hydro - Projeto Alpha", Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, 
				new EventListener<Event>() {
					public void onEvent(Event event) throws SQLException, IOException
					{
						if (Messagebox.ON_YES.equals(event.getName()))
						{
							try
							{
								DaoFactory daof = new DaoFactory();
								daof.beginTransaction();
								daof.getLaudosDAO().remove(selectedLaudos);

								Messagebox.show("Laudo excluido com sucesso.", "Hydro - Projeto Alpha", Messagebox.OK, Messagebox.INFORMATION);																
							}
							catch (Exception e)
							{
								Messagebox.show("Problemas com a conex�o com o banco de dados.\nContate o administrador ou desenvolvedor do sistema",
									"Portal Hydro", Messagebox.OK, Messagebox.ERROR);
								e.printStackTrace();									
							}
							
							selectedLaudos = null;						
							BindUtils.postNotifyChange(null, null, this, "selectedLaudos");
							
							novo();																
							navegar("proximo");							
						}												
					}			
				});
		}
		catch (NullPointerException n)
		{
			Messagebox.show("Selecione um Laudo para a exclus�o!", "Hydro - Projeto Alpha", 
					Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}
	
	@Command
	@NotifyChange({"selectedLaudos","desativado"})
	public void navegar(@BindingParam("acao") String acao) throws IOException
	{
		this.selectedLaudos = null;
		this.desativado=false;
		
		if ( !this.allLaudos.isEmpty() )
		{
			if ( acao.equals("primeiro") )
			{
				this.navegador = 0;
				this.showLaudo( this.navegador );
			}
			
			if( acao.equals("anterior") )
			{
				if ( this.navegador > 0 )
				{
					this.showLaudo( --this.navegador );					
				}
				else 
					this.navegar("primeiro");
			}
		
			if ( acao.equals("proximo")){
				if ( this.navegador < this.allLaudos.size()-1 )
				{
					this.showLaudo( ++this.navegador );				
				}
				else
					this.navegar("ultimo");
			}
		
			if ( acao.equals("ultimo") )
			{
				this.showLaudo( this.navegador = this.allLaudos.size()-1 );				
			}
			
			BindUtils.postNotifyChange(null, null, this, "selectedLaudos");
			BindUtils.postNotifyChange(null, null, this, "selectedCompartimento");
		}
	}
	
	@GlobalCommand
	@NotifyChange({"desativado","selectedLaudos"})
	public void showSelectedLaudo(@BindingParam("selectedLaudo") Laudos laudo, @BindingParam("readOnly") boolean readOnly) throws IOException
	{		
		this.desativado = false;
		this.readOnly = readOnly;
		
		this.selectedLaudos = laudo;
		
		for ( Laudos l : this.allLaudos )			
			if ( l.getId() == this.selectedLaudos.getId() )
				this.showLaudo( this.navegador = this.allLaudos.indexOf(l) );
		
	}
	
	@GlobalCommand
	@NotifyChange({"desativado", "readOnly"})
	public void showLaudosObjetos(@BindingParam("selectedLaudo") Object obj, @BindingParam("readOnly") boolean readOnly) throws IOException
	{		
		this.desativado = false;
		this.readOnly = readOnly;
		
		this.objeto = obj;
		
		this.atualizaLaudos();
		//this.atualizaAmostraCompartimento(id);
		//BindUtils.postNotifyChange(null, null, this, "allAmostra");
		
		this.navegar("primeiro");		
	}
	
	
	@NotifyChange("selectedLaudos")
	public void showLaudo( int i)
	{
		this.selectedLaudos = this.allLaudos.get(i);
		
		DaoFactory daof = new DaoFactory();
		daof.beginTransaction();
		
		this.selectedLaudos = daof.getLaudosDAO().procura(this.selectedLaudos.getId());
		
		this.selectAtivo(this.selectedLaudos.getAtivoLaudos());
		this.atualizaCompartimento();
	}
	
	@NotifyChange({"selectedAtivo", "allAtivo", "allCompartimento"})
	public void selectAtivo(Ativo ativo)
	{
		this.selectedAtivo = ativo;
		
		for ( Ativo a : this.allAtivo )
		{			
			if ( a.getId() == this.selectedAtivo.getId() )
			{				
				this.cbxCompartimento.setSelectedItem(null);
				this.cbxAtivo.setSelectedIndex( this.allAtivo.indexOf( a ) );
				this.cbxAtivo.select();	
				
				BindUtils.postNotifyChange(null, null, this, "allCompartimento");
			}
		}			
	}
	
/*
 *  ENVIA O ARQUIVO DE LAUDOS
*/
	
	@Listen("onUpload=#btnArquivo")
	public void addArquivo(UploadEvent evt)
	{		
		media = evt.getMedia();
	}
	
	@Command
	public void baixar() throws FileNotFoundException  
	{
		File file = new File(selectedLaudos.getArquivo());
		
		Filedownload fd = new Filedownload();
		fd.save(file, null);		
	}
	
	public void criarDiretorio() throws IOException 
	{
		if ( this.media!=null )
		{	
			String path = "rti/alpha";
			path +="/hydro/laudos/";
						
			InputStream fin = media.getStreamData();

            File Diretorio = new File(path);
            Diretorio.mkdirs();
             
            File file = new File(path + this.selectedLaudos.getId()+"_"+media.getName());
           
        	OutputStream outputStream = null;
        	 
        	try 
        	{
        		// read this file into InputStream
        		//inputStream = new FileInputStream("/Users/mkyong/Downloads/holder.js");
        	 
        			// write the inputStream to a FileOutputStream
        		outputStream = new FileOutputStream(file);
        	 
        		int read = 0;
        		byte[] bytes = new byte[1024];
        	 
        		while ((read = fin.read(bytes)) != -1) 
        		{
        			outputStream.write(bytes, 0, read);
        		}
        	 
        		System.out.println("Arquivo gravado!");
        	 
        	} 
        	catch (IOException e) 
        	{
        		e.printStackTrace();
        	} 
        	finally 
        	{        		
        		if ( fin != null ) 
        		{
        			try 
        			{
        				fin.close();        			
        			} 
        			catch (IOException e) 
        			{
        				e.printStackTrace();
        			}
        		}
        		if ( outputStream != null ) 
        		{
        			try 
        			{
        				// outputStream.flush();
        				outputStream.close();
        			}
        			catch (IOException e) 
        			{
        				e.printStackTrace();
        			}
        	 
        		}
        	}
        	
        	this.selectedLaudos.setArquivo(file.getCanonicalPath());
		}
	}
}

