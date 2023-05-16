package ru.netology.tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.data.DataBaseHelper;
import ru.netology.data.DataHelper;
import ru.netology.page.BuyPage;
import ru.netology.page.StartPage;
import static com.codeborne.selenide.Selenide.open;

public class TestBuy {

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
        DataBaseHelper.clearDataBase();
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @BeforeEach
    void setup(){
        Configuration.holdBrowserOpen = true;
        var startPage = open("http://localhost:8080", StartPage.class);
        startPage.buyPage();
    }

    @AfterEach
    public void clearDataBase(){
        DataBaseHelper.clearDataBase();
    }


    @Test
    @DisplayName("Успешная оплата")
    void shouldSuccessfullyPay(){

        var buyPage = new BuyPage();
        var date = DataHelper.getValidDate();
        buyPage.cardDataEntry(
                DataHelper.getApprovedNumberCard(),
                date.getMonth(),
                date.getYear(),
                DataHelper.getValidName(),
                DataHelper.getValidCode());
        buyPage.clickBuy();
        buyPage.buttonNotificationVisible();
        buyPage.notificationOkVisible();

        Assertions.assertEquals("APPROVED", DataBaseHelper.getPaymentEntity().getStatus());
    }

    @Test
    @DisplayName("Попытка оплатить заблокированной картой")
    void shouldTryToPayWithDeclinedCard(){

        var buyPage = new BuyPage();
        var date = DataHelper.getValidDate();
        buyPage.cardDataEntry(
                DataHelper.getDeclinedNumberCard(),
                date.getMonth(),
                date.getYear(),
                DataHelper.getValidName(),
                DataHelper.getValidCode());
        buyPage.clickBuy();
        buyPage.buttonNotificationVisible();
        buyPage.notificationErrorVisible();

        Assertions.assertEquals("DECLINED", DataBaseHelper.getPaymentEntity().getStatus());
    }

    @Test
    @DisplayName("Попытка оплатить картой не зарегистрированной в банке")
    void shouldTryToPayWithWrongCard(){

        var buyPage = new BuyPage();
        var date = DataHelper.getValidDate();
        buyPage.cardDataEntry(
                DataHelper.getWrongNumberCard(),
                date.getMonth(),
                date.getYear(),
                DataHelper.getValidName(),
                DataHelper.getValidCode());
        buyPage.clickBuy();
        buyPage.buttonNotificationVisible();
        buyPage.notificationErrorVisible();
        buyPage.notificationErrorClose();
        buyPage.notificationOkNotVisible();

        Assertions.assertTrue(DataBaseHelper.DataBaseIsEmpty());
    }

    @Test
    @DisplayName("Короткий номер карты")
    void shouldBeNoticeIfNumberCardIsShort(){

        var buyPage = new BuyPage();
        var date = DataHelper.getValidDate();
        buyPage.cardDataEntry(
                DataHelper.getShortNumberCard(),
                date.getMonth(),
                date.getYear(),
                DataHelper.getValidName(),
                DataHelper.getValidCode());
        buyPage.clickBuy();
        buyPage.getErrorFormat();

        Assertions.assertTrue(DataBaseHelper.DataBaseIsEmpty());
    }

    @Test
    @DisplayName("поле Номер карты не заполнен")
    void shouldBeNoticeIfNumberCardIsEmpty(){

        var buyPage = new BuyPage();
        var date = DataHelper.getValidDate();
        buyPage.cardDataEntry(
                DataHelper.getEmpty(),
                date.getMonth(),
                date.getYear(),
                DataHelper.getValidName(),
                DataHelper.getValidCode());
        buyPage.clickBuy();
        buyPage.getErrorEmpty();

        Assertions.assertTrue(DataBaseHelper.DataBaseIsEmpty());
    }

    @Test
    @DisplayName("Неверный формат месяца (1 цифра)")
    void shouldBeNoticeIfMonthIsShort(){

        var buyPage = new BuyPage();
        buyPage.cardDataEntry(
                DataHelper.getApprovedNumberCard(),
                DataHelper.getOneNumber(),
                DataHelper.getValidDate().getYear(),
                DataHelper.getValidName(),
                DataHelper.getValidCode());
        buyPage.clickBuy();
        buyPage.getErrorFormat();

        Assertions.assertTrue(DataBaseHelper.DataBaseIsEmpty());
    }

