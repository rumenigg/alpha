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
import org.zkoss.zul.Image;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Toolbarbutton;

import br.com.rti.alpha.controle.Ordenar;
import br.com.rti.alpha.dao.DaoFactory;
import br.com.rti.alpha.modelo.ativo.Oleo;

public class OleoVM 
{
	@Wire
	private Toolbarbutton tbtnNovoOleo;
	@Wire
	private Image fotoOleo;
	
	private List<Oleo> allOleo;
	private Oleo selectedOleo;
	
	private boolean desativado = true;
	
	private int navegador = 0;
	
	private Media media;

	public List<Oleo> getAllOleo() {
		return allOleo;
	}

	public void setAllOleo(List<Oleo> allOleo) {
		this.allOleo = allOleo;
	}

	public Oleo getSelectedOleo() {
		return selectedOleo;
	}

	public void setSelectedOleo(Oleo selectedOleo) {
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
	@NotifyChange("selectedOleo")
	public void init()
	{
		this.selectedOleo = new Oleo();
		this.atualizaAllOleo();
	}
	
	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view)
	{
		Selectors.wireComponents(view, this, false);
		Selectors.wireEventListeners(view, this);
		
		Clients.showNotification("Clique aqui para adicionar um novo Óleo", "info", this.tbtnNovoOleo, "end_center", 3000);
	}
	
	@GlobalCommand
	@NotifyChange("allOleo")
	public void atualizaAllOleo()
	{
		this.allOleo = null;
		DaoFactory daof = new DaoFactory();
		daof.beginTransaction();
		
		this.allOleo = daof.getOleoDAO().listaTudo();
		
		//Coloca em ordem crescente a lista de óleos usando o objeto Ordenar
		Ordenar o = new Ordenar();
		Collections.sort(this.allOleo, o);
		o = null;
		
		//daof.close();
	}
		
	//Método utilizado para mostrar o óleo selecionado na lista ao lado.
	@GlobalCommand
	@NotifyChange({"selectedOleo","desativado"})
	public void showSelectedOleoItem(@BindingParam("showSelectedOleoItem") int i) throws IOException
	{
		this.desativado = false;
		this.selectedOleo = null;
		this.selectedOleo = this.allOleo.get( this.navegador = i);
				
		if ( (this.selectedOleo.getFoto() != null) && !this.selectedOleo.getFoto().isEmpty() )
		{
			org.zkoss.image.AImage img = new org.zkoss.image.AImage(this.selectedOleo.getFoto());
			this.fotoOleo.setContent(img);
		}
		else
			this.fotoOleo.setSrc(null);
	}

	@Command
	@NotifyChange({"selectedOleo","desativado"})
	public void novo()
	{
		this.desativado = false;
		
		this.selectedOleo = null;
		this.selectedOleo = new Oleo();
	}
	
