/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project_clinic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class Doctor extends Public_Data {

    public Doctor() {
    }

    public Doctor(int id, String f_name, String phone, String date, String address, int age, String username, String password) {

        this.id = id;
        this.name = f_name;
        this.phone = phone;
        this.date = date;
        this.address = address;
        this.age = age;
        this.username = username;
        this.password = password;

    }

    ArrayList<Doctor> list = new ArrayList();
    static Connection connection;
    static Statement statement;
    static String query;
    static ResultSet resultset;
    static PreparedStatement pr_state;

    Scanner read = new Scanner(System.in);
    Connection_Database D = new Connection_Database();

    /*ADD DOCTOR*/
    public void add_Doctor() {

        try {
            connection = D.connect();
            statement = connection.createStatement();

            while (true) {
                System.out.print("Enter the data of Doctor that id number : ");
                int id = read.nextInt();

                System.out.print("Enter the Doctor's F_name: ");
                String f_name = read.next();

                System.out.print("Enter the Doctor's L_name: ");
                String l_name = read.next();

                System.out.print("Enter the Doctor's address: ");
                String address = read.next();

                System.out.print("Enter the Doctor's phone: ");
                String phone = read.next();

                System.out.print("Enter the Doctor's age: ");
                int age = read.nextInt();

                System.out.print("Enter the Doctor's username: ");
                String username = read.next();

                System.out.print("Enter the Doctor's password: ");
                String password = read.next();

                System.out.print("Enter the Date: ");
                String date = read.next();

                query = "insert into doctor values('" + id + "', '" + f_name + "', '" + l_name + "' ,'" + phone + "','" + username + "', '" + password + "' , '" + date + "','" + age + "','" + address + "' )";
                statement.execute(query);

                System.out.println("Do you want to add another Doctor enter : 1 \nIf don't enter : 0   ");
                int x = read.nextInt();
                if (x == 1) {
                    continue;
                } else {
                    break;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                connection.close();
                statement.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /* VIEW DOCTOR*/
    public void view_Doctor() {

        try {
            connection = D.connect();
            statement = connection.createStatement();

            while (true) {
                System.out.println("IF you want to view the data of all Doctors Enter :1 ");
                System.out.println("IF you want to view the data of one Doctor Enter :2");
                int n = read.nextInt();
                /*VIEW ALL DOCTORS*/
                if (n == 1) {
                    query = "select * from doctor";
                    resultset = statement.executeQuery(query);

                    while (resultset.next()) {
                        System.out.print(resultset.getInt("id") + "\t");
                        System.out.print(resultset.getString("f_name") + "\t\t");
                        System.out.print(resultset.getString("address") + "\t");
                        System.out.print(resultset.getString("phone") + "\t\t");
                        System.out.print(resultset.getString("username") + "\t\t");
                        System.out.print(resultset.getString("password") + "\t\t");
                        System.out.println();
                    }
                }
                /*VIEW ONE DOCTOR*/
                if (n == 2) {

                    System.out.print("Enter the ID of the Doctor: ");
                    int P_ID = read.nextInt();

                    query = "select * from doctor";
                    resultset = statement.executeQuery(query);
                    while (resultset.next()) {
                        Doctor d = new Doctor(resultset.getInt("id"), resultset.getString("f_name"), resultset.getString("phone"), resultset.getString("date"), resultset.getString("address"), resultset.getInt("age"), resultset.getString("username"), resultset.getString("password"));
                        list.add(d);
                    }

                    for (int i = 0; i < list.size(); i++) {
                        if (P_ID == list.get(i).id) {
                            System.out.print("ID: " + list.get(i).id + "\t");
                            System.out.print("Name: " + list.get(i).name + "\t");
                            System.out.print("Age: " + list.get(i).age + "\t");
                            System.out.print("Address: " + list.get(i).address + "\t");
                            System.out.print("Phone: " + list.get(i).phone + "\t");
                            System.out.print("Age: " + list.get(i).age + "\t");
                            System.out.print("Username: " + list.get(i).username + "\t");
                            System.out.print("Password: " + list.get(i).password + "\t");
                            System.out.println();
                        }
                    }
                }
                System.out.println("Do you want to see another Doctor enter :1 \nIf don't enter any number");
                int x = read.nextInt();

                if (x == 1) {
                    continue;
                } else {
                    break;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                connection.close();
                statement.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /*LOG_IN*/
    @Override
    public void log_in() {

        try {
            connection = D.connect();
            statement = connection.createStatement();

            int check = 0;
            while (true) {
                System.out.print("Enter your username: ");
                String usernamee = read.next();
                System.out.print("Enter your Password: ");
                String passwordd = read.next();

                query = "select * from doctor";
                resultset = statement.executeQuery(query);
                while (resultset.next()) {
                    Doctor d = new Doctor(resultset.getInt("id"), resultset.getString("f_name"), resultset.getString("phone"), resultset.getString("date"), resultset.getString("address"), resultset.getInt("age"), resultset.getString("username"), resultset.getString("password"));
                    list.add(d);
                }

                for (int i = 0; i < list.size(); i++) {
                    if (usernamee.equals(list.get(i).username) && passwordd.equals(list.get(i).password)) {
                        check = 1;
                        break;
                    }
                }
                if (check == 1) {
                    break;
                }
                System.out.print("Invalid username and password ");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                connection.close();
                statement.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /*CHANGE PASSWORD*/
    @Override
    public void change_password() {

        try {
            connection = D.connect();
            statement = connection.createStatement();

            System.out.print("Enter your id: ");
            int id = read.nextInt();

            System.out.print("Enter The New Password: ");
            String new_pass = read.next();

            query = "update doctor set password='" + new_pass + "' where id='" + id + "'";
            statement.execute(query);

        } catch (SQLException e) {
            System.out.println(e.getMessage());

        } finally {
            try {
                connection.close();
                statement.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }


    /*LOG_OUT*/
    @Override
    public boolean log_out(int admin_c) {
        if (admin_c == 4) {
            return true;
        } else {
            return false;
        }
    }

}
