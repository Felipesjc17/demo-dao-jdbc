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
	
	//dependência conexão
	private Connection conn; // será usado como conexão
	
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
			throw new DbException(e.getMessage()); //excessão personalizada
		}
		finally {//fechando recursos rs e st
		DB.closeStatement(st);
		DB.closeResultSet(rs);
		//não fechar conexão aqui(coon) porque o mesmo obj pode ter mais operações, fechar conexão no program depois
		}
	}
	private Seller instantiateSeller(ResultSet rs, Department dep) throws SQLException {
		Seller obj = new Seller();// var temporária pegando vendedor
		obj.setId(rs.getInt("Id"));
		obj.setName(rs.getString("Name"));
		obj.setEmail(rs.getString("Email"));
		obj.setBaseSalary(rs.getDouble("BaseSalary"));
		obj.setBirthDate(rs.getDate("BirthDate"));
		obj.setDepartment(dep);//pegando objeto dep fazendo associação vendedor e departamento
		//conforme construtor na classe Seller
		return obj;
	}

	//metodo para instânciar departamento, limpando codigo
	private Department instantiateDepartment(ResultSet rs) throws SQLException{ //não trata, já tratou antes
		Department dep = new Department();//var temporária recebendo id e nome do departamento
		dep.setId(rs.getInt("DepartmentId"));//pega ID departamento no bd
		dep.setName(rs.getString("DepName"));//pega nome departamento
		return dep;
	}

	@Override //busca todos os vendedores e apresenta em ordem alfabética 
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
			
			List<Seller> list = new ArrayList<>(); //lista que armazenará resultados
			Map<Integer, Department> map = new HashMap<>();
			
			while (rs.next()) { // pode ser 0 ou mais valores, while no lugar de if percorre resultados 
				//vai verificar se dentro do map já tem algum ID igual departmentId, se não tiver dep vai valer null
				Department dep = map.get(rs.getInt("DepartmentId")); //map tem esse DepartamentoId? se não null
				
				//controlador para não instânciar repetidamente o departamento
				//se dep = null vai instânciar departamento, objetivo é não ficar repetindo instanciar mesmo departamento
				if (dep == null) {
				dep = instantiateDepartment(rs); //dep sendo null(não achando um igual no map, vai ser instânciado)
				map.put(rs.getInt("DepartmentId"), dep); //add no map para departamento não ser instanciado novamente
				}
				
				Seller obj = instantiateSeller(rs, dep); //instÂncia vendedor apontando para instância do departamento
				list.add(obj);//adiciona na lista
			}
			return list;
		}
		catch (SQLException e ) {
			throw new DbException(e.getMessage()); //excessão personalizada
		}
		finally {//fechando recursos rs e st
		DB.closeStatement(st);
		DB.closeResultSet(rs);
		//não fechar conexão aqui(coon) porque o mesmo obj pode ter mais operações, fechar conexão no program depois
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
			
			List<Seller> list = new ArrayList<>(); //lista que armazenará resultados
			Map<Integer, Department> map = new HashMap<>();
			
			while (rs.next()) { // pode ser 0 ou mais valores, while no lugar de if percorre resultados 
				//vai verificar se dentro do map já tem algum ID igual departmentId, se não tiver dep vai valer null
				Department dep = map.get(rs.getInt("DepartmentId")); //map tem esse DepartamentoId? se não null
				
				//controlador para não instânciar repetidamente o departamento
				//se dep = null vai instânciar departamento, objetivo é não ficar repetindo instanciar mesmo departamento
				if (dep == null) {
				dep = instantiateDepartment(rs); //dep sendo null(não achando um igual no map, vai ser instânciado)
				map.put(rs.getInt("DepartmentId"), dep); //add no map para departamento não ser instanciado novamente
				}
				
				Seller obj = instantiateSeller(rs, dep); //instÂncia vendedor apontando para instância do departamento
				list.add(obj);//adiciona na lista
			}
			return list;
		}
		catch (SQLException e ) {
			throw new DbException(e.getMessage()); //excessão personalizada
		}
		finally {//fechando recursos rs e st
		DB.closeStatement(st);
		DB.closeResultSet(rs);
		//não fechar conexão aqui(coon) porque o mesmo obj pode ter mais operações, fechar conexão no program depois
		}
	}

}
