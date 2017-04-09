/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fincrawl;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.Calendar;

public class FinCrawl {
    static Scanner in = new Scanner(System.in);
    static DBConnect db = new DBConnect();
    static WebCrawler wc = new WebCrawler();
    static BuySellTriggers bst = new BuySellTriggers();
    
    public static void readCsv(String fileName) throws Exception{
        String splitBy = ",";
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String line = br.readLine();
        System.out.println("SYMBOL     COMPANY NAME");
        while((line = br.readLine()) != null){
            String[] b = line.split(splitBy);
            System.out.println(b[0] + " " +b[1]);
            db.insertStocksDB(b[0], b[1]);
        }
        br.close();
    }
    public static void pollStock(String selectedSymbol, int interval, int iterate) throws Exception{
        Double stockPrice = 1.0;
        Calendar now = Calendar.getInstance();
        int hour, minute, second;
        for(int i=0; i<iterate; i++){
            stockPrice = wc.crawlGoogleFinance(selectedSymbol);
            now = Calendar.getInstance();
            hour = now.get(Calendar.HOUR_OF_DAY);
            minute = now.get(Calendar.MINUTE);
            second = now.get(Calendar.SECOND);
            System.out.println(stockPrice + " at " 
                    + hour + ":" + minute + ":" + second);
            Thread.sleep(interval);
        }
        Date date = new Date();
        db.updateStockPrice(selectedSymbol, stockPrice, new SimpleDateFormat("dd MM yyyy hh:mm:ss aaa").format(date));
    }
    
    public static void main(String[] args) throws Exception{
        //readCsv("EQUITY_L.csv");
        //db.testDB();
        String selectedSymbol = db.searchStockDB("relian");
        System.out.println("Symbol : " + selectedSymbol);        
        pollStock(selectedSymbol, 1000, 1);
        //wc.getHistorical(selectedSymbol);
        bst.insertStock(selectedSymbol);
    }
}
