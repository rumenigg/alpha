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
	
/*
 *  METODOS DE ATUALIZAÇÃO
 */
	@Command
	@NotifyChange("allLaudos")
	public void atualizaLaudos()
	{
		this.allLaudos = null;
		DaoFactory daof = new DaoFactory();
		daof.beginTransaction();
		
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
 * FUNÇÕES -- TOOLBAR
 */
	@Command
	@NotifyChange({"selectedLaudos","desativado","novo"})
	public void novo(){
		
		this.desativado = false;
				
		this.selectedLaudos=null;
		this.selectedLaudos = new Laudos();		
	}
	
	@Command
	@NotifyChange({"selectedAtivo","desativado","selectedLaudos"})
	public void submit() throws IOException{
		DaoFactory daof = new DaoFactory();
		daof.beginTransaction();
		
		daof.getLaudosDAO().adiciona(this.selectedLaudos);
		
		this.criarDiretorio();

		Messagebox.show("O Laudo " + this.selectedLaudos.getDescricao().toUpperCase() + " foi \nadicionado ou atualizado com sucesso",
				"Hydro - Projeto Alpha", Messagebox.OK, Messagebox.INFORMATION);
		
		this.desativado = true;
		this.selectedAtivo.setCompartimento(null);
	}
	
	@Command
	@NotifyChange({"selectedLaudos","desativado"})
	public void excluir(){
		try
		{
			Messagebox.show("Você realmente deseja excluir o Laudo selecionado?", 
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
								Messagebox.show("Problemas com a conexão com o banco de dados.\nContate o administrador ou desenvolvedor do sistema",
									"Hydro - Projeto Alpha", Messagebox.OK, Messagebox.ERROR);
								e.printStackTrace();									
							}
							
							selectedLaudos = null;						
							
							novo();																
							navegar("proximo");							
						}												
					}			
				});
		}
		catch (NullPointerException n)
		{
			Messagebox.show("Selecione um Laudo para a exclusão!", "Hydro - Projeto Alpha", 
					Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}
	
	@Command
	@NotifyChange({"selectedLaudos","selectedAtivo","selectedCompartimento"})
	public void navegar(@BindingParam("acao") String acao) throws IOException
	{
		this.selectedLaudos = null;
		this.desativado=false;
		
		if ( !this.allLaudos.isEmpty() )
		{
			if ( acao.equals("primeiro") )
			{
				this.navegador = 0;
				this.selectedLaudos = this.allLaudos.get(this.navegador);
				
			}
			
			if( acao.equals("anterior") ){
				if ( this.navegador > 0 ){
					this.selectedLaudos = this.allLaudos.get(--this.navegador);
					
				}
				else 
					this.navegar("primeiro");
			}
		
			if ( acao.equals("proximo")){
				if ( this.navegador < this.allLaudos.size()-1 ){
					this.selectedLaudos = this.allLaudos.get(++this.navegador);
				}
				else
					this.navegar("ultimo");
			}
		
			if ( acao.equals("ultimo") ){
				this.selectedLaudos = this.allLaudos.get(this.navegador = this.allLaudos.size()-1);
			}
		}
		else
		{
			if (this.selectedLaudos == null)
				this.selectedLaudos = new Laudos();
			else
			{
				
				//this.selectedLaudos.setDescricao("");
				//this.selectedLaudos.setObs("");
				
			}
		}
	}
	
/*
 *  ENVIA O ARQUIVO DE LAUDOS
*/
	
	@Listen("onUpload=#btnArquivo")
	public void addArquivo(UploadEvent evt){
		
		media = evt.getMedia();
	}
	@Listen("onClick=#btnDownload")
	public void baixar(UploadEvent evt)  {
		media = evt.getMedia();
		
	}
	public void criarDiretorio() throws IOException {
		if(this.media!=null){	
			BufferedInputStream in = null;
	        BufferedOutputStream out = null;
			String path = "rti/alpha";
			path +="/hydro/laudos/";
						
			InputStream fin = media.getStreamData();
            in = new BufferedInputStream(fin);
            File Diretorio = new File(path);
            Diretorio.mkdirs();
             
            File file = new File(path + this.selectedLaudos.getId()+"_"+media.getName());
 
            OutputStream fout = new FileOutputStream(file);
            out = new BufferedOutputStream(fout);
            
            byte buffer[] = new byte[1024];
            int ch = in.read(buffer);
            while (ch != -1) {
                out.write(buffer, 0, ch);
                ch = in.read(buffer);
            }
            fout.close();
           // Messagebox.show("Arquivo carregado com sucesso :" + this.selectedLaudos.getId()+"_"+media.getName());
            this.selectedLaudos.setArquivo(file.getCanonicalPath());
            
        	}else{
        		//System.out.println("o arquivo nao foi caregado");
		}
	}
}

