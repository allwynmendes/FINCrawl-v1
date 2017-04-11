/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fincrawl;
import java.sql.*;
import java.util.Scanner;
import oracle.jdbc.*;
import java.util.Date;
import java.util.*;
        

public class DBConnect{
    Map<Integer, Double> map = new HashMap<Integer, Double>();
    static Scanner in = new Scanner(System.in);
    private static String URL = "jdbc:oracle:thin:@localhost:1521:XE";
    //private static String dburl1 = "jdbc:mysql://127.0.0.1:3306/mydb";
    private static String USER = "hr";
    private static String PASS = "hr";
    
    void testDB() throws SQLException{
        DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
        Connection con = DriverManager.getConnection(URL, USER, PASS);
        Statement statement = con.createStatement();
        ResultSet rs = statement.executeQuery("Select * from employees");
        int i = 0;
        while(rs.next()){
            String firstName = rs.getString("first_name");               
            System.out.println("Name is  " + firstName);
            i++;
        }
        System.out.println("There are " + i + " employes");
        rs.close();
        statement.close();
        con.close();
    }
    
    void insertStocksDB(String symbol, String name){
        try{
        DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
        Connection con = DriverManager.getConnection(URL, USER, PASS);
        Statement statement = con.createStatement();
        statement.executeQuery("insert into stocks values('" + symbol + "', '" + name + "')");
        statement.close();
        con.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    String searchStockDB(String name) throws Exception{
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            Connection con = DriverManager.getConnection(URL, USER, PASS);
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("select * from stocks where upper(name) like upper('%" + name + "%')");
            String[] Symbol = new String[200];
            String[] companyName = new String[200];
            Symbol[0] = "NULL";
            int i = 0;
            //System.out.println("Symbol   Name");
            while(rs.next()){
                Symbol[i] = rs.getString("symbol");               
                companyName[i] = rs.getString("name");
                i++;
            }
            if(i == 1)
                return Symbol[0];
            if(i>1){
                int j = 0;
                System.out.println("Select Company : ");
                while(i>j){
                    System.out.println(j + ". " + companyName[j]);
                    j++;
                }
                System.out.print("Select Stock : ");
                int ch = in.nextInt();
                //System.out.println(ch);
                return Symbol[ch];
            }
            return Symbol[0];
    }
    
    void updateStockPrice(String symbol, Double price, String date){
        try{
        DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
        Connection con = DriverManager.getConnection(URL, USER, PASS);
        Statement statement = con.createStatement();
        //using stored procedures makes life easy
        statement.execute("call updateStockPrice('" + symbol + "', " + price + ", to_date('"+date+ "', 'dd mm yyyy hh:mi:ss am'))");
        con.close();
        System.out.println(symbol + " updated");
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    int insertStockTrigger(String symbol, Double buyPrice, Double stopLoss, Double profitBook, int status) throws SQLException{
        DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
        Connection con = DriverManager.getConnection(URL, USER, PASS);
        Statement statement = con.createStatement();
        String query = "call updateStockTriggers('" + symbol + "', " + buyPrice + ", " + stopLoss + ", " + profitBook + ", " + status + ")";
        statement.execute(query);
        con.close();
        return 1;
    }

    Map getStockTrigger(String symbol) throws SQLException{
        DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
        Connection con = DriverManager.getConnection(URL, USER, PASS);
        Statement statement = con.createStatement();
        String query = "select * from stockstrigger where symbol = '" + symbol + "'";
        ResultSet rs = statement.executeQuery(query);
        Double buyPrice;
        Double stopLoss;
        Double profitBook;
        int status;
        int i=0;
        while(rs.next()){
            buyPrice = rs.getDouble("buyprice");
            stopLoss = rs.getDouble("stoploss");
            profitBook = rs.getDouble("profitbook");
            status = rs.getInt("status");
            map.put(0, 1.0);
            map.put(1, buyPrice);
            map.put(2, stopLoss);
            map.put(3, profitBook);
            map.put(4, (double)status);
            i++;
        }
        if(i==1)
            return map;
        else{
            map.put(0, 0.0);
            return map;
        }
    }
}
