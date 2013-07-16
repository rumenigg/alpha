
package br.com.rti.alpha.modelo.amostra;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import br.com.rti.alpha.modelo.ativo.Oleo;
import br.com.rti.alpha.modelo.pessoa.Pessoa;

@Entity
public class Analise 
{
	
	@Id
	@GeneratedValue
    private int id;
    private java.util.Date dataAnalise;
    private String combustivel;
    private String oleoescuro;
    private String impureza;
    private String limalha;
    private String silica;
    private String agua;
    private String informacaoAnalise;
    private String situacao;
    
    @OneToOne
    @JoinColumn(name="amostra_id")
    public Amostra amostraAnalise;
    
    @ManyToOne
    @JoinColumn(name="elementos_id")
    public Elementos elementosAnalise;

    
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public java.util.Date getDataAnalise() {
		return dataAnalise;
	}

	public void setDataAnalise(java.util.Date dataAnalise) {
		this.dataAnalise = dataAnalise;
	}

	public String getCombustivel() {
		return combustivel;
	}

	public void setCombustivel(String combustivel) {
		this.combustivel = combustivel;
	}

	public String getOleoescuro() {
		return oleoescuro;
	}

	public void setOleoescuro(String oleoescuro) {
		this.oleoescuro = oleoescuro;
	}

	public String getImpureza() {
		return impureza;
	}

	public void setImpureza(String impureza) {
		this.impureza = impureza;
	}

	public String getLimalha() {
		return limalha;
	}

	public void setLimalha(String limalha) {
		this.limalha = limalha;
	}

	public String getSilica() {
		return silica;
	}

	public void setSilica(String silica) {
		this.silica = silica;
	}

	public String getAgua() {
		return agua;
	}

	public void setAgua(String agua) {
		this.agua = agua;
	}

	public String getInformacaoAnalise() {
		return informacaoAnalise;
	}

	public void setInformacaoAnalise(String informacaoAnalise) {
		this.informacaoAnalise = informacaoAnalise;
	}
	
	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public Amostra getAmostraAnalise() {
		return amostraAnalise;
	}

	public void setAmostraAnalise(Amostra amostraAnalise) {
		this.amostraAnalise = amostraAnalise;
	}

	public Elementos getElementosAnalise() {
		return elementosAnalise;
	}

	public void setElementosAnalise(Elementos elementosAnalise) {
		this.elementosAnalise = elementosAnalise;
	}
    
    
    
    
 }
