package model.dao;

import model.dao.impl.SellerDaoJDBC;

/*
 * Fabrica de Dao = Inst�ncia os DAOs
 */
public class DaoFactory {
	/*retorna o tipo da Interface SellerDao inst�nciando SellerDaoJDBC internamente
	 * n�o expondo a implementa��o
	 * sendo poss�vel inst�nciar a interface SellerDao implementando SellerDaoJDBC
	 * fazendo uma inje��o de depend�ncia 
	 */ 
	public static SellerDao createSellerDao() { 
		return new SellerDaoJDBC(); 
	}
}