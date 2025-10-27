package lab3_.lab3;


import org.springframework.data.repository.CrudRepository;

/**
 * Interface for BuddyInfo CrudRepository
 */
public interface BuddyRepository extends CrudRepository<lab3_.lab3.BuddyInfo, Long>{

    lab3_.lab3.BuddyInfo findByName(String name);
    lab3_.lab3.BuddyInfo findByPhone(String phone);
    lab3_.lab3.BuddyInfo findById(long id);

}
