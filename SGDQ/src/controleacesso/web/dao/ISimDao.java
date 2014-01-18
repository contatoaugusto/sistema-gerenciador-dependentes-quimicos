/**
 * @author Fabian Silva
 *
 */
package controleacesso.web.dao;

import java.util.List;

public interface ISimDao <T> {

 
public void save(T objDao);
public T getObjDao(long id);
public T getObjDao(String nome);
public List<T> list();
public void remove(T objDao);
public void update(T objDao);

 
}