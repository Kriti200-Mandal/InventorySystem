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
      if (userName == null || userName.isEmpty() || password == null || password.isEmpty())
      {
          JOptionPane.showMessageDialog(view, "UserName and Password required","Login Error",JOptionPane.ERROR_MESSAGE);
          return;
      }
    
        //  user loginUser = userModel.authenticate(userName.trim(), password);
          
          
        /*  if(loginUser == null)
          {
              JOptionPane.showMessageDialog(view, "Invalid Username or password");
              return;
          }*/
         
          if(userType.equalsIgnoreCase("admin"))
          {
              user admin = userModel.authenticate(userName.trim(), password);
              if(admin == null)
              {
                  JOptionPane.showMessageDialog(view, "Invalid Admin Credentials", "Access Denied",JOptionPane.ERROR_MESSAGE);
                  return;
              }
            new AdminView(inventoryModel, saleModel).setVisible(true);
          }
          else
          {
            new UserView(inventoryModel, saleModel,userName.trim()).setVisible(true);  
          }
          
    view.dispose();
    
}
    
}
