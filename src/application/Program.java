package application;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {


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
		
	}

}
