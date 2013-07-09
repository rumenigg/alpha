package br.com.rti.alpha.viewModel;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.imageio.ImageIO;

import org.hibernate.annotations.common.AssertionFailure;
import org.hibernate.exception.ConstraintViolationException;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.Converter;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;

import br.com.rti.alpha.controle.AmostraConverter;
import br.com.rti.alpha.controle.Ordenar;
import br.com.rti.alpha.dao.DaoFactory;
import br.com.rti.alpha.modelo.amostra.Amostra;
import br.com.rti.alpha.modelo.amostra.Analise;
import br.com.rti.alpha.modelo.amostra.Elementos;
import br.com.rti.alpha.modelo.amostra.FotoAmostra;
import br.com.rti.alpha.modelo.amostra.PlanoTrabalho;
import br.com.rti.alpha.modelo.amostra.TipoColeta;
import br.com.rti.alpha.modelo.ativo.Ativo;
import br.com.rti.alpha.modelo.ativo.Compartimento;
import br.com.rti.alpha.modelo.ativo.Oleo;

public class AmostraVM
{
	@Wire
	private Toolbarbutton tbtnNovoAmostra;
	@Wire
	private Tab tabAmostra;
	@Wire
	private Combobox cbxAtivo;
	@Wire
	private Combobox cbxCompartimento;
	@Wire
	private Combobox cbxTipoColeta;
	@Wire
	private Combobox cbxPlano;
	@Wire
	private Combobox cbxOleo;
	@Wire
	private Hlayout amostraFoto;
	
	private int navegador;
	
	private boolean desativado = true;
	
	private List<FotoAmostra> fotosAtuais = new ArrayList<FotoAmostra>();
	private List<FotoAmostra> fotosRemovidas = new ArrayList<FotoAmostra>();
	
	private Amostra selectedAmostra;
	private List<Amostra> allAmostra;
	
	private Ativo selectedAtivo;
	private List<Ativo> allAtivo;
	
	private Compartimento selectedCompartimento;
	private List<Compartimento> allCompartimento = new ArrayList<Compartimento>();
	
	private TipoColeta selectedTipoColeta;
	private List<TipoColeta> allTipoColeta;
	
	private PlanoTrabalho selectedPlano;
	private List<PlanoTrabalho> allPlano;
	
	private Oleo selectedOleo;
	private List<Oleo> allOleo;
	
	private Analise selectedAnalise;
	private List<Analise> allAanalise;
	
	private Elementos selectedElementos;
	private List<Elementos> allElementos;
	
	private Converter amostraConverter = new AmostraConverter();
	
