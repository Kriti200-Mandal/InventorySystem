/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Model.InventoryModel;
import Model.SaleModel;
import View.UserView;
import Model.item;
import Model.Sale;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.swing.JOptionPane;
import View.PurchaseView;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author dell
 */
public class UserController {
    private InventoryModel inventoryModel;
    private SaleModel saleModel;
    private UserView userView;
    private PurchaseView pv;
    
    
    public UserController(InventoryModel inventoryModel, SaleModel saleModel, UserView userView)
    {
        this.inventoryModel = inventoryModel;
        this.userView = userView;
        this.saleModel = saleModel;
        
        
    }
    public void buyProduct(String username)
    {
       int row = userView.getInventoryTable().getSelectedRow();
       if(row == -1)
       {
           JOptionPane.showMessageDialog(userView, "please select a product to buy");
           return;
       }
       String productId = userView.getInventoryTable().getValueAt(row, 0).toString();
       item product = inventoryModel.getItemById(productId); // item is the class and product is the variable
       if(product == null)
       {
           JOptionPane.showMessageDialog(userView, "Product not found");
           return;
       }
       int buyQty;
       try{
         String  inputQty = JOptionPane.showInputDialog(userView,"Enter quantity to buy");
         if(inputQty == null || inputQty.trim().isEmpty())
         {
             JOptionPane.showMessageDialog(userView, "Quantity cannot be empty");
             return;
         }
           buyQty = Integer.parseInt(inputQty.trim());
       }catch(NumberFormatException e)
       {
           JOptionPane.showMessageDialog(userView, "Invalid Quantity, Enter a Number");
           return;
       }
           if(buyQty<=0)
           {
               JOptionPane.showMessageDialog(userView, "Quantity must be greater than 0");
               return;
           }
         if (buyQty>product.getQuantity())
         {
             JOptionPane.showMessageDialog(userView, "Not Enough Stock\n Avalable stock"+product.getQuantity(),
                     "Stock Error",JOptionPane.ERROR_MESSAGE);
             return;
         }
       
    

       //Reduce Stock
       int newQty = product.getQuantity()- buyQty;
       inventoryModel.updateItem(productId, newQty, product.getMinimum(), product.getPrice());
       
        // Add sale record
        Sale sale = new Sale(productId,product.getName(),buyQty,product.getPrice(),username,java.time.LocalDateTime.now());
        saleModel.addSale(sale);
        
        // refresh user table
        userView.loadProductData();
        JOptionPane.showMessageDialog(userView, "Purchase Successful");
       
       
   }
    public void openPurchaseHistory() {
    PurchaseView pv = new PurchaseView(saleModel);
    pv.setVisible(true);
    loadPurchaseHistory(pv);
}

private void loadPurchaseHistory(PurchaseView pv) {
    DefaultTableModel model = (DefaultTableModel) pv.getTable().getModel();
    model.setRowCount(0);

    int saleId = 1;
    for (Sale s : saleModel.getAllSaleItems()) {
        model.addRow(new Object[]{ saleId++, s.getName(), s.getQuantity(), s.getPrice(), s.getDate() });
    }
}
public void sortProducts() {

    String choice = userView.getSelectedSortOption();

    java.util.ArrayList<item> list =
            new java.util.ArrayList<>(inventoryModel.getAllItems());

    // ✅ PRICE ASCENDING (Merge Sort)
    if (choice.equals("Price (Ascending)")) {

        java.util.ArrayList<item> sorted =
                userView.mergeSortByPriceAscending(list);

        userView.loadSortedProductData(sorted);
    }

    //  PRICE DESCENDING (Merge Sort + reverse)
    else if (choice.equals("Price (Descending)")) {

        java.util.ArrayList<item> sorted =
                userView.mergeSortByPriceAscending(list);

        java.util.Collections.reverse(sorted); // makes it descending
        userView.loadSortedProductData(sorted);
    }

    //  QUANTITY ASCENDING (Insertion Sort)
    else if (choice.equals("Quantity (Ascending)")) {

        userView.insertionSortByQuantityAscending(list);
        userView.loadSortedProductData(list);
    }

    //  QUANTITY DESCENDING (Insertion Sort)
    else if (choice.equals("Quantity (Descending)")) {

        userView.insertionSortByQuantityDescending(list);
        userView.loadSortedProductData(list);
    }

    else {
        JOptionPane.showMessageDialog(userView, "Sorting option not implemented yet!");
    }
}
public int linearSearchByProductId(java.util.ArrayList<item> list, String keyId) {

    for (int i = 0; i < list.size(); i++) {
        if (list.get(i).getProductId().equalsIgnoreCase(keyId)) {
            return i; //  found index
        }
    }
    return -1; //  not found
}
public int binarySearchByProductId(java.util.ArrayList<item> list, String keyId) {

    int low = 0;
    int high = list.size() - 1;

    while (low <= high) {

        int mid = (low + high) / 2;

        String midId = list.get(mid).getProductId();

        int result = midId.compareToIgnoreCase(keyId);

        if (result == 0) {
            return mid; //  found
        } 
        else if (result > 0) {
            high = mid - 1; // search left
        } 
        else {
            low = mid + 1; // search right
        }
    }

    return -1; //  not found
}
public void selectionSortByProductId(java.util.ArrayList<item> list) {

    int size = list.size();

    for (int step = 0; step < size - 1; step++) {

        int minIndex = step;

        for (int i = step + 1; i < size; i++) {

            if (list.get(i).getProductId()
                    .compareToIgnoreCase(list.get(minIndex).getProductId()) < 0) {
                minIndex = i;
            }
        }

        item temp = list.get(step);
        list.set(step, list.get(minIndex));
        list.set(minIndex, temp);
    }
}

/*public void searchProduct() {

    String keyword = userView.getSearchText();

    if (keyword == null || keyword.trim().isEmpty()) {
        JOptionPane.showMessageDialog(userView, "Please enter something to search!");
        return;
    }

    keyword = keyword.trim().toLowerCase();

    String type = userView.getSearchType(); // "Name" or "ID" or "Name+ID"

    java.util.ArrayList<item> list =
            new java.util.ArrayList<>(inventoryModel.getAllItems());

    //  Get table model directly
    javax.swing.table.DefaultTableModel model =
            (javax.swing.table.DefaultTableModel) userView.getInventoryTable().getModel();

    // Clear old rows first
    model.setRowCount(0);

    boolean found = false;

    for (item i : list) {

        String id = i.getProductId().toLowerCase();
        String name = i.getName().toLowerCase();

        //  Name search (partial)
        if (type.equalsIgnoreCase("Name") && name.contains(keyword)) {
            model.addRow(new Object[]{
                i.getProductId(),
                i.getName(),
                i.getQuantity(),
                i.getMinimum(),
                i.getPrice()
            });
            found = true;
        }

        //  ID search (partial)
        else if (type.equalsIgnoreCase("ID") && id.contains(keyword)) {
            model.addRow(new Object[]{
                i.getProductId(),
                i.getName(),
                i.getQuantity(),
                i.getMinimum(),
                i.getPrice()
            });
            found = true;
        }

        //  Name+ID search (partial)
        else if (type.equalsIgnoreCase("Name+ID") && (name.contains(keyword) || id.contains(keyword))) {
            model.addRow(new Object[]{
                i.getProductId(),
                i.getName(),
                i.getQuantity(),
                i.getMinimum(),
                i.getPrice()
            });
            found = true;
        }
    }

    if (!found) {
        JOptionPane.showMessageDialog(userView, "Product Not Found!");
        userView.loadProductData(); //  restore all products again
    }
}
private void selectionSortByName(java.util.ArrayList<item> list) {

    int size = list.size();

    for (int step = 0; step < size - 1; step++) {
        int minIdx = step;

        for (int i = step + 1; i < size; i++) {

            String name1 = list.get(i).getName();
            String name2 = list.get(minIdx).getName();

            if (name1.compareToIgnoreCase(name2) < 0) {
                minIdx = i;
            }
        }

        // swap
        item temp = list.get(step);
        list.set(step, list.get(minIdx));
        list.set(minIdx, temp);
    }
}*/
public void searchProduct() {

    String searchText = userView.getSearchText();

    if (searchText == null || searchText.trim().isEmpty()) {

        JOptionPane.showMessageDialog(userView, "Please enter something to search!");
        return;
    }

    String keyword = searchText.trim().toLowerCase();
    String type = userView.getSearchType(); // Expected: "Linear" or "Binary"

    java.util.ArrayList<item> originalList = new java.util.ArrayList<>(inventoryModel.getAllItems());

    DefaultTableModel model = (DefaultTableModel) userView.getInventoryTable().getModel();
    model.setRowCount(0); // Clear table

    boolean found = false;

    if (type.equals("Linear")) {

        for (item i : originalList) {

            String id = i.getProductId().toLowerCase();
            String name = i.getName().toLowerCase();

            if (name.contains(keyword) || id.contains(keyword)) {

                model.addRow(new Object[]{
                    i.getProductId(),
                    i.getName(),
                    i.getQuantity(),
                    i.getMinimum(),
                    i.getPrice()
                });
                found = true;
            }
        }
    }
    else if (type.equals("Binary")) {
        java.util.ArrayList<item> sortedList = new java.util.ArrayList<>(originalList);
        selectionSortByName(sortedList);

        int index = binarySearchByName(sortedList, keyword);

        if (index != -1) {
            item foundItem = sortedList.get(index);
            model.addRow(new Object[]{
                foundItem.getProductId(),
                foundItem.getName(),
                foundItem.getQuantity(),
                foundItem.getMinimum(),
                foundItem.getPrice()
            });
            found = true;
            JOptionPane.showMessageDialog(userView, "Product Found!");

        }
    }

    if (!found) {
        JOptionPane.showMessageDialog(userView, "Product Not Found!");
        userView.loadProductData(); // Restore full table
    }
}

private void selectionSortByName(java.util.ArrayList<item> list) {


    int size = list.size();

    for (int step = 0; step < size - 1; step++) {
        int minIdx = step;

        for (int i = step + 1; i < size; i++) {

            String name1 = list.get(i).getName();
            String name2 = list.get(minIdx).getName();

            if (name1.compareToIgnoreCase(name2) < 0) {
                minIdx = i;
            }
        }

        // swap
        item temp = list.get(step);
        list.set(step, list.get(minIdx));
        list.set(minIdx, temp);
    }
}
private int binarySearchByName(java.util.ArrayList<item> list, String key) {

    int low = 0;
    int high = list.size() - 1;

    while (low <= high) {

        int mid = (low + high) / 2;

        String midName = list.get(mid).getName().toLowerCase();

        int result = midName.compareToIgnoreCase(key);

        if (result == 0) {
            return mid; //  FOUND
        } else if (result > 0) {
            high = mid - 1; // search left
        } else {
            low = mid + 1; // search right
        }
    }

    return -1; //  NOT FOUND
}
public void binarySearchProduct() {

    String keyword = userView.getSearchText();

    if (keyword == null || keyword.trim().isEmpty()) {
        JOptionPane.showMessageDialog(userView, "Please enter product to search!");
        return;
    }

    keyword = keyword.trim().toLowerCase();

    java.util.ArrayList<item> list =
            new java.util.ArrayList<>(inventoryModel.getAllItems());

    //  sort first (required)
    selectionSortByName(list);

    //  binary search
    int index = binarySearchByName(list, keyword);

    if (index == -1) {
        JOptionPane.showMessageDialog(userView, "Product Not Found!");
        userView.loadProductData(); // show all again
        return;
    }

    //  Show only found product in table
    javax.swing.table.DefaultTableModel model =
            (javax.swing.table.DefaultTableModel) userView.getInventoryTable().getModel();

    model.setRowCount(0);

    item found = list.get(index);

    model.addRow(new Object[]{
        found.getProductId(),
        found.getName(),
        found.getQuantity(),
        found.getMinimum(),
        found.getPrice()
    });

    JOptionPane.showMessageDialog(userView, "Product Found!");
}








    
}
