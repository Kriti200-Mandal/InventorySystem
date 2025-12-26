/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;
import java.util.Stack;
import java.util.ArrayList;
import java.util.HashSet;

/**
 *
 * @author dell
 */
public class InventoryModel {
    
   private ArrayList<item>Items;
   private Stack<item> recentItems;
   
        public InventoryModel()
        {
            Items = new ArrayList<>();
            recentItems =  new Stack<>();
            
            
        }
        public boolean addItem(item items)
        {
            for(item i : Items)
            {
                if (i.getProductId().equalsIgnoreCase(items.getProductId())) 
  
                {
                    return false;
                }
            }
            Items.add(items);
            recentItems.push(items);
            return true;
            
        }
        public ArrayList<item> getAllItems()
        {
            return Items;
        }
          public boolean deleteItem(String produtId)
        {
            for(item i : Items)
            {
                if(i.getProductId().equalsIgnoreCase(produtId))
                {
                    Items.remove(i);
                      return true;
                }
                  
                }
                return false;
            }
          public ArrayList<item> getLowStockItem()
          {
              ArrayList<item> lowStock = new ArrayList<>();
              for (item i : Items)
              {
                  if (i.getQuantity() <= i.getMinimum())
                  {
                      lowStock.add(i);
                  }
              }
              return lowStock;
          } 
          public item getLastAddedItem()
          {
              if(recentItems.isEmpty())
              {
                  return null;
              }
              return recentItems.peek();
          }
         public boolean updateItem(String productId, int newQuantity, int min, double newPrice) {
    for (item i : Items) {
        if (i.getProductId().equalsIgnoreCase(productId)) {
            i.setQuantity(newQuantity);
            i.setMinimum(min);
            i.setPrice(newPrice);
            return true;
        }
    }
    return false;
}
          
        
        
}
