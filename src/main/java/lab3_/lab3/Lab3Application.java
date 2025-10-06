package lab3_.lab3;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class Lab3Application {

    private static final Logger log = LoggerFactory.getLogger(Lab3Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Lab3Application.class, args);
    }

//    @Bean
//    CommandLineRunner init(BuddyInfoRepository repo1, AddressBookRepository repo2) {
//        return (args) -> {
//            // create AddressBook objects
//            AddressBook addressBook1 = new AddressBook("BuddyAddressBook1");
//            AddressBook addressBook2 = new AddressBook("BuddyAddressBook2");
//
//            // create and save BuddyInfo objects
//            BuddyInfo buddy1 = new BuddyInfo("Cameron", "911");
//            BuddyInfo buddy2 = new BuddyInfo("John", "311");
//            BuddyInfo buddy3 = new BuddyInfo("Jane", "511");
//            BuddyInfo buddy4 = new BuddyInfo("Clarke", "9028273050");
//            BuddyInfo buddy5 = new BuddyInfo("Cameron", "9028272222");
//
//            // add buddies to address books
//            addressBook1.addBuddie(buddy1);
//            addressBook1.addBuddie(buddy2);
//            addressBook1.addBuddie(buddy3);
//
//            addressBook2.addBuddie(buddy4);
//            addressBook2.addBuddie(buddy5);
//
//            // save AddressBook objects
//            repo2.save(addressBook1);
//            repo2.save(addressBook2);
//
//
//            // find all BuddyInfo objects
//            log.info("Buddys found with findAll():");
//            log.info("----------------");
//            repo1.findAll().forEach(buddy -> {
//                log.info(buddy.toString());
//            });
//            log.info("");
//
//            // find buddy with specified phone number
//            BuddyInfo buddy = repo1.findByPhone("9028273050");
//            log.info("Buddy found with findByPhone('9028273050'):");
//            log.info("-----------------");
//            log.info(buddy.toString());
//            log.info("");
//
//            // find all buddys with name cameron
//            log.info("Buddy found with findByName('Cameron'):");
//            log.info("-----------------");
//            repo1.findByName("Cameron").forEach(Cameron -> {
//                log.info(Cameron.toString());
//            });
//            log.info("");
//
//
//            // find all address books
//            log.info("AddressBooks found with findAll()");
//            log.info("----------------");
//            repo2.findAll().forEach(AddressBook -> {
//                log.info(AddressBook.toString());
//            });
//            log.info("");
//
//            // find address book with name (id) BuddyAddressBook1
//            AddressBook addressBook = repo2.findByName("BuddyAddressBook1");
//            log.info("AddressBooks found with findByName('BuddyAddressBook1'):");
//            log.info("-----------------");
//            log.info(addressBook.toString());
//            log.info("");
//        };
//    }
}
