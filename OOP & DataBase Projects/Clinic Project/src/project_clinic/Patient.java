/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project_clinic;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class Patient {

    int id;
    String name;
    String address;
    int age;
    String precription;

    public Patient() {
    }

    public Patient(int id, String name, String address, int age, String precription) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.age = age;
        this.precription = precription;
    }

    ArrayList<Patient> list = new ArrayList();
    static Connection connection;
    static Statement statement;
    static String query;
    static ResultSet resultset;

    Scanner read = new Scanner(System.in);
    Connection_Database D = new Connection_Database();

    public void add_patient() {

        int counter = 1;
        try {
            connection = D.connect();
            statement = connection.createStatement();

            while (true) {
                System.out.print("Enter the patient id: ");
                int id = read.nextInt();

                System.out.print("Enter the patient name: ");
                String name = read.next();

                System.out.print("Enter the patient adress: ");
                String address = read.next();

                System.out.print("Enter the patient phone: ");
                String phone = read.next();

                System.out.print("Enter the patient age: ");
                int age = read.nextInt();
                
                System.out.print("Enter the date: ");
                String date = read.next();
                String prescreption = "no_data";

                query = "insert into Patient values('" + id + "', '" + name + "', '" + address + "','" + phone + "','" + age + "','" + counter + "','"+date+"' ,'" + prescreption + "' )";
                statement.execute(query);

                System.out.println("Do you want to add another patient enter : 1 if don't enter 0  ");
                int x = read.nextInt();
                counter++;
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

    /*VIEW PATIENT...................*/
    public void view_patient() {

        try {
            connection = D.connect();
            statement = connection.createStatement();

            while (true) {
                System.out.println("IF you want to view the data of all patients Enter :1 ");
                System.out.println("IF you want to view the data of one patient Enter :2");
                int n = read.nextInt();
                
                /*VIEW ALL PATIENT*/
                if (n == 1) {
                    query = "select * from Patient";
                    resultset = statement.executeQuery(query);

                    while (resultset.next()) {
                        System.out.print(resultset.getInt("id") + "\t");
                        System.out.print(resultset.getString("name") + "\t\t");
                        System.out.print(resultset.getString("address") + "\t");
                        System.out.print(resultset.getInt("phone") + "\t");
                        System.out.print(resultset.getInt("count") + "\t");
                        System.out.println();
                    }
                }
                /*VIEW ONE PATIENT*/
                if (n == 2) {
                    System.out.print("Enter the ID of the Patient: ");
                    int P_ID = read.nextInt();

                    query = "select * from Patient";
                    resultset = statement.executeQuery(query);
                    while (resultset.next()) {
                        Patient P = new Patient(resultset.getInt("id"), resultset.getString("name"), resultset.getString("address"), resultset.getInt("age"), resultset.getString("precscription"));
                        list.add(P);
                    }

                    for (int i = 0; i < list.size(); i++) {
                        if (P_ID == list.get(i).id) {
                            System.out.print("ID: " + list.get(i).id + "\t");
                            System.out.print("Name: " + list.get(i).name + "\t");
                            System.out.print("Age: " + list.get(i).age + "\t");
                            System.out.println("Address: " + list.get(i).address + "\t");
                            
                            break;
                        }
                    }
                }
                System.out.println("Do you want to see another patient enter :1 \nIf don't enter any number");
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

    /*ADD PRRSCRIPTION...........................*/
    public void prescription() {

        try {
            connection = D.connect();
            statement = connection.createStatement();

            System.out.print("Enter the ID of the Patient: ");
            int P_ID = read.nextInt();

            System.out.print("Enter the medicin: ");
            String medicin = read.next();

            query = "update patient set precscription='" + medicin + "' where id='" + P_ID + "'";
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
