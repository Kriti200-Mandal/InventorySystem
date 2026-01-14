/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;
import java.util.Stack;
import java.util.ArrayList;

/**
 *
 * @author dell
 */
public class InventoryModel {
    
   private ArrayList<item>Items;
   private Stack<item> recentItems;
   private Stack<String> actionStack;
   
        public InventoryModel()
        {
            Items = new ArrayList<>();
            recentItems =  new Stack<>();
            actionStack = new Stack<>();
            
            
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
            //recentItems.push(items);
            recentItems.push(new item(
        items.getProductId(),
        items.getName(),
        items.getQuantity(),
        items.getMinimum(),
        items.getPrice()
));
            actionStack.push("ADD");
            return true;
            
        }
        public ArrayList<item> getAllItems()
        {
            return Items;
        }
          public boolean deleteItem(String productId)
        {
            for(int index = 0; index < Items.size(); index++)
            {
                if(Items.get(index).getProductId().equalsIgnoreCase(productId))
                {
                    item deleted = Items.get(index);

            // push copy to stack
            recentItems.push(new item(
                deleted.getProductId(),
                deleted.getName(),
                deleted.getQuantity(),
                deleted.getMinimum(),
                deleted.getPrice()
            ));
                  actionStack.push("DELETE");
                    Items.remove(index);
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
         /*public boolean updateItem(String productId, int newQuantity, int min, double newPrice) {
    for (item i : Items) {
        if (i.getProductId().equalsIgnoreCase(productId)) {
            recentItems.push(new item(
                        i.getProductId(),
                        i.getName(),
                        i.getQuantity(),
                        i.getMinimum(),
                        i.getPrice()
                ));
            i.setQuantity(newQuantity);
            i.setMinimum(min);
            i.setPrice(newPrice);
            return true;
        }
    }
    return false;
}*/
           public boolean updateItem(String productId, int newQuantity, int min, double newPrice) {
        for (item i : Items) {
            if (i.getProductId().equalsIgnoreCase(productId)) {
                int oldQty = i.getQuantity();
                int oldMin = i.getMinimum();
                double oldPrice = i.getPrice();
               
                if (newQuantity == oldQty && min == oldMin && newPrice == oldPrice) {
                    return false;
                }
               
                recentItems.push(new item(
                    i.getProductId(),
                    i.getName(),
                    oldQty,
                    oldMin,
                    oldPrice

                ));
                 actionStack.push("UPDATE");
               
                i.setQuantity(newQuantity);
                i.setMinimum(min);
                i.setPrice(newPrice);
                return true;
            }
        }
       
        return false;
    }
         public item getItemById(String productId) {
    for (item i : Items) {
        if (i.getProductId().equalsIgnoreCase(productId)) {
            return i;
        }
    }
    return null;
}
        public boolean undo() {

    if (recentItems.isEmpty() || actionStack.isEmpty()) {
        return false;
    }

    String action = actionStack.pop();
    item oldItem = recentItems.pop();

    //  Undo ADD = remove that item
    if (action.equals("ADD")) {
        for (int i = 0; i < Items.size(); i++) {
            if (Items.get(i).getProductId().equalsIgnoreCase(oldItem.getProductId())) {
                Items.remove(i);
                return true;
            }
        }
        return false;
    }

    // Undo DELETE = add deleted item back
    if (action.equals("DELETE")) {
        Items.add(oldItem);
        return true;
    }

    //  Undo UPDATE = restore old values
    if (action.equals("UPDATE")) {
        for (item i : Items) {
            if (i.getProductId().equalsIgnoreCase(oldItem.getProductId())) {
                i.setQuantity(oldItem.getQuantity());
                i.setMinimum(oldItem.getMinimum());
                i.setPrice(oldItem.getPrice());
                return true;
            }
        }
        return false;
    }

    return false;
}

        
        
}
