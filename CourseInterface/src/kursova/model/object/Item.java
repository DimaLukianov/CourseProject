package kursova.model.object;

import java.util.List;

public interface Item {
	int save();
	boolean update();
	boolean delete();
	Item findById(int id);
	List<Item> all();

}
