/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;



/**
 *
 * @author dell
 */
public class item {
    
   private  String product_id;
   private String name;
  // private int category;
   private int minimum_stock_alert;
   private int quantity;
   private double price;
   
   public item(String product_id ,String name ,  int minimum_stock_alert , int quantity, double price )
   {
       this.product_id = product_id;
        this.name = name;
        //this.category = category;
         this.minimum_stock_alert = minimum_stock_alert;
         this.quantity = quantity;
          this.price = price;
   }
   
   public String getProductId()
   {
       return product_id;
   }
   public String getName()
   {
       return name;
   }
   /*public int getCategory()
   {
       return category;
   }*/
   public int getMinimum()
   {
       return minimum_stock_alert;
       
   }
   public int getQuantity()
   {
       return quantity;
   }
   public double getPrice()
   {
       return price;
   }
   public void setProductId(String product_id)
   {
       this.product_id = product_id;
       
   }
   public void setName(String Name)
   {
       this.name = Name;
   }
  /* public void setCategory(int Category)
   {
       this.category = Category;
   }*/
   public void setMinimum(int minimum_stock_alert)
   {
       this.minimum_stock_alert = minimum_stock_alert;
       
   }
   public void setQuantity(int Quantity)
   {
       this.quantity = Quantity;
   }
   public void setPrice(double price)
   {
    this.price = price;      
   }
   @Override
   public String toString()
   {
       return "Item{id = " + product_id + ", name = " + name + "" + ",Quantity" + quantity + "price" + price + "}";
   }
   
          
}
