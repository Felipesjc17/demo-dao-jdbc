package application;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {


		/*
		 * inje��o de depend�ncia sem explicitar a implementa��o SellerDaoJDBC
		 */
		SellerDao sellerDao = DaoFactory.createSellerDao(); // vai permitir a var fazer opera��es no banco de dados
		
		System.out.println("===TEST 1: seller findById =====");
		Seller seller = sellerDao.findById(3); //atrav�s da interface SellerDao pegando o resultado(vendedor) do ID
		
		System.out.println(seller);
	}

}
