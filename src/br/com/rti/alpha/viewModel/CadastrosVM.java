package br.com.rti.alpha.viewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Div;
import org.zkoss.zul.FieldComparator;
import org.zkoss.zul.GroupsModelArray;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listgroup;
import org.zkoss.zul.Window;

import br.com.rti.alpha.controle.CompartimentoListGroupRenderer;
import br.com.rti.alpha.controle.Ordenar;
import br.com.rti.alpha.dao.DaoFactory;
import br.com.rti.alpha.modelo.amostra.PlanoTrabalho;
import br.com.rti.alpha.modelo.amostra.TipoColeta;
import br.com.rti.alpha.modelo.ativo.Ativo;
import br.com.rti.alpha.modelo.ativo.Compartimento;
import br.com.rti.alpha.modelo.ativo.Oleo;
import br.com.rti.alpha.modelo.ativo.TipoCompartimento;
import br.com.rti.alpha.modelo.pessoa.Pessoa;
import br.com.rti.alpha.modelo.supervisao.Frota;
import br.com.rti.alpha.modelo.supervisao.Supervisao;

public class CadastrosVM 
{
	private List<Pessoa> allPessoa = new ArrayList<Pessoa>();
	private Pessoa selectedPessoa;
	private List<TipoCompartimento> allTipoCompartimento = null;
	private TipoCompartimento selectedTipoCompartimento;
	private List<Compartimento> allCompartimento = new ArrayList<Compartimento>();
	private Compartimento selectedCompartimento;
	private List<Ativo> allAtivo = new ArrayList<Ativo>();
	private Ativo selectedAtivo;
	private List<Frota> allFrota = new ArrayList<Frota>();
	private Frota selectedFrota;
	private List<Supervisao> allSupervisao = new ArrayList<Supervisao>();
	private Supervisao selectedSupervisao;
	private List<PlanoTrabalho> allPlanoTrabalho = new ArrayList<PlanoTrabalho>();
	private PlanoTrabalho selectedPlanoTrabalho;
	private List<Oleo> allOleo = new ArrayList<Oleo>();
	private Oleo selectedOleo;
	private List<TipoColeta> allTipoColeta = new ArrayList<TipoColeta>();
	private TipoColeta selectedTipoColeta;
	
	//private Ordenar ordenar = new Ordenar();
	
	//private ListModelList<Pessoa> pessoaDataModel;
	//private ListModelList<Ativo> ativoDataModel;
	//private ListModelList<Supervisao> supervisaoDataModel;
	//private ListModelList<>
		
	/*@Wire
	private Listbox lbPessoa;	
	@Wire
	private Listbox lbAtivo;
	@Wire
	private Listbox lbFrota;
	@Wire
	private Listbox lbSupervisao;
	@Wire
	private Listbox lbPlano;
	@Wire
	private Listbox lbOleo;
	@Wire
	private Listbox lbTipoColeta;*/
	
	@Wire
	private Listbox lbCompartimento;
	@Wire
	private Div winCenter;
	
	public List<Pessoa> getAllPessoa()
	{
		return this.allPessoa;
	}
	
	public void setAllPessoa(List<Pessoa> allPessoa)
	{
		this.allPessoa = allPessoa;
	}
	
	public Pessoa getSelectedPessoa()
	{
		return this.selectedPessoa;
	}
	
	public void setSelectedPessoa(Pessoa selectedPessoa)
	{
		this.selectedPessoa = selectedPessoa;
	}

	public List<TipoCompartimento> getAllTipoCompartimento()
	{
		return this.allTipoCompartimento;
	}
	
	public void setAllTipoCompartimento(List<TipoCompartimento> allTipoCompartimento)
	{
		this.allTipoCompartimento = allTipoCompartimento;
	}
	
	public TipoCompartimento getSelectedTipoCompartimento()
	{
		return this.selectedTipoCompartimento;		
	}
	
	public void setSelectedTipoCompartimento(TipoCompartimento selectedTipoCompartimento)
	{
		this.selectedTipoCompartimento = selectedTipoCompartimento;
	}

	public List<Compartimento> getAllCompartimento()
	{
		return this.allCompartimento;
	}
	
	public void setAllCompartimento(List<Compartimento> allCompartimento)
	{
		this.allCompartimento = allCompartimento;
	}
	
