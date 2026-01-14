/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;
import Model.InventoryModel;
import Model.SaleModel;
import Model.item;
import View.AdminView;
import java.awt.BorderLayout;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import Model.Sale;

/**
 *
 * @author dell
 */
public class AdminController {
    
    private InventoryModel inventoryModel;
    private SaleModel saleModel;
    private AdminView adminView;
    
    
    public AdminController(InventoryModel inventoryModel, SaleModel saleModel, AdminView adminView)
    {
        this.inventoryModel = inventoryModel;
        this.saleModel = saleModel;
        this.adminView = adminView;
    }
    
    public void addProduct()
    {
         try {
        String id = JOptionPane.showInputDialog(adminView, "Enter Product ID:");

        if (id == null || id.trim().isEmpty()) {
            JOptionPane.showMessageDialog(adminView, "Product ID cannot be empty!");
            return;
        }

        id = id.trim().toUpperCase();

        if (!id.matches("^[A-Z][A-Z0-9]*$")) {
            JOptionPane.showMessageDialog(
                adminView,
                "Invalid Product ID!\nIt must start with a letter.",
                "Invalid Input",
                JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        String name = JOptionPane.showInputDialog(adminView, "Enter Product Name:");

if (name == null || name.trim().isEmpty()) {
    JOptionPane.showMessageDialog(adminView, "Product name cannot be empty!");
    return;
}

if (!name.matches("^[A-Za-z ]+[0-9]*$")) {
    JOptionPane.showMessageDialog(
        adminView,
        "Invalid Product Name!",
        "Invalid Input",
        JOptionPane.ERROR_MESSAGE
    );
    return;
}

        int quantity;
        try
        {
            quantity = Integer.parseInt(
            JOptionPane.showInputDialog(adminView, "Enter Quantity:")
        );
                if (quantity <= 0) {
        JOptionPane.showMessageDialog(
            adminView,
            "Quantity must be greater than 0!",
            "Invalid Input",
            JOptionPane.ERROR_MESSAGE
        );
        return;
    }
        }
        catch (NumberFormatException e) {
    JOptionPane.showMessageDialog(
        adminView,
        "Invalid Quantity!\nPlease enter a numeric value.",
        "Invalid Input",
        JOptionPane.ERROR_MESSAGE
    );
    return;
}
       
        int minimum;
        try{
            minimum= Integer.parseInt(
            JOptionPane.showInputDialog(adminView, "Enter Minimum Stock:")
        );
            if(minimum<= 0)
            {
                JOptionPane.showMessageDialog(adminView,"Minimum must be greater than 0",
                        "Invalid Input",
                        JOptionPane.ERROR_MESSAGE);
            return;
            }
        }
        catch(NumberFormatException e)
         {
            JOptionPane.showMessageDialog(adminView, "Enter valid input");
            return;
        }
        
        double price;
        try{
             price= Double.parseDouble(
            JOptionPane.showInputDialog(adminView, "Enter Price:")
        );
              if (price <= 0) {
        JOptionPane.showMessageDialog(
            adminView,
            "Price must be greater than 0!",
            "Invalid Input",
            JOptionPane.ERROR_MESSAGE
        );
        return;
    }
             
        }catch (NumberFormatException e) {
    JOptionPane.showMessageDialog(
        adminView,
        "Invalid Price!\nPlease enter a numeric value.",
        "Invalid Input",
        JOptionPane.ERROR_MESSAGE
    );
    return;
}
        


        item newItem = new item(id, name, quantity, minimum, price);

        boolean added = inventoryModel.addItem(newItem);

        if (added) {
            adminView.refreshInventoryTable();
            adminView.loadLowStockData();   
             adminView.updateSummary(); 
             
            JOptionPane.showMessageDialog(adminView, "Item added successfully!");
        } else {
            JOptionPane.showMessageDialog(
                adminView,
                "Product ID already exists!",
                "Duplicate Error",
                JOptionPane.ERROR_MESSAGE
            );
        }

    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(
            adminView,
            "Please enter valid numeric values!",
            "Invalid Input",
            JOptionPane.ERROR_MESSAGE
        );
    }
    }
    
    
    
    // update product
    public void updateProduct()
    {
         JTable table = adminView.getInvetoryTable();
        int row = table.getSelectedRow();

    if (row == -1) {
        JOptionPane.showMessageDialog(adminView,
            "Select a product to update!",
            "No Selection",
            JOptionPane.WARNING_MESSAGE);
        return;
    }

    String productId = table.getValueAt(row, 0).toString();

    try {
        int quantity = Integer.parseInt(
            JOptionPane.showInputDialog(adminView, "Enter New Quantity:")
        );

        int minimum = Integer.parseInt(
            JOptionPane.showInputDialog(adminView, "Enter New Minimum Stock:")
        );

        double price = Double.parseDouble(
            JOptionPane.showInputDialog(adminView, "Enter New Price:")
        );

        if (quantity < 0 || minimum < 0 || price <= 0) {
            JOptionPane.showMessageDialog(adminView, "Invalid values entered!");
            return;
        }

        inventoryModel.updateItem(productId, quantity, minimum, price);
        adminView.refreshInventoryTable();
        adminView.loadLowStockData();
        adminView.updateSummary();

        JOptionPane.showMessageDialog(adminView, "Item updated successfully!");

    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(adminView,
            "Please enter valid numeric values!",
            "Invalid Input",
            JOptionPane.ERROR_MESSAGE);
    }
    }
    
    // delete product
    
    public void deleteProduct()
    {
         JTable table = adminView.getInvetoryTable();
        int row = table.getSelectedRow();
         
         if (row == -1) {
        JOptionPane.showMessageDialog(
            adminView,
            "Please select a product to delete!",
            "No Selection",
            JOptionPane.WARNING_MESSAGE
        );
        return;
    }
          String productId = table.getValueAt(row, 0).toString();
           int confirm = JOptionPane.showConfirmDialog(
        adminView,
        "Are you sure you want to delete Product ID: " + productId + "?",
        "Confirm Delete",
        JOptionPane.YES_NO_OPTION
    );
           if (confirm != JOptionPane.YES_OPTION) {
        return; 
    }
           boolean deleted = inventoryModel.deleteItem(productId);

    if (deleted) {
        adminView.loadInventoryTable();
        adminView.loadLowStockData();
       adminView.updateSummary();
        JOptionPane.showMessageDialog(adminView, "Item deleted successfully!");
    } else {
        JOptionPane.showMessageDialog(
            adminView,
            "Delete failed!",
            "Error",
            JOptionPane.ERROR_MESSAGE
        );
    }

    }    
   public void loadAnalytics() {

    // ✅ Total Products
    int totalProducts = inventoryModel.getAllItems().size();
    adminView.setTotalProductsValue(String.valueOf(totalProducts));

    // ✅ Total Sales
    int totalSales = saleModel.getAllSaleItems().size();
    adminView.setTotalSalesValue(String.valueOf(totalSales));

    // ✅ Low Stock
    int lowStock = inventoryModel.getLowStockItem().size();
    adminView.setLowStockValue(String.valueOf(lowStock));

    // ✅ Fill Analytics Table (Product | Total Sale | Total Revenue)
    DefaultTableModel model =
            (DefaultTableModel) adminView.getAnalyticsTable().getModel();

    model.setRowCount(0);

    java.util.ArrayList<String> productNames = new java.util.ArrayList<>();
    java.util.ArrayList<Integer> totalSold = new java.util.ArrayList<>();
    java.util.ArrayList<Double> totalRevenue = new java.util.ArrayList<>();

    for (Sale s : saleModel.getAllSaleItems()) {

        String name = s.getName();
        int qty = s.getQuantity();
        double revenue = qty * s.getPrice();

        int index = productNames.indexOf(name);

        if (index == -1) {
            productNames.add(name);
            totalSold.add(qty);
            totalRevenue.add(revenue);
        } else {
            totalSold.set(index, totalSold.get(index) + qty);
            totalRevenue.set(index, totalRevenue.get(index) + revenue);
        }
    }

    // Add rows into analytics table
    for (int i = 0; i < productNames.size(); i++) {
        model.addRow(new Object[]{
            productNames.get(i),
            totalSold.get(i),
            totalRevenue.get(i)
        });
    }

    // ✅ Find Top Product
    if (productNames.isEmpty()) {
        adminView.setTopProductValue("0");
    } else {

        String topProduct = productNames.get(0);
        int maxSold = totalSold.get(0);

        for (int i = 1; i < totalSold.size(); i++) {
            if (totalSold.get(i) > maxSold) {
                maxSold = totalSold.get(i);
                topProduct = productNames.get(i);
            }
        }

        adminView.setTopProductValue(topProduct + " (" + maxSold + ")");
    }
}
   public void sortProducts() {

    String choice = adminView.getSelectedSortOption();

    java.util.ArrayList<item> list =
            new java.util.ArrayList<>(inventoryModel.getAllItems());

    // ✅ PRICE ASCENDING (Merge Sort)
    if (choice.equals("Price (Ascending)")) {

        java.util.ArrayList<item> sorted =
                adminView.mergeSortByPriceAscending(list);

        adminView.loadSortedProductData(sorted);
    }

    //  PRICE DESCENDING (Merge Sort + reverse)
    else if (choice.equals("Price (Descending)")) {

        java.util.ArrayList<item> sorted =
                adminView.mergeSortByPriceAscending(list);

        java.util.Collections.reverse(sorted); // makes it descending
        adminView.loadSortedProductData(sorted);
    }

    //  QUANTITY ASCENDING (Insertion Sort)
    else if (choice.equals("Quantity (Ascending)")) {

        adminView.insertionSortByQuantityAscending(list);
        adminView.loadSortedProductData(list);
    }

    //  QUANTITY DESCENDING (Insertion Sort)
    else if (choice.equals("Quantity (Descending)")) {

        adminView.insertionSortByQuantityDescending(list);
        adminView.loadSortedProductData(list);
    }

    else {
        JOptionPane.showMessageDialog(adminView, "Sorting option not implemented yet!");
    }
}
   
   public void undoInventory() {
    boolean undone = inventoryModel.undo();

    if (undone) {
        adminView.refreshInventoryTable();
        adminView.loadLowStockData();
        adminView.updateSummary();
        JOptionPane.showMessageDialog(adminView, "Undo Successful!");
    } else {
        JOptionPane.showMessageDialog(adminView, "Nothing to Undo!");
    }
}





  

   
    
}