	public boolean isDesativado() {
		return desativado;
	}
	public void setDesativado(boolean desativado) {
		this.desativado = desativado;
	}	
	/*public String getOleoDrenado() 
	{		
		return oleoDrenado = this.selectedAmostra.getOleoDrenado().equals("s") ? "Sim" : "Nao";
	}
	public void setOleoDrenado(String oleoDrenado) 
	{
		this.oleoDrenado = oleoDrenado;
		
		this.selectedAmostra.setOleoDrenado(this.oleoDrenado.equals("Sim") ? "s" : "n");
	}
	public String getFiltroTrocado() {
		return filtroTrocado = this.selectedAmostra.getFiltroTrocado().endsWith("s") ? "Sim" : "Nao";
	}
	public void setFiltroTrocado(String filtroTrocado) {
		this.filtroTrocado = filtroTrocado;
		
		this.selectedAmostra.setFiltroTrocado(this.filtroTrocado.equals("Sim") ? "s" : "n");
	}*/
	public Amostra getSelectedAmostra() {
		return selectedAmostra;
	}
	public void setSelectedAmostra(Amostra selectedAmostra) {
		this.selectedAmostra = selectedAmostra;
	}
	public List<Amostra> getAllAmostra() {
		return allAmostra;
	}
	public void setAllAmostra(List<Amostra> allAmostra) {
		this.allAmostra = allAmostra;
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
	public TipoColeta getSelectedTipoColeta() {
		return selectedTipoColeta;
	}
	public void setSelectedTipoColeta(TipoColeta selectedTipoColeta) {
		this.selectedTipoColeta = selectedTipoColeta;
	}
	public List<TipoColeta> getAllTipoColeta() {
		return allTipoColeta;
	}
	public void setAllTipoColeta(List<TipoColeta> allTipoColeta) {
		this.allTipoColeta = allTipoColeta;
	}
	public PlanoTrabalho getSelectedPlano() {
		return selectedPlano;
	}
	public void setSelectedPlano(PlanoTrabalho selectedPlano) {
		this.selectedPlano = selectedPlano;
	}
	public List<PlanoTrabalho> getAllPlano() {
		return allPlano;
	}
	public void setAllPlano(List<PlanoTrabalho> allPlano) {
		this.allPlano = allPlano;
	}
	public Oleo getSelectedOleo() {
		return selectedOleo;
	}
	public void setSelectedOleo(Oleo selectedOleo) {
		this.selectedOleo = selectedOleo;
	}
	public List<Oleo> getAllOleo() {
		return allOleo;
	}
	public void setAllOleo(List<Oleo> allOleo) {
		this.allOleo = allOleo;
	}	
	public Analise getSelectedAnalise() {
		return selectedAnalise;
	}
	public void setSelectedAnalise(Analise selectedAnalise) {
		this.selectedAnalise = selectedAnalise;
	}
	public List<Analise> getAllAanalise() {
		return allAanalise;
	}
	public void setAllAanalise(List<Analise> allAanalise) {
		this.allAanalise = allAanalise;
	}
	public Elementos getSelectedElementos() {
		return selectedElementos;
	}
	public void setSelectedElementos(Elementos selectedElementos) {
		this.selectedElementos = selectedElementos;
	}
	public List<Elementos> getAllElementos() {
		return allElementos;
	}
	public void setAllElementos(List<Elementos> allElementos) {
		this.allElementos = allElementos;
	}		
	public Converter getAmostraConverter() {
		return amostraConverter;
	}
	public void setAmostraConverter(Converter amostraConverter) {
		this.amostraConverter = amostraConverter;
	}
	
	
	
	@Command
	@NotifyChange("allAmostra")
	public void atualizaAmostra()
	{
		this.allAmostra = null;
		DaoFactory daof = new DaoFactory();
		daof.beginTransaction();
		
		this.allAmostra = daof.getAmostraDAO().listaTudo();
		
		Ordenar o = new Ordenar();
		o.setDescending(true);
		Collections.sort(this.allAmostra, o);
		
		o = null;
		daof = null;
	}
	
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
							
		this.selectedAtivo = daof.getAtivoDAO().procura(this.selectedAmostra.getAtivoAmostra().getId()); 
			
		this.allCompartimento.clear();
		this.allCompartimento.addAll(this.selectedAtivo.getCompartimento());
				
		Collections.sort(this.allCompartimento, new Ordenar());			

		//}
	}
	
	@NotifyChange("allTipoColeta")
	public void atualizaTipoColeta()
	{
		DaoFactory daof = new DaoFactory();
		daof.beginTransaction();
		this.allTipoColeta = daof.getTipoColetaDAO().listaTudo();
		
		Collections.sort(this.allTipoColeta, new Ordenar());
		
	}
	
	@Command
	@NotifyChange("allPlano")
	public void atualizaPlano()
	{
		DaoFactory daof = new DaoFactory();
		daof.beginTransaction();
		
		this.selectedTipoColeta = daof.getTipoColetaDAO().procura(this.selectedAmostra.getTipoColetaAmostra().getId());
		
		this.allPlano = this.selectedTipoColeta.getPlanoTrabalho();
		
		Collections.sort(this.allPlano, new Ordenar());
	}
	
