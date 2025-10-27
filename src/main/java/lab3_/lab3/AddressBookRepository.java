package lab3_.lab3;

import org.springframework.data.repository.CrudRepository;


/**
 * Interface for AddressBook CrudRepository
 */
public interface AddressBookRepository extends CrudRepository<lab3_.lab3.AddressBook, Long> {

    lab3_.lab3.AddressBook findByName(String name);
    lab3_.lab3.AddressBook findById(long id);
}
