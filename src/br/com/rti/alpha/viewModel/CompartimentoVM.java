package br.com.rti.alpha.viewModel;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.hibernate.exception.ConstraintViolationException;
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
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Toolbarbutton;

import br.com.rti.alpha.controle.Ordenar;
import br.com.rti.alpha.dao.DaoFactory;
import br.com.rti.alpha.modelo.ativo.Compartimento;
import br.com.rti.alpha.modelo.ativo.Oleo;
import br.com.rti.alpha.modelo.ativo.TipoCompartimento;
import br.com.rti.alpha.modelo.supervisao.Supervisao;

public class CompartimentoVM 
{
	@Wire
	private Toolbarbutton tbtnNovoCompartimento;
	
	@Wire("#cbxTP")
	private Combobox cbxTP;
	@Wire("#cbxO")
	private Combobox cbxO;
	
	private int navegador = 0;
	
	private List<Compartimento> allCompartimento;
	private Compartimento selectedCompartimento;
	
	private List<TipoCompartimento> allTipoCompartimento;
	private TipoCompartimento selectedTipoCompartimento;
	
	private List<Oleo> allOleo;
	private Oleo selectedOleo;
	
	private boolean desativado = true;
	
	private Media media;
	
	@Wire
	private Image fotoCompartimento;
	
	public List<Compartimento> getAllCompartimento() {
		return allCompartimento;
	}

	public void setAllCompartimento(List<Compartimento> allCompartimento) {
		this.allCompartimento = allCompartimento;
	}

	public Compartimento getSelectedCompartimento() {
		return selectedCompartimento;
	}

	public void setSelectedCompartimento(Compartimento selectedCompartimento) {
		this.selectedCompartimento = selectedCompartimento;
	}

	public List<TipoCompartimento> getAllTipoCompartimento() {
		return allTipoCompartimento;
	}

	public void setAllTipoCompartimento(List<TipoCompartimento> allTipoCompartimento) {
		this.allTipoCompartimento = allTipoCompartimento;
	}

	public TipoCompartimento getSelectedTipoCompartimento() {
		return selectedTipoCompartimento;
	}

	public void setSelectedTipoCompartimento(
			TipoCompartimento selectedTipoCompartimento) {
		this.selectedTipoCompartimento = selectedTipoCompartimento;
	}
	
	public List<Oleo> getAllOleo()
	{
		return this.allOleo;
	}
	
	public void setAllOleo(List<Oleo> allOleo)
	{
		this.allOleo = allOleo;
	}
	
	public Oleo getSelectedOleo()
	{
		return this.selectedOleo;
	}
	
	public void setSelectedOleo(Oleo selectedOleo)
	{
		this.selectedOleo = selectedOleo;
	}
	
	public boolean getDesativado()
	{
		return this.desativado;
	}
	
	public void setDesativado(boolean desativado)
	{
		this.desativado = desativado;
	}

