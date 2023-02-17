package andrei.logic.validators;


/**
 * Validator for the different types of objects in the relational database schema.
 * @param <T> -> type to validate
 */
public interface Validator<T> {

    public String validate(T t);
}
