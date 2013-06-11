package br.com.rti.alpha.controle;

import java.util.Map;

import org.zkoss.bind.Property;
import org.zkoss.bind.ValidationContext;
import org.zkoss.bind.validator.AbstractValidator;

public class PessoaValidator extends AbstractValidator
{

	@Override
	public void validate(ValidationContext ctx) {
		// TODO Auto-generated method stub
		Map<String,Property> beanProps = ctx.getProperties(ctx.getProperty().getBase());
		
		//first let's check the passwords match
		validatePasswords(ctx, (String)beanProps.get("senha").getValue(), (String)ctx.getValidatorArg("confirmarSenha"));
		//validateAge(ctx, (Integer)beanProps.get("age").getValue());
		//validateWeight(ctx, (Double)beanProps.get("weight").getValue());
		//validateEmail(ctx, (String)beanProps.get("email").getValue());
		//validateCaptcha(ctx, (String)ctx.getValidatorArg("captcha"), (String)ctx.getValidatorArg("captchaInput"));
	}
	
	private void validatePasswords(ValidationContext ctx, String password, String retype) {	
		if(password == null || retype == null || (!password.equals(retype))) {
			this.addInvalidMessage(ctx, "senha", "As senhas não conferem, redigite-as novamente!");
		}
	}
	
}