    @Test
    @DisplayName("поле Месяц не заполнен")
    void shouldBeNoticeIfMonthIsEmpty(){

        var buyPage = new BuyPage();
        buyPage.cardDataEntry(
                DataHelper.getApprovedNumberCard(),
                DataHelper.getEmpty(),
                DataHelper.getValidDate().getYear(),
                DataHelper.getValidName(),
                DataHelper.getValidCode());
        buyPage.clickBuy();
        buyPage.getErrorEmpty();

        Assertions.assertTrue(DataBaseHelper.DataBaseIsEmpty());
    }

    @Test
    @DisplayName("в поле Месяц введено '00'")
    void shouldBeNoticeIfMonthIsWrong(){

        var buyPage = new BuyPage();
        buyPage.cardDataEntry(
                DataHelper.getApprovedNumberCard(),
                DataHelper.getZeros(),
                DataHelper.getValidDate().getYear(),
                DataHelper.getValidName(),
                DataHelper.getValidCode());
        buyPage.clickBuy();
        buyPage.getErrorWrongDateCard();

        Assertions.assertTrue(DataBaseHelper.DataBaseIsEmpty());
    }

    @Test
    @DisplayName("Неверный формат года (1 цифра)")
    void shouldBeNoticeIfYearIsShort(){

        var buyPage = new BuyPage();
        buyPage.cardDataEntry(
                DataHelper.getApprovedNumberCard(),
                DataHelper.getValidDate().getMonth(),
                DataHelper.getOneNumber(),
                DataHelper.getValidName(),
                DataHelper.getValidCode());
        buyPage.clickBuy();
        buyPage.getErrorFormat();

        Assertions.assertTrue(DataBaseHelper.DataBaseIsEmpty());
    }

    @Test
    @DisplayName("поле Год не заполнен")
    void shouldBeNoticeIfYearIsEmpty(){

        var buyPage = new BuyPage();
        buyPage.cardDataEntry(
                DataHelper.getApprovedNumberCard(),
                DataHelper.getValidDate().getMonth(),
                DataHelper.getEmpty(),
                DataHelper.getValidName(),
                DataHelper.getValidCode());
        buyPage.clickBuy();
        buyPage.getErrorEmpty();

        Assertions.assertTrue(DataBaseHelper.DataBaseIsEmpty());
    }

    @Test
    @DisplayName("Карта просрочена")
    void shouldBeNoticeIfCardExpired(){

        var buyPage = new BuyPage();
        var date = DataHelper.getPastDate();
        buyPage.cardDataEntry(
                DataHelper.getApprovedNumberCard(),
                date.getMonth(),
                date.getYear(),
                DataHelper.getValidName(),
                DataHelper.getValidCode());
        buyPage.clickBuy();
        buyPage.getErrorCardExpired();

        Assertions.assertTrue(DataBaseHelper.DataBaseIsEmpty());
    }

    @Test
    @DisplayName("Неверно указан срок действия карты (больше пяти лет от текущей)")
    void shouldBeNoticeIfOverFiveYearsDate(){

        var buyPage = new BuyPage();
        var date = DataHelper.getOverFiveYearsDate();
        buyPage.cardDataEntry(
                DataHelper.getApprovedNumberCard(),
                date.getMonth(),
                date.getYear(),
                DataHelper.getValidName(),
                DataHelper.getValidCode());
        buyPage.clickBuy();
        buyPage.getErrorWrongDateCard();

        Assertions.assertTrue(DataBaseHelper.DataBaseIsEmpty());
    }

    @Test
    @DisplayName("CVV/CVC короткий")
    void shouldBeNoticeIfCVCisShort(){

        var buyPage = new BuyPage();
        var date = DataHelper.getValidDate();
        buyPage.cardDataEntry(
                DataHelper.getApprovedNumberCard(),
                date.getMonth(),
                date.getYear(),
                DataHelper.getValidName(),
                DataHelper.getShortCode());
        buyPage.clickBuy();
        buyPage.getErrorFormat();

        Assertions.assertTrue(DataBaseHelper.DataBaseIsEmpty());
    }

