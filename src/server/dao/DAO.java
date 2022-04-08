package server.dao;
 
import utils.Const;

import java.sql.Connection;
import java.sql.DriverManager;
 
public class DAO {
    public static Connection con;
     
    public DAO(){
        if(con == null){
            String dbUrl = "jdbc:sqlserver://" + Const.SERVER + ";DatabaseName=" + Const.DBNAME;
 
            try {
                con = DriverManager.getConnection (dbUrl, Const.USERNAME, Const.PASSWORD);
            }catch(Exception e) {
                e.printStackTrace();
            }
        }
    }
}