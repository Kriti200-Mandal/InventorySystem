/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;
import java.time.LocalDateTime;

/**
 *
 * @author dell
 */
public class Sale {
     private  String product_id;
   private String name;
   private int quantity;
   private double price;
   private String userName;
   private LocalDateTime Date;
   
   
   public Sale(String product_id , String name , int quantity , double price , String userName, LocalDateTime Date)
   {
       this.product_id = product_id;
       this.name = name ;
       this.price = price;
       this.quantity = quantity;
       this.userName = userName;
       this.Date = Date;
   }
   public String getProductId()
   {
       return product_id;
   }
    public String getName()
   {
       return name;
   } 
     public double  getPrice()
   {
       return price;
   }
      public int  getQuantity()
   {
       return quantity;
   }
       public String getUsername()
   {
       return userName ;
   }
        public LocalDateTime getDate()
   {
       return Date;
   }
       @Override
   public String toString()
   {
       return "Sale{id = " + product_id + ", name = " + name + "" + ",Quantity" + quantity + "price" + price +  "Date" + Date +"}";
   }
   
   
   
    
}
