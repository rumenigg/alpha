package br.com.rti.alpha.modelo.amostra;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import br.com.rti.alpha.modelo.ativo.Ativo;
import br.com.rti.alpha.modelo.ativo.Compartimento;

@Entity
public class Laudos {
	@Id
	@GeneratedValue
	private int id;
	private String descricao;
	private String arquivo;
	private String vistoriado;
	private String obs;
	
	@ManyToOne
    @JoinColumn(name="Ativo_id")
    private Ativo ativoLaudos;

    @ManyToOne
    @JoinColumn(name="Compartimento_id")
    private Compartimento compartimentoLaudos;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getArquivo() {
		return arquivo;
	}

	public void setArquivo(String arquivo) {
		this.arquivo = arquivo;
	}

	public String getVistoriado() {
		return vistoriado;
	}

	public void setVistoriado(String vistoriado) {
		this.vistoriado = vistoriado;
	}

	public String getObs() {
		return obs;
	}

	public void setObs(String obs) {
		this.obs = obs;
	}

	public Ativo getAtivoLaudos() {
		return ativoLaudos;
	}

	public void setAtivoLaudos(Ativo ativoLaudos) {
		this.ativoLaudos = ativoLaudos;
	}

	public Compartimento getCompartimentoLaudos() {
		return compartimentoLaudos;
	}

	public void setCompartimentoLaudos(Compartimento compartimentoLaudos) {
		this.compartimentoLaudos = compartimentoLaudos;
	}
    
	

}
