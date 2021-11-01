package model.dao;

import java.util.List;

import model.entities.Department;
import model.entities.Seller;

public interface SellerDao {
	//assinaturas dos m�todos
	void insert(Seller obj); //insere obj bd
	void update(Seller obj);
	void deleteById(Integer id);
	Seller findById(Integer id);//recebe ID consulta no bd, se existir retorna id do vendedor, sen�o retorna null
	List<Seller>findAll(); //retorna todos vendedores em uma lista
	List<Seller> findByDepartment(Department department); // busca department
}



