package project_clinic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class Receptionest extends Public_Data {

    public Receptionest() {
    }

    public Receptionest(int id, String f_name, String phone, String date, String address, int age, String username, String password) {

        this.id = id;
        this.name = f_name;
        this.phone = phone;
        this.date = date;
        this.address = address;
        this.age = age;
        this.username = username;
        this.password = password;
    }

    ArrayList<Receptionest> list = new ArrayList();
    static Connection connection;
    static Statement statement;
    static String query;
    static ResultSet resultset;
    static PreparedStatement pr_state;

    Scanner read = new Scanner(System.in);
    Connection_Database D = new Connection_Database();

    /*LOG_IN ........................*/
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

                query = "select * from Receptionest";
                resultset = statement.executeQuery(query);

                while (resultset.next()) {
                    Receptionest r = new Receptionest(resultset.getInt("id"), resultset.getString("f_name"), resultset.getString("phone"), resultset.getString("date"), resultset.getString("address"), resultset.getInt("age"), resultset.getString("username"), resultset.getString("password"));
                    list.add(r);
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


    /*ADD PECEPTIONEST.........................*/
    public void add_Receptionest() {

        try {

            connection = D.connect();
            statement = connection.createStatement();

            while (true) {
                System.out.print("Enter the Receptionest id number : ");
                int id = read.nextInt();

                System.out.print("Enter the Receptionest's F_name: ");
                String f_name = read.next();

                System.out.print("Enter the Receptionest's L_name: ");
                String l_name = read.next();

                System.out.print("Enter the Receptionest's address: ");
                String address = read.next();

                System.out.print("Enter the Receptionest's phone: ");
                String phone = read.next();

                System.out.print("Enter the Receptionest's age: ");
                int age = read.nextInt();

                System.out.print("Enter the Receptionest's username: ");
                String username = read.next();

                System.out.print("Enter the Receptionest's password: ");
                String password = read.next();

                System.out.print("Enter the Date: ");
                String date = read.next();

                query = "insert into Receptionest values('" + id + "', '" + f_name + "', '" + l_name + "' ,'" + phone + "','" + username + "', '" + password + "' , '" + date + "','" + age + "','" + address + "' )";
                statement.execute(query);

                System.out.println("Do you want to add another Receptionest enter : 1 \nIf don't enter : 0   ");
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

    /* VIEW PECEPTIONEST............................*/
    public void view_Receptionest() {
        try {

            connection = D.connect();
            statement = connection.createStatement();

            while (true) {
                System.out.println("IF you want to view the data of all Receptionests Enter :1 ");
                System.out.println("IF you want to view the data of one Receptionest Enter :2");
                int n = read.nextInt();

                /*VIEW ALL PATIENT........................*/
                if (n == 1) {
                    query = "select * from Receptionest";
                    resultset = statement.executeQuery(query);

                    while (resultset.next()) {
                        System.out.print(resultset.getInt("id") + "\t");
                        System.out.print(resultset.getString("f_name") + "\t\t");
                        System.out.print(resultset.getString("Address") + "\t");
                        System.out.print(resultset.getString("Phone") + "\t\t");
                        System.out.print(resultset.getString("Username") + "\t\t");
                        System.out.print(resultset.getString("Password") + "\t\t");
                        System.out.println();
                    }
                }

                /*VIEW ONE PATIENT ..................*/
                if (n == 2) {

                    System.out.print("Enter the ID of the Receptionest: ");
                    int P_ID = read.nextInt();

                    query = "select * from Receptionest";
                    resultset = statement.executeQuery(query);

                    while (resultset.next()) {
                        Receptionest r = new Receptionest(resultset.getInt("id"), resultset.getString("f_name"), resultset.getString("phone"), resultset.getString("date"), resultset.getString("address"), resultset.getInt("age"), resultset.getString("username"), resultset.getString("password"));
                        list.add(r);
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
                System.out.println("Do you want to see another Receptionest enter :1 \nIf don't enter any number");
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

    /*CHANGE PASSWORD........................*/
    @Override
    public void change_password() {

        try {
            connection = D.connect();
            statement = connection.createStatement();

            System.out.print("Enter your id: ");
            int id = read.nextInt();

            System.out.print("Enter The New Password: ");
            String new_pass = read.next();

            query = "update Receptionest set Password='" + new_pass + "' where id='" + id + "'";
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

    /*CREAT_BILL .....................................*/
    public void create_bill() {
        System.out.print("Enter number of days that patient stayed: ");
        int numberDays = read.nextInt();
        System.out.print("Enter room cost: ");
        double bed = read.nextDouble();
        System.out.print("Enter your way of charges: ");
        String charge = read.next();
        System.out.print("Enter the cost of prescription: ");
        double presc = read.nextDouble();
        System.out.print("The total cost: " + (numberDays * bed + presc));
    }

    /*LOG_OUT..........................*/
    @Override
    public boolean log_out(int admin_c) {
        if (admin_c == 5) {
            return true;
        } else {
            return false;
        }
    }

}
