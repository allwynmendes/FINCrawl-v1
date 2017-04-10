/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fincrawl;

import java.util.Scanner;

public class BuySellTriggers {
    
    static DBConnect db = new DBConnect();
    
    //stopLoss and profitBook is in Percentage
    void insertStock(String symbol) throws Exception{
        Scanner in = new Scanner(System.in);
        System.out.println("Price and Trigger Values for " + symbol);
        System.out.println("*******************************************");
        System.out.println("Buy Price     : ");
        double buyPrice = in.nextDouble(); 
        System.out.println("Stop Loss %   : ");
        double stopLoss = in.nextDouble(); 
        System.out.println("Profit Book % : ");
        double profitBook = in.nextDouble();
        int status = 1;
        int success = db.insertStockTrigger(symbol, buyPrice, stopLoss, profitBook, status);
        if (success == 1)
            System.out.println("Stock Successfully Inserted");
    }
    
}