	@NotifyChange("allOleo")
	public void atualizaOleo()
	{
		DaoFactory daof = new DaoFactory();
		daof.beginTransaction();
		this.allOleo = daof.getOleoDAO().listaTudo();
		
		Collections.sort(this.allOleo, new Ordenar());
	}
		
	@Init
	public void init()
	{
		this.selectedAmostra = new Amostra();
		
		this.selectedAnalise = new Analise();
		this.selectedElementos = new Elementos();
		
		this.atualizaAmostra();
		this.atualizaAtivo();
		//this.atualizaCompartimento();
		this.atualizaTipoColeta();
		//this.atualizaPlano();
		this.atualizaOleo();		
	}
	
	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view)
	{
		Selectors.wireComponents(view, this, false);
		//Selectors.wireEventListeners(view, this);
				
		Clients.showNotification("Clique aqui para adicionar um novo Ativo", "info", this.tbtnNovoAmostra, "end_center", 3000);
		//init();
	}
	
	@Command
	@NotifyChange({"desativado", "selectedAmostra", "selectedAtivo", "selectedCompartimento", "selectedTipoColeta", "selectedPlano", "selectedOleo","selectedAnalise"})
	public void novo()
	{
		this.desativado = false;
		this.selectedAmostra = null;
		
		this.selectedAmostra = new Amostra();
		this.selectedAmostra.setAtivoAmostra(null);
		
		this.selectedAnalise = new Analise();
		//this.selectedElementos = new Elementos();		
		
		this.limparFotos();
	}
	
	@Command
	@NotifyChange({"desativado", "selectedAmostra","allAmostra","selectedAnalise","selectedElementos"})
	public void submit()
	{
		try
		{
			DaoFactory daof = new DaoFactory();
			daof.beginTransaction();
			
			if ( !this.fotosRemovidas.isEmpty() )
			{
				for ( FotoAmostra fa : this.fotosRemovidas )
					daof.getFotoAmostraDAO().remove(fa);
			}					
			
			this.selectedAmostra.setAnalise(this.selectedAnalise);
			
			if ( this.selectedAnalise != null && this.selectedElementos != null)
				if ( this.selectedAmostra.getSituacao() != null )
				{
					if ( !this.selectedAmostra.getSituacao().equals("critico") )
						this.selectedAmostra.setSituacao(this.verificarAnalise());
				}
				else
					this.selectedAmostra.setSituacao(this.verificarAnalise());
			
			daof.getAmostraDAO().adiciona(this.selectedAmostra);
			
			this.selectedAnalise.setAmostraAnalise(this.selectedAmostra);	
			
			daof.getElementosDAO().adiciona(this.selectedElementos);
			
			this.selectedAnalise.setElementosAnalise(this.selectedElementos);
			
			daof.getAnaliseDAO().adiciona(this.selectedAnalise);			
			
			this.salvarFoto();
			
			daof.commit();			
			daof = null;
			
			Messagebox.show("A amostra foi adicionado ou atualizado com sucesso.",
					"Portal Hydro", Messagebox.OK, Messagebox.INFORMATION);		
						
			this.atualizaAmostra();	
			BindUtils.postNotifyChange(null, null, this, "allAmostra");
			//this.novo();
			this.selectedAmostra = null;
			this.selectedAnalise = null;
			this.selectedElementos = null;
			this.desativado = true;
			this.limparFotos();
		}
		catch (ConstraintViolationException e)
		{
			Messagebox.show("Por favor, verifique as informações fornecidas. Os campos (Ativo, Compartimento, Tipos de Coleta, Plano e Óleo), são obrigatórios.",
					"Portal Hydro", Messagebox.OK, Messagebox.EXCLAMATION);
			e.printStackTrace();
		}
		catch (AssertionFailure e)
		{
			System.out.println("teste...");
		}
		catch (NullPointerException npe)
		{
			Messagebox.show("Por favor, verifique as informações fornecidas. Os campos (Ativo, Compartimento, Tipos de Coleta, Plano e Óleo), são obrigatórios.",
					"Portal Hydro", Messagebox.OK, Messagebox.EXCLAMATION);
			npe.printStackTrace();
		}
		catch (Exception e)
		{
			//e.printStackTrace();
		}		
	}
	
	@Command
	@NotifyChange("selectedAmostra")
	public void excluir()
	{
		try
		{
			Messagebox.show("Você realmente deseja excluir a amostra selecionada?", 
					"Portal Hydro", Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, 
				new EventListener<Event>() {
					public void onEvent(Event event) throws SQLException, IOException
					{
						if (Messagebox.ON_YES.equals(event.getName()))
						{
							try
							{
								DaoFactory daof = new DaoFactory();
								daof.beginTransaction();
								daof.getAmostraDAO().remove(selectedAmostra);

								Messagebox.show("Amostra excluida com sucesso.", "Portal Hydro", Messagebox.OK, Messagebox.INFORMATION);																
							}
							catch (Exception e)
							{
								Messagebox.show("Problemas com a conexão com o banco de dados.\nContate o administrador ou desenvolvedor do sistema",
									"Portal Hydro", Messagebox.OK, Messagebox.ERROR);
								e.printStackTrace();									
							}
							
							selectedAmostra = null;
							
							limparFotos();							
							novo();																
							navegar("proximo");							
						}												
					}			
				});
		}
		catch (NullPointerException n)
		{
			Messagebox.show("Selecione um Tipo de Coleta para a exclusão!", "Portal Hydro", 
					Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}
	
	@Command
	@NotifyChange({"selectedAmostra","desativado"})
	public void navegar(@BindingParam("acao") String acao) throws IOException
	{
		this.selectedAmostra = null;
				
		this.desativado = false;
		
		if ( !this.allAmostra.isEmpty() )
		{
			if ( acao.equals("primeiro") )
			{
				this.navegador = 0;
				this.showAmostra( this.navegador );
			}
			
			if( acao.equals("anterior") )
			{
				if ( this.navegador > 0 )
				{
					this.showAmostra( --this.navegador );
				}
				else 
					this.navegar("primeiro");
			}
		
			if ( acao.equals("proximo") )
			{
				if ( this.navegador < this.allAmostra.size()-1 )
				{
					this.showAmostra( ++this.navegador );
				}
				else
					this.navegar("ultimo");
			}
		
			if ( acao.equals("ultimo") )
			{	
				this.showAmostra( this.navegador = this.allAmostra.size()-1 );
			}
		}		
	}
	
	@Command
	@NotifyChange({"desativado","selectedAmostra"})
	public void showSelectedAmostra() throws IOException
	{		
		this.desativado = false;
		for ( Amostra a : this.allAmostra )
		{
			if ( a.getId() == this.selectedAmostra.getId() )
			{
				this.showAmostra( this.navegador = this.allAmostra.indexOf(a) );
			}
		}			
	}
	
	@NotifyChange({"selectedAmostra","selectedAnalise"})
	public void showAmostra( int i ) throws IOException
	{
		this.tabAmostra.setSelected(true);
		this.desativado = false;	
		
		this.selectedAmostra = this.allAmostra.get( i );
		
		DaoFactory daof = new DaoFactory();
		daof.beginTransaction();
		
		this.selectedAmostra = daof.getAmostraDAO().procura(this.selectedAmostra.getId());
				
		this.selectAtivo( this.selectedAmostra.getAtivoAmostra() );
		this.atualizaCompartimento();
				
		this.selectTipoColeta( this.selectedAmostra.getTipoColetaAmostra() );
		this.atualizaPlano();
		
		this.selectOleo( this.selectedAmostra.getOleoAmostra() );
				
		if ( this.selectedAmostra.getAnalise() != null )
		{
			this.selectedAnalise = this.selectedAmostra.getAnalise();
			if ( this.selectedAnalise.getElementosAnalise() != null )
				this.selectedElementos = this.selectedAnalise.getElementosAnalise();
			else
				this.selectedElementos = new Elementos();
		}
		else
		{
			this.selectedAnalise = new Analise();
			this.selectedElementos = new Elementos();
		}
		
		BindUtils.postNotifyChange(null, null, this, "selectedAnalise");
		BindUtils.postNotifyChange(null, null, this, "selectedElementos");
		
		this.showFotos();		
	}
	
	@NotifyChange({"selectedAtivo","allAtivo","allCompartimento"})
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
			}
		}	
		BindUtils.postNotifyChange(null, null, this, "selectedAmostra");
		BindUtils.postNotifyChange(null, null, this, "allCompartimento");		
	}
	
	@NotifyChange({"selectedTipoColeta","allTipoColeta","allPlano"})
	public void selectTipoColeta(TipoColeta tipoColeta)
	{
		this.selectedTipoColeta = null;
		this.selectedTipoColeta = tipoColeta;
		
		for ( TipoColeta tc : this.allTipoColeta )
		{
			if ( tc.getId() == this.selectedTipoColeta.getId() )
			{
				this.cbxPlano.setSelectedItem(null);
				this.cbxTipoColeta.setSelectedIndex(this.allTipoColeta.indexOf( tc ) );
				this.cbxTipoColeta.select();
			}
		}
		BindUtils.postNotifyChange(null, null, this, "selectedAmostra");
		BindUtils.postNotifyChange(null, null, this, "allPlano");
	}
	
	@NotifyChange("selectedOleo")
	public void selectOleo(Oleo oleo)
	{
		this.selectedOleo = null;
		this.selectedOleo = oleo;
		
		for ( Oleo o : this.allOleo )
		{
			if ( o.getId() == this.selectedOleo.getId() )
			{
				this.cbxOleo.setSelectedIndex(this.allOleo.indexOf( o ) );
			}
		}
	}
	
	public void limparFotos()
	{
		if ( this.amostraFoto != null )
		{
			for ( int i = 0; i < this.amostraFoto.getChildren().size(); i++ )
			{
				//Component c = this.amostraFoto.getChildren().get(i);
				this.amostraFoto.removeChild(this.amostraFoto.getChildren().get(i));
				this.amostraFoto.getChildren().clear();				
			}
			this.amostraFoto.setHeight("100px");
			this.fotosAtuais.clear();
			this.fotosRemovidas.clear();			
		}
	}
	
	public void showFotos() throws IOException
	{
		this.limparFotos();
		String height = "";
		if ( !this.selectedAmostra.getAmostraFoto().isEmpty() )
		{
			for ( FotoAmostra fa : this.selectedAmostra.getAmostraFoto() )
			{
				org.zkoss.image.AImage img = new org.zkoss.image.AImage(fa.getFoto());
			
				height = String.valueOf(img.getHeight()) + "px";
			
				Textbox tx = new Textbox();
				tx.setValue(fa.getComentario());
				tx.setInplace(true);			
				tx.setMultiline(true);
				tx.setRows(5);
				tx.setId( String.valueOf(fa.getId()) );
				tx.setParent(this.amostraFoto);
			
				Checkbox cb = new Checkbox();				
				cb.setImageContent(img);
				cb.setContext("popupFoto");
				cb.setTooltiptext("Marque a foto para excluí-la");
				cb.setName(img.getName());
				cb.setParent(this.amostraFoto);
			
				this.fotosAtuais.add(fa);
			}
			this.amostraFoto.setHeight(height);
		}
		else
			this.amostraFoto.setHeight("70px");		
	}
	
	public boolean existeFoto(String foto)
	{
		boolean result = false;
		
		if ( !foto.isEmpty() )
		{
			for ( FotoAmostra fa : this.fotosAtuais )
			{
				//Textbox tx = (Textbox) this.amostraFoto.getChildren().get(i);			
				int id = Integer.parseInt(foto);
				
				if ( id == fa.getId() )
				{
					result = true;
					break;
				}
				else 
					result = false;
			}
		}		
		return result;
	}
	
	public void salvarFoto() throws IOException
	{		
		if ( this.amostraFoto != null )
		{				
			List<FotoAmostra> list = new ArrayList<FotoAmostra>();
			
			for (int i = 0; i < this.amostraFoto.getChildren().size(); i++ )
			{				
				if ( this.amostraFoto.getChildren().get(i) instanceof Textbox )
				{
					Textbox tx = (Textbox) this.amostraFoto.getChildren().get(i);
					if ( !this.existeFoto(tx.getId()) )
					{
						Checkbox cbx = (Checkbox) this.amostraFoto.getChildren().get(i+1);
				
						String data = this.selectedAmostra.getDataAmostra().toString().substring(0,10);
				
						String path = "rti/alpha";//System.getProperty("user.home");
						path += "/hydro/img/imagens/amostra/" + this.selectedAmostra.getId()+"_"+data+"_" + cbx.getName();
				
						BufferedImage imagem = ImageIO.read( cbx.getImageContent().getStreamData() );		
				
						File arquivo = new File(path);
						arquivo.mkdirs();
						// fazer algo com a imagem...
						ImageIO.write(imagem, "PNG", arquivo);	
				
						DaoFactory daof = new DaoFactory();
						daof.beginTransaction();
						//this.selectedAmostra = daof.getAmostraDAO().procura(id)
						
						FotoAmostra fa = new FotoAmostra(tx.getValue(), arquivo.getCanonicalPath());//path);
				
						fa.setAmostraFoto(this.selectedAmostra);
						daof.getFotoAmostraDAO().adiciona(fa);
									
						list.add(fa);
					}
					else
					{
						//Atualiza os comentários das fotos
						for ( FotoAmostra a2 : this.fotosAtuais )
						{
							int i2 = Integer.parseInt(tx.getId());
							if ( a2.getId() == i2 )
							{
								if ( !a2.getComentario().equalsIgnoreCase(tx.getValue()) )
								{
									a2.setComentario(tx.getValue());
									DaoFactory daof = new DaoFactory();
									daof.beginTransaction();
									daof.getFotoAmostraDAO().adiciona(a2);
								}
							}
						}
					}
				}				
			}
			if ( !list.isEmpty() )
				this.selectedAmostra.setAmostraFoto(list);
		}		
	}	
	
	@Command
	public void removerFotos()
	{
		for ( int i = 0; i < amostraFoto.getChildren().size(); i++ )
		{								
			if ( amostraFoto.getChildren().get(i) instanceof Checkbox )
			{
				Checkbox c = (Checkbox) amostraFoto.getChildren().get(i);
				if ( c.isChecked() )
				{																	
					amostraFoto.getChildren().get(i).detach();
					
					if ( amostraFoto.getChildren().get(i-1) instanceof Textbox )
					{
						Textbox tx = (Textbox) amostraFoto.getChildren().get(i-1);
						for (FotoAmostra fa : this.fotosAtuais)
						{
							if ( !tx.getId().equals("") )
							{
								int id = Integer.parseInt(tx.getId());
								if (id == fa.getId())
									this.fotosRemovidas.add(fa);
							}
						}
					}
					amostraFoto.getChildren().get(i-1).detach();
					removerFotos();
				}
			}
		}
		if (amostraFoto.getChildren().isEmpty())
			amostraFoto.setHeight("70px");
	}
	
	public String verificarAnalise()
	{
		String situacao = "normal";
		
		if ( this.selectedAnalise.getCombustivel() !=null )
			if ( this.selectedAnalise.getCombustivel().equals("s") )
				situacao = "anormal";
		
		if ( this.selectedAnalise.getOleoescuro() !=null )
			if ( this.selectedAnalise.getOleoescuro().equals("s") )
				situacao = "anormal";
		
		if ( this.selectedAnalise.getImpureza() != null )
			if ( this.selectedAnalise.getImpureza().equals("s") )
				situacao = "anormal";
		
		if ( this.selectedAnalise.getLimalha() != null )
			if ( this.selectedAnalise.getLimalha().equals("s") )
				situacao = "anormal";
		
		if ( this.selectedAnalise.getSilica() != null )
			if ( this.selectedAnalise.getSilica().equals("s") )
				situacao = "anormal";
		
		if ( this.selectedAnalise.getAgua() != null )
			if ( this.selectedAnalise.getAgua().equals("s") )
				situacao = "anormal";
		
		Elementos tendencia = this.selectedAmostra.getCompartimentoAmostra().getTipoCompartimento().getElementos();
		
		if ( this.selectedElementos.getFuligem() > tendencia.getFuligem() || this.selectedElementos.getFuligem() > tendencia.getFuligem() ||
			 this.selectedElementos.getOxidacao() > tendencia.getOxidacao() || this.selectedElementos.getNitracao() > tendencia.getNitracao() ||
			 this.selectedElementos.getSulfatacao() > tendencia.getSulfatacao() || this.selectedElementos.getTbn() > tendencia.getTbn() ||
			 this.selectedElementos.getViscosidade() > tendencia.getViscosidade() || this.selectedElementos.getAgua() > tendencia.getAgua() ||
			 this.selectedElementos.getSt() > tendencia.getSt() || this.selectedElementos.getSul() > tendencia.getSul() ||
			 this.selectedElementos.getFe() > tendencia.getFe() || this.selectedElementos.getCu() > tendencia.getCu() ||
			 this.selectedElementos.getCr() > tendencia.getCr() || this.selectedElementos.getPb() > tendencia.getPb() ||
			 this.selectedElementos.getSn() > tendencia.getSn() || this.selectedElementos.getMo() > tendencia.getMo() ||
			 this.selectedElementos.getNi() > tendencia.getNi() || this.selectedElementos.getNi() > tendencia.getNi() || 
			 this.selectedElementos.getAg() > tendencia.getAg() || this.selectedElementos.getAl() > tendencia.getAl() ||
			 this.selectedElementos.getSi() > tendencia.getSi() || this.selectedElementos.getNa() > tendencia.getNa() ||
			 this.selectedElementos.getK() > tendencia.getK() || this.selectedElementos.getCa() > tendencia.getCa() ||
			 this.selectedElementos.getP() > tendencia.getP() || this.selectedElementos.getZn() > tendencia.getZn() ||
			 this.selectedElementos.getMg() > tendencia.getMg() || this.selectedElementos.getB() > tendencia.getB() ||
			 this.selectedElementos.getBa() > tendencia.getBa() || this.selectedElementos.getDiesel() > tendencia.getDiesel() ||
			 this.selectedElementos.getZddp() > tendencia.getZddp() || this.selectedElementos.getIso4u() > tendencia.getIso4u() ||
			 this.selectedElementos.getIso6u() > tendencia.getIso6u() || this.selectedElementos.getIso14u() > tendencia.getIso14u() ||
			 this.selectedElementos.getNorma4u() > tendencia.getNorma4u() || this.selectedElementos.getNorma6u() > tendencia.getNorma6u() ||
			 this.selectedElementos.getNorma14u() > tendencia.getNorma14u() || this.selectedElementos.getFiltro().equals("anormal") ||
			 !this.selectedElementos.getW().equals(tendencia.getW()) || !this.selectedElementos.getF().equals(tendencia.getF()) )
		{
			situacao = "anormal";
		}
					
		return situacao;
	}
}