
package br.com.rti.alpha.modelo.ativo;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import br.com.rti.alpha.modelo.amostra.Amostra;

@Entity
public class Oleo {
	
	@Id
	@GeneratedValue
    private int id;
    private String referencia;
    private String fabricante;
    private String obs;
    private String foto;
    private int tendenciaViscosidade;
    
    @OneToMany(mappedBy="oleo")
    private Set<Compartimento> compartimento;
    
    @OneToMany(mappedBy="oleoAmostra")
    private Set<Amostra> amostra;
    
    public Oleo()
    {
    	
    }
	
    public int getId() 
    {
		return id;
	}
    
	public void setId(int id) 
	{
		this.id = id;
	}
	
	public String getReferencia() 
	{
		return referencia;
	}
	
	public void setReferencia(String referencia) 
	{
		this.referencia = referencia;
	}
	
	public String getFabricante() 
	{
		return fabricante;
	}
	
	public void setFabricante(String fabricante) 
	{
		this.fabricante = fabricante;
	}
	
	public String getObs() 
	{
		return obs;
	}
	
	public void setObs(String obs) 
	{
		this.obs = obs;
	}
	
	public String getFoto() 
	{
		return foto;
	}
	
	public void setFoto(String foto) 
	{
		this.foto = foto;
	}
	
	public int getTendenciaViscosidade() 
	{
		return tendenciaViscosidade;
	}
	
	public void setTendenciaViscosidade(int tendenciaViscosidade) 
	{
		this.tendenciaViscosidade = tendenciaViscosidade;
	}
	
    public Set<Compartimento> getCompartimento()
    {
    	return this.compartimento;
    }
    
    public void setCompartimento(Set<Compartimento> compartimento)
    {
    	this.compartimento = compartimento;
    }

	public Set<Amostra> getAmostra() {
		return amostra;
	}

	public void setAmostra(Set<Amostra> amostra) {
		this.amostra = amostra;
	}
    
    
 }