	@Init
	public void init()
	{
		this.selectedCompartimento = new Compartimento();
		this.selectedTipoCompartimento = new TipoCompartimento();
		this.selectedOleo = new Oleo();
		
		this.atualizaAllCompartimento();
		this.atualizaAllTipoCompartimento();	
		this.atualizaAllOleo();
	}
	
	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view)
	{
		Selectors.wireComponents(view, this, false);
		Selectors.wireEventListeners(view, this);
		
		Clients.showNotification("Clique aqui para adicionar um novo Compartimento", "info", this.tbtnNovoCompartimento, "end_center", 3000);
	}
	
	@GlobalCommand
	@NotifyChange("allCompartimento")
	public void atualizaAllCompartimento()
	{
		this.allCompartimento = null;
		DaoFactory daof = new DaoFactory();
		daof.beginTransaction();
		
		this.allCompartimento = daof.getCompartimentoDAO().listaTudo();
		
		//Coloca em ordem crescente a lista de compartimento usando o objeto Ordenar
		Ordenar o = new Ordenar();
		Collections.sort(this.allCompartimento, o);		
		o = null;
		
		//daof.close();
	}
	
	@NotifyChange("allTipoCompartimento")
	public void atualizaAllTipoCompartimento()
	{
		this.allTipoCompartimento = null;
		
		DaoFactory daof = new DaoFactory();
		daof.beginTransaction();
		
		this.allTipoCompartimento = daof.getTipoCompartimentoDAO().listaTudo();

		//Coloca em ordem crescente a lista dos tipos de compartimentos usando o objeto Ordenar
		Ordenar o = new Ordenar();
		Collections.sort(this.allTipoCompartimento, o);
		o = null;
		
		//daof.close();
	}
	
	@NotifyChange("allOleo")
	public void atualizaAllOleo()
	{
		this.allOleo = null;
		
		DaoFactory daof = new DaoFactory();
		daof.beginTransaction();
		
		this.allOleo = daof.getOleoDAO().listaTudo();
		
		//Coloca em ordem crescente a lista de compartimento usando o objeto Ordenar
		Ordenar o = new Ordenar();
		Collections.sort(this.allOleo, o);
		o = null;
		
		Oleo o2 = new Oleo();
		o2.setReferencia("Nenhum");
		
		this.allOleo.add(0, o2);
		
		//daof.close();
	}
	
	@GlobalCommand
	@NotifyChange({"allTipoCompartimento", "allOleo"})
	public void atualizaCompartimentoLists(@BindingParam("atualizaCompartimentoLists") Object o)
	{
		if ( o instanceof TipoCompartimento)
		{
			this.allTipoCompartimento = null;
			this.atualizaAllTipoCompartimento();
		}
		if ( o instanceof Oleo )
		{
			this.allOleo = null;
			this.atualizaAllOleo();
		}
	}
	
	/*@GlobalCommand
	@NotifyChange("allOleo")
	public void atualizaOleoListCompartimento(@BindingParam("atualizaOleoListCompartimento") Oleo oleo)
	{
		this.allOleo = null;
		this.atualizaAllOleo();
	}*/
	
	@Command
	@NotifyChange({"selectedCompartimento","selectedTipoCompartimento","selectedOleo","desativado"})
	public void novo()
	{
		this.selectedCompartimento = null;
		this.selectedCompartimento = new Compartimento();
		
		this.selectedTipoCompartimento = null;
		
		this.selectedOleo = null;
		//this.selectedTipoCompartimento = new TipoCompartimento();
		
		this.desativado = false;
	}
	
	@Command
	@NotifyChange({"selectedCompartimento","selectedTipoCompartimento","selectedOleo","desativado"})
	public void submit()
	{
		try
		{
			if ( this.cbxTP.getSelectedItem().getValue().equals("") )
			{
				Messagebox.show("Você deve selecionar um Tipo de Compartimento para prosseguir!", "Hydro - Projeto Alpha", 
						Messagebox.OK, Messagebox.EXCLAMATION);
			}
			else
			{
				this.selectedCompartimento.setTipoCompartimento(this.selectedTipoCompartimento);
				
				if ( this.selectedOleo != null && !this.selectedOleo.getReferencia().equals("") && !this.selectedOleo.getReferencia().equals("Nenhum") )
					this.selectedCompartimento.setOleo(this.selectedOleo);
				else
					this.selectedCompartimento.setOleo(null);
								
				DaoFactory daof = new DaoFactory();
				daof.beginTransaction();
				
				if ( this.media != null && this.selectedCompartimento.getFoto() != null )
					this.delFoto(this.selectedCompartimento.getFoto());
				
				this.salvarFoto();
				
				daof.getCompartimentoDAO().adiciona(this.selectedCompartimento);
												
				Messagebox.show("O Compatimento " + this.selectedCompartimento.getTag().toUpperCase() + " foi \nadicionado ou atualizado com sucesso",
						"Hydro - Projeto Alpha", Messagebox.OK, Messagebox.INFORMATION);		
				
				//this.selectedCompartimento = null;
				//this.selectedTipoCompartimento = null;
				
				//Atualiza a lista na aba Compartimento na janela de Cadastros;
				this.atualizaBindComponent("atualizaListas", "atualizaListas", this.selectedCompartimento);
				this.atualizaBindComponent("atualizaAtivoListCompartimento", "atualizaAtivoListCompartimento", this.selectedCompartimento);
				
				this.selectedCompartimento = null;
				
				this.novo();	
				this.atualizaAllCompartimento();
				
				this.desativado = true;
			}
		}
		catch (WrongValueException wve)
		{
			//Messagebox.show("Você deve selecionar um Tipo de Compartimento, campo obrigatório.", "Hydro - Projeto Alpha", 
							//Messagebox.OK, Messagebox.EXCLAMATION);
			Clients.showNotification("Tipo de Compartimento deve ser selecionado", "info", this.cbxTP, "end_center", 3000);
			
		}
		catch (NullPointerException npe)
		{
			Clients.showNotification("Tipo de Compartimento deve ser selecionado", "info", this.cbxTP, "end_center", 3000);
		}
		catch (Exception e)
		{
			Messagebox.show("Problemas com a conexão com o banco de dados.\nContate o administrador ou desenvolvedor do sistema.",
					"Hydro - Projeto Alpha", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}
	
	@Command
	@NotifyChange({"selectedCompartimento", "selectedTipoCompartimento"})
	public void excluir()
	{
		try
		{
			Messagebox.show("Você realmente deseja excluir o compartimento " + this.selectedCompartimento.getTag().toUpperCase() + "?", "Hydro - Projeto Alpha",
					Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, 
					new EventListener<Event>() {
						public void onEvent(Event event) throws SQLException, IOException
						{
							if (Messagebox.ON_YES.equals(event.getName()))
							{
								try
								{
									DaoFactory daof = new DaoFactory();
									daof.beginTransaction();
									daof.getCompartimentoDAO().remove(selectedCompartimento);
									
									if ( selectedCompartimento.getFoto() != null )
										delFoto(selectedCompartimento.getFoto());

									Messagebox.show("Compartimento excluido com sucesso.", "Hydro - Projeto Alpha", Messagebox.OK, Messagebox.INFORMATION);
								
									//Atualiza a lista na aba Compartimento na janela de Cadastros;
									atualizaBindComponent("atualizaListas", "atualizaListas", selectedCompartimento);
									atualizaBindComponent("atualizaAtivoListCompartimento", "atualizaAtivoListCompartimento", selectedCompartimento);
								}
								catch (ConstraintViolationException cve)
								{
									Messagebox.show("Você não pode excluir esse compartimento pois ele está associado a um ativo. Substitua ou remova, no ativo, por outro para poder excluir.",
											"Hydro - Projeto Alpha", Messagebox.OK, Messagebox.ERROR);
								}
								catch (Exception e)
								{
									Messagebox.show("Problemas com a conexão com o banco de dados.\nContate o administrador ou desenvolvedor do sistema",
												"Hydro - Projeto Alpha", Messagebox.OK, Messagebox.ERROR);
									e.printStackTrace();									
								}
							
								selectedCompartimento = null;
								selectedTipoCompartimento = null;
								atualizaAllCompartimento();
								novo();																
								navegar("proximo");
							}												
						}			
					});
		}
		catch (NullPointerException n)
		{
			Messagebox.show("Selecione um Compartimento para a exclusão!", "Hydro - Projeto Alpha", 
					Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}
	
	@Command
	@NotifyChange({"selectedCompartimento","selectedTipoCompartimento","selectedOleo","desativado"})
	public void navegar(@BindingParam("acao") String acao) throws IOException
	{
		this.selectedCompartimento = null;
		this.selectedTipoCompartimento = null;
		this.selectedOleo = null;
		
		this.desativado = false;
		
		if ( !this.allCompartimento.isEmpty() )
		{
			if ( acao.equals("primeiro") )
			{	
				this.navegador = 0;
				this.showSelectedCompartimentoItem( this.navegador );
				//this.selectTipoCompartimento(this.selectedCompartimento.getTipoCompartimento());
				//this.selectOleo(this.selectedCompartimento.getOleo());
			}
			
			if( acao.equals("anterior") )
			{
				if ( this.navegador > 0 )
				{
					this.showSelectedCompartimentoItem( --this.navegador );
					//this.selectTipoCompartimento(this.selectedCompartimento.getTipoCompartimento());
					//this.selectOleo(this.selectedCompartimento.getOleo());
				}
				else 
					this.navegar("primeiro");
			}
		
			if ( acao.equals("proximo") )
			{
				if ( this.navegador < this.allCompartimento.size()-1 )
				{
					this.showSelectedCompartimentoItem( ++this.navegador );
					//this.selectTipoCompartimento(this.selectedCompartimento.getTipoCompartimento());
					//this.selectOleo(this.selectedCompartimento.getOleo());
				}
				else
					this.navegar("ultimo");
			}
		
			if ( acao.equals("ultimo") )
			{
				this.showSelectedCompartimentoItem( this.navegador = this.allCompartimento.size()-1 );
				//this.selectTipoCompartimento(this.selectedCompartimento.getTipoCompartimento());
				//this.selectOleo(this.selectedCompartimento.getOleo());
			}
		}
		
	}
	
	@NotifyChange("selectedTipoCompartimento")
	public void selectTipoCompartimento(TipoCompartimento tipoCompartimento)
	{
		this.selectedTipoCompartimento = null;
		this.selectedTipoCompartimento = tipoCompartimento;
		
		if ( this.selectedTipoCompartimento != null  )
		{
			for (int i = 0; i < this.allTipoCompartimento.size(); i++)
			{
				TipoCompartimento tc = this.allTipoCompartimento.get(i);
				if (tc.getId() == this.selectedTipoCompartimento.getId())
					this.cbxTP.setSelectedIndex(i);
			}
		}
	}
	
	@NotifyChange("selectedOleo")
	public void selectOleo(Oleo oleo)
	{
		this.selectedOleo = null;
		this.selectedOleo = oleo;
		
		if ( this.selectedOleo != null )
		{
			for (int i = 0; i < this.allOleo.size(); i++)
			{
				Oleo o = this.allOleo.get(i);
				if (o.getId() == this.selectedOleo.getId())
					this.cbxO.setSelectedIndex(i);
			}
		}
	}
	
	//Método utilizado para atualizar os dados dos comboboxes ou listas em outras janelas
	public void atualizaBindComponent(String bindingParam, String metodo, Object obj)
	{
		Map args = new HashMap();
		args.put(bindingParam, obj);
		BindUtils.postGlobalCommand(null, null, metodo, args);
	}
	
	//Método utilizado para mostrar o compartimento selecionado na lista ao lado.
	@GlobalCommand
	@NotifyChange({"selectedCompartimento","selectedTipoCompartimento","selectedOleo","desativado"})
	public void showSelectedCompartimentoItem(@BindingParam("showItem") int i) throws IOException
	{
		this.desativado = false;
		
		this.selectedCompartimento = null;
		this.selectedCompartimento = this.allCompartimento.get( this.navegador = i );
				
		this.selectedTipoCompartimento = null;
		this.selectedTipoCompartimento = this.selectedCompartimento.getTipoCompartimento();
		this.selectTipoCompartimento(selectedTipoCompartimento);
		
		this.selectedOleo = null;
		this.selectedOleo = this.selectedCompartimento.getOleo();
		this.selectOleo(this.selectedOleo);
		
		if ( (this.selectedCompartimento.getFoto() != null) && !this.selectedCompartimento.getFoto().isEmpty() )
		{
			org.zkoss.image.AImage img = new org.zkoss.image.AImage(this.selectedCompartimento.getFoto());
			this.fotoCompartimento.setContent(img);
		}
		else
			this.fotoCompartimento.setSrc(null);
	}
	
	@Listen("onUpload=#btnCompartFoto")
	public void addFoto(UploadEvent evt)
	{
		this.media = evt.getMedia();
	}
	
	public void salvarFoto() throws IOException
	{
		if ( this.media != null )
		{		
			String path = "rti/alpha";//String path = System.getProperty("user.home"); 
			
			path += "/hydro/img/imagens/compartimentos/" + this.selectedCompartimento.getTag()+"_" + this.media.getName();
			
			BufferedImage imagem = ImageIO.read( this.media.getStreamData() );
			File arquivo = new File(path);
			arquivo.mkdirs();
			// fazer algo com a imagem...
			ImageIO.write(imagem, "PNG", arquivo);

			this.selectedCompartimento.setFoto(arquivo.getCanonicalPath());//path);
		}
	}
	
	public void delFoto(String foto) throws IOException
	{
		File arquivo = new File(foto);
		
		arquivo.delete();
	}
}
