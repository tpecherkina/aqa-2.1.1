package ru.netology.selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import static org.openqa.selenium.By.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class SeleniumTest {
    private WebDriver driver;

    @BeforeAll
    static void setUpAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void SetUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:7777");
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    void shouldPropmtToTheNextPage() {
        driver.get("http://localhost:7777");
        WebElement form = driver.findElement(cssSelector("form[class='form form_size_m form_theme_alfa-on-white']"));
        driver.findElement(cssSelector("[data-test-id=name] input")).sendKeys("Таня");
        driver.findElement(cssSelector("[data-test-id=phone] input")).sendKeys("+79195631111");
        driver.findElement(cssSelector("[data-test-id=agreement] ")).click();
        driver.findElement(cssSelector("span[class=button__text]")).click();
        assertTrue(!driver.findElements(cssSelector("[data-test-id=\"order-success\"]")).isEmpty(),"Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.");
    }


    @Test void shouldGiveAnErrorMessageOnName() {
        driver.get("http://localhost:7777");
        WebElement form = driver.findElement(cssSelector("form[class='form form_size_m form_theme_alfa-on-white']"));
        driver.findElement(cssSelector("[data-test-id=name] input")).sendKeys("Tanya");
        driver.findElement(cssSelector("[data-test-id=phone] input")).sendKeys("+72022571580");
        driver.findElement(cssSelector("[data-test-id=agreement] ")).click();
        driver.findElement(cssSelector("span[class=button__text]")).click();
        assertTrue(!driver.findElements(cssSelector(".input_invalid ")).isEmpty(), 
                    "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.");
    }


    @Test
    void shouldGiveAnErrorMessageOnPhone() {
        driver.get("http://localhost:7777");
        WebElement form = driver.findElement(cssSelector("form[class='form form_size_m form_theme_alfa-on-white']"));
        driver.findElement(cssSelector("[data-test-id=name] input")).sendKeys("Таня");
        driver.findElement(cssSelector("[data-test-id=phone] input")).sendKeys("+720225715801");
        driver.findElement(cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(cssSelector("span[class=button__text]")).click();
       assertTrue(!driver.findElements(cssSelector(".input_invalid ")).isEmpty(), "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.");
    }

    @Test
    void shouldNotPromptToTheNextPageAgreementUnchecked() {
        driver.findElement(cssSelector("[data-test-id=name] input")).sendKeys("Таня");
        driver.findElement(cssSelector("[data-test-id=phone] input")).sendKeys("+79195631111");
           driver.findElement(cssSelector("span[class=button__text]")).click();
        assertTrue(!driver.findElements(cssSelector(".input_invalid ")).isEmpty(), "Не нажат флажок согласия на обработку персональных данных");
    }
}
