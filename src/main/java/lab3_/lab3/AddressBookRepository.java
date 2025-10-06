package lab3_.lab3;

import org.springframework.data.repository.CrudRepository;

public interface AddressBookRepository extends CrudRepository<AddressBook, String> {

    AddressBook findByName(String name);

}