	@Command
	@NotifyChange({"selectedOleo","desativado"})
	public void submit()
	{
		try
		{
			DaoFactory daof = new DaoFactory();
			daof.beginTransaction();
			
			if ( this.media != null && this.selectedOleo.getFoto() != null )
				this.delFoto(this.selectedOleo.getFoto());
			
			this.salvarFoto();
			
			daof.getOleoDAO().adiciona(this.selectedOleo);
			daof.commit();
				
			Messagebox.show("O Óleo " + this.selectedOleo.getReferencia().toUpperCase() + "\nfoi adicionado ou atualizado com sucesso",
					"Portal Hydro", Messagebox.OK, Messagebox.INFORMATION);		
				
			//Atualiza a lista na aba Compartimento na janela de Cadastros;
			this.atualizaBindComponent("atualizaListas", "atualizaListas", this.selectedOleo);
			//Atualiza a lista no combobox da janela Compartimento
			this.atualizaBindComponent("atualizaOleoListCompartimento", "atualizaOleoListCompartimento", this.selectedOleo);
			this.novo();	
			this.atualizaAllOleo();
			
			this.desativado = true;
		}
		catch (Exception e)
		{
			Messagebox.show("Problemas com a conexão com o banco de dados.\nContate o administrador ou desenvolvedor do sistema.",
					"Portal Hydro", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}		
	}
	
	@Command
	@NotifyChange("selectedOleo")
	public void excluir()
	{
		try
		{
			Messagebox.show("Você realmente deseja excluir o Óleo " + this.selectedOleo.getReferencia().toUpperCase() + "?", "Portal Hydro",
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
									daof.getOleoDAO().remove(selectedOleo);
									
									if ( selectedOleo.getFoto() != null )
										delFoto(selectedOleo.getFoto());
									
									daof.commit();
									Messagebox.show("Óleo excluido com sucesso.", "Portal Hydro", Messagebox.OK, Messagebox.INFORMATION);
								
									//Atualiza a lista na aba Compartimento na janela de Cadastros;
									atualizaBindComponent("atualizaListas", "atualizaListas", selectedOleo);
									//Atualiza a lista no combobox da janela Compartimento
									atualizaBindComponent("atualizaCompartimenoLists", "atualizaCompartimentoLists", selectedOleo);
								}
								catch (ConstraintViolationException cve)
								{
									Messagebox.show("Você não pode excluir esse óleo pois ele está associado a um compartimento. Substitua o óleo, no compartimento, por outro para poder excluir.",
											"Portal Hydro", Messagebox.OK, Messagebox.ERROR);
								}
								catch (Exception e)
								{
									Messagebox.show("Problemas com a conexão com o banco de dados.\nContate o administrador ou desenvolvedor do sistema",
												"Portal Hydro", Messagebox.OK, Messagebox.ERROR);
									e.printStackTrace();									
								}
							
								selectedOleo = null;
								atualizaAllOleo();
																								
								navegar("proximo");
							}												
						}			
					});
		}
		catch (NullPointerException n)
		{
			Messagebox.show("Selecione um Óleo para a exclusão!", "Portal Hydro", 
					Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}
	
	@Command
	@NotifyChange({"selectedOleo","desativado"})
	public void navegar(@BindingParam("acao") String acao) throws IOException
	{
		this.desativado = false;
		
		this.selectedOleo = null;
		
		if ( !this.allOleo.isEmpty() )
		{
			if ( acao.equals("primeiro") )
			{
				this.showSelectedOleoItem( this.navegador = 0 );
			}
			
			if( acao.equals("anterior") )
			{
				if ( this.navegador > 0 )
					this.showSelectedOleoItem( --this.navegador );
				else 
					this.navegar("primeiro");
			}
		
			if ( acao.equals("proximo") )
			{
				if ( this.navegador < this.allOleo.size()-1 )
					this.showSelectedOleoItem( ++this.navegador );
				else
					this.navegar("ultimo");
			}
		
			if ( acao.equals("ultimo") )
				this.showSelectedOleoItem( this.navegador = this.allOleo.size()-1 );
		}		
	}
	
	public void atualizaBindComponent(String bindingParam, String metodo, Object obj)
	{
		Map args = new HashMap();
		args.put(bindingParam, obj);
		BindUtils.postGlobalCommand(null, null, metodo, args);
	}
	
	@Listen("onUpload=#btnOleoFoto")
	public void addFoto(UploadEvent evt)
	{
		this.media = evt.getMedia();
	}
	
	public void salvarFoto() throws IOException
	{
		if ( this.media != null )
		{
			String path = "rti/alpha";//System.getProperty("user.home");
			path += "/hydro/img/imagens/oleos/" + this.selectedOleo.getReferencia()+"_" + this.media.getName();
			
			BufferedImage imagem = ImageIO.read( this.media.getStreamData() );
			File arquivo = new File(path);
			arquivo.mkdirs();
			// fazer algo com a imagem...
			ImageIO.write(imagem, "PNG", arquivo);
			
			this.selectedOleo.setFoto(arquivo.getCanonicalPath());//path);
		}
	}
	
	public void delFoto(String foto) throws IOException
	{
		File arquivo = new File(foto);
		
		arquivo.delete();
	}
}
