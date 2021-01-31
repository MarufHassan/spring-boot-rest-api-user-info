package com.marufhassan.userinfo;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.marufhassan.userinfo.models.Address;
import com.marufhassan.userinfo.models.Child;
import com.marufhassan.userinfo.models.Parent;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

@RunWith(JUnit4.class)
@SpringBootTest(classes = UserInfoApplication.class,
				webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserInfoApplicationTests {

	@Autowired
	private TestRestTemplate restTemplate;

	private static String getRootUrl() {
		return "http://localhost:8080/api/v1/users";
	}

	private static List<Address> addresses;
	private static List<Parent> parents;
	private static List<Child> childs;

	/**
     * Add some dummy value in the database
     */
	@BeforeAll
    public static void init() {
		addresses = Arrays.asList(new Address[] {
			new Address(1, "Access Road", "Agrabad", "Chittagong", "7000"),
			new Address(2, "PC Road", "Halishahar", "Chittagong", "8000"),
			new Address(3, "Pragati Soroni", "Badda", "Dhaka", "1200")
		});

		parents = Arrays.asList(new Parent[] {
			new Parent(1, "Monir", "Ahmed", addresses.get(0)),
			new Parent(2, "Abul", "Kashem", addresses.get(1)),
			new Parent(3, "Mosarraf", "Hossain", addresses.get(2)) 
		});

		childs = Arrays.asList(new Child[] {
			new Child(1, "Hridoy", "Ahmed", parents.get(0)),
			new Child(2, "Hridoy", "Ahmed", parents.get(1)),
			new Child(3, "Shoeb", "Mahmud", parents.get(2))
		});

		TestRestTemplate tRestTemplate = new TestRestTemplate();

        tRestTemplate.postForEntity(
			getRootUrl() + "/parents", parents.get(0), Parent.class);
			tRestTemplate.postForEntity(
			getRootUrl() + "/parents", parents.get(1), Parent.class);
		tRestTemplate.postForEntity(
			getRootUrl() + "/parents", parents.get(2), Parent.class);

		tRestTemplate.postForEntity(
			getRootUrl() + "/childs", childs.get(0), Child.class);
		tRestTemplate.postForEntity(
			getRootUrl() + "/childs", childs.get(1), Child.class);
		tRestTemplate.postForEntity(
			getRootUrl() + "/childs", childs.get(2), Child.class);
    }

	/**
     * Here we test that we can get all the parents in the database
     * using the GET method
     */
    @Test
    public void testGetAllParents() {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/parents",
            HttpMethod.GET, entity, String.class);

		Assert.assertEquals(200, response.getStatusCodeValue());
		Assert.assertNotNull(response.getBody());
	}
	
	/**
     * Here we test that we can get all the children in the database
     * using the GET method
     */
    @Test
    public void testGetAllChilds() {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/childs",
            HttpMethod.GET, entity, String.class);

		Assert.assertEquals(200, response.getStatusCodeValue());
		Assert.assertNotNull(response.getBody());
	}
	
	/**
     * Here we test that we can get a parent's information using the GET method
     */
    @Test
    public void testGetParentById() {
        int id = 1;
		Parent parent = restTemplate.getForObject(getRootUrl() + "/parents/" + id, Parent.class);
		
		Assert.assertNotNull(parent);
		Assert.assertEquals(parents.get(0), parent);
	}

	/**
     * Here we test that we can get a childs's information using the GET method
     */
    @Test
    public void testGetChildtById() {
        int id = 1;
		Child child = restTemplate.getForObject(getRootUrl() + "/childs/" + id, Child.class);
		
		Assert.assertNotNull(child);
		Assert.assertEquals(childs.get(0), child);
	}

	/**
     * Here we test that we can create a parent using the POST method
     */
	@Test
    public void testCreateParent() {
		Parent parent = new Parent(4, "Fokhrul", "Islam", new Address(4, "Port Colony", "Bandar", "Chittagong", "7000"));
		
        ResponseEntity<Parent> postResponse = restTemplate.postForEntity(
			getRootUrl() + "/parents", parent, Parent.class);
		Assert.assertNotNull(postResponse);

		Parent newParent = postResponse.getBody();
		Assert.assertTrue(parent.equals(newParent));
	}
	
	/**
     * Here we test that we can create a child using the POST method
     */
	@Test
    public void testCreateChild() {
		Parent parent = new Parent(4, "Fokhrul", "Islam", new Address(4, "Port Colony", "Bandar", "Chittagong", "7000"));
		Child child = new Child(4, "Tehjibul", "Islam", parent);

        ResponseEntity<Child> postResponse = restTemplate.postForEntity(
			getRootUrl() + "/childs", child, Child.class);
		Assert.assertNotNull(postResponse);
		
		Child newChild = postResponse.getBody();
		Assert.assertTrue(child.equals(newChild));
	}

	/**
     * Here we test that we can update a parent's information using the PUT method
     */
    @Test
    public void testUpdateParent() {
        int id = 4;
        Parent parent = restTemplate.getForObject(getRootUrl() + "/parents/" + id, Parent.class);
		parent.setFirstname("Siddique");
		parent.setLastname("Hossain");

        restTemplate.put(getRootUrl() + "/parents/" + id, parent);

        Parent updatedParent = restTemplate.getForObject(getRootUrl() + "/parents/" + id, Parent.class);
		Assert.assertNotNull(updatedParent);
		Assert.assertEquals(parent, updatedParent);
	}

	/**
     * Here we test that we can update a child's information using the PUT method
     */
	@Test
    public void testUpdateChild() {
        int id = 3;
		Child child = restTemplate.getForObject(getRootUrl() + "/childs/" + id, Child.class);
		child.setId(id);
		child.setFirstname("Rifat");
		child.setLastname("Islam");

        restTemplate.put(getRootUrl() + "/childs/" + child.getId(), child);

        Child updatedChild = restTemplate.getForObject(getRootUrl() + "/childs/" + child.getId(), Child.class);
		Assert.assertNotNull(updatedChild);
		Assert.assertEquals(child, updatedChild);
	}

	
	/**
     * Here we test that we can delete a parent's information using the DELETE method
     */
    @Test
    public void testDeleteParent() {
		Parent parent = new Parent(0, "Saddam", "Hossain", new Address(0, "GEC", "Double Mooring", "Chittagong", "7000"));
		
        ResponseEntity<Parent> postResponse = restTemplate.postForEntity(
			getRootUrl() + "/parents", parent, Parent.class);

		int id = postResponse.getBody().getId();

		Map <String, String> params = new HashMap <String, String>();
		params.put("id", String.valueOf(id));
		
		restTemplate.delete(getRootUrl() + "/parents/{id}", params);
		
		HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/parents/" + id,
			HttpMethod.GET, entity, String.class);
		Assert.assertEquals(404, response.getStatusCode().value());
	}

	/**
     * Here we test that we can delete a child's information using the DELETE method
     */
    @Test
    public void testDeleteChild() {
		Parent parent = new Parent(0, "Anam", "Ahmed", new Address(0, "Noyabazar", "Halishahar", "Chittagong", "2367"));
		Child child = new Child(0, "Moinul", "Hassan", parent);

		ResponseEntity<Child> postResponse = restTemplate.postForEntity(
			getRootUrl() + "/childs", child, Child.class);
		int id = postResponse.getBody().getId();

		Map <String, String> params = new HashMap <String, String>();
		params.put("id", String.valueOf(id));
		
		restTemplate.delete(getRootUrl() + "/childs/{id}", params);
		
		HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/childs/" + id,
			HttpMethod.GET, entity, String.class);
		Assert.assertEquals(404, response.getStatusCode().value());
	}
}
