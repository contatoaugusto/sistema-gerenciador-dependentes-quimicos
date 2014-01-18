/**
 * @author Antonio Augusto
 */
package br.com.sgdq.app.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {

	public static Date formataData (String strData){
		return new Date();
	}

	public static Date converterData (String aaaaMMdd) {
		DateFormat formatar = null;
        Date convertido = null;
        try{     
        	formatar = new SimpleDateFormat("yyyyMMdd");
			convertido = (Date) formatar.parse(aaaaMMdd);
		} catch (ParseException e) {
			e.printStackTrace();
		}
        return convertido;
	}

	public static String space(String text, int qtdChar) {  
		while (text.length() <= qtdChar) {  
			text += " ";  
		}
		return text;
	}
}
