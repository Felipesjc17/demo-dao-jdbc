package model.dao;

import java.util.List;

import model.entities.Department;

public interface DepartmentDao {

	void insert(Department obj); //insere obj bd
	void update(Department obj);
	void deleteById(Integer id);
	Department findById(Integer id);//recebe ID consulta no bd, se existir retorna id do departamento, senão retorna null
	List<Department>findAll(); //retorna todos departamentos em uma lista
	
}