	public Compartimento getSelectedCompartimento()
	{
		return this.selectedCompartimento;
	}
	
	public void setSelectedCompartimento(Compartimento selectedCompartimento)
	{
		this.selectedCompartimento = selectedCompartimento;
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

	public List<Ativo> getAllAtivo()
	{
		return this.allAtivo;
	}
	
	public void setAllAtivo(List<Ativo> allAtivo)
	{
		this.allAtivo = allAtivo;
	}
	
	public Ativo getSelectedAtivo()
	{
		return this.selectedAtivo;
	}
	
	public void setSelectedAtivo(Ativo selectedAtivo)
	{
		this.selectedAtivo = selectedAtivo;
	}

	public List<Frota> getAllFrota() {
		return allFrota;
	}

	public void setAllFrota(List<Frota> allFrota) {
		this.allFrota = allFrota;
	}

	public Frota getSelectedFrota() {
		return selectedFrota;
	}

	public void setSelectedFrota(Frota selectedFrota) {
		this.selectedFrota = selectedFrota;
	}

	public List<Supervisao> getAllSupervisao() {
		return allSupervisao;
	}
	
	public void setAllSupervisao(List<Supervisao> allSupervisao) {
		this.allSupervisao = allSupervisao;
	}

	public Supervisao getSelectedSupervisao() {
		return selectedSupervisao;
	}

	public void setSelectedSupervisao(Supervisao selectedSupervisao) {
		this.selectedSupervisao = selectedSupervisao;
	}

	public List<PlanoTrabalho> getAllPlanoTrabalho() {
		return allPlanoTrabalho;
	}

	public void setAllPlanoTrabalho(List<PlanoTrabalho> allPlanoTrabalho) {
		this.allPlanoTrabalho = allPlanoTrabalho;
	}

	public PlanoTrabalho getSelectedPlanoTrabalho() {
		return selectedPlanoTrabalho;
	}

	public void setSelectedPlanoTrabalho(PlanoTrabalho selectedPlanoTrabalho) {
		this.selectedPlanoTrabalho = selectedPlanoTrabalho;
	}

	public List<TipoColeta> getAllTipoColeta() {
		return allTipoColeta;
	}

	public void setAllTipoColeta(List<TipoColeta> allTipoColeta) {
		this.allTipoColeta = allTipoColeta;
	}

	public TipoColeta getSelectedTipoColeta() {
		return selectedTipoColeta;
	}

	public void setSelectedTipoColeta(TipoColeta selectedTipoColeta) {
		this.selectedTipoColeta = selectedTipoColeta;
	}

	@Init
	public void init()
	{
		this.selectedPessoa = new Pessoa();
		/*this.atualizaPessoa();
		this.atualizaTipoCompartimento();
		this.atualizaCompartimento();		
		this.atualizaAtivo();
		this.atualizaFrota();		
		this.atualizaSupervisao();
		this.atualizaPlanoTrabalho();
		this.atualizaOleo();
		this.atualizaTipoColeta();*/
	}
	
	@AfterCompose
	@NotifyChange({"allTipoCompartimento", "allCompartimento"})
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view)
	{
		Selectors.wireComponents(view, this, false);
		
		this.init();
		/*List<Compartimento> compart = this.allCompartimento;
		Compartimento[] compartArray = compart.toArray(new Compartimento[compart.size()]);
		GroupsModelArray<Compartimento, Object, Object, Object> groupModel = 
			new GroupsModelArray<Compartimento, Object, Object, Object>(
                    compartArray, new FieldComparator("tipoCompartimento.descricao", true));
		
		this.lbCompartimento.setModel(groupModel);
		this.lbCompartimento.setItemRenderer(new CompartimentoListGroupRenderer());*/
	}
	
	/*Métodos responsáveis por popular e atualizar a lista de Pessoas*/
	//Método utilizado para carregar as pessoas do banco de dados em um objeto List
	@Command
	@NotifyChange("allPessoa")
	public void atualizaPessoa()
	{
		DaoFactory daof = new DaoFactory();
		daof.beginTransaction();
		
		this.allPessoa = daof.getPessoaDAO().listaTudo();
		
		//Ordenar op = new Ordenar();		
		Collections.sort(this.allPessoa, new Ordenar());
		
		/*ListModelList<Pessoa> pessoaDataModel = new ListModelList<Pessoa>();
		pessoaDataModel.addAll(this.allPessoa);
		this.lbPessoa.setModel(pessoaDataModel);*/
		//daof.close();
	}
	
