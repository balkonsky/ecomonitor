package com.mqtt.ecomonitor;

import java.sql.*;

public class DAO {
    private static final String dburl = "jdbc:mysql://localhost:3306/MagistrBase";
    private static final String user = "root";
    private static final String password = "Ael8Raix";

    private static Connection con;
    private static Statement stmt;
    private static ResultSet rs;
    private static PreparedStatement stmt_in;

    public void setPressure (double pressure){
        String query = "INSERT INTO `MagistrBase`.`press` (`PRESSURE`)  VALUES (?)";


        try {
            con = DriverManager.getConnection(dburl,user,password);
            stmt_in = con.prepareStatement(query);
            stmt_in.setString(1,String.valueOf(pressure));
            stmt_in.executeUpdate();
        }

        catch (SQLException e){
            System.out.println(e);
        }

        finally {
            try {con.close();} catch (SQLException e) { System.out.println(e);}
            try {stmt_in.close();} catch (SQLException e ){ System.out.println(e);}
        }

    }

    public void setAir (double air){
        String query = "INSERT INTO `MagistrBase`.`air`(`AIR`) VALUES (?)";

        try {
            con = DriverManager.getConnection(dburl,user,password);
            stmt_in = con.prepareStatement(query);
            stmt_in.setString(1,String.valueOf(air));
            stmt_in.executeUpdate();
        }

        catch (SQLException e){
            System.out.println(e);
        }

        finally {
            try {con.close();} catch (SQLException e) { System.out.println(e);}
            try {stmt_in.close();} catch (SQLException e) { System.out.println(e);}
        }

    }

    public void setHumidity (double humidity){
        String query = "INSERT INTO `MagistrBase`.`hum`(`HUMIDITY`) VALUES (?)";

        try {
            con = DriverManager.getConnection(dburl,user,password);
            stmt_in = con.prepareStatement(query);
            stmt_in.setString(1,String.valueOf(humidity));
            stmt_in.executeUpdate();
        }

        catch (SQLException e){
            System.out.println(e);
        }

        finally {
            try {con.close();} catch (SQLException e) { System.out.println(e);}
            try {stmt_in.close();} catch (SQLException e) { System.out.println(e);}
        }

    }

    public void setTemperature (double temperature){
        String query = "INSERT INTO `MagistrBase`.`temp`(`TEMPERATURE`) VALUES (?)";
    try {
        con = DriverManager.getConnection(dburl, user, password);
        stmt_in = con.prepareStatement(query);
        stmt_in.setString(1,String.valueOf(temperature));
        stmt_in.executeUpdate();
        }
    catch (SQLException e) {
        System.out.println(e);
        }
    finally {
        try {con.close();} catch (SQLException e) { System.out.println(e);}
        try {stmt_in.close();} catch (SQLException e ) { System.out.println(e);}
        }
    }


    public double getPressure (){
        String query = "SELECT PRESSURE FROM press ORDER by ID DESC LIMIT 1";
        double result=0;

        try {
            con = DriverManager.getConnection(dburl,user,password);
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);

            while (rs.next()){
                result = Double.valueOf(rs.getString(1));
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }

        finally {
            try {con.close();} catch (SQLException e) { System.out.println(e);}
            try {stmt.close();} catch (SQLException e) { System.out.println(e);}
            try {rs.close();} catch (SQLException e) { System.out.println(e);}
        }

        return result;
    }

    public double getAir (){
        String query = "SELECT AIR from air ORDER by ID DESC LIMIT 1";
        double result = 0;

        try {
            con = DriverManager.getConnection(dburl,user,password);
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);

            while (rs.next()){
                result = Double.valueOf(rs.getString(1));
            }
        }

        catch (SQLException e) {
            e.printStackTrace();
        }

        finally {
            try {con.close();} catch (SQLException e) { System.out.println(e);}
            try {stmt.close();} catch (SQLException e) { System.out.println(e);}
            try {rs.close();} catch (SQLException e) { System.out.println(e);}
        }

        return result;
    }

    public double getHumidity (){
        String query = "SELECT HUMIDITY from hum ORDER by ID DESC LIMIT 1";
        double result = 0;

        try {
            con = DriverManager.getConnection(dburl,user,password);
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);

            while (rs.next()){
                result = Double.valueOf(rs.getString(1));
            }
        }
        catch (SQLException e){
            System.out.println(e);
        }

        finally {
            try {con.close();} catch (SQLException e ) { System.out.println(e);}
            try {stmt.close();} catch (SQLException e) { System.out.println(e);}
            try {rs.close();} catch (SQLException e ) { System.out.println(e);}
        }

        return result;
    }

    public double getTemperature (){
        String query = "SELECT TEMPERATURE FROM temp ORDER by ID DESC LIMIT 1";
        double result = 0;

        try {
            con = DriverManager.getConnection(dburl,user,password);
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);

            while (rs.next()){
                result = Double.valueOf(rs.getString(1));
            }

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

}
