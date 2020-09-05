package test.cmc.stepdefs;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;


import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Map;

public class CMCBackEndStepDefs {

    private ValidatableResponse json;
    private Response response;

    private static String apiAuthHeaderKey = "X-CMC_PRO_API_KEY"; //custom header defined in API Specs
    private static String apiAuthHeaderValue = "cdd95adb-ab9c-47f6-94a6-0132a2befada"; //belongs to umair.minhas@ebryx.com
    private static  String baseURL = "https://pro-api.coinmarketcap.com/v1"; // this contains live data as sandbox-api URI contains limited data
    ArrayList<Integer> ids;

    @Given("^get ID of BTC, USDT, ETH using /cryptocurrency/map call$")
    public void get_ID_of_BTC_USDT_ETH_using_cryptocurrency_map_call() {
        // Write code here that turns the phrase above into concrete actions
        String url = baseURL + "/cryptocurrency/map";
        response = given().header(apiAuthHeaderKey, apiAuthHeaderValue)
                .header("Content-Type", "application/json")
                .get(url);
    }

    @When("^the IDs are received successfully$")
    public void the_IDs_are_received_successfully() {
        // Write code here that turns the phrase above into concrete actions
        json = response.then().statusCode(200);

        JsonPath
                extractor = response.jsonPath();
        ArrayList<String> transId = extractor.get("data.symbol");
        ids = new ArrayList<Integer>();
        int size = transId.size();

        for (int i = 0; i < size; i++) {
            if (transId.get(i).equals("USDT")) {
                ids.add(((ArrayList<java.lang.Integer>) (extractor.get("data.id"))).get(i));
            }
            if (transId.get(i).equals("BTC")) {
                ids.add(((ArrayList<java.lang.Integer>) (extractor.get("data.id"))).get(i));
            }
            if (transId.get(i).equals("ETH")) {
                ids.add(((ArrayList<java.lang.Integer>) (extractor.get("data.id"))).get(i));
            }
        }
    }

    @Then("^convert them to Bolivian Boliviano using the /tools/price-conversion call$")
    public void convert_them_to_Bolivian_Boliviano_using_the_tools_price_conversion_call() {
        // Write code here that turns the phrase above into concrete actions
        for(int i=0; i< ids.size();i++) {
            String urlGet = baseURL + "/tools/price-conversion"; //+"?id="+ids.get(i)+"&amount=50&convert=BOB";
            given().header(apiAuthHeaderKey, apiAuthHeaderValue)
                    .header("Content-Type", "application/json")
                    .param("id", ids.get(i))
                    .param("amount", 50)
                    .param("convert", "BOB")
                    .get(urlGet)
                    .then().assertThat().body("data.quote.BOB.price", notNullValue());
        }
    }

    @Given("^get ID (\\d+) tech doc web from cryptocurrency/info call$")
    public void get_ID_tech_doc_web_from_cryptocurrency_info_call(int ethID) {
        // Write code here that turns the phrase above into concrete actions
        String url = baseURL + "/cryptocurrency/info";
        response = given()
                .header(apiAuthHeaderKey, apiAuthHeaderValue)
                .header("Content-Type", "application/json")
                .param("id", ethID)
                .get(url);
    }

    @When("^response is received$")
    public void response_is_received() {
        // Write code here that turns the phrase above into concrete actions
        json = response.then().statusCode(200);

    }

    @Then("^validate following from response$")
    public void validate_following_from_response(Map<String, String> responseFields) {
        // Write code here that turns the phrase above into concrete actions
        // For automatic transformation, change DataTable to one of
        // List<YourType>, List<List<E>>, List<Map<K,V>> or Map<K,V>.
        // E,K,V must be a scalar (String, Integer, Date, enum etc)
        for (Map.Entry<String,String> field : responseFields.entrySet()) {
            if (!field.getValue().equals("null"))  {
                if (StringUtils.isNumeric(field.getValue())) {
                    json.body(field.getKey(),equalTo(Integer.parseInt(field.getValue())));
                }
                else
                    json.body(field.getKey(),equalTo(field.getValue()));
            }
            else {
                json.body(field.getKey(), not(notNullValue()));
            }
        };
    }

    @Given("^get the currencies info of top ten ID (.+)$")
    public void get_the_currencies_info_of_top_ten_id(String ccid) {
        String url = baseURL + "/cryptocurrency/info";
        response = given()
                .header(apiAuthHeaderKey, apiAuthHeaderValue)
                .header("Content-Type", "application/json")
                .param("id", ccid)
                .get(url);
        response.then()
                .statusCode(200);
    }

    @Then("^check currencies with (.+) has mineable tag$")
    public void check_currencies_with_has_mineable_tag(String ccid) {
        response.then().body("data."+ccid+".tags", hasItem("mineable"));
    }

    @And("^verify IDs (.+) vs Names (.+)$")
    public void verify_ids_vs_names(String ccid, String ccname)  {
        response.then().body("data."+ccid+".name", is(ccname));
    }

}
