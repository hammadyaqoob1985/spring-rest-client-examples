package guru.springframework.springrestclientexamples;

import com.fasterxml.jackson.databind.JsonNode;
import org.json.JSONObject;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class RestTemplateExamples {

    public static final String API_ROOT = "https://api.predic8.de:443/shop/";

    @Test
    public void getCategories() {

        String apiUrl = API_ROOT + "categories/";
        RestTemplate restTemplate = new RestTemplate();

        JsonNode jsonNode = restTemplate.getForObject(apiUrl, JsonNode.class);
        System.out.println(jsonNode.toString());
    }

    @Test
    public void createCustomers() {

        String apiUrl = API_ROOT + "customers/";
        RestTemplate restTemplate = new RestTemplate();

        Map<String, String> postMap = new HashMap<>();
        postMap.put("firstname", "Joe");
        postMap.put("lastname", "Bloggs");

        JsonNode jsonNode = restTemplate.postForObject(apiUrl, postMap, JsonNode.class);
        System.out.println(jsonNode.toString());

    }

    @Test
    public void updateCustomers() {

        String apiUrl = API_ROOT + "customers/";
        RestTemplate restTemplate = new RestTemplate();

        Map<String, String> postMap = new HashMap<>();
        postMap.put("firstname", "Joe");
        postMap.put("lastname", "Bloggs");

        JsonNode jsonNode = restTemplate.postForObject(apiUrl, postMap, JsonNode.class);
        System.out.println(jsonNode.toString());

        String customerUrl = jsonNode.get("customer_url").toString();

        String id = customerUrl.split("/")[3];
        id = id.replace("\"","");

        postMap.put("firstname", "Joe 1");
        postMap.put("lastname", "Bloggs 1");

        restTemplate.put(apiUrl + id, postMap);

        JsonNode jsonNodeUpdated = restTemplate.getForObject(apiUrl + id, JsonNode.class);
        System.out.println(jsonNodeUpdated.toString());

    }

    @Test
    public void updateCustomerUsingPath() {

        String apiUrl = API_ROOT + "customers/";

        //added in to work with patch update
        HttpComponentsClientHttpRequestFactory httpComponentsClientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        RestTemplate restTemplate = new RestTemplate(httpComponentsClientHttpRequestFactory);

        Map<String, String> postMap = new HashMap<>();
        postMap.put("firstname", "Sam");
        postMap.put("lastname", "Axe");

        JsonNode jsonNode = restTemplate.postForObject(apiUrl, postMap, JsonNode.class);
        System.out.println(jsonNode.toString());

        String customerUrl = jsonNode.get("customer_url").toString();

        String id = customerUrl.split("/")[3];
        id = id.replace("\"","");

        postMap.put("firstname", "Sam 1");
        postMap.put("lastname", "Axe 1");

        HttpHeaders httpHeaders =  new HttpHeaders();
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> entity =  new HttpEntity(postMap,httpHeaders);
        JsonNode jsonNodeUpdated = restTemplate.patchForObject(apiUrl + id, entity, JsonNode.class);

        System.out.println(jsonNodeUpdated.toString());
    }

    @Test(expected = HttpClientErrorException.class)
    public void deleteACustomer() {
        String apiUrl = API_ROOT + "customers/";

       RestTemplate restTemplate = new RestTemplate();

        Map<String, String> postMap = new HashMap<>();
        postMap.put("firstname", "Sam");
        postMap.put("lastname", "Axe");

        JsonNode jsonNode = restTemplate.postForObject(apiUrl, postMap, JsonNode.class);
        System.out.println(jsonNode.toString());

        String customerUrl = jsonNode.get("customer_url").toString();

        String id = customerUrl.split("/")[3];
        id = id.replace("\"","");

        restTemplate.delete(apiUrl + id);

        JsonNode jsonNodeUpdated = restTemplate.getForObject(apiUrl + id, JsonNode.class);
        System.out.println(jsonNodeUpdated.toString());
    }
}
