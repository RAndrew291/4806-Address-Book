package lab3_.lab3;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BuddyInfoRepository extends CrudRepository<BuddyInfo, String> {

    List<BuddyInfo> findByName(String name);

    BuddyInfo findByPhone(String phone);
}