    @Test
    @DisplayName("CVV/CVC не заполнен")
    void shouldBeNoticeIfCVCIsEmpty(){

        var buyPage = new BuyPage();
        var date = DataHelper.getValidDate();
        buyPage.cardDataEntry(
                DataHelper.getApprovedNumberCard(),
                date.getMonth(),
                date.getYear(),
                DataHelper.getValidName(),
                DataHelper.getEmpty());
        buyPage.clickBuy();
        buyPage.getErrorEmpty();

        Assertions.assertTrue(DataBaseHelper.DataBaseIsEmpty());
    }

    @Test
    @DisplayName("поле Владелец не заполнено")
    void shouldBeNoticeIfHolderIsEmpty(){

        var buyPage = new BuyPage();
        var date = DataHelper.getValidDate();
        buyPage.cardDataEntry(
                DataHelper.getApprovedNumberCard(),
                date.getMonth(),
                date.getYear(),
                DataHelper.getEmpty(),
                DataHelper.getValidCode());
        buyPage.clickBuy();
        buyPage.getErrorEmpty();

        Assertions.assertTrue(DataBaseHelper.DataBaseIsEmpty());
    }

    @Test
    @DisplayName("поле Владелец заполнено на кириллице")
    void shouldBeNoticeIfHolderInСyrillic(){

        var buyPage = new BuyPage();
        var date = DataHelper.getValidDate();
        buyPage.cardDataEntry(
                DataHelper.getApprovedNumberCard(),
                date.getMonth(),
                date.getYear(),
                DataHelper.getCyrillicName(),
                DataHelper.getValidCode());
        buyPage.clickBuy();
        buyPage.getErrorFormat();

        Assertions.assertTrue(DataBaseHelper.DataBaseIsEmpty());
    }

    @Test
    @DisplayName("поле Владелец заполнено пробелами")
    void shouldBeNoticeIfHolderInSpaces(){

        var buyPage = new BuyPage();
        var date = DataHelper.getValidDate();
        buyPage.cardDataEntry(
                DataHelper.getApprovedNumberCard(),
                date.getMonth(),
                date.getYear(),
                DataHelper.getMultipleSpaces(),
                DataHelper.getValidCode());
        buyPage.clickBuy();
        buyPage.getErrorEmpty();

        Assertions.assertTrue(DataBaseHelper.DataBaseIsEmpty());
    }

    @Test
    @DisplayName("поле Владелец заполнено спецсимволами")
    void shouldBeNoticeIfHolderInSpecialSymbols(){

        var buyPage = new BuyPage();
        var date = DataHelper.getValidDate();
        buyPage.cardDataEntry(
                DataHelper.getApprovedNumberCard(),
                date.getMonth(),
                date.getYear(),
                DataHelper.getSpecialSymbols(),
                DataHelper.getValidCode());
        buyPage.clickBuy();
        buyPage.getErrorFormat();

        Assertions.assertTrue(DataBaseHelper.DataBaseIsEmpty());
    }

    @Test
    @DisplayName("поле Владелец заполнено цифрами")
    void shouldBeNoticeIfHolderInNumbers(){

        var buyPage = new BuyPage();
        var date = DataHelper.getValidDate();
        buyPage.cardDataEntry(
                DataHelper.getApprovedNumberCard(),
                date.getMonth(),
                date.getYear(),
                DataHelper.getRandomNumbers(),
                DataHelper.getValidCode());
        buyPage.clickBuy();
        buyPage.getErrorFormat();

        Assertions.assertTrue(DataBaseHelper.DataBaseIsEmpty());
    }

    @Test
    @DisplayName("поле Владелец заполнено 27 символами")
    void shouldBeNoticeIfHolderInLongName(){

        var buyPage = new BuyPage();
        var date = DataHelper.getValidDate();
        buyPage.cardDataEntry(
                DataHelper.getApprovedNumberCard(),
                date.getMonth(),
                date.getYear(),
                DataHelper.getLongName(),
                DataHelper.getValidCode());
        buyPage.clickBuy();
        buyPage.getErrorFormat();

        Assertions.assertTrue(DataBaseHelper.DataBaseIsEmpty());
    }
}
