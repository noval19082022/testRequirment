package utilities;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.io.Files;

public class SeleniumHelpers
{
	WebDriver driver;
	JavaHelpers helper;
	Actions actions;

	String os = System.getProperty("os.name").toLowerCase();

	public SeleniumHelpers(WebDriver driver)
	{
		this.driver = driver;
		helper = new JavaHelpers();
		actions = new Actions(driver);
	}

    // Take screenshot
		/**
	 * Take screenshot of the web page
	 *
	 * @param fileName screenshot file name
	 * @throws IOException
	 */
	public void takeScreenshot(String fileName) throws IOException
	{
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		Files.copy(scrFile,
				new File(Constants.SCREENSHOT_LOCATION + fileName + helper.getTimeStamp("_yyyyMMdd_HHmmss") + ".png"));
	}


	//Elements
		/**
	 * Enter text to input field
	 * @param e WebElement object
	 * @param text input text
	 * @param clear set true if want to clear field else set false
	 */
	public void enterText(WebElement e, String text, boolean clear)
	{
		e = waitTillElementIsClickable(e);
		if(clear)
		{
			e.clear();
			if (System.getProperty("os.name").contains("Mac")) {
				e.sendKeys(Keys.COMMAND + "A");
			}
			else {
				e.sendKeys(Keys.CONTROL + "A");
			}
			e.sendKeys(Keys.DELETE);
		}
		e.sendKeys(text);
	}

	/**
	 * Enter text to input field
     * @param by By object
     * @param text input text
     * @param clear set true if want to clear field else set false
     */
	public void enterText(By by, String text, boolean clear)
	{
		WebElement e = waitTillElementIsClickable(by);
		if(clear)
		{
			e.clear();
		}
		e.sendKeys(text);
	}

	/**
	 * Enter text to input field
	 * @param e WebElement object
	 * @param text input text
	 * @param clear set true if want to clear field else set false
	 * @throws InterruptedException
	 */
	public void enterTextCharacterByCharacter(WebElement e, String text, boolean clear) throws InterruptedException
	{
		e = waitTillElementIsClickable(e);
		if(clear)
		{
			e.clear();
		}

		for (int i = 0; i < text.length(); i++)
		{
	        char c = text.charAt(i);
	        String s = new StringBuilder().append(c).toString();
	        e.sendKeys(s);
	        Thread.sleep(500); // Waiting for 0.5 second
	    }
	}

	/**
	 * Double click and enter text to input field
	 * @param e WebElement object
	 * @param text input text
	 * @throws InterruptedException
	 */
	public void fillFieldAfterDoubleClick( WebElement e, String text) {
		Actions actions = new Actions(driver);
		actions.moveToElement(e);
		actions.doubleClick();
		actions.sendKeys(text);
		actions.build().perform();
	}

	/**
	 * Get Text from field
	 * @param e WebElement object
	 * @return text from field
	 */
	public String getText(WebElement e)
	{
		return waitTillElementIsVisible(e).getText().trim();
	}

	public String getText(By object)
	{
		return driver.findElement(object).getText();
	}

	/**
	 * Get Text from input
	 * @param e WebElement object
	 * @return text from input
	 */
	public String getInputText(WebElement e)
	{
		return waitTillElementIsVisible(e).getAttribute("value").trim();
	}

