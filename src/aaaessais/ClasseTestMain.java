package aaaessais;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import outils.Prix;
import outils.UniteMesure;

/**
 * @author aympa
 *
 * Classe qui permet de tester ce qu'on vient d'écrire (classe impropre)
 */
public class ClasseTestMain {
	
	public static void main(String[] args) throws ParseException {
		
		//Test des Unites Mesures et Prix :
		
			//Test des différents constructeurs : 
		UniteMesure u = new UniteMesure("Litres", "L");
		System.out.println(u.toString());
		
		//UniteMesure u1 = new UniteMesure("L");
		
		Prix p = new Prix(30);
		System.out.println(p.toString());
		
		Prix p1 = new Prix(31.543, "E");
		System.out.println(p1.toString());
		
		Prix p2 = new Prix(34, "Euros", "€");
		System.out.println(p2.toString());
		
		
			//Test de la méthode comparerPrix
		System.out.println(p.comparerPrix(p1));
		
		System.out.println(p1.comparerPrix(p));
		
		System.out.println(p.comparerPrix(p));
		
		
			//Test des constructeurs par copie
		Prix pp = new Prix(p1);
		
		System.out.println(pp.toString());
		System.out.println(pp.equals(p1));
		System.out.println(p1.toString());
		
		UniteMesure u2 = new UniteMesure(u);
		System.out.println(u2.toString());
		System.out.println(u.toString());
		
		
		System.out.println(UUID.randomUUID().toString());
		
//        String input = "Tue Apr 23 09:52:06 CEST 2019";
//        SimpleDateFormat parser = new SimpleDateFormat("EEE MMM d HH:mm:ss zzz yyyy");
//        Date date = parser.parse(input);
//        System.out.println(date.toString());
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//        String formattedDate = formatter.format(date);
		
//		System.out.println(Date.parse("Tue Apr 23 09:52:06 CEST 2019"));
//		System.out.println(d.toString());
		SimpleDateFormat parser = new SimpleDateFormat("dd/MM/yy");
		Date d = Calendar.getInstance().getTime();
		String strDate = parser.format(d);
		System.out.println(strDate.toString());
		System.out.println(d.toString());

		
	}

}

