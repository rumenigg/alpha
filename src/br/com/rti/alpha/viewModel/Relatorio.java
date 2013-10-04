package br.com.rti.alpha.viewModel;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zkmax.zul.Fusionchart;
import org.zkoss.zkmax.zul.fusionchart.config.CategoryChartConfig;
import org.zkoss.zkmax.zul.fusionchart.config.SeriesPropertiesMap;
import org.zkoss.zul.CategoryModel;
import org.zkoss.zul.Chart;
import org.zkoss.zul.PieModel;
import org.zkoss.zul.SimpleCategoryModel;
import org.zkoss.zul.SimplePieModel;

import br.com.rti.alpha.controle.Ordenar;
import br.com.rti.alpha.dao.DaoFactory;
import br.com.rti.alpha.modelo.amostra.Amostra;
import br.com.rti.alpha.modelo.amostra.Analise;
import br.com.rti.alpha.modelo.amostra.Elementos;
import br.com.rti.alpha.modelo.amostra.Laudos;
import br.com.rti.alpha.modelo.ativo.Ativo;
import br.com.rti.alpha.modelo.ativo.Compartimento;
import br.com.rti.alpha.modelo.pessoa.Pessoa;
import br.com.rti.alpha.modelo.supervisao.Frota;
import br.com.rti.alpha.modelo.supervisao.Supervisao;
//import org.zkoss.poi.hssf.record.chart.PieRecord;

public class Relatorio{

	@Wire
	private int totalAmostras,totaldeFrotas,totalAtivos,totalSupervisao,totalCompartimento,totalAnalise;

	@Wire
	private int nNormal, nCriticos, nAnormal;

	private Analise amostra_id;

	private boolean explode = false;
	private boolean threeD=false;
	private boolean desativado = true;
	
	//private PieModel model;
	private CategoryModel modelcat;
	private CategoryModel modelLine;
	
	private String message;

	private Ativo selectedAtivo;
	private List<Ativo> allAtivo;

	private Frota ativo;
	private Frota selectedFrota;
	private List<Frota> allFrota=new ArrayList<Frota>();

	private Amostra selectedAmostra;
	private List<Amostra>allAmostra;

	private Elementos selectedElementos;
	private List<Elementos> allElementos;

	private Analise amostraAnalise;
	private Analise selectedAnalise;
	private List<Analise> allAnalise;

	private Compartimento compartimentoAmostras;
	private Compartimento selectedCompartimento;
	private List<Compartimento> allCompartimento;

	private Supervisao pessoaResponsavelSupervisao;
	private Supervisao selectedSupervisao;
	private List<Supervisao> allSupervisao;

	//private Laudos laudos;
	private Laudos selectedLaudos;
	private List<Laudos> allLaudos;

	public Supervisao pessoaSupervisao;
	private Pessoa selectedPessoa=new Pessoa();
	private List<Pessoa> allPessoa=new ArrayList<Pessoa>();


	public int getTotalAmostras() {
		return totalAmostras;
	}

	public void setTotalAmostras(int totalAmostras) {
		this.totalAmostras = totalAmostras;
	}

	public int getTotaldeFrotas() {
		return totaldeFrotas;
	}

	public void setTotaldeFrotas(int totaldeFrotas) {
		this.totaldeFrotas = totaldeFrotas;
	}

	public int getTotalAtivos() {
		return totalAtivos;
	}

	public void setTotalAtivos(int totalAtivos) {
		this.totalAtivos = totalAtivos;
	}

	public int getTotalSupervisao() {
		return totalSupervisao;
	}

	public void setTotalSupervisao(int totalSupervisao) {
		this.totalSupervisao = totalSupervisao;
	}

	public int getTotalCompartimento() {
		return totalCompartimento;
	}

	public void setTotalCompartimento(int totalCompartimento) {
		this.totalCompartimento = totalCompartimento;
	}

	public int getTotalAnalise() {
		return totalAnalise;
	}

	public void setTotalAnalise(int totalAnalise) {
		this.totalAnalise = totalAnalise;
	}

	public int getnNormal() {
		return nNormal;
	}

	public void setnNormal(int nNormal) {
		this.nNormal = nNormal;
	}

	public int getnCriticos() {
		return nCriticos;
	}

	public void setnCriticos(int nCriticos) {
		this.nCriticos = nCriticos;
	}

	public int getnAnormal() {
		return nAnormal;
	}

	public void setnAnormal(int nAnormal) {
		this.nAnormal = nAnormal;
	}

