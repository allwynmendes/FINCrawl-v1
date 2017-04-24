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
        Double[] d1 = new Double[5];
        Double buyPrice, stopLoss, profitBook, temp;
        int returnStatus, stockStatus, i = 0;
        map = db.getStockTrigger(symbol);
        for(Map.Entry m :map.entrySet()){
            d1[i] = (Double)m.getValue();
            i++;
        }
        temp = d1[0];
            returnStatus = temp.intValue();
            if (returnStatus == 1.0){
                buyPrice = d1[1];
                stopLoss = d1[2];
                profitBook = d1[3];
                temp = (Double)d1[4];
                stockStatus = temp.intValue();
                System.out.println("Stock       : " + symbol);
                System.out.println("Buy Price   : " + buyPrice);
                System.out.println("Stop Loss   : " + stopLoss);
                System.out.println("Profit Book : " + profitBook);
                System.out.println("Status      : " + stockStatus);
            }else{
                System.out.println("Stock does not exist in stockstrigger");
            }
    }
}
