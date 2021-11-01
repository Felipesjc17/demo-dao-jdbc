package model.dao;

import db.DB;
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
		return new SellerDaoJDBC(DB.getConnection()); //A Interface SellerDao retorna SellerDaoJDBC (responsável por ações no banco) que tem a conexão como argumento 
	}
}
