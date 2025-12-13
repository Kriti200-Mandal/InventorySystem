/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author dell
 */
public class user {
    private String userName;
    private String password;
    private String role;
   
    
    
    public user(String userName , String password , String role)
    {
        this.userName = userName;
        this.password = password;
        this.role = role;
    }
    public String getUserName()
    {
       return userName; 
    }
    public String getPassword()
    {
        return password;
    }
    public String getRole()
    {
        return role;
    }
    public void setUserName(String userName )
    {
        this.userName = userName;
    }
    
  
    

}
