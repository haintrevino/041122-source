package dao;

public interface DAOInterface<T, ID, ID2> {
	
	public void create(T element);
	
	public T get(ID id);
	//eg public User getUser(String id);
	
	public void update(T element);
	
	public void delete(T element);

	public boolean exist(ID id);
	
	public void getOpen();

	public void getAll();

	public void getMy(ID2 id);

	public void getMyMinus(ID2 id2, ID id);

	public T getCust(ID2 id2);

	public int getNum(ID2 id2);
	
}
