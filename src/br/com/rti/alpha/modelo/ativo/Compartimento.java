
package br.com.rti.alpha.modelo.ativo;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import br.com.rti.alpha.modelo.amostra.Amostra;

/**
 * 
 * 
 */
@Entity
public class Compartimento {

	@Id
	@GeneratedValue
    private int id;
	
    private String tag;
    private double capacidade;
    private String fabricante;
    private String foto;
    
    @ManyToOne
    @JoinColumn(name="tipocompartimento_id")
    private TipoCompartimento tipoCompartimento;
    
    @ManyToOne
    @JoinColumn(name="oleo_id")
    private br.com.rti.alpha.modelo.ativo.Oleo oleo;
    
    @ManyToOne
    @JoinColumn(name="ativo_id")
    private Ativo ativo;
    
    @OneToMany(mappedBy="compartimentoAmostra", fetch=FetchType.LAZY)
    private Set<Amostra> amostra;
    
    public Compartimento()
    {
    	super();
    }
	
    public int getId() {
		return id;
	}
    
	public void setId(int id) {
		this.id = id;
	}
	
	public String getTag() {
		return tag;
	}
	
	public void setTag(String tag) {
		this.tag = tag;
	}
	
	public double getCapacidade() {
		return capacidade;
	}
	
	public void setCapacidade(double capacidade) {
		this.capacidade = capacidade;
	}
	
	public String getFabricante() {
		return fabricante;
	}
	
	public void setFabricante(String fabricante) {
		this.fabricante = fabricante;
	}
	
	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public TipoCompartimento getTipoCompartimento()
	{
		return this.tipoCompartimento;
	}
	
	public void setTipoCompartimento(TipoCompartimento tipoCompartimento)
	{
		this.tipoCompartimento = tipoCompartimento;
	}
	
	public br.com.rti.alpha.modelo.ativo.Oleo getOleo() {
		return oleo;
	}
	
	public void setOleo(br.com.rti.alpha.modelo.ativo.Oleo oleo) {
		this.oleo = oleo;
	}  
	
	public Ativo getAtivo()
	{
		return this.ativo;
	}
	
	public void setAtivo(Ativo ativo)
	{
		this.ativo = ativo;
	}

	public Set<Amostra> getAmostra() {
		return amostra;
	}

	public void setAmostra(Set<Amostra> amostra) {
		this.amostra = amostra;
	}
	
	
 }