	/**
	 * Verify element clickable
	 * @param el WebElement object
	 * @return boolean
	 */
	public boolean isClickable(WebElement el, int waitDurationInSeconds) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, waitDurationInSeconds);
			wait.until(ExpectedConditions.elementToBeClickable(el));
			return true;
		}
		catch (Exception e) {
			return false;
		}
	}

	/**
	 * Click on Element
	 * @param e WebElement object
	 * @throws InterruptedException
	 */
	public void clickOn(WebElement e) throws InterruptedException
	{
		waitTillElementIsClickable(e).click();
		waitForJavascriptToLoad();
	}

	/**
	 * Click on Element
	 * @param e WebElement object
	 * @throws InterruptedException
	 */
	public void click(WebElement e) throws InterruptedException
	{
		e.click();
		waitForJavascriptToLoad();
	}

	/**
	 * Click on Element
	 * @param by By object
	 * @throws InterruptedException
	 */
	public void clickOn(By by) throws InterruptedException
	{
		waitTillElementIsClickable(by).click();
		waitForJavascriptToLoad();
	}

	/**
	 * To determine whether WebElement has given Attribute or not
	 * @param e WebElement object
	 * @param attributeName attribute name e.g. style
	 * @return boolean
	 */
	public boolean isElementAtrributePresent(WebElement e, String attributeName)
	{
		return e.getAttribute(attributeName) != null;
	}

	/**
	 * To get Element attribute value
	 * @param by By object
	 * @param attributeName attribute name e.g. style
	 * @return attribute value
	 */
	public String getElementAttributeValue(By by, String attributeName)
	{
		if(isElementAtrributePresent(driver.findElement(by),attributeName))
		{
			return driver.findElement(by).getAttribute(attributeName);
		}
		return "Attribute" + attributeName +" not found";
	}

	/**
	 * To get Element attribute value
	 * @param e WebElement object
	 * @param attributeName attribute name e.g. style
	 * @return attribute value
	 */
	public String getElementAttributeValue(WebElement e, String attributeName)
	{
		if(isElementAtrributePresent(e,attributeName))
		{
			return e.getAttribute(attributeName);
		}
		return "Attribute" + attributeName +" not found";
	}

	/**
	 * method verify whether element is present on screen
	 *
	 * @param targetElement element to be present
	 * @return true if element is present else throws exception
	 * @throws InterruptedException Thrown when a thread is waiting, sleeping, or
	 *                              otherwise occupied, and the thread is
	 *                              interrupted, either before or during the
	 *                              activity.
	 */
	public Boolean isElementPresent(By targetElement)
	{
		return driver.findElements(targetElement).size() > 0;
	}


	/**
	 * Verify element is displayed
	 * @param el WebElement object
	 * @return boolean
	 */
	public Boolean isElementDisplayed(WebElement el)
	{
		try {
			el.isDisplayed();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Verify element is not displayed
	 * @param el WebElement object
	 * @return boolean
	 */
	public Boolean isElementNotDisplayed(WebElement el) {
		{
			try {
				return el.isDisplayed();
			} catch (Exception e) {
				return false;
			}
		}
	}

	//Waits
	/**
	 * To wait until element is clickable
	 * @param e WebElement object
	 * @return WebElement object
	 */
	public WebElement waitTillElementIsClickable(WebElement e)
	{
		 WebDriverWait wait = new WebDriverWait(driver, Constants.WEBDRIVER_WAIT_DURATION);
		 wait.until(ExpectedConditions.elementToBeClickable(e));
		 return e;
	}

	/**
	 * To wait until element is clickable
	 * @param by By object
	 * @return WebElement object
	 */
	public WebElement waitTillElementIsClickable(By by)
	{
		 WebDriverWait wait = new WebDriverWait(driver, Constants.WEBDRIVER_WAIT_DURATION);
		 return wait.until(ExpectedConditions.elementToBeClickable(by));
	}

	/**
	 * o wait until element is visible
	 * @param e WebElement object
	 * @return WebElement object
	 */
	public WebElement waitTillElementIsVisible(WebElement e)
	{
		WebDriverWait wait = new WebDriverWait(driver, Constants.WEBDRIVER_WAIT_DURATION);
		wait.until(ExpectedConditions.visibilityOf(e));
		return e;
	}

	/**
	 * o wait until element is not visible
	 * @param e WebElement object
	 * @param waitDurationInSeconds wait duration in seconds
	 * @return WebElement object
	 */
	public void waitTillElementIsNotPresent(WebElement e, int waitDurationInSeconds)
	{
		WebDriverWait wait = new WebDriverWait(driver, waitDurationInSeconds);
		ExpectedCondition<Boolean> elementIsDisplayed = arg0 -> {
			try {
				e.isDisplayed();
				return false;
			}
			catch (NoSuchElementException | StaleElementReferenceException e1) {
				return true;
			}
		};
		wait.until(elementIsDisplayed);
	}

	/**
	 * o wait until element is visible
	 * @param e WebElement object
	 * @param waitDurationInSeconds wait duration in seconds
	 * @return WebElement object
	 */
	public WebElement waitTillElementIsVisible(WebElement e, int waitDurationInSeconds)
	{
		 WebDriverWait wait = new WebDriverWait(driver, waitDurationInSeconds);
		 wait.until(ExpectedConditions.visibilityOf(e));
		 return e;
	}

	/**
	 * o wait until element is visible
	 * @param by By object
	 * @param waitDurationInSeconds wait duration in seconds
	 * @return WebElement object
	 */
	public void waitTillElementIsVisible(By by, int waitDurationInSeconds)
	{
		 WebDriverWait wait = new WebDriverWait(driver, waitDurationInSeconds);
		 wait.until(ExpectedConditions.visibilityOfElementLocated(by));
	}

	/**
	 * Wait until element is invisible
	 * @param e WebElement object
	 */
	public void waitTillElementIsNotVisible(WebElement e)
	{
		 WebDriverWait wait = new WebDriverWait(driver, Constants.WEBDRIVER_WAIT_DURATION);
		 wait.until(ExpectedConditions.invisibilityOf(e));
	}

	/**
	 * Wait until element is invisible
	 * @param by By object
	 */
	public void waitTillElementIsNotVisible(By by)
	{
		 WebDriverWait wait = new WebDriverWait(driver, Constants.WEBDRIVER_WAIT_DURATION);
		 wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
	}

	/**
	 * Wait until element is invisible
	 * @param e WebElement object
	 * @param waitDurationInSeconds wait duration in seconds
	 */
	public void waitTillElementIsNotVisible(WebElement e,int waitDurationInSeconds)
	{
		 WebDriverWait wait = new WebDriverWait(driver, waitDurationInSeconds);
		 wait.until(ExpectedConditions.invisibilityOf(e));
	}

	/**
	 * Wait until element is invisible
	 * @param waitDurationInSeconds wait duration in seconds
	 * @param by By object
	 */
	public void waitTillElementIsNotVisible(By by, int waitDurationInSeconds)
	{
		 WebDriverWait wait = new WebDriverWait(driver,  waitDurationInSeconds);
		 wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
	}

	/**
	 * Wait for specified duration and check if element is visible or not
	 * @param e WebElement object
	 * @param duration wait duration in seconds
	 * @return WebElement if visible or null if not visible
	 */
	public WebElement waitInCaseElementVisible(WebElement e, int duration)
	{
		 WebDriverWait wait = new WebDriverWait(driver, duration);
		 try
		 {
			 return wait.until(ExpectedConditions.visibilityOf(e));
		 }
		 catch (Exception ex)
		 {
			 return null;
		 }
	}

	/**
	 * Wait for specific duration and check if element present or not
	 * @param e WebElement object
	 * @param duration wait duration in seconds
	 * @return WebElement if presesnt null if not visible
	 */
	public WebElement waitInCaseElementPresent(By e, int duration) {
		WebDriverWait wait = new WebDriverWait(driver, duration);
		try{
			return wait.until(ExpectedConditions.presenceOfElementLocated(e));
		}
		catch (Exception ex) {
			return null;
		}
	}

	/**
	 * Wait for specified duration and check if element is visible or not
	 * @param by By object
	 * @param duration wait duration in seconds
	 * @return WebElement if visible or null if not visible
	 */
	public WebElement waitInCaseElementVisible(By by, int duration)
	{
		 WebDriverWait wait = new WebDriverWait(driver, duration);
		 try
		 {
			 return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		 }
		 catch (Exception ex)
		 {
			 return null;
		 }
	}

	/**
	 * Wait for specified duration and check if element is clickable or not
	 * @param e WebElement object
	 * @param duration wait duration in seconds
	 * @return WebElement if clickable or null if not visible
	 */
	public WebElement waitInCaseElementClickable(WebElement e, int duration)
	{
		 WebDriverWait wait = new WebDriverWait(driver, duration);
		 try
		 {
			 return wait.until(ExpectedConditions.elementToBeClickable(e));
		 }
		 catch (Exception ex)
		 {
			 return null;
		 }
	}

	/**
	 * Wait till Element count is less than provided number
	 * @param by By object
	 * @param count provide number
	 */
	public void waitTillElementsCountIsLessThan(By by, int count)
	{
		 WebDriverWait wait = new WebDriverWait(driver, Constants.WEBDRIVER_WAIT_DURATION);
		 wait.until(ExpectedConditions.numberOfElementsToBeLessThan(by, count));
	}

	/**
	 * Wait till Element count is more than provided number
	 * @param by By object
	 * @param count provide number
	 */
	public void waitTillElementsCountIsMoreThan(By by, int count)
	{
		WebDriverWait wait = new WebDriverWait(driver, Constants.WEBDRIVER_WAIT_DURATION);
		wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(by, count));
	}

	/**
	 * Wait till Element count is equal to provided number
	 * @param by By object
	 * @param count provide number
	 */
	public void waitTillElementsCountIsEqualTo(By by, int count)
	{
		 WebDriverWait wait = new WebDriverWait(driver, Constants.WEBDRIVER_WAIT_DURATION);
		 wait.until(ExpectedConditions.numberOfElementsToBe(by, count));
	}

	/**
	 * Wait till frame is available for switching
	 * @param e WebElement object
	 */
	public void waitTillframeToBeAvailableAndSwitchToIt(WebElement e)
	{
		WebDriverWait wait = new WebDriverWait(driver, Constants.WEBDRIVER_WAIT_DURATION);
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(e));
	}

	/**
	 * Wait till element not attached to DOM
	 * @param e WebElement object
	 */
	public void waitTillElementNotAttachedToDOM(WebElement e)
	{
		WebDriverWait wait = new WebDriverWait(driver, Constants.WEBDRIVER_WAIT_DURATION);
		wait.until(ExpectedConditions.stalenessOf(e));
	}

	/**
	 * Waiting before performing next action
	 * @param seconds provide duration e.g. 1,2 etc
	 * @throws InterruptedException
	 */
	public void hardWait(int seconds) throws InterruptedException
	{
		Thread.sleep(seconds * 1000);
	}

	/**
	 * Wait till all elements are located
	 * @param by By object
	 * @return List of WebElement
	 */
	public List<WebElement> waitTillAllElementsAreLocated(By by)
	{
		 WebDriverWait wait = new WebDriverWait(driver, Constants.WEBDRIVER_WAIT_DURATION);
		 return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(by));
	}

	/**
	 * Wait till element is refreshed
	 * @param e WebElement object
	 * @return WebElement object
	 */
	public WebElement waitTillElementIsRefreshed(WebElement e)
	{
		 WebDriverWait wait = new WebDriverWait(driver, Constants.WEBDRIVER_WAIT_DURATION);
		 return wait.until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(e)));
	}

	/** This function will wait for page to load (waiting for java script to finish loading) before moving further
     *
     * @paramWaitTime  Maximum time is the time out time. if the page loading completes before timeout, code will process
     * @throws InterruptedException
     */
    public  void waitForJavascriptToLoad() throws InterruptedException
    {
	    Thread.sleep(1000);
	    ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>()
	    {
	        public Boolean apply(WebDriver driver)
	        {
	          return ((JavascriptExecutor)driver).executeScript("return document.readyState").equals("complete");
	        }
	     };
	     Wait <WebDriver> wait = new WebDriverWait(driver, Constants.WEBDRIVER_WAIT_DURATION);
	     try
	     {
	    	 wait.until(expectation);
	     }
	     catch(Exception e)
	     {
	    	 e.printStackTrace();
	     }
	     catch(Error e)
	     {
	    	 e.printStackTrace();
	     }
    }


	//Navigation
	public void navigateToPage(String url)
	{
		driver.get(url);
	}

	public void refreshPage()
	{
		driver.navigate().refresh();
	}

	public String getURL()
	{
		return driver.getCurrentUrl();
	}


	//Alerts
    public void waitTillAlertPresent()
	{
		WebDriverWait wait = new WebDriverWait(driver, Constants.WEBDRIVER_WAIT_DURATION);
		wait.until(ExpectedConditions.alertIsPresent());
	}

    public void dismissAlert()
    {
    	driver.switchTo().alert().dismiss();
    }

    public void acceptAlert()
    {
    	driver.switchTo().alert().accept();
    }

	/**
	 * Switch to alert
	 */
	public void switchToAlert()
	{
		driver.switchTo().alert();
	}

	public String getTextFromAlert()
    {
    	return driver.switchTo().alert().getText().trim();
    }

	/**
	 * Check if alert present
	 * @return alert present true, otherwise false
	 */
	public boolean isAlertPresent() {
		WebDriverWait wait = new WebDriverWait(driver, Constants.WEBDRIVER_WAIT_DURATION);
		try {
			wait.until(ExpectedConditions.alertIsPresent());
			return true;
		}catch (Exception e) {
			return false;
		}
	}

	/**
	 * Check if alert present
	 * @param waitDuration input with desired wait time
	 * @return alert present true, otherwise false
	 */
	public boolean isAlertPresent(int waitDuration) {
		WebDriverWait wait = new WebDriverWait(driver, waitDuration);
		try {
			wait.until(ExpectedConditions.alertIsPresent());
			return true;
		}catch (Exception e) {
			return false;
		}
	}


	//Drop-down
	public void selectDropdownValueByText(WebElement e, String text) {
		new Select(e).selectByVisibleText(text);
	}

	public String getSelectedDropdownValue(WebElement e)
	{
		return new Select(e).getFirstSelectedOption().getText();
	}

	public String selectDropdownValueByIndex(WebElement e, int index) {
		new Select(e).selectByIndex(index);
		return new Select(e).getFirstSelectedOption().getText().trim();
	}

	public String selectDropdownValueByVisibleText(WebElement e, String text) {
		new Select(e).selectByVisibleText(text);
		return new Select(e).getFirstSelectedOption().getText().trim();
	}

	public List<String> getAllDropdownValues(WebElement e)
	{
		List<String> dropdownvalues = new ArrayList<String>();
		List<WebElement> list = new Select(e).getOptions();

		for(WebElement item :list)
		{
			dropdownvalues.add(item.getText().trim());
		}

		return dropdownvalues;
	}


	//Action events
	public void focusOnElement(WebElement e)
	{
		actions.moveToElement(e).click().build().perform();
	}

	public void doubleClickOnElement(WebElement e)
	{
		actions.doubleClick(e).build().perform();
	}

	public void doubleClickOnElementWithOffset(WebElement e,int x_off ,int y_off)
	{
		actions.moveToElement(e,x_off,y_off).doubleClick().build().perform();
	}

	public void singleClickOnElementWithOffset(WebElement e,int x_off ,int y_off)
	{
		actions.moveToElement(e,x_off,y_off).click().build().perform();
	}

	public void singleClickOnElement(WebElement e)
	{
		actions.moveToElement(e).click().build().perform();
	}

	public void dragAndDrop(WebElement drag,WebElement drop) throws InterruptedException
	{
		Actions actions = new Actions(driver);
		actions.clickAndHold(drag).build().perform();
		hardWait(3);
		actions.moveToElement(drop).build().perform();
		hardWait(3);
		actions.release(drop).build().perform();
		hardWait(3);
	}


	//Page scrolls
	public WebElement pageScrollInView(WebElement e)
	{
	    JavascriptExecutor jse = (JavascriptExecutor) driver;
	    jse.executeScript("arguments[0].scrollIntoView(true);",e);
	    return e;
	}

	public WebElement pageScrollInView(By by)
	{
		WebElement e = driver.findElement(by);
	    JavascriptExecutor jse = (JavascriptExecutor) driver;
	    jse.executeScript("arguments[0].scrollIntoView(true);",e);
	    return e;
	}

	/**
	 * Click on Element
	 * @param x scroll horixontal
	 * @param y By object   
	 */
	public void pageScrollUsingCoordinate(int x, int y) {
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		jse.executeScript("scroll(" + x + "," + y + ")");
	}

	public void windowFocus()
	{
	    JavascriptExecutor jse = (JavascriptExecutor) driver;
	    jse.executeScript("window.focus();");
	}


	//Java-script Helpers
	public void javascriptSetValue(WebElement e, String value) {
		String script = "arguments[0].value='" + value + "';";
		((JavascriptExecutor) driver).executeScript(script, e);
	}

	public void javascriptClickOn(WebElement e) {
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", e);
	}

	public void JSScrollAndClickOn(WebElement element) {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
		Actions act=new Actions(driver);
		act.moveToElement(element).click().build().perform();
	}

	/**
	 * Click on Element
	 * @param by By object
	 */
	public void javascriptClickOn(By by) {
		WebElement element = driver.findElement(by);
		JavascriptExecutor executor = (JavascriptExecutor)driver;
		executor.executeScript("arguments[0].click();", element);
	}

	public void javascriptSetAnAttribute(WebElement e, String attribute, String value) {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		String jsscript = "arguments[0].setAttribute(arguments[1], arguments[2])";
		jse.executeScript(jsscript,e,attribute,value);
	}

	/**
	 * Execute javascript
	 * @param javaScript  javascript details
	 */
	public void enterTextUsingJavascriptExecute(String javaScript) {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript(javaScript);
	}

	/**
	 * Execute javascript
	 * @param element  javascript details
	 */
	public void enterTextUsingJavascriptExecute(WebElement element, String value, boolean clear)
	{
		JavascriptExecutor jse = (JavascriptExecutor)driver;

		element = waitTillElementIsClickable(element);
		if(clear)
		{
			jse.executeScript("arguments[0].value='';", element);
		}
		jse.executeScript("arguments[0].value='"+ value +"';", element);
	}


	//Browser's Tab handler
	public String getWindowHandle()
	{
		return driver.getWindowHandle();
	}

	public Set<String> getWindowHandles()
	{
		return driver.getWindowHandles();
	}

	public void switchToWindow(int tabNumber)
	{
		int i = 1;
		for (String winHandle : getWindowHandles())
		{
			driver.switchTo().window(winHandle);
			if (i == tabNumber)
				break;
			i++;
		}
	}

	/**
	 * Switch to last window / tab of the browser
	 */
	public void switchToLastWindow() {
		ArrayList<String> tabs = new ArrayList<>(getWindowHandles());
		switchToWindow(tabs.size());
	}

	/**
	 * Close current tab browser after open new tab
	 * @throws InterruptedException
	 */
	public void closeTabWindowBrowser() throws InterruptedException {
		String currentTab = driver.getWindowHandle();
		driver.close();

		for (String tab : driver.getWindowHandles()) {
			if (!tab.equals(currentTab)) {
				driver.switchTo().window(tab);
			}
		}
	}

	/**
	 * Javascript open new tab
	 */
	public void openNewTab() {
		((JavascriptExecutor) driver).executeScript("window.open()");
	}

    /**
     * Maximize the window
     */
    public void maximizeWindow()
    {
        driver.manage().window().maximize();
    }

    /**
     * Set the window size
     * @param width (int) provide width window size
     * @param height (int) provide height window size
     * eg : setSizeWindow(1920, 1080);
     */
    public void setSizeWindow(int width, int height)
    {
        Dimension dimension = new Dimension(width, height);
        driver.manage().window().setSize(dimension);
    }


    /**
	 * Switch to current window
	 * @throws InterruptedException
	 */
	public void switchToWindow()
	{
		String winHandleBefore = driver.getWindowHandle();

		driver.switchTo().window(winHandleBefore);
	}

	/**
	 * Wait until element is invisible
	 * go to back to previous page
	 */
	public void back() {
		driver.navigate().back();
	}


	//iFrames
	public void switchToIframe(String iframeId)
	{
		driver.switchTo().frame(iframeId);
	}

	public void switchToIframe(int iframeIndex)
	{
		driver.switchTo().frame(iframeIndex);
	}

	public void swtichToMainIframe()
	{
		driver.switchTo().defaultContent();
	}

	/**
	 * Send key backspace
	 * @param element object
	 */
	public void sendKeyBackSpace(WebElement element){
		if (os.contains("mac")){
			element.sendKeys(Keys.chord(Keys.DELETE));
		}else{
			element.sendKeys(Keys.chord(Keys.BACK_SPACE));
		}
	}

	/**
	 * Send key escape
	 */
	public void sendKeyEscape(){
		actions.sendKeys(Keys.ESCAPE);
	}

	/**
	 * Send key enter
	 */
	public void sendKeyEnter(WebElement element){
		if (os.contains("mac")){
			element.sendKeys(Keys.chord(Keys.RETURN));
		}else{
			element.sendKeys(Keys.chord(Keys.ENTER));
		}
	}

	/**
	 * Clear text field
	 */
	public void clearTextField(WebElement element){
		if (os.contains("mac")){
			element.sendKeys(Keys.COMMAND, "a");
			element.sendKeys(Keys.BACK_SPACE);
		}else{
			element.sendKeys(Keys.CONTROL, "a");
			element.sendKeys(Keys.DELETE);
		}
	}

	/**
	 * Get current position with Javascript Executor
	 */
	public Long getPageYOffset() {
		JavascriptExecutor j = (JavascriptExecutor) driver;
		Long value;
		String type = j.executeScript("return window.pageYOffset;").getClass().getSimpleName();
		if(type.equals("Double")){
			Double values = (Double) j.executeScript("return window.pageYOffset;");
			value = values.longValue();
		}else {
			value = (Long) j.executeScript("return window.pageYOffset;");
		}
		return value;
	}

	////Cookies part
	/**
	 * Add cookies based on cookie set
	 * @param cookie cookie data type
	 */
	public void addCookies(Cookie cookie) {
		driver.manage().addCookie(cookie);
	}

	/**
	 * Add cookies based on key and value
	 * @param key cookie key
	 * @param value cookie value
	 */
	public void addCookies(String key, String value) {
		driver.manage().addCookie(new Cookie(key, value));
	}

	/**
	 * Get named cookie by it key
	 * @param key cookie key name
	 * @return String data type
	 */
	public Cookie getNamedCookie(String key) {
		return driver.manage().getCookieNamed(key);
	}

	/**
	 * Get all cookies of current page
	 * @return Set<Cookie>
	 */
	public Set<Cookie> getAllCookies() {
		return driver.manage().getCookies();
	}

	/**
	 * Delete cookie by cookie set
	 * @param cookie data type
	 */
	public void deleteCookie(Cookie cookie) {
		driver.manage().deleteCookie(cookie);
	}

	/**
	 * Delete all available cookies
	 */
	public void deleteAllCookies() {
		driver.manage().deleteAllCookies();
	}
	////cookies part
}

