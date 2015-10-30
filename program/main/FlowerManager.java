package main;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class FlowerManager {
	
	private Connection connection;
	
	private String url = "jdbc:hsqldb:hsql://localhost/abc";
	
	private String createTableFlower = "CREATE TABLE Flower(idFlower bigint GENERATED BY DEFAULT AS IDENTITY, flowerName varchar(30), flowerPrice varchar(30))";


	
	private PreparedStatement addFlowerStmt;
	private PreparedStatement deleteAllFlowerStmt;
	private PreparedStatement deleteFlowerStmt;
	private PreparedStatement getAllFlowerStmt;
	private PreparedStatement updateFlowerStmt;
	
	private Statement statement;

	public FlowerManager(){
		try{
			Class.forName("org.hsqldb.jdbcDriver");
			
			connection = DriverManager.getConnection(url);
			statement = connection.createStatement();
			
			ResultSet rs = connection.getMetaData().getTables(null,null,null, null);
			
			boolean tableExists = false;
					while (rs.next()){
						if("Flower".equalsIgnoreCase(rs.getString("TABLE_NAME"))){
							tableExists = true;
							break;
						}
						
					}
		
					if(!tableExists)
						statement.executeUpdate(createTableFlower);
					
					addFlowerStmt =  connection.prepareStatement("INSERT INTO Flower(flowerName, flowerPrice)VALUES(?,?)");
					deleteAllFlowerStmt = connection.prepareStatement("DELETE FROM Flower");
					deleteFlowerStmt = connection.prepareStatement("DELETE FROM Flower WHERE idFlower =? ");
					getAllFlowerStmt = connection.prepareStatement("SELECT idFlower, flowerName, flowerPrice FROM Flower");
				    updateFlowerStmt = connection.prepareStatement("UPDATE Flower SET flowerName = ? WHERE idFlower = ?");
		
		}
		catch(SQLException e){
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
		 public Connection getConnection(){
			return connection;
		}
		public void clearFlower(){
			try{
				deleteAllFlowerStmt.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
		}
		}
		
		public void deleteFlower(Flower flower){
			
			try{
				deleteFlowerStmt.setInt(1,(int) flower.getIdFlower());				
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		
		public void updateFlower(Flower flower){
			try{
				updateFlowerStmt.setString(1, flower.getFlowerName());
				updateFlowerStmt.setLong(2, flower.getIdFlower());
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
		
		
		public int addFlower(Flower flower){
			int count = 0;
			try{
				addFlowerStmt.setString(1, flower.getFlowerName());
				addFlowerStmt.setString(2, flower.getFlowerPrice());
				
				count = addFlowerStmt.executeUpdate();
			}catch(SQLException e){
				e.printStackTrace();
			}
			return count;
		}
	
		public List<Flower> getAllFlower(){
			List<Flower> flower = new ArrayList<Flower>();
			
			try{
				ResultSet rs = getAllFlowerStmt.executeQuery();
				
				while(rs.next()){
					Flower f = new Flower();
					f.setIdFlower(rs.getInt("idFlower"));
					f.setFlowerName(rs.getString("flowerName"));
					f.setFlowerPrice(rs.getString("flowerPrice"));
					flower.add(f);
					
				}
			}
			catch(SQLException e){
				e.printStackTrace();
			}
			return flower;
		}
	
	
}
