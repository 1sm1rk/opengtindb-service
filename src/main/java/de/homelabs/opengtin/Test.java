package de.homelabs.opengtin;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.regex.Pattern;

public class Test {

	record MyClass(String error, String asin, String name, String detailname, String vendor,
			String maincat, String subcat, String contents, String origin, String descr) {};
	
	String response = "\n"
			+ "error=0\n"
			+ "---\n"
			+ "asin=\n"
			+ "name=Paprikapaste\n"
			+ "detailname=Rewe Beste Wahl Ajvar Mild\n"
			+ "vendor=Huberti GmbH, Dorfstrasse 61, 40776 Meerbusch, Deutschland\n"
			+ "maincat=Kochzutaten\n"
			+ "subcat=Gewürze\n"
			+ "maincatnum=13\n"
			+ "subcatnum=4\n"
			+ "contents=0\n"
			+ "origin=Deutschland\n"
			+ "descr=Zubereitung aus Paprika und Aubergine\\nZutaten: Paprika 84%, Aubergine 7%, Sonnenblumenöl 3%, Zucker, Speisesalz, scharfe Pfefferoni, Knoblauch, Branntweinsessig. \\n\\nNach dem Öffnen im Kühlschrank aufbewahren und innerhalb kurzer Zeit verbrauchen.\n"
			+ "name_en=\n"
			+ "detailname_en=\n"
			+ "descr_en=\n"
			+ "validated=0 %\n"
			+ "---\n";
	
	String response1 = "\n"
			+ "error=2\n"
			+ "---\n";
			
	String response2 = "\n"
			+ "error=0\n"
			+ "---\n"
			+ "asin=\n"
			+ "name=Paprikapaste\n"
			+ "detailname=Rewe Beste Wahl Ajvar Mild\n"
			+ "vendor=Huberti GmbH, Dorfstrasse 61, 40776 Meerbusch, Deutschland\n"
			+ "maincat=Kochzutaten\n"
			+ "subcat=Gewürze\n"
			+ "maincatnum=13\n"
			+ "subcatnum=4\n"
			+ "contents=0\n"
			+ "origin=Deutschland\n"
			+ "descr=Zubereitung aus Paprika und Aubergine\\nZutaten: Paprika 84%, Aubergine 7%, Sonnenblumenöl 3%, Zucker, Speisesalz, scharfe Pfefferoni, Knoblauch, Branntweinsessig. \\n\\nNach dem Öffnen im Kühlschrank aufbewahren und innerhalb kurzer Zeit verbrauchen.\n"
			+ "name_en=\n"
			+ "detailname_en=\n"
			+ "descr_en=\n"
			+ "validated=0 %\n"
			+ "---\n"
			+ "\n"
			+ "asin=\n"
			+ "name=\n"
			+ "detailname=Lukull Sauce Hollandaise Balance 250 ml\n"
			+ "vendor=Lukull\n"
			+ "maincat=\n"
			+ "subcat=\n"
			+ "maincatnum=-1\n"
			+ "subcatnum=\n"
			+ "contents=\n"
			+ "origin=Niederlande\n"
			+ "descr=Trinkwasser, entrahmte MILCH, pflanzliche Öle (Palm, Raps), EIGELB, modifizierte Stärke, Zucker, Speisesalz, Zitronensaftkonzentrat, Verdickungsmittel (Xanthan, Guarkernmehl, Johannisbrotkernmehl), Säuerungsmittel (Milchsäure), Aromen (mit MILCH, SELLERIE) Dextrose.Kann GLUTEN enthalten. Nährwertangaben je 100 ml Brennwert 710 kJ 170 kcal Fett 15,0 g davon gesättigte Fettsäuren 5,0 g Kohlenhydrate 6,0 g davon Zucker 3,0 g Eiweiß 2,0 g Salz 1,0 g Ballaststoffe 0,3 g Sonderpreis: geringes Mindesthaltbarkeitsdatum\n"
			+ "name_en=\n"
			+ "detailname_en=\n"
			+ "descr_en=\n"
			+ "validated=0 %\n"
			+ "---\n";
	
	String response3 = "\n"
			+ "error=2\n"
			+ "---\n"
			+ "\n";
	
	public void convert() {
		/*
		 * - analyse of the reponse for correctness
		 * - more than two occurrence of "---" means more products in reponse
		 * - add number of "---" means an error somware
		 * - split multiple products (if response has more than 2 "---")
		 *  
		 */
		String error_pattern = "[\\n]error=[0-9]+[\\n]---";
		
		Properties test = new Properties();
		
		try {
			//long error_marker_counter = Pattern.compile(error_pattern).matcher(response3).results().count();
			//long error_marker_counter = response3.
			//System.out.println("Error:"+error_marker_counter);
			String[] parts = response.split("---");
			
			//parse error code
			test.load(new ByteArrayInputStream(parts[0].getBytes()));
			if (!test.containsKey("error")) {
				System.out.println("Error code not found!");
				System.exit(1);
			} else {
				System.out.println("Status Code: " + test.getProperty("error"));
			}
			
			System.out.println("====");
			
			//normaly 3 parts, start, product, end, otherwise multiple products or error
			System.out.println("Products found: "+ (parts.length-2));
			
			for (int i=1; i<parts.length-1; i++) {
				System.out.println("---- Product "+(i-1)+" ----");
				Properties prod = new Properties();
				prod.load(new ByteArrayInputStream(parts[i].getBytes()));
				System.out.println("Name: "+prod.getProperty("name"));
				System.out.println("Detailname: "+prod.getProperty("detailname"));
				System.out.println("Vendor: "+prod.getProperty("vendor"));
				System.out.println("Origin: "+prod.getProperty("origin"));
				System.out.println("Descr: "+prod.getProperty("descr"));
			}
			
			/***********************************************************************************/
			for (String part : parts) {
				System.out.println("p:"+part);				
			}
			System.out.println("====");
			System.out.println("Num Objects:"+Pattern.compile("---").matcher(response).results().count());
			
			if (Pattern.compile("---").matcher(response).results().count() != 2) {
				/* error or more products */
				
			}
			
			/*test.load(new ByteArrayInputStream(response3.getBytes()));
			System.out.println("Error: "+test.getProperty("error"));
			System.out.println("Asin: "+test.getProperty("asin"));
			System.out.println("name: "+test.getProperty("name"));
			System.out.println("detailname: "+test.getProperty("detailname"));
			System.out.println("vendor: "+test.getProperty("vendor"));
			System.out.println("maincat"+test.getProperty("maincat"));
			System.out.println("subcat: "+test.getProperty("subcat"));
			System.out.println("maincatnum: "+test.getProperty("maincatnum"));
			System.out.println("subcatnum: "+test.getProperty("subcatnum"));
			System.out.println("contents: "+test.getProperty("contents"));
			System.out.println("origin: "+test.getProperty("origin"));
			System.out.println("descr: "+test.getProperty("descr"));
			System.out.println("name_en: "+test.getProperty("name_en"));
			System.out.println("detailname_en: "+test.getProperty("detailname_en"));
			System.out.println("descr_en: "+test.getProperty("descr_en"));
			System.out.println("validated: "+test.getProperty("validated"));*/
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.exit(0);
	}
}
