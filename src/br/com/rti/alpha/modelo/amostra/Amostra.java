
package br.com.rti.alpha.modelo.amostra;

import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import br.com.rti.alpha.modelo.ativo.Ativo;
import br.com.rti.alpha.modelo.ativo.Compartimento;
import br.com.rti.alpha.modelo.ativo.Oleo;
import br.com.rti.alpha.modelo.pessoa.Pessoa;
import br.com.rti.alpha.modelo.supervisao.Supervisao;

@Entity
public class Amostra 
{
	@Id
	@GeneratedValue
    private int id;
    private java.util.Date dataAmostra;
    private java.math.BigInteger horimetro;
    private java.math.BigInteger horaTrabalhada;
    private int capacidade;
    private String descricaoFluido;
    private String oleoDrenado;
    private String filtroTrocado;
    private java.util.Date dataColeta;
    private java.util.Date dataEntrega;
    private String informacoes;
    
    @ManyToOne
    @JoinColumn(name="ativo_id")
    public Ativo ativoAmostra;
    
    @ManyToOne
    @JoinColumn(name="compartimento_id")
    public Compartimento compartimentoAmostra;
    
    @ManyToOne
    @JoinColumn(name="oleo_id")
    public Oleo oleoAmostra;
    
    @OneToMany(mappedBy="amostraFoto", fetch=FetchType.LAZY)
    private List<FotoAmostra> amostraFoto;
    
    @ManyToOne
    @JoinColumn(name="tipocoleta_id")    
    private TipoColeta tipoColetaAmostra;  
    
    @ManyToOne
    @JoinColumn(name="PlanoTrabalho_id")
    public PlanoTrabalho planoTrabalhoAmostra;
    
    @OneToOne(mappedBy="amostraAnalise", fetch = FetchType.LAZY)
    public Analise analise;
    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}	
	public java.util.Date getDataAmostra() {
		return dataAmostra;
	}
	public void setDataAmostra(java.util.Date dataAmostra) {
		this.dataAmostra = dataAmostra;
	}
	public java.math.BigInteger getHorimetro() {
		return horimetro;
	}
	public void setHorimetro(java.math.BigInteger horimetro) {
		this.horimetro = horimetro;
	}
	public java.math.BigInteger getHoraTrabalhada() {
		return horaTrabalhada;
	}
	public void setHoraTrabalhada(java.math.BigInteger horaTrabalhada){
		this.horaTrabalhada = horaTrabalhada;
	}
	public int getCapacidade() {
		return capacidade;
	}
	public void setCapacidade(int capacidade) {
		this.capacidade = capacidade;
	}
	public String getDescricaoFluido() {
		return descricaoFluido;
	}
	public void setDescricaoFluido(String descricaoFluido) {
		this.descricaoFluido = descricaoFluido;
	}
	public String getOleoDrenado() {
		return oleoDrenado;
	}
	public void setOleoDrenado(String oleoDrenado) {
		this.oleoDrenado = oleoDrenado;
	}
	public String getFiltroTrocado() {
		return filtroTrocado;
	}
	public void setFiltroTrocado(String filtroTrocado) {
		this.filtroTrocado = filtroTrocado;
	}
	public java.util.Date getDataColeta() {
		return dataColeta;
	}
	public void setDataColeta(java.util.Date dataColeta) {
		this.dataColeta = dataColeta;
	}
	public java.util.Date getDataEntrega() {
		return dataEntrega;
	}
	public void setDataEntrega(java.util.Date dataEntrega) {
		this.dataEntrega = dataEntrega;
	}
	public String getInformacoes() {
		return informacoes;
	}
	public void setInformacoes(String informacoes) {
		this.informacoes = informacoes;
	}
	public Ativo getAtivoAmostra() {
		return ativoAmostra;
	}
	public void setAtivo(Ativo ativoAmostra) {
		this.ativoAmostra = ativoAmostra;
	}
	public TipoColeta getTipoColetaAmostra() {
		return tipoColetaAmostra;
	}
	public void setTipoColetaAmostra(TipoColeta tipoColetaAmostra) {
		this.tipoColetaAmostra = tipoColetaAmostra;
	}
	public Compartimento getCompartimentoAmostra() {
		return compartimentoAmostra;
	}
	public void setCompartimentoAmostra(Compartimento compartimentoAmostra) {
		this.compartimentoAmostra = compartimentoAmostra;
	}
	public Oleo getOleoAmostra() {
		return oleoAmostra;
	}
	public void setOleoAmostra(Oleo oleoAmostra) {
		this.oleoAmostra = oleoAmostra;
	}
	public List<FotoAmostra> getAmostraFoto() {
		return amostraFoto;
	}
	public void setAmostraFoto(List<FotoAmostra> amostraFoto) {
		this.amostraFoto = amostraFoto;
	}
	public void setAtivoAmostra(Ativo ativoAmostra) {
		this.ativoAmostra = ativoAmostra;
	}
	public PlanoTrabalho getPlanoTrabalhoAmostra() {
		return planoTrabalhoAmostra;
	}
	public void setPlanoTrabalhoAmostra(PlanoTrabalho planoTrabalhoAmostra) {
		this.planoTrabalhoAmostra = planoTrabalhoAmostra;
	}
	public Analise getAnalise() {
		return analise;
	}
	public void setAnalise(Analise analise) {
		this.analise = analise;
	}
    
    
 }
