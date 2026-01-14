/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;
import java.util.ArrayList;
import java.util.Stack;

/**
 *
 * @author dell
 */
public class SaleModel {
    
    private ArrayList<Sale> sale;
    private Stack<ArrayList<Sale>> undoStack;
    
    public SaleModel()
    {
        sale = new ArrayList<Sale>();
    }
    public void addSale(Sale newSale) {
        sale.add(newSale);
    }
    public ArrayList<Sale> getAllSaleItems()  // return the sale for admin
    {
        return sale;
    }
    public ArrayList<Sale> getUserSales(String userName )
    {
      
                  ArrayList<Sale> userSales = new ArrayList<>();
                  if(userName == null)
                  {
                      return userSales;
                  }
                  for (Sale i : sale)
              {
                  if (i.getUsername().equals(userName) )
                  {
                      userSales.add(i);
                  }
              }
              return userSales;
           

    }
    public void clearSales() {
        undoStack.push(new ArrayList<>(sale)); // Save copy
        sale.clear();
    }
    public boolean undoClearSales() {
        if (undoStack.isEmpty()) {
            return false;
        }
        sale = undoStack.pop(); // restore old list
        return true;
    }
    
            
    
}
