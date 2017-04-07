/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fincrawl;

import java.io.*;
import java.net.URL;
import java.util.Iterator;
import javax.lang.model.element.Element;
import javax.swing.text.Document;
import org.jsoup.*;
import org.jsoup.select.Elements;


public class WebCrawler {
    double crawlGoogleFinance(String symbol) throws Exception{

        String data1, data2=null;
        String toCrawl = "http://www.google.com/finance?q=NSE%3A" + symbol;     
        Double currentPrice;
        
        URL oracle = new URL(toCrawl);
        BufferedReader in = new BufferedReader(new InputStreamReader(oracle.openStream()));
        while((data1=in.readLine()) !=  null){
            data2 = data2 + data1 + "\n";
        }
         //System.out.println(data2);
         
         org.jsoup.nodes.Document document = Jsoup.parse(data2); 
         currentPrice = Double.parseDouble(document.body().getElementById("price-panel")
                 .getElementsByClass("pr").select(":last-child").text().replace(",", ""));
        
        return currentPrice;
    }


    void getHistorical(String symbol) throws Exception{
        String data1, data2=null;
        String toCrawl = "http://www.google.com/finance/historical?q=NSE%3A" + symbol;     
        Double currentPrice;
        
        URL oracle = new URL(toCrawl);
        BufferedReader in = new BufferedReader(new InputStreamReader(oracle.openStream()));
        while((data1=in.readLine()) !=  null){
            data2 = data2 + data1 + "\n";
        }
        
        org.jsoup.nodes.Document document = Jsoup.parse(data2); 
        org.jsoup.nodes.Element table = document.select("table[class=gf-table historical_price]").first();
        Iterator<org.jsoup.nodes.Element> ite = table.select("tr").iterator();
        
        String hOpen, hHigh, hLow, hClose, hVolume;
        ite.next();
        while(ite.hasNext()){
            String toClean1 = ite.next().text();
            String hDate = toClean1.substring(0, 12).replace(",", "");
            String toClean2 = toClean1.substring(12, toClean1.length());
            if (" ".equals(toClean2.substring(0,1))){
                toClean2 = toClean2.substring(1);
            }
            String b[] = toClean2.split(" ");
            hOpen = b[0];
            hHigh = b[1];
            hLow = b[2];
            hClose = b[3];
            hVolume = b[4].replace(",", "");
            System.out.println(hDate + " " + hOpen + " " + hHigh + " " + hLow + " " + hClose + " " + hVolume);
        }
    }
}