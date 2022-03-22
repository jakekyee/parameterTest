import java.util.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.*; 

public class stockAlgorithm {
    

   public static void main(String[] args) {
       try {
           Connection conn;
//           conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/numbers?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC", "root", "root");
        //    conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/numbers?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC", "root", "root");
         //  conn = DriverManager.getConnection("jdbc:mysql://localhost:1434/numbers?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC", "root", "root");
           conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/numbers","root","root");  
           System.out.println("Connection established OwO");
           
           
           

           
//Connection  connection;
//   try {
//      Class.forName("com.mysql.jdbc.Driver");
//connection = DriverManager.getConnection("jdbc:mariadb:numbers://localhost:3306/", "root", "");
          
//          for (int i = 0; i < 100000; i++) {
//                         connection = DriverManager.getConnection("jdbc:mariadb://localhost:" + i + "/numbers?user=root&password=");
//           if (connection != null) {
//              System.out.println("Connection established  " + i);
//           }


//           connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/numbers?user=root&password=root");
//           if (connection != null) {
//              System.out.println("Connection established owo");
//           }
//
//           
//            Statement stmt = connection.createStatement();
//            stmt.executeUpdate("");
//            stmt.close();
//            connection.close();
//       } catch (SQLException ex) {
//           Logger.getLogger(stockAlgorithm.class.getName()).log(Level.SEVERE, null, ex);

long start = System.currentTimeMillis();
String best = "";
double highest = 0;
//double currentResult = 0;
//for (double a = -1; a < 1; a = a + 0.1) {
//    for (double b= -1; b < 1; b = b + 0.1) {
//        for (double c = -1; c < 1; c = c + 0.1) {
//            for (double d = 0; d < 0.001; d = d + 0.0001) {
//                
//                
//                currentResult = lofiSim (a,b,c,d, 5, 60, 120, 125,  0.001, "btcTraining", conn);
//                if (currentResult > highest) {
//                    highest = currentResult;
//                    best = Double.toString(a) + Double.toString(b) + Double.toString(c) + Double.toString(d);
//                    System.out.println(best + " / " + highest);
//                }
//                
//            }
//        }
//    }
//}
long end = System.currentTimeMillis();
System.out.println(end - start);



//           for (double a = 0; a < 1; a = a + 0.1) {
//           for (double b= 0; b < 1; b = b + 0.1) {
//           for (double c = 0; c < 1; c = c + 0.1) {
//           for (double d = 0; d < 1; d = d + 0.1) {
//           for (double e= 0; e < 1; e = e + 0.1) {
//           for (double f = 0; f < 1; f = f + 0.1) {             
//
//           }
//           }
//           }
//           }
//           }
//           }
       } catch (SQLException ex) {
           Logger.getLogger(stockAlgorithm.class.getName()).log(Level.SEVERE, null, ex);
       }
           
           
       }
   





public static double lofiSim(double one, double two, double three, double sensitivity, int A, int B, int C, int timeline, double fee, String table, Connection conn) {
    try {

    double valueIn =1000;
    double valueOut = 0;
    double value = 0;
    double valueTotal = valueIn + valueOut;
    int size = 0;
    int iteration = 0;
    double currentPrice = 0;
    double lastPrice = 0;
    double pastPriceA = 0;
    double pastPriceB = 0;
    double currentAverage = 0;
    double xDayMovingAverage = 0;
    double yDayMovingAverage = 0;
    double decision =0;
    double change = 0;
    
    double memory[] = new double[timeline];
    ResultSet rset;
    String query = "SELECT close FROM "+ table + ";"; // Make query
    Statement stmt = conn.createStatement();
    
    rset = stmt.executeQuery(query); // Sent query
    
    while(rset.next()) {  
            if ( iteration < timeline) {
             memory[iteration] = rset.getDouble("close");
             iteration++;
            } else { 
                memory = shift(memory, rset.getDouble("close"));
                
                System.out.println(Arrays.toString(memory));
                
                currentPrice = memory[timeline-1];
                lastPrice = memory[timeline - A];
                pastPriceA = memory[timeline - B];
                pastPriceB = memory[timeline - C];
                currentAverage = currentPrice/lastPrice;
                xDayMovingAverage = currentPrice/pastPriceA;
                yDayMovingAverage = currentPrice/pastPriceB;
                valueIn = valueIn * currentAverage;
                
                
                System.out.println(currentAverage + " / " + currentPrice + " / " + lastPrice + " / " + valueIn + " / " + valueOut);
                //logic
                
                
                decision = (currentAverage*one + xDayMovingAverage*two + yDayMovingAverage*three)/3;
                
                if (decision > sensitivity) {
                 change = valueOut - valueIn;
                 if ( change > 0) {
                 valueIn = valueIn + (valueOut*(1-fee));   
                 }
                 }
                
                if (decision < (sensitivity*-1)) {
                change = valueIn - valueOut;
                 if ( change > 0) {
                 valueOut = valueOut + (valueIn*(1-fee));   
                 }
                }
                iteration++;
                //System.out.println(valueTotal + " / " + " / " + " / "+ iteration);
            
            }
    }
System.out.println(valueTotal);
return valueTotal;
    } catch (SQLException ex) {
           Logger.getLogger(stockAlgorithm.class.getName()).log(Level.SEVERE, null, ex);
       }
    return 0;
}

public static double lfSim(double one, double two, double three, double sensitivity, int A, int B, int C, int timeline, double fee, String table, Connection conn) {
    //one = weighting over past 5 min
    // two =
    // three = 
    try {
    double[][][][] valueIn = new double[20][20][20][10];
    double[][][][] valueOut = new double[20][20][20][10];
    double[][][][] number = new double[20][20][20][];
    Arrays.fill(valueIn, 1000);
    Arrays.fill(valueOut, 0);
    double value = 0;
    
    int size = 0;
    int iteration = 0;
    double currentPrice = 0;
    double lastPrice = 0;
    double pastPriceA = 0;
    double pastPriceB = 0;
    double currentAverage = 0;
    double xDayMovingAverage = 0;
    double yDayMovingAverage = 0;
    double decision =0;
    double change = 0;
    
    double memory[] = new double[timeline];
    ResultSet rset;
    String query = "SELECT close FROM "+ table + ";"; // Make query
    Statement stmt = conn.createStatement();
    
    rset = stmt.executeQuery(query); // Sent query
    
    while(rset.next()) {  
            if ( iteration < timeline) {
             memory[iteration] = rset.getDouble("close");
             iteration++;
            } else { 
                memory = shift(memory, rset.getDouble("close"));
                
                System.out.println(Arrays.toString(memory));
                
                currentPrice = memory[timeline-1];
                lastPrice = memory[timeline - A];
                pastPriceA = memory[timeline - B];
                pastPriceB = memory[timeline - C];
                currentAverage = currentPrice/lastPrice;
                xDayMovingAverage = currentPrice/pastPriceA;
                yDayMovingAverage = currentPrice/pastPriceB;
                
                decision = (1 - currentAverage) + (1 - xDayMovingAverage);
                
               
                //logic
                
                
                for (int a = 0; a < 19; a = a + 1) {
                for (int b = 0; b < 19; b = b + 1) {
                for (int c = 0; c < 19; c = c + 1) {
                for (int d = 0; d < 9; d = c + 1) {
                valueIn[a][b][c][d] = valueIn[a][b][c][d]*currentAverage;
                    
                    if (decision > (a-10)/100) {
                    valueIn[a][b][c][d] = valueIn[a][b][c][d] + ((valueOut[a][b][c][d]*((d+1)/10)) *(1-fee));
                    }
                
                    if (decision > (b-10)/100) {
                    valueIn[a][b][c][d] = valueIn[a][b][c][d] + ((valueOut[a][b][c][d]*((d+1)/10)) *(1-fee));
                    }
                }
                }
               
                    
                    
                    
                    
                    
                }
                }
                
                
                
                
                decision = (currentAverage*one + xDayMovingAverage*two + yDayMovingAverage*three)/3;
                
               
                iteration++;
                //System.out.println(valueTotal + " / " + " / " + " / "+ iteration);
            
            }
    }
System.out.println(valueTotal);
return valueTotal;
    } catch (SQLException ex) {
           Logger.getLogger(stockAlgorithm.class.getName()).log(Level.SEVERE, null, ex);
       }
    return 0;
}








public static double[] shift( double[] array, double add) {
double tempArray[] = new double[array.length];
    for (int i = 0; i < array.length-1  ; i++) {
        tempArray[i] = array[i+1];
    }
tempArray[array.length-1] = add;
return tempArray;
}

   
   
public static double optimal(double one, double two, double three, double four, double five, double six) {
double x = 1;
double currentVolume = 0;
double currentPrice = 0;
double xDayMovingAverage = 0;
double yDayMovingAverage = 0;
double factor1 = xDayMovingAverage * one; // Average over long period of time
double factor2 = yDayMovingAverage * two; // Average over short period of time
double factor3 = 0;
double factor4 =0;
double factor5 =0;





double odds = 0;



return x;
}
}   


