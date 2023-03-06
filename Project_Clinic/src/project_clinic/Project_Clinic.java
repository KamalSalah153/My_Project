package project_clinic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Project_Clinic {

    static Connection connection;
    static Statement statement;
    static String query;
    static PreparedStatement pr_state;

    public static void main(String[] args) {

        Connection_Database D = new Connection_Database();
        Admmin admin = new Admmin();
        Patient patient = new Patient();
        Doctor doctor = new Doctor();
        Receptionest receptionest = new Receptionest();

        try {
            Scanner read = new Scanner(System.in);
            connection = D.connect();
            statement = connection.createStatement();

            while (true) {

                System.out.println("1- Admin.");
                System.out.println("2- Doctor.");
                System.out.println("3- Receptionest.");
                System.out.println("4- Exit.");
                System.out.print("Enter Your Choice number: ");
                int choice = read.nextInt();
                /*ADMIN..................*/
                switch (choice) {
                    /*DOCTPR...............*/
                    case 1:
                        admin.log_in();
                        while (true) {
                            System.out.println("1- Add Doctor.");
                            System.out.println("2- View Doctor.");
                            System.out.println("3- Add Receptionest.");
                            System.out.println("4- View Receptionest.");
                            System.out.println("5- Add Admin.");
                            System.out.println("6-log_out.");
                            System.out.println("Enter Your Choice number: ");

                            int admin_ch = read.nextInt();

                            switch (admin_ch) {
                                case 1:
                                    doctor.add_Doctor();
                                    break;
                                case 2:
                                    doctor.view_Doctor();
                                    break;
                                case 3:
                                    receptionest.add_Receptionest();
                                    break;
                                case 4:
                                    receptionest.view_Receptionest();
                                    break;
                                case 5:
                                    admin.add_Admin();
                                    break;
                                default:
                                    break;
                            }
                            if (admin.log_out(admin_ch)) {
                                break;
                            }
                            System.out.println();
                            System.out.println("If You Will do Another");

                        }
                        break;
                    case 2:
                        doctor.log_in();
                        while (true) {
                            System.out.println("1- View Patient.");
                            System.out.println("2- Add Prescription.");
                            System.out.println("3- Change Password.");
                            System.out.println("4- Log_out. ");
                            int doctor_ch = read.nextInt();
                            switch (doctor_ch) {
                                case 1:
                                    patient.view_patient();
                                    break;
                                case 2:
                                    patient.prescription();
                                    break;
                                case 3:
                                    doctor.change_password();
                                    break;
                                default:
                                    break;
                            }
                            if (doctor.log_out(doctor_ch)) {
                                break;
                            }
                            System.out.println();
                            System.out.println("If You Will do Another");
                        }

                        /*RECEPTIONEST..............*/
                        break;
                    case 3:
                        receptionest.log_in();
                        while (true) {
                            System.out.println("1- Add Patient.");
                            System.out.println("2- View Patient.");
                            System.out.println("3- Change Password.");
                            System.out.println("4- Create Bill.");
                            System.out.println("5- Log_out.");
                            System.out.println("Enter Your Choice: ");
                            int pateint_ch = read.nextInt();
                            switch (pateint_ch) {
                                case 1:
                                    patient.add_patient();
                                    break;
                                case 2:
                                    patient.view_patient();
                                    break;
                                case 3:
                                    receptionest.change_password();
                                    break;
                                case 4:
                                    receptionest.create_bill();
                                    break;
                                default:
                                    break;
                            }
                            if (receptionest.log_out(pateint_ch)) {
                                break;
                            }
                            System.out.println();
                            System.out.println("If You Will do Another");
                        }

                        /*EXIT...............*/
                        break;
                    case 4:
                        return;
                    default:
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

}

//            query = "alter table doctor drop(Date varchar(25) not null)";
//            statement.execute(query);
//            query = "alter table doctor add(address varchar(30))";
//            statement.execute(query);
//            query = "create table Admin (id int not null, f_name varchar(25) not null, l_name varchar(25), Phone varchar(12), "
//                    + "Username varchar(30) not null, Password varchar(30) not null, constraint pk1 primary key(ID))";
//            statement.execute(query);
//            System.out.println("ok");

