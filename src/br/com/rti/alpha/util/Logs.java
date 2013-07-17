package br.com.rti.alpha.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import org.apache.poi.hssf.record.formula.functions.T;
import org.hibernate.Hibernate;
import org.hibernate.Session;

import br.com.rti.alpha.dao.Dao;
import br.com.rti.alpha.dao.DaoFactory;

public class Logs extends DaoFactory{
	private  Session session;
	File arquivo;
	FileReader fr;
	BufferedReader br;
	FileWriter fw;
	BufferedWriter bw;
	
	public Logs(String erros) {
		escreverLog(erros);
	}

	private void escreverLog(String erros){
		try{	
			arquivo = new File("Loger.lg");//cria arquivo de log
			fr = new FileReader(arquivo);
			br = new BufferedReader(fr);
			Vector texto = new Vector();
			while(br.ready()){
				texto.add(br.readLine());
			}
			/**
			 * escrever no arquivo
			 */
			fw = new FileWriter(arquivo);
			bw = new BufferedWriter(fw);
			//escreve o conteudo atual do arqvo
			for (int i=0;i<texto.size();i++) {
				bw.write(texto.get(i).toString());//grava linha por linha
				bw.newLine();
			}
			//escreve os erros
			bw.write(erros);
			br.close();
			bw.close();
			
		}catch (FileNotFoundException e) {
			try {
				arquivo.createNewFile();
				escreverLog(erros);
				
			} catch (IOException e1) {
				
				e1.printStackTrace();
			}
			
		}
		catch(IOException er){
			System.exit(0);
		}
	}
	public static void main (String [] args){
		Logs l = new Logs("");
		l.session =  HibernateUtil.getSessionFactory().getCurrentSession();	
		Date dt = new Date();
		DaoFactory df = new DaoFactory();
		Dao d = new Dao<Class<T>>(l.session, df.getClass()); 	
		
		
		
		new Logs("");
	}
	
	
}