	//Método utilizado para mostrar, na janela pessoa, a pessoa selecionada na lista
	/*@Command
	public void showSelectedPessoaItem()
	{
		this.showWindow("winPessoas", "/zk/pessoas.zul");
		
		Map args = new HashMap();
		args.put("atualizaPessoa", this.selectedPessoa);
		BindUtils.postGlobalCommand(null, null, "showSelectedPessoaItem", args);
	}*/
	
	/*Métodos responsáveis por popular e atualizar os Tipos de Compartimento e os compartimentos */
	//Método utilizado para carregar os Tipos de Compartimentos do banco de dados em um objeto List 
	@Command
	@NotifyChange("allTipoCompartimento")
	public void atualizaTipoCompartimento()
	{
		DaoFactory daof = new DaoFactory();
		daof.beginTransaction();
		
		this.allTipoCompartimento = daof.getTipoCompartimentoDAO().listaTudo();
		
		Collections.sort(this.allTipoCompartimento, new Ordenar());
		
		//daof.close();
	}
	
	/*//Método utilizado para atualizar automaticamente os tipos de compartimentos inseridos ou excluídos
	@GlobalCommand
	@NotifyChange("allTipoCompartimento")
	public void atualizaTipoCompartimentoListCad(@BindingParam("atualizaTipoCompartimentoListCad") TipoCompartimento tipoCompartimento)
	{
		this.allTipoCompartimento = null;
		this.atualizaTipoCompartimento();		
	}*/
	
	//Método utilizado para carregar os Compartimentos do banco de dados em um objeto List
	@Command
	@NotifyChange("allCompartimento")
	public void atualizaCompartimento()
	{
		DaoFactory daof = new DaoFactory();
		daof.beginTransaction();
		
		this.allCompartimento = daof.getCompartimentoDAO().listaTudo();
		
		Collections.sort(this.allCompartimento, new Ordenar());
		
		this.atualizaListBoxCompartimento();
	}
	
	/*Métodos responsáveis por popular e atualizar os Ativos */
	//Método utilizado para carregar os Ativos do Banco de Dados em um objeto List
	@Command
	@NotifyChange("allAtivo")
	public void atualizaAtivo()
	{
		DaoFactory daof = new DaoFactory();
		daof.beginTransaction();
		
		this.allAtivo = daof.getAtivoDAO().listaTudo();
		
		Collections.sort(this.allAtivo, new Ordenar());
		
		/*ListModelList<Ativo> ativoDataModel = new ListModelList<Ativo>();
		ativoDataModel.addAll(this.allAtivo);
		this.lbAtivo.setModel(ativoDataModel);*/
		//daof.close();
	}
	
	/*@Command
	public void showSelectedAtivoItem()
	{
		this.showWindow("winAtivo", "/zk/ativo/ativo.zul");
		
		Map args = new HashMap();
		args.put("showSelectedAtivoItem", this.selectedAtivo);
		BindUtils.postGlobalCommand(null, null, "showSelectedAtivoItem", args);
	}*/	
	
	//Método utilizado para mostrar o item selecionado, na lista compartimento, na janela Compartimento
	/*@Command
	public void showSelectedCompartimentoItem()
	{
		this.showWindow("winCompartimento", "/zk/ativo/compartimento.zul");
		
		Map args = new HashMap();
		args.put("showItem", this.selectedCompartimento);
		BindUtils.postGlobalCommand(null, null, "showSelectedCompartimentoItem", args);
	}*/
	@NotifyChange("allCompartimento")
	public void atualizaListBoxCompartimento()
	{
		List<Compartimento> compart = this.allCompartimento;
		Compartimento[] compartArray = compart.toArray(new Compartimento[compart.size()]);
		GroupsModelArray<Compartimento, Object, Object, Object> groupModel = 
			new GroupsModelArray<Compartimento, Object, Object, Object>(
                    compartArray, new FieldComparator("tipoCompartimento.descricao", true));
		
		//this.lbCompartimento = new Listbox();
		
		this.lbCompartimento.setModel(groupModel);
		this.lbCompartimento.setItemRenderer(new CompartimentoListGroupRenderer());
	}
	
