package steps.alami;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import pageobjects.alami.transactionPO;
import utilities.ThreadManager;

public class transactionStep {
        private WebDriver driver = ThreadManager.getDriver();
        private transactionPO transaction = new transactionPO(driver);

        @When("user search {string} and click product best seller")
        public void user_search_and_click(String komputer) throws InterruptedException {
            transaction.clickFieldSearchAndClickSearchButton(komputer);
        }
        @And("user choose one product")
        public void user_choose_one_product() throws InterruptedException {
        transaction.userChooseOneProduct();
    }
        @And("user fill with quantity Three")
        public void user_fill_quantity_Three() throws InterruptedException {
            for (int i = 1; i < 3; i++) {
                transaction.userFillQuantityThree();
            }
        }
        @And("user add to card")
        public void user_add_to_card() throws InterruptedException {
        transaction.userAddToCard();
        }
        @And("user select change kurir")
        public void user_select_change_kurir() throws InterruptedException {
            transaction.userSelectChangeKurir();
        }

        @And("user click on batal button")
        public void user_click_on_batal_button() throws InterruptedException {
        transaction.userClickOnBatalButton();
        }

        @And("user click on hapus button")
        public void user_click_on_hapus_button() throws InterruptedException {
        transaction.userClickOnHapusButton();
        }

        @And("user click on ok button")
        public void user_click_on_ok_button() throws InterruptedException {
        transaction.userClickOnOkButton();
        }

        @Then("user see message {string}")
        public void user_see_message(String cart) {
        Assert.assertEquals(transaction.userSeeMessage(cart), "Tidak ada produk di Shopping Cart.");
    }
}

