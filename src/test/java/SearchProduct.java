import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;


import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SearchProduct extends BaseTest {

    @Test
    @Order(1)
    public void false_email() throws InterruptedException {
        //false login

        driver.findElement(By.id("nav-link-accountList")).click();
        driver.findElement(By.id("ap_email")).sendKeys("yanlisdeneme");
        driver.findElement(By.id("continue")).click();
        driver.getPageSource().contains("Yanlış veya geçersiz e-posta adresi veya cep telefonu numarası. Lütfen düzeltin ve tekrar deneyin.");
    }

    @Test
    @Order(2)
    public void false_pasword() throws InterruptedException {
        driver.findElement(By.id("nav-link-accountList")).click();
        driver.findElement(By.id("ap_email")).sendKeys("testotomasyon1234@gmail.com");
        driver.findElement(By.id("continue")).click();
        driver.findElement(By.id("ap_password")).sendKeys("asx.");
        driver.findElement(By.id("signInSubmit")).click();
        driver.getPageSource().contains("Bir sorun oluştu\n" +
                "Şifreniz yanlış");


    }

    @Test
    @Order(3)
    public void true_login() throws InterruptedException {

        driver.findElement(By.id("nav-link-accountList")).click();
        driver.findElement(By.id("ap_email")).sendKeys("testotomasyon1234@gmail.com");
        driver.findElement(By.id("continue")).click();
        driver.findElement(By.id("ap_password")).sendKeys("melike123.");
        driver.findElement(By.id("signInSubmit")).click();

    }

    @Test
    @Order(4)
    public void search_product() throws InterruptedException {

        driver.findElement(By.id("nav-link-accountList")).click();
        driver.findElement(By.id("ap_email")).sendKeys("testotomasyon1234@gmail.com");
        driver.findElement(By.id("continue")).click();
        driver.findElement(By.id("ap_password")).sendKeys("melike123.");
        driver.findElement(By.id("signInSubmit")).click();
        //giriş yaptığımızı kontrol ettirme
        //Assertions.assertEquals();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement accountName = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-link-accountList-nav-line-1")));

        //arama çubuğu boş ise anasayfada olduğumuzu doğrularız.İşlemlerimizi anasayfada olduğumuzu doğruladıktan sonra devam ederiz.
        WebElement searchBox = driver.findElement(By.id("twotabsearchtextbox"));
        String nullSearch = searchBox.getAttribute("value");

        if (nullSearch == null || nullSearch.isEmpty()) {

            //search işlemi
            WebElement search = driver.findElement(By.xpath("//*[@id=\"twotabsearchtextbox\"]"));
            search.sendKeys("ayakkabı");
            search.sendKeys(Keys.ENTER);

            //ayakkabı aratılmış mı diye başlık komtrolü
            Assertions.assertEquals("Amazon.com.tr : ayakkabı", driver.getTitle());
            JavascriptExecutor js = (JavascriptExecutor) driver;

            //sayfada kontrollü şekilde kaydırma işlemi
            int scroll = 1000;
            int scrollPAuseTime = 600;

            for (int i = 0; i < 10; i++) {
                js.executeScript("window.scrollBy(0, arguments[0])", scroll);
                Thread.sleep(scrollPAuseTime);
            }

            try {
                Thread.sleep(700);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //sayfayı en yukarı taşıma
            js.executeScript("window.scrollTo(0,0)");
        } else {
            System.out.println("Searching for: " + nullSearch);
        }
        //ürün seçme ve sepete ekleme
        driver.findElement(By.xpath("//*[@id=\"search\"]/div[1]/div[1]/div/span[1]/div[1]/div[3]/div/div/div/div/span/div/div/div[1]/div/span/a/div")).click();
        driver.findElement(By.id("atc-declarative")).click();
       // driver.findElement(By.id("desktop-ptc-button-celWidget")).click();


        //sepete eklendi mi kontrolu
        WebElement shopping = driver.findElement(By.id("nav-cart-count"));

        if (shopping.isDisplayed()) {
            driver.findElement(By.id("sc-buy-box-ptc-button")).click();
            driver.findElement(By.id("address-ui-widgets-countryCode")).click();
            WebElement listBox = driver.findElement(By.xpath("//*[@id=\"a-popover-1\"]/div/div"));

            JavascriptExecutor js = (JavascriptExecutor) driver;
            long scrollHeight = (Long) js.executeScript("return arguments[0].scrollHeight", listBox);
            js.executeScript("arguments[0].scrollTop = 0", listBox);

            // Yavaş yavaş kaydırma işlemi
            for (int i = 0; i < scrollHeight; i += 10) {
                js.executeScript("arguments[0].scrollTop = arguments[0].scrollTop + 40", listBox);
                try
                {
                    Thread.sleep(100);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }

            }
            //alışverişi tamamlama,bilgileri girme
            Thread.sleep(700);
            js.executeScript("arguments[0].scrollTop = arguments[0].scrollHeight", listBox);
            driver.findElement(By.id("address-ui-widgets-countryCode-dropdown-nativeId_239")).click();
            Thread.sleep(700);
            driver.findElement(By.id("address-ui-widgets-enterAddressFullName")).sendKeys("test otomasyon");
            Thread.sleep(700);
            driver.findElement(By.id("address-ui-widgets-enterAddressLine1")).sendKeys("test otomasyon");
            Thread.sleep(700);
            driver.findElement(By.id("address-ui-widgets-enterAddressLine2")).sendKeys("test otomasyon");
            Thread.sleep(700);
            driver.findElement(By.id("address-ui-widgets-enterAddressCity")).sendKeys("test otomasyon");
            Thread.sleep(700);
            driver.findElement(By.id("address-ui-widgets-enterAddressStateOrRegion")).sendKeys("test otomasyon");
            Thread.sleep(700);
            driver.findElement(By.id("address-ui-widgets-enterAddressPostalCode")).sendKeys("test otomasyon");
            Thread.sleep(700);
            WebElement phoneNumber=  driver.findElement(By.id("address-ui-widgets-enterAddressPhoneNumber"));
            phoneNumber.sendKeys("test otomasyon");
            Thread.sleep(700);
            driver.findElement(By.id("address-ui-widgets-form-submit-button")).click();
            Thread.sleep(700);
            WebElement phoneNumber1=  driver.findElement(By.id("address-ui-widgets-enterAddressPhoneNumber"));
//yanlış bilgi girildiğine uyarı vermesi
            phoneNumber1.sendKeys(Keys.CONTROL+"a");
            phoneNumber1.sendKeys(Keys.DELETE);
            Thread.sleep(700);
            phoneNumber1.sendKeys("545 386 4552");
            Thread.sleep(700);
            driver.findElement(By.id("address-ui-widgets-form-submit-button")).click();
            Thread.sleep(2000);
            //address-ui-widgets-enterAddressPhoneNumber
            driver.findElement(By.id("tax-registration-number-field-tr")).sendKeys("12345678910");
            driver.findElement(By.id("business-legal-name-tr")).sendKeys("deneme test");
            driver.findElement(By.xpath("//*[@id=\"taxid-continue-enabled\"]/span/input")).click();


        }

    }
}