	@Command
	@NotifyChange("allFrota")
	public void atualizaFrota()
	{
		this.allFrota = null;
		DaoFactory daof = new DaoFactory();
		daof.beginTransaction();
		
		this.allFrota = daof.getFrotaDAO().listaTudo();
		
		Collections.sort(this.allFrota, new Ordenar());
		
		/*ListModelList<Frota> frotaDataModel = new ListModelList<Frota>();
		frotaDataModel.addAll(this.allFrota);
		this.lbFrota.setModel(frotaDataModel);*/
	}
	
	/*@Command
	public void showSelectedFrotaItem()
	{
		this.showWindow("winFrota", "/zk/supervisao/frota.zul");
		
		Map args = new HashMap();
		args.put("showSelectedFrotaItem", this.selectedFrota);
		BindUtils.postGlobalCommand(null, null, "showSelectedFrotaItem", args);
	}*/
	
	@Command
	@NotifyChange("allSupervisao")
	public void atualizaSupervisao() throws IndexOutOfBoundsException
	{
		this.allSupervisao = null;
		DaoFactory daof = new DaoFactory();
		daof.beginTransaction();
		
		this.allSupervisao = daof.getSupervisaoDAO().listaTudo();
				
		Collections.sort(this.allSupervisao, new Ordenar());
		
		/*ListModelList<Supervisao> supervisaoDataModel = new ListModelList<Supervisao>();
		supervisaoDataModel.addAll(this.allSupervisao);
		this.lbSupervisao.setModel(supervisaoDataModel);*/
	}
	
	@Command
	@NotifyChange("allPlanoTrabalho")
	public void atualizaPlanoTrabalho() throws IndexOutOfBoundsException
	{
		DaoFactory daof = new DaoFactory();
		daof.beginTransaction();
		this.allPlanoTrabalho = daof.getPlanoTrabalhoDAO().listaTudo();
				
		Collections.sort(this.allPlanoTrabalho, new Ordenar());
		
		/*ListModelList<PlanoTrabalho> planoDataModel = new ListModelList<PlanoTrabalho>();
		planoDataModel.addAll(this.allPlanoTrabalho);
		this.lbPlano.setModel(planoDataModel);*/
	}
	
	/*@Command
	public void showSelectedSupervisaoItem()
	{
		this.showWindow("winSupervisao", "/zk/supervisao/supervisao.zul");
		
		Map args = new HashMap();
		args.put("showSelectedSupervisaoItem", this.selectedSupervisao);
		BindUtils.postGlobalCommand(null, null, "showSelectedSupervisaoItem", args);
	}*/
	
	/*Métodos responsáveis por popular e atualizar os Óleos */
	//Método utilizado para carregar os Óleos do banco de dados em um objeto List
	@Command
	@NotifyChange("allOleo")
	public void atualizaOleo() throws IndexOutOfBoundsException
	{
		this.allOleo = null;		
		DaoFactory daof = new DaoFactory();
		daof.beginTransaction();
		
		this.allOleo = daof.getOleoDAO().listaTudo();
		
		Collections.sort(this.allOleo, new Ordenar());
		
		/*ListModelList<Oleo> oleoDataModel = new ListModelList<Oleo>();
		oleoDataModel.addAll(this.allOleo);
		this.lbOleo.setModel(oleoDataModel);*/
		//daof.close();	
	}
	
	@Command
	@NotifyChange("allTipoColeta")
	public void atualizaTipoColeta() throws IndexOutOfBoundsException
	{
		DaoFactory daof = new DaoFactory();
		daof.beginTransaction();
		
		this.allTipoColeta = daof.getTipoColetaDAO().listaTudo();
		
		Collections.sort(this.allTipoColeta, new Ordenar());
		
		/*ListModelList<TipoColeta> coletaDataModel = new ListModelList<TipoColeta>();
		coletaDataModel.addAll(this.allTipoColeta);
		this.lbTipoColeta.setModel(coletaDataModel);*/
	}
	
