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

public class Admmin extends Public_Data {

    public Admmin() {
    }

    public Admmin(int id, String f_name, String phone, String username, String password) {

        this.id = id;
        this.name = f_name;
        this.phone = phone;
        this.username = username;
        this.password = password;

    }

    static Connection connection;
    static Statement statement;
    static String query;
    static ResultSet resultset;
    static PreparedStatement pr_state;

    ArrayList<Admmin> list = new ArrayList();
    Scanner read = new Scanner(System.in);
    Connection_Database D = new Connection_Database();

    /*ADD ADMIN...............*/
    public void add_Admin() {
        try {
            connection = D.connect();
            statement = connection.createStatement();

            while (true) {
                System.out.print("Enter the Admin id number : ");
                int id = read.nextInt();

                System.out.print("Enter the Admin's F_name: ");
                String f_name = read.next();

                System.out.print("Enter the Admin's L_name: ");
                String l_name = read.next();

                System.out.print("Enter the Admin's phone: ");
                String phone = read.next();

                System.out.print("Enter the Admin's username: ");
                String username = read.next();

                System.out.print("Enter the Admin's password: ");
                String password = read.next();

                query = "insert into admin values('" + id + "', '" + f_name + "', '" + l_name + "' ,'" + phone + "','" + username + "', '" + password + "' )";
                statement.execute(query);

                System.out.println("Do you want to add another Admin enter : 1 \nIf don't enter : 0   ");
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

    /*LOG_IN......................*/
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

                query = "select * from admin";
                resultset = statement.executeQuery(query);
                while (resultset.next()) {
                    Admmin a = new Admmin(resultset.getInt("id"), resultset.getString("f_name"), resultset.getString("phone"), resultset.getString("username"), resultset.getString("password"));
                    list.add(a);
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
    
    /*LOG_OUT.........................*/
    @Override
    public boolean log_out(int admin_c) {
        if (admin_c == 6) {
            return true;
        } else {
            return false;
        }
    }

    /*CHANGE PASSWORD................*/
    @Override
    public void change_password() {

        try {
            connection = D.connect();
            statement = connection.createStatement();

            System.out.print("Enter your id: ");
            int id = read.nextInt();
            System.out.print("Enter The New Password: ");
            String new_pass = read.next();

            query = "update admin set password='" + new_pass + "' where id='" + id + "'";
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
}
