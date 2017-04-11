/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fincrawl;

import java.util.*;
import java.util.Scanner;

public class BuySellTriggers {
    Map<Integer, Double> map = new HashMap<Integer, Double>();
    static DBConnect db = new DBConnect();
    
    //stopLoss and profitBook is in Percentage
    void insertStock(String symbol) throws Exception{
        Scanner in = new Scanner(System.in);
        System.out.println("Price and Trigger Values for " + symbol);
        System.out.println("*******************************************");
        System.out.println("Buy Price         : ");
        double buyPrice = in.nextDouble(); 
        System.out.println("Stop Loss Price   : ");
        double stopLoss = in.nextDouble(); 
        System.out.println("Profit Book Price : ");
        double profitBook = in.nextDouble();
        int status = 1;
        int success = db.insertStockTrigger(symbol, buyPrice, stopLoss, profitBook, status);
        if (success == 1)
            System.out.println("Stock Successfully Inserted");
    }
    
    /*This functions polls Google Finance for 
     * price trigger range set by user
    */
    void pollStockTrigger(String symbol) throws Exception{
        map = db.getStockTrigger(symbol);
        for(Map.Entry m :map.entrySet()){
            System.out.println(m.getKey() + " " + m.getValue());
        }
    }
}
