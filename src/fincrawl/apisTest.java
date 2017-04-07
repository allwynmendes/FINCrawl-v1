/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fincrawl;

import java.util.Calendar;
import java.util.List;
import yahoofinance.*;
import yahoofinance.histquotes.HistoricalQuote;

public class apisTest{
 
    void getStockHistory() throws Exception{
        Calendar from = Calendar.getInstance();
        Calendar to = Calendar.getInstance();
        from.add(Calendar.YEAR, -1); // from 1 year ago
        yahoofinance.Stock google = yahoofinance.YahooFinance.get("NSE:LOVABLE");
        List<HistoricalQuote> googleHistQuotes = google.getHistory();
        System.out.println(googleHistQuotes);
    }
}
