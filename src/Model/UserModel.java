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
      
      users.put("admin",us);
     
      
      
  }
    
    public user authenticate(String userName , String password )
    {
        if (!users.containsKey(userName))
        {
            return null; 
        }
         
         user User = users.get(userName);
         
        if (User.getPassword().equals(password))
        {
            
            return User;
             
        }
        return null;
    }
    
    public boolean addUser(user User)
    {
        if(users.containsKey(User.getUserName())) //prevents the two user with same name 
        {
            return false;
        }
        users.put(User.getUserName(),User); // store new user 
        return true;
    }
    
    
    
    
    
}
