
package br.com.rti.alpha.modelo.ativo;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import br.com.rti.alpha.modelo.amostra.Amostra;
import br.com.rti.alpha.modelo.supervisao.Frota;

/**
 * 
 * 
 */
@Entity
public class Ativo {

	@Id
	@GeneratedValue
    private int id;
    private String tag;
    private String fabricante;
    private String modelo;
    private String serie;
    private java.util.Date dataAquisicao;
    private String obs;
    private String foto;
    
    @OneToMany(mappedBy="ativo", fetch=FetchType.LAZY)
    public Set<Compartimento> compartimento;
    
    @ManyToOne
    @JoinColumn(name="frota_id")
    public Frota frota;
    
    @OneToMany(mappedBy="ativoAmostra", fetch=FetchType.LAZY)
    public Set<Amostra> amostra;
	
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
	public String getFabricante() {
		return fabricante;
	}
	public void setFabricante(String fabricante) {
		this.fabricante = fabricante;
	}
	public String getModelo() {
		return modelo;
	}
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
	public String getSerie() {
		return serie;
	}
	public void setSerie(String serie) {
		this.serie = serie;
	}
	public java.util.Date getDataAquisicao() {
		return dataAquisicao;
	}
	public void setDataAquisicao(java.util.Date dataAquisicao) {
		this.dataAquisicao = dataAquisicao;
	}
	public String getObs() {
		return obs;
	}
	public void setObs(String obs) {
		this.obs = obs;
	}
	public String getFoto() {
		return foto;
	}
	public void setFoto(String foto) {
		this.foto = foto;
	}
	public Set<Compartimento> getCompartimento() {
		return this.compartimento;
	}
	public void setCompartimento(Set<Compartimento> compartimento) {
		this.compartimento = compartimento;
	}    
    public Frota getFrota()
    {
    	return this.frota;
    }    
    public void setFrota(Frota frota)
    {
    	this.frota = frota;
    }
	public Set<Amostra> getAmostra() {
		return amostra;
	}
	public void setAmostra(Set<Amostra> amostra) {
		this.amostra = amostra;
	}
    
 }
