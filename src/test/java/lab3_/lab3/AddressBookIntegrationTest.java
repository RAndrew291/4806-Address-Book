package lab3_.lab3;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AddressBookIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String getBaseUrl() {
        return "http://localhost:" + port;
    }

    @Test
    public void testGetAllAddressBooks() {
        String url = getBaseUrl() + "/addressBooks";
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void testCreateAddressBook() {
        String url = getBaseUrl() + "/addressBooks";

        AddressBook addressBook = new AddressBook("TestBook");
        ResponseEntity<AddressBook> response = restTemplate.postForEntity(url, addressBook, AddressBook.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo("TestBook");
    }

    @Test
    public void testGetAddressBookByName() {
        // First create an address book
        String createUrl = getBaseUrl() + "/addressBooks";
        AddressBook addressBook = new AddressBook("MyAddressBook");
        restTemplate.postForEntity(createUrl, addressBook, AddressBook.class);

        // Then retrieve it
        String getUrl = getBaseUrl() + "/addressBooks/MyAddressBook";
        ResponseEntity<AddressBook> response = restTemplate.getForEntity(getUrl, AddressBook.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo("MyAddressBook");
    }

    @Test
    public void testAddBuddyToAddressBook() {
        // Create an address book
        String createUrl = getBaseUrl() + "/addressBooks";
        AddressBook addressBook = new AddressBook("BuddyTestBook");
        restTemplate.postForEntity(createUrl, addressBook, AddressBook.class);

        // Add a buddy to it
        String addBuddyUrl = getBaseUrl() + "/addressBooks/BuddyTestBook/buddies";
        BuddyInfo buddy = new BuddyInfo("John Doe", "613-555-1234");
        ResponseEntity<AddressBook> response = restTemplate.postForEntity(addBuddyUrl, buddy, AddressBook.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getBuddies()).hasSize(1);
        assertThat(response.getBody().getBuddies().get(0).getName()).isEqualTo("John Doe");
    }

    @Test
    public void testDeleteBuddyFromAddressBook() {
        // Create an address book
        String createUrl = getBaseUrl() + "/addressBooks";
        AddressBook addressBook = new AddressBook("DeleteTestBook");
        restTemplate.postForEntity(createUrl, addressBook, AddressBook.class);

        // Add a buddy
        String addBuddyUrl = getBaseUrl() + "/addressBooks/DeleteTestBook/buddies";
        BuddyInfo buddy = new BuddyInfo("Jane Smith", "613-555-5678");
        restTemplate.postForEntity(addBuddyUrl, buddy, AddressBook.class);

        // Delete the buddy
        String deleteBuddyUrl = getBaseUrl() + "/addressBooks/DeleteTestBook/buddies/613-555-5678";
        restTemplate.delete(deleteBuddyUrl);

        // Verify buddy is deleted
        String getUrl = getBaseUrl() + "/addressBooks/DeleteTestBook";
        ResponseEntity<AddressBook> response = restTemplate.getForEntity(getUrl, AddressBook.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getBuddies()).isEmpty();
    }

    @Test
    public void testDeleteAddressBook() {
        // Create an address book
        String createUrl = getBaseUrl() + "/addressBooks";
        AddressBook addressBook = new AddressBook("ToDeleteBook");
        restTemplate.postForEntity(createUrl, addressBook, AddressBook.class);

        // Delete it
        String deleteUrl = getBaseUrl() + "/addressBooks/ToDeleteBook";
        restTemplate.delete(deleteUrl);

        // Verify it's deleted
        ResponseEntity<AddressBook> response = restTemplate.getForEntity(deleteUrl, AddressBook.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void testViewAddressBooksPage() {
        String url = getBaseUrl() + "/view/addressBooks";
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void testAddBuddyWithAddress() {
        // Create an address book
        String createUrl = getBaseUrl() + "/addressBooks";
        AddressBook addressBook = new AddressBook("AddressTestBook");
        restTemplate.postForEntity(createUrl, addressBook, AddressBook.class);

        // Add a buddy with an address
        String addBuddyUrl = getBaseUrl() + "/addressBooks/AddressTestBook/buddies";
        BuddyInfo buddy = new BuddyInfo("Alice Johnson", "613-555-9999", "123 Main St, Ottawa");
        ResponseEntity<AddressBook> response = restTemplate.postForEntity(addBuddyUrl, buddy, AddressBook.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getBuddies()).hasSize(1);
        assertThat(response.getBody().getBuddies().get(0).getName()).isEqualTo("Alice Johnson");
        assertThat(response.getBody().getBuddies().get(0).getAddress()).isEqualTo("123 Main St, Ottawa");
    }
}