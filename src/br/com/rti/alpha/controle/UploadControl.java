package br.com.rti.alpha.controle;

import org.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException; 
import org.zkoss.zk.au.http.AuUploader;

public class UploadControl extends AuUploader 
{   	 
	   protected String handleError(Throwable ex) {                
	      if ( ex instanceof SizeLimitExceededException )
	      {            
	         SizeLimitExceededException e = (SizeLimitExceededException) ex;           
	         return "O arquivo é maior do que o permitido. O limite permitido de 200KB. Diminua o tamanho do arquivo ou escolha um menor.";      
	      }        
	      return super.handleError(ex);    
	   } 
}
