
package br.com.rti.alpha.modelo.amostra;

import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Elementos 
{
	@Id
	@GeneratedValue
    private int id;
    private double st;
    private double sul;
    private double fe;
    private double cu;
    private double cr;
    private double pb;
    private double sn;
    private double mo;
    private double ni;
    private double ag;
    private double al;
    private double si;
    private double na;
    private double k;
    private double ca;
    private double p;
    private double zn;
    private double mg;
    private double b;
    private double ba;
    private String w;
    private String f;
    private double viscosidade;
    private double fuligem;
    private double oxidacao;
    private double nitracao;
    private double sulfatacao;
    private double tbn;
    private double agua;
    private double iso4u;
    private double iso6u;
    private double iso14u;
    private double norma4u;
    private double norma6u;
    private double norma14u;
    private String filtro;
    private double diesel;
    private double zddp;
        
    //public br.com.rti.alpha.modelo.amostra.LimiteTendencia limiteTendencia;

    @OneToMany(mappedBy="elementosAnalise", fetch=FetchType.LAZY)
    public List<Analise> analise;
    
   
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public double getSt() {
		return st;
	}
	public void setSt(double st) {
		this.st = st;
	}
	public double getOxidacao() {
		return oxidacao;
	}
	public void setOxidacao(double oxidacao) {
		this.oxidacao = oxidacao;
	}
	public double getNitracao() {
		return nitracao;
	}
	public void setNitracao(double nitracao) {
		this.nitracao = nitracao;
	}
	public double getSul() {
		return sul;
	}
	public void setSul(double sul) {
		this.sul = sul;
	}
	public double getFe() {
		return fe;
	}
	public void setFe(double fe) {
		this.fe = fe;
	}
	public double getCu() {
		return cu;
	}
	public void setCu(double cu) {
		this.cu = cu;
	}
	public double getCr() {
		return cr;
	}
	public void setCr(double cr) {
		this.cr = cr;
	}
	public double getPb() {
		return pb;
	}
	public void setPb(double pb) {
		this.pb = pb;
	}
	public double getSn() {
		return sn;
	}
	public void setSn(double sn) {
		this.sn = sn;
	}
	public double getMo() {
		return mo;
	}
	public void setMo(double mo) {
		this.mo = mo;
	}
	public double getNi() {
		return ni;
	}
	public void setNi(double ni) {
		this.ni = ni;
	}
	public double getAg() {
		return ag;
	}
	public void setAg(double ag) {
		this.ag = ag;
	}
	public double getAl() {
		return al;
	}
	public void setAl(double al) {
		this.al = al;
	}
	public double getSi() {
		return si;
	}
	public void setSi(double si) {
		this.si = si;
	}
	public double getNa() {
		return na;
	}
	public void setNa(double na) {
		this.na = na;
	}
	public double getK() {
		return k;
	}
	public void setK(double k) {
		this.k = k;
	}
	public double getCa() {
		return ca;
	}
	public void setCa(double ca) {
		this.ca = ca;
	}
	public double getP() {
		return p;
	}
	public void setP(double p) {
		this.p = p;
	}
	public double getZn() {
		return zn;
	}
	public void setZn(double zn) {
		this.zn = zn;
	}
	public double getMg() {
		return mg;
	}
	public void setMg(double mg) {
		this.mg = mg;
	}
	public double getB() {
		return b;
	}
	public void setB(double b) {
		this.b = b;
	}
	public double getBa() {
		return ba;
	}
	public void setBa(double ba) {
		this.ba = ba;
	}
	public String getW() {
		return w;
	}
	public void setW(String w) {
		this.w = w;
	}
	public String getF() {
		return f;
	}
	public void setF(String f) {
		this.f = f;
	}
	public double getViscosidade() {
		return viscosidade;
	}
	public void setViscosidade(double viscosidade) {
		this.viscosidade = viscosidade;
	}
	public double getFuligem() {
		return fuligem;
	}
	public void setFuligem(double fuligem) {
		this.fuligem = fuligem;
	}
	public double getSulfatacao() {
		return sulfatacao;
	}
	public void setSulfatacao(double sulfatacao) {
		this.sulfatacao = sulfatacao;
	}
	public double getTbn() {
		return tbn;
	}
	public void setTbn(double tbn) {
		this.tbn = tbn;
	}
	public double getAgua() {
		return agua;
	}
	public void setAgua(double agua) {
		this.agua = agua;
	}
	public double getIso4u() {
		return iso4u;
	}
	public void setIso4u(double iso4u) {
		this.iso4u = iso4u;
	}
	public double getIso6u() {
		return iso6u;
	}
	public void setIso6u(double iso6u) {
		this.iso6u = iso6u;
	}
	public double getIso14u() {
		return iso14u;
	}
	public void setIso14u(double iso14u) {
		this.iso14u = iso14u;
	}
	public double getNorma4u() {
		return norma4u;
	}
	public void setNorma4u(double norma4u) {
		this.norma4u = norma4u;
	}
	public double getNorma6u() {
		return norma6u;
	}
	public void setNorma6u(double norma6u) {
		this.norma6u = norma6u;
	}
	public double getNorma14u() {
		return norma14u;
	}
	public void setNorma14u(double norma14u) {
		this.norma14u = norma14u;
	}	
	public String getFiltro() {
		return filtro;
	}
	public void setFiltro(String filtro) {
		this.filtro = filtro;
	}
	public double getDiesel() {
		return diesel;
	}
	public void setDiesel(double diesel) {
		this.diesel = diesel;
	}
	public double getZddp() {
		return zddp;
	}
	public void setZddp(double zddp) {
		this.zddp = zddp;
	}
	/*public br.com.rti.alpha.modelo.amostra.LimiteTendencia getLimiteTendencia() {
		return limiteTendencia;
	}
	public void setLimiteTendencia(
			br.com.rti.alpha.modelo.amostra.LimiteTendencia limiteTendencia) {
		this.limiteTendencia = limiteTendencia;
	}*/
	public List<Analise> getAnalise() {
		return analise;
	}
	public void setAnalise(List<Analise> analise) {
		this.analise = analise;
	}
    
    
 }
