package org.example.db.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.example.shop.Client;

public class HsqlClientDao implements ClientDao{

	
	private Statement stmt;

	private PreparedStatement insert;
	private PreparedStatement delete;
	private PreparedStatement update;
	private PreparedStatement select;
	private PreparedStatement selectId;
	
	
	
	public HsqlClientDao(Connection connection)
	{
		
		try {
			
			
			ResultSet rs = connection.getMetaData().getTables(null, null, null, null);
			boolean exist = false;
			
			stmt =connection.createStatement();
			
			while(rs.next())
			{
				if(rs.getString("TABLE_NAME").equalsIgnoreCase("Client"))
				{
					exist = true;
					break;
				}
			}
			if(!exist)
			{
				stmt.executeUpdate("CREATE TABLE Client("
						+ "id bigint GENERATED BY DEFAULT AS IDENTITY,"
						+ "name VARCHAR(50),"
						+ "surname VARCHAR(50),"
						+ "email VARCHAR(50),"
						+ "number VARCHAR(50)"
						+ ")");
			}
			
			insert = connection.prepareStatement(""
					+ "insert into client(name,surname,email,number) values (?,?,?,?)");
			
			update = connection.prepareStatement(""
					+ "update client set"
					+ "(name,surname,email,number)=(?,?,?,?)"
					+ "where id=?");
			
			delete = connection.prepareStatement(""
					+ "delete from Client where id=?");
			
			selectId = connection.prepareStatement(""
					+ "select * from Client where id=?");
			
			select = connection.prepareStatement(""
					+ "select * from client");
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		
	}
	
	
	public void save(Client ent) {
	
		try 
		{
			insert.setString(1, ent.getName());
			insert.setString(2, ent.getSurname());
			insert.setString(3, ent.getEmail());
			insert.setString(4, ent.getNumber());
			
			insert.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void delete(Client ent) {
		try 
		{
			delete.setInt(1, ent.getId());
			delete.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public List<Client> getAll() {
		
		List<Client> clients = new ArrayList<Client>();
		
		try
		{
			ResultSet rs = select.executeQuery();
			while(rs.next()){
			
				Client c = new Client();
				c.setEmail(rs.getString("email"));
				c.setName(rs.getString("name"));
				c.setSurname(rs.getString("surname"));
				c.setNumber(rs.getString("number"));
				c.setId(rs.getInt("id"));
				clients.add(c);
			}
		}
		
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
		return clients;
	}

	public Client get(int id) {
		
		try {
			selectId.setInt(1, id);
			ResultSet rs = selectId.executeQuery();
			while(rs.next()){
			
				Client c = new Client();
				c.setEmail(rs.getString("email"));
				c.setName(rs.getString("name"));
				c.setSurname(rs.getString("surname"));
				c.setNumber(rs.getString("number"));
				c.setId(rs.getInt("id"));
				return c;
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	public void setAddresses(Client c) {
		
		
	}

	public void setOrders(Client c) {
		// TODO Auto-generated method stub
		
	}


	public void update(Client ent) {
		
		try
		{
			update.setString(1, ent.getName());
			update.setString(2, ent.getSurname());
			update.setString(3, ent.getEmail());
			update.setString(4, ent.getNumber());
			update.setInt(5, ent.getId());
			update.executeUpdate();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
		
	}

}
