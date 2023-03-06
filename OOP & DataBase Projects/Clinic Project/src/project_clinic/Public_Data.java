/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project_clinic;

/**
 *
 * @author mohamed
 */
public abstract class Public_Data {
    
    protected int id;
    protected int age;
    protected String name;
    protected String phone;
    protected String date;
    protected String job;
    protected String address;
    protected String username;
    protected String password;
    
    public abstract void log_in();
    public abstract boolean log_out(int admin_c);
    public abstract void change_password();
}

