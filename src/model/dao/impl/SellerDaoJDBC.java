package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;
/*
 * implementando interface SellerDao
 * Responsabel por pegar do banco dados em tabela e transformar em objetos associados
 * Seller com Department
 */

public class SellerDaoJDBC implements SellerDao {
	
	//depend�ncia conex�o
	private Connection conn; // ser� usado como conex�o
	
	public SellerDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Seller obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Seller obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Seller findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try { //comando sql
			st = conn.prepareStatement( //iniciando prepareStatement
					"SELECT seller.*,department.Name as DepName " 
					+"FROM seller INNER JOIN department "
					+"ON seller.DepartmentId = department.Id " 
					+"WHERE seller.Id = ?");
			
			st.setInt(1, id);//primeiro e unico ? preenche com id
			rs = st.executeQuery(); // pegando resultado
			if (rs.next()) { //se falso null, se verdadeiro retorna tabela do vendedor
				//associar vendedor com departamento
				Department dep = instantiateDepartment(rs);
				Seller obj = instantiateSeller(rs, dep);
				return obj; //retornando o objeto
			}
			return null;
		}
		catch (SQLException e ) {
			throw new DbException(e.getMessage()); //excess�o personalizada
		}
		finally {//fechando recursos rs e st
		DB.closeStatement(st);
		DB.closeResultSet(rs);
		//n�o fechar conex�o aqui(coon) porque o mesmo obj pode ter mais opera��es, fechar conex�o no program depois
		}
	}
	private Seller instantiateSeller(ResultSet rs, Department dep) throws SQLException {
		Seller obj = new Seller();// var tempor�ria pegando vendedor
		obj.setId(rs.getInt("Id"));
		obj.setName(rs.getString("Name"));
		obj.setEmail(rs.getString("Email"));
		obj.setBaseSalary(rs.getDouble("BaseSalary"));
		obj.setBirthDate(rs.getDate("BirthDate"));
		obj.setDepartment(dep);//pegando objeto dep fazendo associa��o vendedor e departamento
		//conforme construtor na classe Seller
		return obj;
	}

	//metodo para inst�nciar departamento, limpando codigo
	private Department instantiateDepartment(ResultSet rs) throws SQLException{ //n�o trata, j� tratou antes
		Department dep = new Department();//var tempor�ria recebendo id e nome do departamento
		dep.setId(rs.getInt("DepartmentId"));//pega ID departamento no bd
		dep.setName(rs.getString("DepName"));//pega nome departamento
		return dep;
	}

	@Override //busca todos os vendedores e apresenta em ordem alfab�tica 
	public List<Seller> findAll() { // mesmo cod doo findByDepartment tirando Where e o preenchimento do ?
		PreparedStatement st = null;
		ResultSet rs = null;
		try { //comando sql
			st = conn.prepareStatement( //iniciando prepareStatement
					"SELECT seller.*,department.Name as DepName " 
					+ "FROM seller INNER JOIN department "
					+ "ON seller.DepartmentId = department.Id "
					+ "ORDER BY Name");
			
			
			
			rs = st.executeQuery(); // pegando resultado
			
			List<Seller> list = new ArrayList<>(); //lista que armazenar� resultados
			Map<Integer, Department> map = new HashMap<>();
			
			while (rs.next()) { // pode ser 0 ou mais valores, while no lugar de if percorre resultados 
				//vai verificar se dentro do map j� tem algum ID igual departmentId, se n�o tiver dep vai valer null
				Department dep = map.get(rs.getInt("DepartmentId")); //map tem esse DepartamentoId? se n�o null
				
				//controlador para n�o inst�nciar repetidamente o departamento
				//se dep = null vai inst�nciar departamento, objetivo � n�o ficar repetindo instanciar mesmo departamento
				if (dep == null) {
				dep = instantiateDepartment(rs); //dep sendo null(n�o achando um igual no map, vai ser inst�nciado)
				map.put(rs.getInt("DepartmentId"), dep); //add no map para departamento n�o ser instanciado novamente
				}
				
				Seller obj = instantiateSeller(rs, dep); //inst�ncia vendedor apontando para inst�ncia do departamento
				list.add(obj);//adiciona na lista
			}
			return list;
		}
		catch (SQLException e ) {
			throw new DbException(e.getMessage()); //excess�o personalizada
		}
		finally {//fechando recursos rs e st
		DB.closeStatement(st);
		DB.closeResultSet(rs);
		//n�o fechar conex�o aqui(coon) porque o mesmo obj pode ter mais opera��es, fechar conex�o no program depois
		}
	}

	@Override
	public List<Seller> findByDepartment(Department department) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try { //comando sql
			st = conn.prepareStatement( //iniciando prepareStatement
					"SELECT seller.*,department.Name as DepName " 
					+ "FROM seller INNER JOIN department "
					+ "ON seller.DepartmentId = department.Id "
					+ "WHERE DepartmentId = ? "
					+ "ORDER BY Name");
			
			st.setInt(1, department.getId());//primeiro e unico ? preenche com id do departamento
			
			rs = st.executeQuery(); // pegando resultado
			
			List<Seller> list = new ArrayList<>(); //lista que armazenar� resultados
			Map<Integer, Department> map = new HashMap<>();
			
			while (rs.next()) { // pode ser 0 ou mais valores, while no lugar de if percorre resultados 
				//vai verificar se dentro do map j� tem algum ID igual departmentId, se n�o tiver dep vai valer null
				Department dep = map.get(rs.getInt("DepartmentId")); //map tem esse DepartamentoId? se n�o null
				
				//controlador para n�o inst�nciar repetidamente o departamento
				//se dep = null vai inst�nciar departamento, objetivo � n�o ficar repetindo instanciar mesmo departamento
				if (dep == null) {
				dep = instantiateDepartment(rs); //dep sendo null(n�o achando um igual no map, vai ser inst�nciado)
				map.put(rs.getInt("DepartmentId"), dep); //add no map para departamento n�o ser instanciado novamente
				}
				
				Seller obj = instantiateSeller(rs, dep); //inst�ncia vendedor apontando para inst�ncia do departamento
				list.add(obj);//adiciona na lista
			}
			return list;
		}
		catch (SQLException e ) {
			throw new DbException(e.getMessage()); //excess�o personalizada
		}
		finally {//fechando recursos rs e st
		DB.closeStatement(st);
		DB.closeResultSet(rs);
		//n�o fechar conex�o aqui(coon) porque o mesmo obj pode ter mais opera��es, fechar conex�o no program depois
		}
	}

}
