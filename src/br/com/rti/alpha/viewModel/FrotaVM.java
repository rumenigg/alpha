package br.com.rti.alpha.viewModel;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Toolbarbutton;

import br.com.rti.alpha.controle.Ordenar;
import br.com.rti.alpha.dao.DaoFactory;
import br.com.rti.alpha.modelo.ativo.Ativo;
import br.com.rti.alpha.modelo.pessoa.Pessoa;
import br.com.rti.alpha.modelo.supervisao.Frota;

public class FrotaVM 
{
	@Wire
	private Listbox lbAtivo;
	@Wire
	private Listbox lbAtivoFrota;
	@Wire
	private Toolbarbutton tbtnNovoFrota;
	@Wire
	private Combobox cbxPessoa;
	@Wire
	private Image fotoFrota;
	
	private Media media;
	
	private List<Ativo> removedAtivoList = new ArrayList<Ativo>();
	
	private ListModelList<Ativo> ativoDataModel;
	private ListModelList<Ativo> ativoFrotaDataModel;
	
	private int navegador = 0;
	private boolean desativado = true;
	
	private Pessoa selectedPessoa;
	private List<Pessoa> allPessoa;
	
	private Ativo selectedAtivo;
	private List<Ativo> allAtivo;
	
	private Frota selectedFrota;
	private List<Frota> allFrota;
	
	private Ativo selectedAtivoFrota;
	private List<Ativo> allAtivoFrota;
	
	public Pessoa getSelectedPessoa() {
		return selectedPessoa;
	}
	public void setSelectedPessoa(Pessoa selectedPessoa) {
		this.selectedPessoa = selectedPessoa;
	}
	public List<Pessoa> getAllPessoa() {
		return allPessoa;
	}
	public void setAllPessoa(List<Pessoa> allPessoa) {
		this.allPessoa = allPessoa;
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
	public Frota getSelectedFrota() {
		return selectedFrota;
	}
	public void setSelectedFrota(Frota selectedFrota) {
		this.selectedFrota = selectedFrota;
	}
	public List<Frota> getAllFrota() {
		return allFrota;
	}
	public void setAllFrota(List<Frota> allFrota) {
		this.allFrota = allFrota;
	}
	public boolean isDesativado()
	{
		return this.desativado;
	}
	public void setDesativado(boolean desativado)
	{
		this.desativado = desativado;
	}
	public Ativo getSelectedAtivoFrota()
	{
		return this.selectedAtivoFrota;
	}
	public void setSelectedAtivoFrota(Ativo selectedAtivoFrota)
	{
		this.selectedAtivoFrota = selectedAtivoFrota;
	}
	public List<Ativo> getAllAtivoFrota()
	{
		return this.allAtivoFrota;
	}
	public void setAllAtivoFrota(List<Ativo> allAtivoFrota)
	{
		this.allAtivoFrota = allAtivoFrota;
	}	
	
	@NotifyChange("allPessoa")
	public void atualizaAllPessoa()
	{
		DaoFactory daof = new DaoFactory();
		daof.beginTransaction();
		this.allPessoa = daof.getPessoaDAO().listaTudo();
		
		//Coloca em ordem crescente a lista de pessoas usando o objeto Ordenar
		Ordenar o = new Ordenar();
		Collections.sort(this.allPessoa, o);
		o = null;
		
		Pessoa p = new Pessoa();
		p.setNome("Nenhum");
		this.allPessoa.add(0, p);
	}
	@NotifyChange("allAtivo")
	public void atualizaAllAtivo()
	{
		DaoFactory daof = new DaoFactory();
		daof.beginTransaction();
		this.allAtivo = daof.getAtivoDAO().listaTudo();
		
		//Coloca em ordem crescente a lista de ativos usando o objeto Ordenar
		Ordenar o = new Ordenar();
		Collections.sort(this.allAtivo, o);
		o = null;
	}
	@GlobalCommand
	@NotifyChange("allFrota")
	public void atualizaAllFrota()
	{
		DaoFactory daof = new DaoFactory();
		daof.beginTransaction();
		this.allFrota = daof.getFrotaDAO().listaTudo();
		
		//Coloca em ordem crescente a lista de frotas usando o objeto Ordenar
		Ordenar o = new Ordenar();
		Collections.sort(this.allFrota, o);
		o = null;
	}
	
	@Init
	public void init()
	{
		this.allAtivo = new ArrayList<Ativo>();
		this.allAtivoFrota = new ArrayList<Ativo>();
		this.allFrota = new ArrayList<Frota>();
		
		this.selectedPessoa = new Pessoa();
		this.selectedAtivo = new Ativo();
		this.selectedFrota = new Frota();
		this.atualizaAllAtivo();
		this.atualizaAllPessoa();
		this.atualizaAllFrota();
	}
	
	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view)
	{
		Selectors.wireComponents(view, this, false);
		Selectors.wireEventListeners(view, this);
		
		this.ativoDataModel = new ListModelList<Ativo>();
		this.ativoDataModel.addAll(this.allAtivo);
		this.ativoDataModel.setMultiple(true);
		this.lbAtivo.setModel(this.ativoDataModel);
		
		this.lbAtivoFrota.setModel(this.ativoFrotaDataModel = new ListModelList<Ativo>());
		this.ativoFrotaDataModel.setMultiple(true);
		
		Clients.showNotification("Clique aqui para adicionar uma nova Frota", "info", this.tbtnNovoFrota, "end_center", 3000);
	}
	