	//Método utilizado para mostrar o item selecionado, na lista öleo, na janela Óleo
	/*@Command
	public void showSelectedOleoItem()
	{
		this.showWindow("winOleo", "/zk/ativo/oleo.zul");
		
		Map args = new HashMap();
		args.put("showSelectedOleoItem", this.selectedOleo);
		BindUtils.postGlobalCommand(null, null, "showSelectedOleoItem", args);
	}*/
	
	public void showWindow(String id, String path)
	{		
		if ( this.winCenter.hasFellow(id) )
		{
			if ( this.winCenter.getFellowIfAny(id).isVisible() == false )
				this.winCenter.getFellowIfAny(id).setVisible( true );			
		}
		else
		{
			final Window win = (Window) Executions.createComponents(path, 
					this.winCenter, null);
			win.setId(id);
			win.setClosable(true);
			win.setMinimizable(true);
		}	
	}
	
	/*
	  Método utilizado para atualizar automaticamente as pessoas, compartimentos, ativos, frotas, supervisões,
	  planos, óleos e tipos de coletas, inseridos ou excluídos em suas janelas.
	*/
	@GlobalCommand
	@NotifyChange("*")
	public void atualizaListas(@BindingParam("atualizaListas") Object object) throws IndexOutOfBoundsException
	{
		if ( object instanceof Pessoa )
		{
			this.allPessoa = null;
			this.atualizaPessoa();
		}
		if ( object instanceof TipoCompartimento )
		{
			this.allTipoCompartimento = null;
			this.atualizaTipoCompartimento();
		}
		if ( object instanceof Compartimento )
		{
			this.allCompartimento = null;
			this.atualizaCompartimento();
			this.atualizaListBoxCompartimento();
		}
		if ( object instanceof Ativo )
		{
			this.allAtivo = null;
			this.atualizaAtivo();
		}
		if ( object instanceof Frota )
		{
			this.allFrota = null;
			this.atualizaFrota();
		}
		if ( object instanceof Supervisao )
		{
			this.allSupervisao = null;
			this.atualizaSupervisao();
		}
		if ( object instanceof PlanoTrabalho )
		{
		 	this.allPlanoTrabalho = null;
		 	this.atualizaPlanoTrabalho();
		}		
		if ( object instanceof Oleo )
		{
			this.allOleo = null;
			this.atualizaOleo();
		}		
		if ( object instanceof TipoColeta )
		{
			this.allTipoColeta = null;
			this.atualizaTipoColeta();
		}
	}
	
	public void esconderJanelas(String componente)
    {
   	 if ( !winCenter.getFellows().isEmpty() )
   	 {
   		 for ( int i = 0; i < winCenter.getChildren().size(); i++)
   		 {
   			 Component listChild = winCenter.getChildren().get(i);
   			 if ( !listChild.equals(winCenter.getFellowIfAny(componente)) )
   			 {
   				 Window winChild = (Window) listChild;
   				 if ( !winChild.inOverlapped() )
   				 	winChild.setVisible(false);
   			 }
   				 
   		 }
   	 }
    }
    
