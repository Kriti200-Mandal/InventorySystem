/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;
import java.util.HashMap;


/**
 *
 * @author dell
 */
public class UserModel {
    
    
    
   private final HashMap < String,user>users;
   
  public UserModel()
  {
      users = new HashMap<>();
      user us = new user("admin" ,"admin123","admin");
      user uss = new user("user","user123","user");
      users.put("admin",us);
      users.put("user",uss);
      
  }
    
    public user authenticate(String userName , String password )
    {
        if (!users.containsKey(userName))
        {
            return null; 
        }
         
         user us = users.get(userName);
         
        if (us.getPassword().equals(password))
        {
            
            return us;
             
        }
        return null;
    }
    
    
    
    
    
    
}
