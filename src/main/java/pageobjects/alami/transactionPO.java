package pageobjects.alami;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import utilities.Constants;
import utilities.SeleniumHelpers;

public class transactionPO {
    WebDriver driver;
    SeleniumHelpers selenium;

    public transactionPO(WebDriver driver) {
        this.driver = driver;
        selenium = new SeleniumHelpers(driver);

        // This initElements method will create all WebElements
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, Constants.PAGEFACTORY_WAIT_DURATION), this);
    }


    @FindBy(xpath = "//input[@id='AKCKwd']")
    private WebElement clickSearch;

    @FindBy(xpath = "//div[@class='header-big']//button[@class='btn-search']")
    private WebElement clickSearchButton;

    @FindBy(xpath = "//a[normalize-space()='Produk terlaris']")
    private WebElement clickProductTerlaris;

    @FindBy(xpath = "(//a[@class='img'])[1]")
    private WebElement clickOneProduct;

    @FindBy(xpath = "//div[@class='optionSelect']//button[@class='ico_btnType incre'][normalize-space()='Increase']")
    private WebElement incraseButton;

    @FindBy(xpath = "//a[normalize-space()='Tambahkan ke Cart']")
    private WebElement userAddToCard;

    @FindBy(xpath = "//a[@href='http://www.elevenia.co.id/cart/CartAction/getCartList.do']")
    private WebElement yesPopUpButton;

    @FindBy(xpath = "//a[normalize-space()='Ubah Kurir']")
    private WebElement changeKurir;

    @FindBy(xpath = "//strong[normalize-space()='Tidak ada produk di Shopping Cart.']")
    private WebElement seeMessage;

    /**
     * click field Search And Click search button
     * @throws InterruptedException
     */
    public void clickFieldSearchAndClickSearchButton (String komputer) throws InterruptedException {
        selenium.hardWait(8);
        selenium.clickOn(clickSearch);
        selenium.enterText(clickSearch,komputer, false);
        selenium.clickOn(clickSearchButton);
        selenium.hardWait(8);
        selenium.clickOn(clickProductTerlaris);
    }

    /**
     * click one product
     * @throws InterruptedException
     */
    public void userChooseOneProduct () throws InterruptedException {
        selenium.pageScrollInView(clickOneProduct);
        selenium.hardWait(10);
        selenium.clickOn(clickOneProduct);
    }
    /**
     * click incrase button
     * @throws InterruptedException
     */
    public void userFillQuantityThree () throws InterruptedException {
        selenium.clickOn(incraseButton);
    }
    /**
     * click add to card
     * @throws InterruptedException
     */
    public void userAddToCard () throws InterruptedException {
        selenium.clickOn(userAddToCard);
        selenium.clickOn(yesPopUpButton);
    }
    /**
     * click change kurir
     * @throws InterruptedException
     */
    public void userSelectChangeKurir () throws InterruptedException {
        selenium.clickOn(changeKurir);

    }
    /**
     * click cancel button
     * @throws InterruptedException
     */
    public void userClickOnBatalButton () throws InterruptedException {
        driver.switchTo().frame(driver.findElement(By.xpath("//iframe[@id='ifrLayer']")));
        WebElement element = driver.findElement(By.xpath("//a[normalize-space()='Batal']"));
        selenium.clickOn(element);
        driver.switchTo().defaultContent();
    }

    /**
     * click delete button
     * @throws InterruptedException
     */
    public void userClickOnHapusButton () throws InterruptedException {
        WebElement element = driver.findElement(By.xpath("//td[@class='btn cartfont']/a[.='Hapus']"));
        selenium.clickOn(element);
    }

    /**
     * click ok button
     * @throws InterruptedException
     */
    public void userClickOnOkButton () throws InterruptedException {
        WebElement element = driver.findElement(By.xpath("//a[@id='chkDelPopY']"));
        selenium.hardWait(5);
        selenium.clickOn(element);
    }

    /**
     * see message
     */
    public String userSeeMessage(String cart){
        return selenium.getText(seeMessage);
    }
}