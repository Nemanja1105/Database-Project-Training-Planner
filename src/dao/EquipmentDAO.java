package dao;

import java.sql.SQLException;
import java.util.List;

import models.Equipment;
import models.EquipmentDTO;

public interface EquipmentDAO {
	List<Equipment> findAll()throws SQLException;
}
