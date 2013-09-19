package br.com.rti.alpha.viewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale.Category;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
//import org.zkoss.poi.hssf.record.chart.PieRecord;
import org.zkoss.zul.CategoryModel;
import org.zkoss.zul.GanttModel;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.PieModel;
import org.zkoss.zul.SimpleCategoryModel;
import org.zkoss.zul.SimplePieModel;

import br.com.rti.alpha.controle.Ordenar;
import br.com.rti.alpha.dao.DaoFactory;
import br.com.rti.alpha.modelo.amostra.Amostra;
import br.com.rti.alpha.modelo.amostra.Laudos;
import br.com.rti.alpha.modelo.ativo.Ativo;
import br.com.rti.alpha.modelo.ativo.Compartimento;
import br.com.rti.alpha.modelo.supervisao.Frota;

public class Relatorio {
	private int totaldeFrotas;
	private int totalAmostras;
	
	private double nNormal, nCriticos, nAnormal,total;
	
	private Ativo selectedAtivo;
	private List<Ativo> allAtivo;
	
	private Frota selectedFrota;
	private List<Frota> allFrota;
	
	private Amostra selectedAmostra;
	private List<Amostra>allAmostra = new ArrayList<Amostra>();

	private boolean explode = false;
	private boolean threeD=false;

	private PieModel model;
	private String message;
	
	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}
	public int getTotalAmostras() {
		return totalAmostras;
	}

	public void setTotalAmostras(int totalAmostras) {
		this.totalAmostras = totalAmostras;
	}

	public double getnNormal() {
		return nNormal;
	}

	public void setnNormal(double nNormal) {
		this.nNormal = nNormal;
	}

	public double getnCriticos() {
		return nCriticos;
	}

	public void setnCriticos(double nCriticos) {
		this.nCriticos = nCriticos;
	}

	public double getnAnormal() {
		return nAnormal;
	}

	public void setnAnormal(double nAnormal) {
		this.nAnormal = nAnormal;
	}
	public boolean isThreeD() {
		return threeD;
	}

	public void setThreeD(boolean threeD) {
		this.threeD = threeD;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setModel(PieModel model) {
		this.model = model;
	}

	public int getTotaldeFrotas() {
		return totaldeFrotas;
	}

	public void setTotaldeFrotas(int totaldeFrotas) {
		this.totaldeFrotas = totaldeFrotas;
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

	public boolean isExplode() {
		return explode;
	}
	
	@Command
	@NotifyChange("allAtivo")	
	public void atualizaAtivo(){
		DaoFactory daof = new DaoFactory();
		daof.beginTransaction();
		this.allAtivo = daof.getAtivoDAO().listaTudo();
		this.allAmostra = daof.getAmostraDAO().listaTudo();
		
		daof = null;
	}

	@Init
	public void init() {
		 new Relatorio();
		 this.selectedAmostra = new Amostra();
		 this.selectedAtivo = new Ativo();
		 this.model=this.getModel();
		 this.atualizaAtivo();
	
	 }
	

	public  PieModel getModel(){
        PieModel model = new SimplePieModel();

       // List<Amostra> list = new ArrayList<Amostra>();
             
        for (int i=0;i < this.allAmostra.size(); i++) {
        	String situacao=this.allAmostra.get(i).getSituacao().toString();
			
			
			if(situacao.equals("anormal")){
				nAnormal++;
				System.out.println("TotalAnormal: " + nAnormal);
			}
			this.setnAnormal(nAnormal);
			
			if(situacao.equals("critico")){
				nCriticos++;
				System.out.println("Total Critico: " + nCriticos);
			}
			this.setnCriticos(nCriticos);
			
			if(situacao.equals("normal")){
				nNormal++;
				System.out.println("Total Normal: " + nNormal);
			}
			this.setnNormal(nNormal);
		}
        
      /* System.out.println("Total Anormal : " + this.getnAnormal());
        System.out.println("Total Critico : " + this.getnCriticos());
        System.out.println("Total Normal : " + this.getnNormal());*/
        
        
        
        model.setValue("Critico", new Double(this.getnCriticos()));
        model.setValue("Anormal", new Double(this.getnAnormal()));
        
        model.setValue("Normal", new Double(this.getnNormal()));
        return model;
    }
	

	/*
	public GanttModel getGant(){
		GanttModel model = new GanttModel();
		model.addChartDataListener(null);
		return model;
	}
*/

	@Command("showMessage") 
	@NotifyChange("message")
	public void onShowMessage(@BindingParam("msg") String message){
	    this.message = message;
	 
	}
	
	@GlobalCommand("dataChanged") 
	@NotifyChange("model")
	public void onDataChanged(@BindingParam("category")String category , @BindingParam("num") Number num){
	   model.setValue(category, num);
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

}
