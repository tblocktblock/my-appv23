package com.example.application.daten;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class sqldriver2<bufImage> {

    public static String test(String i) throws Exception {
        String url = "jdbc:mysql://buslernen.de:3306/kkredoku?serverTimezone=UTC";
        String uname = "tblock";
        String pass = "2easy4U";
        String query = "INSERT INTO `kkredoku`.`all_files` (`idall_files`, `filename`, mandant) VALUES ('5', '" + i + "','46125');";

        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("MySQL JDBC driver loaded ok.");

        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
        }

        Connection con = DriverManager.getConnection(url, uname, pass);
        Statement st = con.createStatement();
        st.executeUpdate(query);
        st.close();
        con.close();
        return "fertigg";
    }

    public static ArrayList<Belege> test2(String i) throws Exception {
        String url = "jdbc:mysql://buslernen.de:3306/kkredoku?serverTimezone=UTC";
        String uname = "tblock";
        String pass = "2easy4U";
        //String query = "SELECT filename, indexx, mandant FROM kkredoku.all_files where idall_files =5;";
        String query = "SELECT filename, indexx, mandant, belegdatum, abrechnungsobjekt, mandant,sachkonto, verwendungszweck, rechnungsbetrag FROM kkredoku.all_files ORDER BY belegdatum DESC;";
        //String query = "SELECT filename, indexx, mandant, belegdatum FROM kkredoku.all_files;";
        //String query = "DELETE  FROM kkredoku.all_files;";

        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection(url, uname, pass);
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(query);
        ArrayList<Belege> belegListe = new ArrayList<Belege>();

        String suche = "";

        while (rs.next()) {
            suche = rs.getString(1);
            Belege beleg1 = new Belege(rs.getString(1), rs.getString(4), rs.getString(2), "ok", "ok", rs.getString(2), rs.getString(5),rs.getString(6),rs.getString(7) ,rs.getString(8), rs.getDouble(9));
            belegListe.add(beleg1);
        }
        st.close();
        con.close();
        return belegListe;
    }

    public static String test3(String i) throws Exception {
        String url = "jdbc:mysql://buslernen.de:3306/kkredoku?serverTimezone=UTC";
        String uname = "tblock";
        String pass = "2easy4U";
        String query = "INSERT INTO `kkredoku`.`all_files` (`idall_files`, `filename`, mandant) VALUES ('5', '" + i + "','46125');";

        Connection connection = null;
        ResultSet rs = null;
        PreparedStatement psmnt = null;
        FileInputStream fis;

        File image = new File("src/main/resources/pdf/46100100402932.pdf");

        try {

            // Load JDBC driver "com.mysql.jdbc.Driver"
            Class.forName("com.mysql.cj.jdbc.Driver");
            /* Create a connection by using getConnection() method that takes
               parameters of string type connection url, user name and password to
               connect to database. */
            connection = DriverManager.getConnection(url, uname, pass);

            /* prepareStatement() is used for create statement object that is
               used for sending sql statements to the specified database. */
            psmnt = connection.prepareStatement("insert into `kkredoku`.`all_files`(filename, belegpdf) " + "values(?,?)");

            psmnt.setString(1, "46100100400271.pdf");

            fis = new FileInputStream(image);
            psmnt.setBinaryStream(2, fis, (int) (image.length()));
            /* executeUpdate() method execute specified sql query. Here this query
               insert data and image from specified address. */
            int s = psmnt.executeUpdate();
            if (s > 0) {
                System.out.println("Uploaded successfully !");
            } else {
                System.out.println("unsucessfull to upload image.");
            }
        }
        // catch if found any exception during rum time.
        catch (Exception ex) {
            System.out.println("Found some error : " + ex);
        } finally {
            // close all the connections.
            connection.close();
            psmnt.close();
        }
        return "ferig";
    }

    public static String test4(String i, byte[] mybytes) throws Exception {
        String url = "jdbc:mysql://buslernen.de:3306/kkredoku?serverTimezone=UTC";
        String uname = "tblock";
        String pass = "2easy4U";
        String query = "INSERT INTO `kkredoku`.`all_files` (`idall_files`, `filename`, mandant) VALUES ('5', '" + i + "','46125');";

        Connection connection = null;
        ResultSet rs = null;
        PreparedStatement psmnt = null;
        FileInputStream fis;

        File image = new File("src/main/resources/pdf/46100100402932.pdf");

        try {

            // Load JDBC driver "com.mysql.jdbc.Driver"
            Class.forName("com.mysql.cj.jdbc.Driver");
            /* Create a connection by using getConnection() method that takes
               parameters of string type connection url, user name and password to
               connect to database. */
            connection = DriverManager.getConnection(url, uname, pass);

            /* prepareStatement() is used for create statement object that is
               used for sending sql statements to the specified database. */
            psmnt = connection.prepareStatement("insert into `kkredoku`.`all_files`(filename, belegpdf, belegdatum) " + "values(?,?,?)");

            java.util.Date dt = new java.util.Date();
            SimpleDateFormat sdf =
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String currentTime = sdf.format(dt);

            psmnt.setString(1, i);
            System.out.println(currentTime);

            fis = new FileInputStream(image);
            //psmnt.setBinaryStream(2, fis, (int)(image.length()));

            psmnt.setBytes(2, mybytes);
            psmnt.setString(3, currentTime);
            
            /* executeUpdate() method execute specified sql query. Here this query
               insert data and image from specified address. */
            int s = psmnt.executeUpdate();
            if (s > 0) {
                System.out.println("Uploaded successfully !");
            } else {
                System.out.println("unsucessfull to upload image.");
            }
        }
        // catch if found any exception during rum time.
        catch (Exception ex) {
            System.out.println("Found some error : " + ex);
        } finally {
            // close all the connections.
            connection.close();
            psmnt.close();
        }
        return "ferig";
    }

    public static BufferedImage test7(String i) throws Exception {
        String url = "jdbc:mysql://buslernen.de:3306/kkredoku?serverTimezone=UTC";
        String uname = "tblock";
        String pass = "2easy4U";

        Connection connection = null;
        PreparedStatement psmnt = null;
        FileInputStream fis;

        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(url, uname, pass);

        System.out.println("Test");

        psmnt = connection.prepareStatement("SELECT belegpdf FROM kkredoku.all_files where indexx = 192;");

        ResultSet rs = psmnt.executeQuery();

        while (rs.next()) {
            //save blob as an image
            System.out.println("gefunden");
            FileOutputStream fos = new FileOutputStream("src/main/resources/pdf/xxx.pdf");
            InputStream is = rs.getBinaryStream(1);
            byte[] buf = new byte[3000];
            int read = 0;

            while ((read = is.read(buf)) > 0) {
                fos.write(buf, 0, read);

            }
            fos.close();
            is.close();
        }

        connection.close();
        psmnt.close();

        //BufferedInputStream bis = new BufferedInputStream( rs.getBinaryStream(1) );

        BufferedImage bufImage = null;
        return bufImage;
    }

    public static String test8(String i) throws Exception {
        String url = "jdbc:mysql://buslernen.de:3306/kkredoku?serverTimezone=UTC";
        String uname = "tblock";
        String pass = "2easy4U";

        Connection connection = null;
        PreparedStatement psmnt = null;
        FileInputStream fis;

        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(url, uname, pass);

        psmnt = connection.prepareStatement("SELECT belegpdf FROM kkredoku.all_files where indexx = " + i + ";");

        ResultSet rs = psmnt.executeQuery();

        File f = File.createTempFile("rechnung", ".pdf");

        while (rs.next()) {
            //save blob as an image
            System.out.println("gefunden");
            FileOutputStream fos = new FileOutputStream(f);
            InputStream is = rs.getBinaryStream(1);
            byte[] buf = new byte[3000];
            int read = 0;

            while ((read = is.read(buf)) > 0) {
                fos.write(buf, 0, read);
            }
            fos.close();
            is.close();

        }

        System.out.println("Test" + f.getPath() + " " + f.getAbsolutePath() + " " + f.getTotalSpace());

        connection.close();
        psmnt.close();
        return f.getPath();
    }

        public static String test9(String indexx, String mandant, String i, String sachkonto, String verwendungszweck, Double rechnungsbetrag ) throws Exception {
            String url = "jdbc:mysql://buslernen.de:3306/kkredoku?serverTimezone=UTC";
            String uname = "tblock";
            String pass = "2easy4U";
            //String query = "UPDATE `kkredoku`.`all_files` SET `abrechnungsobjekt` = "+  i + ", 'mandant' = " + mandant + " WHERE (`indexx` = " + indexx +");";
            String query = "UPDATE `kkredoku`.`all_files` SET `mandant` = "+  mandant + ", `abrechnungsobjekt` = "+  i + ", `sachkonto` = "+  sachkonto + ", `verwendungszweck` = '"+  verwendungszweck + "', `rechnungsbetrag` = "+  rechnungsbetrag  + " WHERE (`indexx` = " + indexx +");";

            System.out.println(query);

            try {
                // Load the MySQL JDBC driver
                Class.forName("com.mysql.cj.jdbc.Driver");
                System.out.println("MySQL JDBC driver loaded ok.");

            } catch (Exception e) {
                System.err.println("Exception: " + e.getMessage());
            }

            Connection con = DriverManager.getConnection(url, uname, pass);
            Statement st = con.createStatement();
            st.executeUpdate(query);
            st.close();
            con.close();
            return "fertigg";
        }

    }
