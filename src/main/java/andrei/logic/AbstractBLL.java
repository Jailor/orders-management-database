package andrei.logic;

import java.util.List;

/**
 * This is a generic abstract class that defines the CRUD functions to be implemented by each business logic class.
 * This is something used
 * @param <T> - the type for the business logic. One element from the objects in the model: Orders, Product or Client
 */
public abstract class AbstractBLL<T> {
    /**
     * This function does a select statement and gives all the objects of type T from the database
     * @return list of objects of the required type
     */
    public abstract List<T> findAll();

    /**
     * The object with the fields defined/taken from the GUI is to be inserted into the database.
     * @param obj - object to be inserted
     * @return - error code or null in case of success
     */
    public abstract String insert(T obj);

    /**
     * The object to be edited with the updated fields taken from the GUI is to be updated in the database.
     * @param obj - object to be updated
     * @return - error code or null in the case of an error
     */
    public abstract String update(T obj);

    /**
     * The object to be deleted from the database is sent as a parameter.
     * @param obj - object to be deleted
     * @return - always null
     */
    public abstract String delete(T obj);
}