	@Command
	@NotifyChange({"selectedFrota","selectedPessoa","desativado"})
	public void novo()
	{
		this.selectedFrota = null;
		this.selectedPessoa = null;
		this.selectedFrota = new Frota();
		this.desativado = false;
		
		if ( !this.ativoFrotaDataModel.isEmpty() )
			this.ativoFrotaDataModel.clear();
				
		this.ativoDataModel.clearSelection();
	}
	
	@Command
	@NotifyChange({"selectedFrota","selectedPessoa","desativado"})
	public void submit()
	{
		try
		{	
			//if ( this.selectedFrota.getPessoa().equals(this.selectedPessoa) )
			//{
				DaoFactory daof = new DaoFactory();
				daof.beginTransaction();
				
				if ( this.selectedPessoa.getNome().equals("Nenhum") )
					this.selectedFrota.setPessoa(null);
				else
					this.selectedFrota.setPessoa(this.selectedPessoa);
				
				this.salvarFoto();
				
				daof.getFrotaDAO().adiciona(this.selectedFrota);
			
				if ( !this.allAtivoFrota.isEmpty() )
				{			
					for (Ativo a : this.allAtivoFrota)
					{
						a.setFrota(this.selectedFrota);
						daof.getAtivoDAO().adiciona(a);						
					}
				}
				
				if ( !this.removedAtivoList.isEmpty() )
				{
					for ( Ativo a : this.removedAtivoList )
					{
						a.setFrota(null);
						daof.getAtivoDAO().adiciona(a);
					}
				}
			
				Messagebox.show("A Frota " + this.selectedFrota.getDescricao().toUpperCase() + " foi adicionada ou atualizada com sucesso",
						"Hydro - Projeto Alpha", Messagebox.OK, Messagebox.INFORMATION);
			
				
				//Atualiza a lista na aba Compartimento na janela de Cadastros;
				this.atualizaBindComponent(this.selectedFrota);
				this.novo();	
				this.atualizaAllFrota();
				this.atualizaAllAtivo();
				//Adiciona os itens no Listbox compartimento de acordo com os tipos de compartimentos
				//this.addLBCompartimento();
				
				this.desativado = true;
				//}
				//else
				//{
					//Clients.showNotification("Essa pessoa é responsável por outra frota, selecione outra.", "info", this.cbxPessoa, "end_center", 2500);
					//this.cbxPessoa.setFocus(true);
				//}			
		}
		catch (WrongValueException wve)
		{
			//Messagebox.show("Você deve selecionar um Tipo de Compartimento, campo obrigatório.", "Hydro - Projeto Alpha", 
							//Messagebox.OK, Messagebox.EXCLAMATION);
			//Clients.showNotification("Tipo de Compartimento deve ser selecionado", "info", this.cbxTP, "end_center", 3000);
			wve.printStackTrace();
			
		}
		catch (NullPointerException npe)
		{
			//Clients.showNotification("Tipo de Compartimento deve ser selecionado", "info", this.cbxTP, "end_center", 3000);
			npe.printStackTrace();
		}
		catch (Exception e)
		{
			Messagebox.show("Problemas com a conexão com o banco de dados.\nContate o administrador ou desenvolvedor do sistema.",
					"Hydro - Projeto Alpha", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}
	
	@Command
	@NotifyChange("selectedFrota")
	public void excluir()
	{
		try
		{
			if ( !this.selectedFrota.getAtivo().isEmpty() )
			{
				Messagebox.show("Atenção, a Frota " + this.selectedFrota.getDescricao().toUpperCase() + " possui ativo(s) associado(s). "+
						"Caso prossiga com a exclusão os ativos ficarão sem frota. Você realmente deseja prosseguir com a exclusão?", "Hydro - Projeto Alpha",
						Messagebox.YES | Messagebox.NO, Messagebox.EXCLAMATION, 
						new EventListener<Event>() {
							public void onEvent(Event event) throws SQLException
							{
								if (Messagebox.ON_YES.equals(event.getName()))
								{
									try
									{
										DaoFactory daof = new DaoFactory();
										daof.beginTransaction();
										
										selectedFrota = daof.getFrotaDAO().procura(selectedFrota.getId());
										for ( Ativo a : selectedFrota.getAtivo() )
											a.setFrota(null);										
										
										daof.getFrotaDAO().remove(selectedFrota);
										//daof.commit();
										
										if ( selectedFrota.getFoto() != null )
											delFoto(selectedFrota.getFoto());
										
										Messagebox.show("Frota excluida com sucesso.", "Hydro - Projeto Alpha", Messagebox.OK, Messagebox.INFORMATION);
										
										desativado = true;
										atualizaAllFrota();													
										navegar("proximo");
									
										//Atualiza a lista na aba Compartimento na janela de Cadastros;
										atualizaBindComponent(selectedFrota);
									}
									catch (ConstraintViolationException cve)
									{
										Messagebox.show("Você não pode excluir essa frota pois ela está associado a uma supervisão. Substitua ou remova a frota, na supervisão, por outro para excluir.",
												"Hydro - Projeto Alpha", Messagebox.OK, Messagebox.ERROR);
									}
									catch (Exception e)
									{
										Messagebox.show("Problemas com a conexão com o banco de dados.\nContate o administrador ou desenvolvedor do sistema",
													"Hydro - Projeto Alpha", Messagebox.OK, Messagebox.ERROR);
										e.printStackTrace();									
									}
								}								
							}			
						});				
			}
			else 
			{			
				Messagebox.show("Você realmente deseja excluir a Frota " + this.selectedFrota.getDescricao().toUpperCase() + "?", "Hydro - Projeto Alpha",
					Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, 
					new EventListener<Event>() {
						public void onEvent(Event event) throws SQLException
						{
							if (Messagebox.ON_YES.equals(event.getName()))
							{
								try
								{		
									DaoFactory daof = new DaoFactory();
									daof.beginTransaction();
									
									daof.getFrotaDAO().remove(selectedFrota);
									//daof.commit();
									
									Messagebox.show("Frota excluida com sucesso.", "Hydro - Projeto Alpha", Messagebox.OK, Messagebox.INFORMATION);
									
									desativado = true;
									atualizaAllFrota();													
									navegar("proximo");
								
									//Atualiza a lista na aba Compartimento na janela de Cadastros;
									atualizaBindComponent(selectedFrota);										
								}
								catch (ConstraintViolationException cve)
								{
									Messagebox.show("Você não pode excluir essa frota pois ela está associado a uma supervisão. Substitua ou remova a frota, na supervisão, por outra para excluir.",
											"Hydro - Projeto Alpha", Messagebox.OK, Messagebox.ERROR);
								}
								catch (Exception e)
								{
									Messagebox.show("Problemas com a conexão com o banco de dados.\nContate o administrador ou desenvolvedor do sistema",
												"Hydro - Projeto Alpha", Messagebox.OK, Messagebox.ERROR);
									e.printStackTrace();									
								}
							}							
						}			
					});				
				}
		}
		catch (NullPointerException n)
		{
			Messagebox.show("Selecione uma Frota para a exclusão!", "Hydro - Projeto Alpha", 
					Messagebox.OK, Messagebox.EXCLAMATION);
		}		
	}
	
	@Command
	@NotifyChange({"selectedFrota", "desativado"})
	public void navegar(@BindingParam("acao") String acao) throws IOException
	{
		this.selectedFrota = null;
		
		this.desativado = false;
		
		if ( !this.allFrota.isEmpty() )
		{
			if ( acao.equals("primeiro") )
			{
				this.navegador = 0;
				this.showSelectedFrotaItem( this.navegador );			
			}
			
			if( acao.equals("anterior") )
			{
				if ( this.navegador > 0 )
				{
					this.showSelectedFrotaItem( --this.navegador );
				}
				else 
					this.navegar("primeiro");
			}
		
			if ( acao.equals("proximo") )
			{
				if ( this.navegador < this.allFrota.size()-1 )
				{
					this.showSelectedFrotaItem( ++this.navegador );
				}
				else
					this.navegar("ultimo");
			}
		
			if ( acao.equals("ultimo") )
			{
				this.showSelectedFrotaItem( this.navegador = this.allFrota.size()-1 );
			}
		}
		else
		{
			if (this.selectedFrota == null)
				this.selectedFrota = new Frota();
			else
			{
				this.selectedFrota.setDescricao("");
				this.selectedFrota.setObs("");
				this.selectedPessoa = null;
			}
		}
	}
	
	public void adicionarAtivoUnidade(Ativo a)
	{
		if (this.ativoFrotaDataModel.isEmpty())
			this.ativoFrotaDataModel.add(a);
		else if ( this.ativoFrotaDataModel.contains(a) )
		{
			Messagebox.show("O Ativo " + a.getTag() + " já faz parte dessa Frota", "Hydro - Projeto Alpha", Messagebox.OK, Messagebox.INFORMATION);
			//break;
		}
		else 
			this.ativoFrotaDataModel.add(a);
	}
	
	@Command
	@NotifyChange("allAtivoFrota")
	public void adicionarAtivo(Set sa)	
	{
		Set set = sa==null ? this.ativoDataModel.getSelection() : sa;
					
		for (Object o : set)
		{
			final Ativo a = (Ativo) o;
			
			//Se o ativo adicionado vier da lista de ativos
			if ( sa == null )
			{
				if ( a.getFrota() != null )
				{
					Messagebox.show("ATENÇÃO, o Ativo selecionado pertence a outra Frota. "+
							"Caso continue com a adição, o ativo selecionado passará a fazer parte da frota atual. "+ 
							"Você deseja continuar com a adição do ativo?", "Hydro - Projeto Alpha",
							Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, 
							new EventListener<Event>() {
							public void onEvent(Event event) throws SQLException
							{
								if (Messagebox.ON_YES.equals(event.getName()))
									adicionarAtivoUnidade(a);									
							}
					});
				}
				else
					this.adicionarAtivoUnidade(a);
			}
			else
				this.adicionarAtivoUnidade(a);
		}
		
		this.lbAtivoFrota.setModel(ativoFrotaDataModel);
		this.allAtivoFrota = ativoFrotaDataModel.getInnerList();
		
		this.ativoDataModel.clearSelection();
	}
	
	@Command
	public void removerAtivo()
	{
		Set<Ativo> set = this.ativoFrotaDataModel.getSelection();
		//for (Compartimento c : set)
		//{
		if ( this.removedAtivoList == null)
			this.removedAtivoList = new ArrayList<Ativo>();
			
		this.removedAtivoList.addAll(set);
		
		this.ativoFrotaDataModel.removeAll(set);
		//}
		this.lbAtivoFrota.setModel(this.ativoFrotaDataModel);
	}

	public void atualizaBindComponent(Object obj)
	{
		Map args = new HashMap();
		args.put("atualizaListas", obj);
		BindUtils.postGlobalCommand(null, null, "atualizaListas", args);
	}
	
	@NotifyChange("selectedPessoa")
	public void selectPessoa(Pessoa pessoa)
	{
		this.selectedPessoa = pessoa;
		
		//List<Comboitem> li = this.cbxFuncao.getItems().;
		if ( this.selectedPessoa != null )
		{
			for (int i = 0; i < this.allPessoa.size(); i++)
			{
				Pessoa p = this.allPessoa.get(i);
				if (p.getId() == this.selectedPessoa.getId())
					this.cbxPessoa.setSelectedIndex(i);
			}
		}
	}
	
	/*
	 * Mostra o item selecionado na aba cadastros correspondente a Frotas 
	 */
	@GlobalCommand
	@NotifyChange({"selectedFrota","desativado"})
	public void showSelectedFrotaItem(@BindingParam("showSelectedFrotaItem") int i) throws IOException
	{		
		this.desativado = false;
		
		this.selectedFrota = null;
		
		if ( this.removedAtivoList == null )
			this.removedAtivoList = new ArrayList<Ativo>();
		else
			this.removedAtivoList.clear();		
		
		DaoFactory daof = new DaoFactory();
		daof.beginTransaction();
		
		this.selectedFrota = this.allFrota.get( this.navegador = i );		
		this.selectedFrota = daof.getFrotaDAO().procura(selectedFrota.getId());		
		
		this.selectPessoa(this.selectedFrota.getPessoa());
		
		Set<Ativo> sa = this.selectedFrota.getAtivo();	
		
		this.ativoFrotaDataModel.clear();
		this.adicionarAtivo(sa);
		
		if ( (this.selectedFrota.getFoto() != null) && !this.selectedFrota.getFoto().isEmpty() )
		{
			org.zkoss.image.AImage img = new org.zkoss.image.AImage(this.selectedFrota.getFoto());
			this.fotoFrota.setContent(img);
		}
		else
			this.fotoFrota.setSrc(null);
		//daof.close();
	}
	
	/*
	 * Método utilizado para atualizar a lista de pessoas. Este ação será disparada na janela Pessoa ou em qualquer 
	 * outra que fizer alteração a uma pessoa qualquer.
	 */
	@GlobalCommand
	@NotifyChange("allPessoa")
	public void atualizaAllPessoaFrota(@BindingParam("atualizaAllPessoaFrota") Pessoa selectedPessoa)
	{
		this.allPessoa = null;
		this.atualizaAllPessoa();
	}
	/*
	@Command
	public boolean verificarResponsavel()
	{
		boolean existe = false;
		
		if ( !this.allFrota.isEmpty() )
			for ( Frota f : allFrota )
				if ( (this.selectedPessoa != null) && (f != null) && (f.getPessoa().getId() == this.selectedPessoa.getId()) ) 
					//if ( (f.getPessoa().getId() == this.selectedPessoa.getId()) )
					{
						existe = true;
						Clients.showNotification("Essa pessoa é responsável por outra frota, selecione outra.", "info", this.cbxPessoa, "end_center", 2500);
						this.cbxPessoa.setFocus(true);
					}
		
		return existe;
	}*/
	@Listen("onUpload=#btnFotoFrota")
	public void addFoto(UploadEvent evt)
	{
		this.media = evt.getMedia();
	}	
	
	public void salvarFoto() throws IOException
	{
		if ( this.media != null )
		{			
			String path = "rti/alpha";//System.getProperty("user.home"); 
			 
			path += "/hydro/img/imagens/frota/" + this.selectedFrota.getDescricao() + "_" + this.media.getName();
			
			BufferedImage imagem = ImageIO.read( this.media.getStreamData() );
			File arquivo = new File(path);
			arquivo.mkdirs();
			// fazer algo com a imagem...
			ImageIO.write(imagem, "PNG", arquivo);
			
			this.selectedFrota.setFoto(arquivo.getCanonicalPath());//"/img/imagens/pessoas/" + this.selectedPessoa.getNome()+"_" + this.media.getName());
		}
	}
	
	public void delFoto(String foto) throws IOException
	{		
		File arquivo = new File(foto);//path + "img\\imagens\\" + nomeArquivo);
		
		arquivo.delete();
	}
}