/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;
import Model.user;
import Model.UserModel;
import Model.InventoryModel;
import Model.SaleModel;
import View.LoginView;
import View.AdminView;
import View.UserView;


import javax.swing.JOptionPane;


/**
 *
 * @author dell
 */
public class LoginController {
    private LoginView view;
    private UserModel userModel;
    private InventoryModel inventoryModel;
    private SaleModel saleModel;
    
    
    
    
    public LoginController(LoginView view , UserModel userModel , InventoryModel inventoryModel , SaleModel saleModel)
    {
        this.view = view;
        this.userModel = userModel;
        this.inventoryModel = inventoryModel;
        this.saleModel = saleModel;
    }
    public void handleLogin(String userName , String password, String userType)
    {
      
        if (userName == null || userName.trim().isEmpty() ||
        password == null || password.trim().isEmpty()) {

        JOptionPane.showMessageDialog(view,
                "UserName and Password required",
                "Login Error",
                JOptionPane.ERROR_MESSAGE);
        return;
    }

    userName = userName.trim();
    password = password.trim();

    // Username validation (no number allowed)
    if (!userName.matches("^[A-Za-z ]+$")) {
        JOptionPane.showMessageDialog(view,
                "Invalid Username!",
                "Login Error",
                JOptionPane.ERROR_MESSAGE);
        return;
    }

    //  ADMIN (only one fixed admin)
    if (userType.equalsIgnoreCase("Admin")) {

        if (!userName.equalsIgnoreCase("admin") || !password.equals("admin123")) {
            JOptionPane.showMessageDialog(view,
                    "Invalid Admin Credentials!",
                    "Access Denied",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        new AdminView(inventoryModel, saleModel).setVisible(true);
        view.dispose();
        return;
    }

    // USER (multiple users allowed)
    user u = userModel.authenticate(userName, password);

    if (u == null) {
        JOptionPane.showMessageDialog(view,
                "Invalid User Credentials!",
                "Login Failed",
                JOptionPane.ERROR_MESSAGE);
        return;
    }

    new UserView(inventoryModel, saleModel, userName).setVisible(true);
    view.dispose();
    
}
    
}
