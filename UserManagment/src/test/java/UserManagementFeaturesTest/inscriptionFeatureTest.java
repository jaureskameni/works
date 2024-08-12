package UserManagementFeaturesTest;

import com.example.usermanagment.service.ManagementService;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;


public class inscriptionFeatureTest {

    private ManagementService managementService;

    @Given("I am on the registration page")
    public void iAmOnTheRegistrationPage() {

    }

    @When("I fill out the registration form with (.*)and (.*) and(.*)")
    public void iFillOutTheRegistrationFormWithUserNameAndEmailAndPassWord() {
    }

    @And("I submit my registration form")
    public void iSubmitMyRegistrationForm() {

    }

    @Then("I receive the confirmation")
    public void iReceiveTheConfirmation() {
    }

    //-----------------------------------------LOGIN USER SUCCESSFULLY ------------------------------------------------------//

    @Given("I am on the login page")
    public void iAmOnTheLoginPage() {
    }

    @When("I enter my information with (.*) and (.*)")
    public void iEnterMyInformationWithEmailAndPassWord() {
    }


    @And("I submit my login form")
    public void iSubmitMyLoginForm() {

    }

    @Then("I am redirected to my home page")
    public void iAmRedirectedToMyHomePage() {
    }

    //-----------------------------------------LOGIN USER ERROR ------------------------------------------------------//

    @Then("I receive the message error {string}")
    public void iReceiveTheMessageError() {
    }


}
