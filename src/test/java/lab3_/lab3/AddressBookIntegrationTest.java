package lab3_.lab3;



import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;



@SpringBootTest
@AutoConfigureMockMvc
public class AddressBookIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private AddressBookRepository addressBookRepository;

    @Autowired
    private BuddyRepository buddyRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        // Clean up repositories before each test
        addressBookRepository.deleteAll();
        buddyRepository.deleteAll();
    }

    // ========== GET MAPPINGS TESTS ==========

    @Test
    public void testFindAllAddressBooks_Empty() throws Exception {
        mvc.perform(get("/addressBooks"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void testFindAllAddressBooks_WithData() throws Exception {
        // Create test data
        AddressBook book1 = new AddressBook("TestBook1");
        AddressBook book2 = new AddressBook("TestBook2");
        addressBookRepository.save(book1);
        addressBookRepository.save(book2);

        mvc.perform(get("/addressBooks"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("TestBook1")))
                .andExpect(jsonPath("$[1].name", is("TestBook2")));
    }

    @Test
    public void testFindAddressBookByName_Found() throws Exception {
        // Create test data
        AddressBook book = new AddressBook("MyBook");
        addressBookRepository.save(book);

        mvc.perform(get("/addressBooks/MyBook"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is("MyBook")));
    }

    @Test
    public void testFindAddressBookByName_NotFound() throws Exception {
        mvc.perform(get("/addressBooks/NonExistentBook"))
                .andExpect(status().isNotFound());
    }

    // ========== POST MAPPINGS TESTS ==========

    @Test
    public void testCreateAddressBook() throws Exception {
        AddressBook newBook = new AddressBook("NewBook");
        String bookJson = objectMapper.writeValueAsString(newBook);

        mvc.perform(post("/addressBooks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("NewBook")))
                .andExpect(jsonPath("$.id").exists());

        // Verify it was saved
        AddressBook savedBook = addressBookRepository.findByName("NewBook");
        assert savedBook != null;
        assert savedBook.getName().equals("NewBook");
    }

    @Test
    public void testAddBuddyToAddressBook() throws Exception {
        // Create an address book first
        AddressBook book = new AddressBook("FriendsBook");
        addressBookRepository.save(book);

        // Create a buddy to add (as a simple JSON string to match how the API receives data)
        String buddyJson = "{\"name\":\"John Doe\",\"phone\":\"613-555-1234\"}";

        mvc.perform(post("/addressBooks/FriendsBook/contacts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(buddyJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("FriendsBook")))
                .andExpect(jsonPath("$.contacts", hasSize(1)))
                .andExpect(jsonPath("$.contacts[0].name", is("John Doe")))
                .andExpect(jsonPath("$.contacts[0].phone", is("613-555-1234")));

        // Verify the buddy was added by checking the buddy repository directly
        BuddyInfo savedBuddy = buddyRepository.findByPhone("613-555-1234");
        assert savedBuddy != null : "Buddy should be saved";
        assert savedBuddy.getName().equals("John Doe") : "Buddy name should be John Doe";
    }

    @Test
    public void testAddBuddyToNonExistentAddressBook() throws Exception {
        BuddyInfo buddy = new BuddyInfo("Jane Doe", "613-555-5678", "123 Fleet Street");
        String buddyJson = objectMapper.writeValueAsString(buddy);

        mvc.perform(post("/addressBooks/NonExistentBook/contacts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(buddyJson))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testAddMultipleBuddiesToAddressBook() throws Exception {
        // Create an address book
        AddressBook book = new AddressBook("WorkContacts");
        addressBookRepository.save(book);

        // Add first buddy
        BuddyInfo buddy1 = new BuddyInfo("Alice", "613-111-1111", "123 Fleet Street");
        String buddy1Json = objectMapper.writeValueAsString(buddy1);
        mvc.perform(post("/addressBooks/WorkContacts/contacts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(buddy1Json))
                .andExpect(status().isOk());

        // Add second buddy
        BuddyInfo buddy2 = new BuddyInfo("Bob", "613-222-2222", "234 Fleet Street");
        String buddy2Json = objectMapper.writeValueAsString(buddy2);
        mvc.perform(post("/addressBooks/WorkContacts/contacts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(buddy2Json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.contacts", hasSize(2)));
    }

    // ========== DELETE MAPPINGS TESTS ==========

    @Test
    public void testDeleteAddressBook() throws Exception {
        // Create an address book
        AddressBook book = new AddressBook("ToDelete");
        addressBookRepository.save(book);

        // Verify it exists
        assert addressBookRepository.findByName("ToDelete") != null;

        // Delete it
        mvc.perform(delete("/addressBooks/ToDelete"))
                .andExpect(status().isNoContent());

        // Verify it's gone
        assert addressBookRepository.findByName("ToDelete") == null;
    }

    @Test
    public void testDeleteNonExistentAddressBook() throws Exception {
        mvc.perform(delete("/addressBooks/NonExistent"))
                .andExpect(status().isNoContent()); // Still returns 204 as per your implementation
    }

    @Test
    public void testDeleteBuddyFromAddressBook() throws Exception {
        // Create an address book with a buddy
        AddressBook book = new AddressBook("ContactList");
        BuddyInfo buddy = new BuddyInfo("Charlie", "613-333-3333", "123 Fleet Street");
        book.addBuddy(buddy);
        addressBookRepository.save(book);

        // Delete the buddy
        mvc.perform(delete("/addressBooks/ContactList/contacts/613-333-3333"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.contacts", hasSize(0)));

        // Verify buddy is deleted from database
        BuddyInfo deletedBuddy = buddyRepository.findByPhone("613-333-3333");
        assert deletedBuddy == null : "Buddy should be deleted from database";
    }

    @Test
    public void testDeleteBuddyFromNonExistentAddressBook() throws Exception {
        mvc.perform(delete("/addressBooks/NonExistent/contacts/613-999-9999"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteNonExistentBuddyFromAddressBook() throws Exception {
        // Create an address book without buddies
        AddressBook book = new AddressBook("EmptyBook");
        addressBookRepository.save(book);

        mvc.perform(delete("/addressBooks/EmptyBook/contacts/613-999-9999"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteOneBuddyLeavesOthers() throws Exception {
        // Create an address book with multiple buddies
        AddressBook book = new AddressBook("MultipleContacts");
        BuddyInfo buddy1 = new BuddyInfo("David", "613-444-4444", "123 Fleet Street");
        BuddyInfo buddy2 = new BuddyInfo("Eve", "613-555-5555", "234 Fleet Street");
        BuddyInfo buddy3 = new BuddyInfo("Trump", "666-666-6666", "666 Fleet Street");
        book.addBuddy(buddy1);
        book.addBuddy(buddy2);
        book.addBuddy(buddy3);
        addressBookRepository.save(book);

        // Delete one buddy
        mvc.perform(delete("/addressBooks/MultipleContacts/contacts/613-555-5555"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.contacts", hasSize(2)));

        // Verify the deleted buddy is gone
        BuddyInfo deletedBuddy = buddyRepository.findByPhone("613-555-5555");
        assert deletedBuddy == null : "Deleted buddy should not exist";

        // Verify the other buddies remain
        BuddyInfo remainingBuddy1 = buddyRepository.findByPhone("613-444-4444");
        BuddyInfo remainingBuddy2 = buddyRepository.findByPhone("666-666-6666");
        assert remainingBuddy1 != null : "Buddy 1 should still exist";
        assert remainingBuddy2 != null : "Buddy 3 should still exist";
        assert remainingBuddy1.getName().equals("David");
        assert remainingBuddy2.getName().equals("Trump");
    }


}
