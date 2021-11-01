package model.dao;

import model.dao.impl.SellerDaoJDBC;

/*
 * Fabrica de Dao = Instância os DAOs
 */
public class DaoFactory {
	/*retorna o tipo da Interface SellerDao instÂnciando SellerDaoJDBC internamente
	 * não expondo a implementação
	 * sendo possível instânciar a interface SellerDao implementando SellerDaoJDBC
	 * fazendo uma injeção de dependência 
	 */ 
	public static SellerDao createSellerDao() { 
		return new SellerDaoJDBC(); 
	}
}