	/*
	 * Método utilizado para mostrar as janelas coorrespondentes as abas selecionadas
	 */
	@Command
    public void showCenterWindow(@BindingParam("aba") String label)
    {
    	if ( label.equalsIgnoreCase("Pessoas") )
    	{
    		esconderJanelas("win"+label);
   		
    		if ( (winCenter.hasFellow("winPessoas")) )
    		{
    			winCenter.getFellowIfAny("winPessoas").setVisible(winCenter.getFellowIfAny("winPessoas").isVisible() ? false : true);
    		}
    		else 
    		{
    			final Window winPessoas = (Window)Executions.createComponents(
    					"/zk/pessoas.zul", winCenter, null);;
				    							    	
						winPessoas.setId("winPessoas");
				    	winPessoas.setClosable(true);
				    	winPessoas.setMinimizable(true);
    		}
    	}
    	if ( label.equalsIgnoreCase("Tipos de Compartimentos") )
    	{
    		esconderJanelas("winTipoCompartimento");
   		
    		if ( (winCenter.hasFellow("winTipoCompartimento")) )
    		{
    			winCenter.getFellowIfAny("winTipoCompartimento").setVisible(winCenter.getFellowIfAny("winTipoCompartimento").isVisible() ? false : true);
    		}
    		else 
    		{
    			final Window winPessoas = (Window)Executions.createComponents(
    					"/zk/ativo/tipoCompartimento.zul", winCenter, null);;
				    							    	
						winPessoas.setId("winTipoCompartimento");
				    	winPessoas.setClosable(true);
				    	winPessoas.setMinimizable(true);
    		}
    	}
    	if (label.equalsIgnoreCase("compartimento"))
    	{
    		esconderJanelas("win"+label);
   			
    		if ( winCenter.hasFellow("winCompartimento") )
    		{
    			winCenter.getFellowIfAny("winCompartimento").setVisible(winCenter.getFellowIfAny("winCompartimento").isVisible() ? false : true);			
    		}
    		else
    		{
    			final Window winCompartimento = (Window) Executions.createComponents("/zk/ativo/compartimento.zul", 
    					winCenter, null);
    			winCompartimento.setId("winCompartimento");
    			winCompartimento.setClosable(true);
    			winCompartimento.setMinimizable(true);
    		}
    	}		
   	
    	if (label.equalsIgnoreCase("ativo"))
    	{
    		esconderJanelas("win"+label);
   		
    		if ( winCenter.hasFellow("winAtivo") )
    		{
    			winCenter.getFellowIfAny("winAtivo").setVisible(winCenter.getFellowIfAny("winAtivo").isVisible() ? false : true);
    		}
    		else
    		{
    			final Window winAtivo = (Window) Executions.createComponents("/zk/ativo/ativo.zul",
    					winCenter, null);
    			winAtivo.setId("winAtivo");
    			winAtivo.setClosable(true);
    			winAtivo.setMinimizable(true);
    		}
    	}
    	
    	if (label.equalsIgnoreCase("frota"))
    	{
    		esconderJanelas("win"+label);
			
    		if ( winCenter.hasFellow("winFrota") )
    		{
    			winCenter.getFellowIfAny("winFrota").setVisible(winCenter.getFellowIfAny("winFrota").isVisible() ? false : true);			
    		}
    		else
    		{
    			final Window winFrota = (Window) Executions.createComponents("/zk/supervisao/frota.zul", 
    					winCenter, null);
    			winFrota.setId("winFrota");
    			winFrota.setClosable(true);
    			winFrota.setMinimizable(true);
    		}
    	}
    	if (label.equalsIgnoreCase("supervisão"))
    	{
			esconderJanelas("winSupervisao");
			
			if ( winCenter.hasFellow("winSupervisao") )
			{
				winCenter.getFellowIfAny("winSupervisao").setVisible(winCenter.getFellowIfAny("winSupervisao").isVisible() ? false : true);			
			}
			else
			{
				final Window winSupervisao = (Window) Executions.createComponents("/zk/supervisao/supervisao.zul", 
						winCenter, null);
				winSupervisao.setId("winSupervisao");
				winSupervisao.setClosable(true);
				winSupervisao.setMinimizable(true);
			}
    	}
    	if (label.equalsIgnoreCase("planos"))
    	{
			esconderJanelas("winPlanoTrabalho");
			
			if ( winCenter.hasFellow("winPlanoTrabalho") )
			{
				winCenter.getFellowIfAny("winPlanoTrabalho").setVisible(winCenter.getFellowIfAny("winPlanoTrabalho").isVisible() ? false : true);			
			}
			else
			{
				final Window winPlanoTrabalho = (Window) Executions.createComponents("/zk/amostra/planoTrabalho.zul", 
						winCenter, null);
				winPlanoTrabalho.setId("winPlanoTrabalho");
				winPlanoTrabalho.setClosable(true);
				winPlanoTrabalho.setMinimizable(true);
			}
    	}
    	if (label.equalsIgnoreCase("óleo"))
    	{
    		esconderJanelas("winOleo");
   			
    		if ( winCenter.hasFellow("winOleo") )
    		{
    			winCenter.getFellowIfAny("winOleo").setVisible(winCenter.getFellowIfAny("winOleo").isVisible() ? false : true);			
    		}
    		else
    		{
    			final Window winOleo = (Window) Executions.createComponents("/zk/ativo/oleo.zul", 
    					winCenter, null);
    			winOleo.setId("winOleo");
    			winOleo.setClosable(true);
    			winOleo.setMinimizable(true);
    		}
    	}
    	if (label.equalsIgnoreCase("Tipos de Coletas")) 
    	{
	 		esconderJanelas("winTipoColeta");
			
	 		if ( winCenter.hasFellow("winTipoColeta") )
	 		{
	 			winCenter.getFellowIfAny("winTipoColeta").setVisible(winCenter.getFellowIfAny("winTipoColeta").isVisible() ? false : true);			
	 		}
	 		else
	 		{
	 			final Window winTipoColeta = (Window) Executions.createComponents("/zk/amostra/tipoColeta.zul", 
	 					winCenter, null);
	 			winTipoColeta.setId("winTipoColeta");
	 			winTipoColeta.setClosable(true);
	 			winTipoColeta.setMinimizable(true);
	 		}
    	}		        		 
    }
	
	
	/*
	 * Método utilizado para mostrar em cada janela correspondente os itens selecionados em cada aba, pessoas, compartimentos, ativos, 
	 * frotas, supervisões, planos, óleos e tipos de coletas.
	 */
	@Command
	public void showSelectedItem(@BindingParam("tipoItem") String tipoItem)
	{
		String bindingParam = "";
		String metodo = "";
		Map args = new HashMap();
		
		if ( tipoItem.equals("pessoa") )
		{
			this.showWindow("winPessoas", "/zk/pessoas.zul");
			
			metodo = "showSelectedPessoaItem";
			args.put("atualizaPessoa", this.allPessoa.indexOf(this.getSelectedPessoa()));
		}
		if ( tipoItem.equals("tipoCompartimento") )
		{
			this.showWindow("winTipoCompartimento", "/zk/ativo/tipoCompartimento.zul");
			
			metodo = "showSelectedTipoCompartimentoItem";
			args.put("showSelectedTipoCompartimentoItem", this.allTipoCompartimento.indexOf(this.getSelectedTipoCompartimento()));
		}
		if ( tipoItem.equals("compartimento") )
		{
			this.showWindow("winCompartimento", "/zk/ativo/compartimento.zul");
			metodo = "showSelectedCompartimentoItem";
			args.put("showItem", this.allCompartimento.indexOf(this.getSelectedCompartimento()));
		}
		if ( tipoItem.equals("ativo") )
		{
			this.showWindow("winAtivo", "/zk/ativo/ativo.zul");
			
			metodo = "showSelectedAtivoItem";
			args.put("showSelectedAtivoItem", this.allAtivo.indexOf(this.getSelectedAtivo()));
		}
		if ( tipoItem.equals("frota") )
		{
			this.showWindow("winFrota", "/zk/supervisao/frota.zul");
			
			metodo = "showSelectedFrotaItem";
			args.put("showSelectedFrotaItem", this.allFrota.indexOf(this.getSelectedFrota()));
		}
		if ( tipoItem.equals("supervisao") )
		{
			this.showWindow("winSupervisao", "/zk/supervisao/supervisao.zul");
			
			metodo = "showSelectedSupervisaoItem";
			args.put("showSelectedSupervisaoItem", this.allSupervisao.indexOf(this.getSelectedSupervisao()));			
		}
		if ( tipoItem.equals("planoTrabalho") ) 
		{
			this.showWindow("winPlanoTrabalho", "/zk/amostra/planoTrabalho.zul");
			
			metodo = "showSelectedPlanoTrabalhoItem";
			args.put("showSelectedPlanoTrabalhoItem", this.allPlanoTrabalho.indexOf(this.getSelectedPlanoTrabalho()));
		}
		if ( tipoItem.equals("oleo") )
		{
			this.showWindow("winOleo", "/zk/ativo/oleo.zul");
			
			metodo = "showSelectedOleoItem";
			args.put("showSelectedOleoItem", this.allOleo.indexOf(this.getSelectedOleo()));
		}
		if ( tipoItem.equals("tipoColeta") )
		{
			this.showWindow("winTipoColeta", "/zk/amostra/tipoColeta.zul");
			
			metodo = "showSelectedTipoColetaItem";
			args.put("showSelectedTipoColetaItem", this.allTipoColeta.indexOf(this.getSelectedTipoColeta()));
		}
		
		BindUtils.postGlobalCommand(null, null, metodo, args);
	}
}
