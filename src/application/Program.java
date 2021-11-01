package application;

import model.dao.DaoFactory;
import model.dao.SellerDao;
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
	}

}