	public Analise getAmostra_id() {
		return amostra_id;
	}

	public void setAmostra_id(Analise amostra_id) {
		this.amostra_id = amostra_id;
	}

	public boolean isThreeD() {
		return threeD;
	}

	public void setThreeD(boolean threeD) {
		this.threeD = threeD;
	}

	public CategoryModel getModelcat() {
		return modelcat;
	}

	public void setModelcat(CategoryModel modelcat) {
		this.modelcat = modelcat;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
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

	public Frota getAtivo() {
		return ativo;
	}

	public void setAtivo(Frota ativo) {
		this.ativo = ativo;
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

	public Analise getAmostraAnalise() {
		return amostraAnalise;
	}

	public void setAmostraAnalise(Analise amostraAnalise) {
		this.amostraAnalise = amostraAnalise;
	}

	public Analise getSelectedAnalise() {
		return selectedAnalise;
	}

	public void setSelectedAnalise(Analise selectedAnalise) {
		this.selectedAnalise = selectedAnalise;
	}

	public List<Analise> getAllAnalise() {
		return allAnalise;
	}

	public void setAllAnalise(List<Analise> allAnalise) {
		this.allAnalise = allAnalise;
	}

	public Compartimento getCompartimentoAmostras() {
		return compartimentoAmostras;
	}

	public void setCompartimentoAmostras(Compartimento compartimentoAmostras) {
		this.compartimentoAmostras = compartimentoAmostras;
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

	public Supervisao getPessoaResponsavelSupervisao() {
		return pessoaResponsavelSupervisao;
	}

	public void setPessoaResponsavelSupervisao(
			Supervisao pessoaResponsavelSupervisao) {
		this.pessoaResponsavelSupervisao = pessoaResponsavelSupervisao;
	}

	public Supervisao getSelectedSupervisao() {
		return selectedSupervisao;
	}

	public void setSelectedSupervisao(Supervisao selectedSupervisao) {
		this.selectedSupervisao = selectedSupervisao;
	}

	public List<Supervisao> getAllSupervisao() {
		return allSupervisao;
	}

	public void setAllSupervisao(List<Supervisao> allSupervisao) {
		this.allSupervisao = allSupervisao;
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

	public Supervisao getPessoaSupervisao() {
		return pessoaSupervisao;
	}

	public void setPessoaSupervisao(Supervisao pessoaSupervisao) {
		this.pessoaSupervisao = pessoaSupervisao;
	}

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

	public boolean isExplode() {
		return explode;
	}


	public boolean isDesativado() {
		return desativado;
	}

	public CategoryModel getModelLine() {
		return modelLine;
	}

	public void setModelLine(CategoryModel modelLine) {
		this.modelLine = modelLine;
	}

	public void setDesativado(boolean desativado) {
		this.desativado = desativado;
	}

	
	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view){
		Selectors.wireComponents(view, this, false);
		Selectors.wireEventListeners(view, this);	
		this.desativado=false;	
		
	}

	@Init
	public void init() {
			
		this.selectedSupervisao=null;
		this.selectedSupervisao =new Supervisao();

		this.selectedFrota=null;
		this.selectedFrota=new Frota();

		this.selectedAtivo=null;
		this.selectedAtivo =new Ativo();

		this.selectedCompartimento=null;
		this.selectedCompartimento=new Compartimento();

		this.selectedAmostra = null;
		this.selectedAmostra =new Amostra();

		this.selectedAnalise=null;
		this.selectedAnalise =new Analise();

		this.selectedElementos=null;
		this.selectedElementos =new Elementos();

		//this.model=this.getModel();
		this.modelcat=this.getModeloCat();
		this.atualizaSupervisao(); 
		
	}

	@Command
	@NotifyChange("allPessoa")	
	public void atualizaPessoa(){
		this.allPessoa = null;
		this.allPessoa = new ArrayList<Pessoa>();

		DaoFactory daof = new DaoFactory();
		daof.beginTransaction();

		this.allPessoa=daof.getPessoaDao().listaTudo();

		Collections.sort(this.allPessoa, new Ordenar());
		daof = null;
	}

	@Command
	@NotifyChange("allSupervisao")	
	public void atualizaSupervisao(){
		
		this.desativado=false;	
		this.allSupervisao=null;
		this.allSupervisao=new ArrayList<Supervisao>();

		DaoFactory daof = new DaoFactory();
		daof.beginTransaction();
		this.allSupervisao.clear();
		this.allSupervisao = daof.getSupervisaoDAO().listaTudo();

		this.totalSupervisao=this.allSupervisao.size();
		System.out.println("TOTAL DE SUPERVISAO : " + this.getTotalSupervisao());

		Collections.sort(this.allSupervisao, new Ordenar());
		daof = null;
		this.modelcat.clear();
	}

	@Command
	@NotifyChange({"allFrota","selectedSupervisao","totaldeFrotas","desativado"})
	public void atualizaFrota(){
		
		this.desativado=false;	
		this.allFrota=null;
		this.allFrota=new ArrayList<Frota>();

		DaoFactory daof = new DaoFactory();
		daof.beginTransaction();

		int idsupervisao=this.selectedSupervisao.getId();

		this.selectedSupervisao=daof.getSupervisaoDAO().procura(idsupervisao);
		this.allSupervisao.clear();
		this.allFrota.addAll(this.selectedSupervisao.getFrota());

		
		this.totaldeFrotas=this.allFrota.size();
		
		System.out.println("TOTAL DE FROTAS : " + this.getTotaldeFrotas());

		Collections.sort(this.allFrota, new Ordenar());
		
		daof = null;
		this.modelcat.clear();
	}

	@Command
	@NotifyChange({"allAtivo","selectedFrota","totalAtivos","desativado"})
	public void atualizaAtivo(){
		this.desativado=false;	
		this.allAtivo= null;
		this.allAtivo = new ArrayList<Ativo>();

		DaoFactory daof = new DaoFactory();
		daof.beginTransaction();

		this.selectedFrota=daof.getFrotaDAO().procura(this.selectedFrota.getId());
		this.allAtivo.clear();
		this.allAtivo.addAll(this.selectedFrota.getAtivo());

		totalAtivos=this.allAtivo.size();
		System.out.println("TOTAL DE ATIVOS : " + this.getTotalAtivos());

		Collections.sort(this.allAtivo, new Ordenar());
		daof = null;
		this.modelcat.clear();


	}

	@Command
	@NotifyChange({"allCompartimento","selectedAtivo","totalCompartimento","desativado"})
	public void atualizaCompartimento(){
		this.desativado=false;	
		this.allCompartimento = null;
		this.allCompartimento = new ArrayList<Compartimento>();

		DaoFactory daof = new DaoFactory();
		daof.beginTransaction();

		this.selectedAtivo = daof.getAtivoDAO().procura(this.selectedAmostra.getAtivoAmostra().getId()); 
		this.allCompartimento.clear();
		this.allCompartimento.addAll(this.selectedAtivo.getCompartimento());

		this.totalCompartimento=this.allCompartimento.size();
		System.out.println("TOTAL DE Compartimentos: " + this.getTotalCompartimento());
		
		Collections.sort(this.allCompartimento, new Ordenar());	
		daof=null;
		this.modelcat.clear();

	}

	@Command
	@NotifyChange({"allAmostra","selectedCompartimento","nAnormal","nNormal","nCriticos","totalAmostras","desativado"})
	public void atualizaAmostra(){
		this.modelcat.clear();
		this.desativado=false;	
		
		this.allAmostra=null;
		this.allAmostra = new ArrayList<Amostra>();

		DaoFactory daof = new DaoFactory();
		daof.beginTransaction();

		this.selectedCompartimento= daof.getCompartimentoDAO().procura(this.selectedAmostra.getCompartimentoAmostra().getId());
		this.allAmostra.clear();
		this.allAmostra.addAll(this.selectedCompartimento.getAmostra());

		totalAmostras=this.allAmostra.size();
		
		verificaSituacao();

		Collections.sort(this.allAmostra, new Ordenar());
		
		daof = null;

		this.desativado=true;	
	}

	@Command
	@NotifyChange({"allAnalise","selectedAmostra"})
	public void atualizaAnalise(){
		this.allAnalise = null;
		this.allAnalise = new ArrayList<Analise>();

		DaoFactory daof = new DaoFactory();
		daof.beginTransaction();

		this.allAnalise = daof.getAnaliseDAO().listaTudo();

		totalAnalise=this.allAnalise.size();
		
		Collections.sort(this.allAnalise, new Ordenar());
	
		daof = null;
	}

	@Command
	@NotifyChange({"allAmostra","selectedAmostra"})
	public void atualizaElementos(){
		DaoFactory daof = new DaoFactory();
		daof.beginTransaction();
		this.selectedAmostra = new Amostra();
		this.selectedElementos = new Elementos();

		//this.allAmostra = daof.getAmostraDAO().listaTudo();
		this.allElementos = daof.getElementosDAO().listaTudo();

		Collections.sort(this.allElementos, new Ordenar());
		daof = null;
		
	}

	@Command
	@NotifyChange({"allLaudos","selectedCompartimento","allAmostra","selectedAmostra"})
	public void atualizaLaudos(){
		this.allLaudos=null;
		this.allLaudos=new ArrayList<Laudos>();

		DaoFactory daof = new DaoFactory();
		daof.beginTransaction();

		//this.allLaudos = daof.getLaudosDAO().listaTudo();

		this.selectedCompartimento=daof.getCompartimentoDAO().procura(this.selectedAmostra.getCompartimentoAmostra().getId());
		this.allLaudos.addAll(this.selectedCompartimento.getLaudos());

		Collections.sort(this.allLaudos, new Ordenar());
		daof = null;
	}

	@Command
	@NotifyChange({"nAnormal","nNormal","nCriticos","totalAmostras","totaldeFrotas"})
	public void verificaSituacao(){

		nAnormal=0;nNormal=0;nCriticos=0;totaldeFrotas=0;totalAmostras=0;	
		this.totaldeFrotas=this.allFrota.size();

		for(int f=0;f<this.totaldeFrotas;f++){

			//System.out.println("* Frotas : " +this.allFrota.get(f).getId()+" - " +this.allFrota.get(f).getDescricao());
			String frota=this.selectedFrota.getDescricao();//this.allFrota.get(f).getDescricao();

			nAnormal=0;nNormal=0;nCriticos=0;totalAmostras=0;
			this.totalAmostras=this.allAmostra.size();

			for (int i=0;i<this.totalAmostras; i++) {

				String situacao=this.allAmostra.get(i).getSituacao().toString();

				if(situacao.equals("anormal")){
					nAnormal++;
					this.modelcat.setValue(situacao,frota, new Integer(nAnormal));
					//System.out.println("FROTA : "+ f + " AMOSTRA : "+ i +" Total "+ situacao+" : " + nAnormal);
				}

				if(situacao.equals("critico")){
					nCriticos++;
					this.modelcat.setValue(situacao,frota, new Integer(nCriticos));
					//System.out.println("FROTA : "+ f + " AMOSTRA : "+ i +" Total"+ situacao+" : " + nCriticos);
				}

				if(situacao.equals("normal")){
					nNormal++;
					this.modelcat.setValue(situacao,frota, new Integer(nNormal));	
					//System.out.println("FROTA : "+ f + " AMOSTRA : "+ i +" Total "+ situacao+" : " + nNormal);
				}							
			}
			this.modelcat.setValue("Total","Total de Amostras", new Integer(this.getTotalAmostras()));
		}
	}

	//grafico linear	
	public CategoryModel getModelCatLine(){
		
		CategoryModel modelLine = new SimpleCategoryModel();
		return modelLine;
	}
	
	//grafico categoria padrao
	public  CategoryModel getModeloCat(){
		
		CategoryModel modelcat = new SimpleCategoryModel();
		return modelcat;   
		
	}
	
	@Command("showMessage") 
	@NotifyChange("message")
	public void onShowMessage(@BindingParam("msg") String message){
		this.message = message;
		//Clients.showNotification(message,"info",null,"center",3000);
		//Clients.showNotification(message);
	}

	@GlobalCommand("dataChanged") 
	@NotifyChange("model")
	public void onDataChanged(@BindingParam("category")String category , @BindingParam("num") Number num){
		modelcat.setValue(category, category, num);
	}

	@GlobalCommand("configChanged") 
	@NotifyChange({"threeD","engine"})
	public void onConfigChanged(@BindingParam("threeD") boolean threeD,@BindingParam("exploded") boolean exploded){
		this.threeD = threeD;
		setExplode(exploded);

	}

	public void setExplode(boolean explode) {
		this.explode = explode;
	}

	public void ShowNotifica(){
		Clients.showNotification(message);
	}

	public  PieModel getModel(){
		PieModel model = new SimplePieModel();

		verificaSituacao();
		model.setValue("Critico", new Double(this.getnCriticos()));
		model.setValue("Anormal", new Double(this.getnAnormal()));
		model.setValue("Normal", new Double(this.getnNormal()));
		model.setValue("Total", new Double(this.getTotalAmostras()));
		return model;
	}
}
