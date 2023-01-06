import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

	int productID;
	String productName;
	int productPrice;
	int choosenID;
	
	ArrayList<Product> Vec = new ArrayList<>();
	Scanner scan = new Scanner(System.in);
	Connect con = Connect.getConnection();
	PreparedStatement ps;
	ResultSet rs;
	
	String selectQuery = "SELECT * FROM product";
	String insertQuery = "INSERT INTO product VALUES (?, ?, ?)";
	String updateQuery = "UPDATE product SET ProductID = ?, ProductName = ?, ProductPrice = ? WHERE ProductID = ?";
	String deleteQuery = "DELETE FROM product WHERE ProductID = ?";

	public Main() {
		int menu = 0;
		do {
			System.out.println("1. Select ");
			System.out.println("2. Insert");
			System.out.println("3. Update");
			System.out.println("4. Delete");
			System.out.println("5. Exit");
			System.out.printf(">> ");
			menu = scan.nextInt();
			scan.nextLine();
			
			switch (menu) {
			case 1:
				Vec.clear();
				ps = con.prepareStatement(selectQuery);
				try {
					rs = ps.executeQuery();
					while(rs.next()) {
						int currID = rs.getInt(1);
						String currName = rs.getString(2);
						int currPrice = rs.getInt(3);
						Vec.add(new Product(currID, currName, currPrice));
					}
				
					System.out.println("===========================================");
					System.out.println("| ProductID | ProductName  | ProductPrice |");
					System.out.println("===========================================");
					for (Product product : Vec) {
						System.out.printf("| %-9s | %-12s | %-12s |\n", product.getProductID(), product.getProductName(), product.getProductPrice());
					}
					System.out.println("===========================================");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			
			case 2:
				System.out.printf("Product ID: ");
				productID = scan.nextInt();
				scan.nextLine();
				System.out.printf("Product Name: ");
				productName = scan.nextLine();
				System.out.printf("Product Price: ");
				productPrice = scan.nextInt();
				scan.nextLine();
				
				ps = con.prepareStatement(insertQuery);
				
				try {
					ps.setInt(1, productID);
					ps.setString(2, productName);
					ps.setInt(3, productPrice);
					ps.executeUpdate();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("Product has been inserted");
				
				break;
				
			case 3:
				System.out.printf("Product ID: ");
				productID = scan.nextInt();
				scan.nextLine();
				System.out.printf("Product Name: ");
				productName = scan.nextLine();
				System.out.printf("Product Price: ");
				productPrice = scan.nextInt();
				scan.nextLine();
				System.out.printf("Input choose ID: ");
				choosenID = scan.nextInt();
				scan.nextLine();
			
				ps = con.prepareStatement(updateQuery);
				try {
					ps.setInt(1, productID);
					ps.setString(2, productName);
					ps.setInt(3, productPrice);
					ps.setInt(4, choosenID);
					ps.executeUpdate();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("Product has been updated");
				break;
				
			case 4:
				System.out.println("Input choosen ID: ");
				choosenID = scan.nextInt();
				scan.nextLine();
				
				ps = con.prepareStatement(deleteQuery);
				try {
					ps.setInt(1, choosenID);
					ps.executeUpdate();
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("Product has been deleted");
				break;
				
			default:
				break;
			}
		} while (menu != 5);
	}

	public static void main(String[] args) {
		new Main();
	}

}
