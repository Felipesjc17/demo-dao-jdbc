package application;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		
		/*
		 * injeção de dependÊncia sem explicitar a implementação SellerDaoJDBC
		 */
		SellerDao sellerDao = DaoFactory.createSellerDao(); // vai permitir a var fazer operações no banco de dados
		
		System.out.println("===TEST 1: seller findById =====");
		Seller seller = sellerDao.findById(3); //através da interface SellerDao pegando o resultado(vendedor) do ID
		
		System.out.println(seller);
		
		System.out.println("\n===TEST 2: seller findByDepartment =====");
		Department department = new Department(2, null);//pegando uma department só com ID
		List<Seller> list = sellerDao.findByDepartment(department);//pegando a var de cima
		//lista de departamento sendo impressa
		for (Seller obj : list) {
			System.out.println(obj);
		}
		
		System.out.println("\n===TEST 3: seller findByDepartment =====");
		
		list = sellerDao.findAll();//passando para list lista de vendedores
		//lista de departamento sendo impressa
		for (Seller obj : list) {
			System.out.println(obj);
		}
		
		System.out.println("\n===TEST 4: seller insert =====");
		Seller newSeller = new Seller(null, "Greg", "greg@gmail.com" , new Date(), 4000.0, department);
		sellerDao.insert(newSeller);
			System.out.println("Inserted! New id = " + newSeller.getId());
		
		System.out.println("\n===TEST 5: seller update =====");
		seller = sellerDao.findById(1);
		seller.setName("Martha Waine");
		sellerDao.update(seller);
		System.out.println("Update completed");
		
		System.out.println("\n===TEST 6: seller delete =====");
		System.out.println("Enter id for delete test: ");
		int id = sc.nextInt();
		sellerDao.deleteById(id);
		System.out.println("Delete completed");
		
		sc.close();
	}
	
	

}
