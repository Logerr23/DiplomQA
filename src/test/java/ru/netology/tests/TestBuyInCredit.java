package ru.netology.tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.data.DataBaseHelper;
import ru.netology.data.DataHelper;
import ru.netology.page.BuyInCreditPage;
import ru.netology.page.StartPage;
import static com.codeborne.selenide.Selenide.open;

public class TestBuyInCredit {

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
        startPage.buyInCreditPage();
    }

    @AfterEach
    public void clearDataBase(){
        DataBaseHelper.clearDataBase();
    }

    @Test
    @DisplayName("Успешная оплата")
    void shouldSuccessfullyPay(){

        var buyInCreditPage = new BuyInCreditPage();
        var date = DataHelper.getValidDate();
        buyInCreditPage.cardDataEntry(
                DataHelper.getApprovedNumberCard(),
                date.getMonth(),
                date.getYear(),
                DataHelper.getValidName(),
                DataHelper.getValidCode());
        buyInCreditPage.clickBuy();
        buyInCreditPage.buttonNotificationVisible();
        buyInCreditPage.notificationOkVisible();

        Assertions.assertEquals("APPROVED", DataBaseHelper.getCreditRequestEntity().getStatus());
    }

    @Test
    @DisplayName("Попытка оплатить заблокированной картой")
    void shouldTryToPayWithDeclinedCard(){

        var buyInCreditPage = new BuyInCreditPage();
        var date = DataHelper.getValidDate();
        buyInCreditPage.cardDataEntry(
                DataHelper.getDeclinedNumberCard(),
                date.getMonth(),
                date.getYear(),
                DataHelper.getValidName(),
                DataHelper.getValidCode());
        buyInCreditPage.clickBuy();
        buyInCreditPage.buttonNotificationVisible();
        buyInCreditPage.notificationErrorVisible();

        Assertions.assertEquals("DECLINED", DataBaseHelper.getCreditRequestEntity().getStatus());
    }

    @Test
    @DisplayName("Попытка оплатить картой не зарегистрированной в банке")
    void shouldTryToPayWithWrongCard(){

        var buyInCreditPage = new BuyInCreditPage();
        var date = DataHelper.getValidDate();
        buyInCreditPage.cardDataEntry(
                DataHelper.getWrongNumberCard(),
                date.getMonth(),
                date.getYear(),
                DataHelper.getValidName(),
                DataHelper.getValidCode());
        buyInCreditPage.clickBuy();
        buyInCreditPage.buttonNotificationVisible();
        buyInCreditPage.notificationErrorVisible();
        buyInCreditPage.notificationErrorClose();
        buyInCreditPage.notificationOkNotVisible();

        Assertions.assertTrue(DataBaseHelper.DataBaseIsEmpty());
    }

    @Test
    @DisplayName("Короткий номер карты")
    void shouldBeNoticeIfNumberCardIsShort(){

        var buyInCreditPage = new BuyInCreditPage();
        var date = DataHelper.getValidDate();
        buyInCreditPage.cardDataEntry(
                DataHelper.getShortNumberCard(),
                date.getMonth(),
                date.getYear(),
                DataHelper.getValidName(),
                DataHelper.getValidCode());
        buyInCreditPage.clickBuy();
        buyInCreditPage.getErrorFormat();

        Assertions.assertTrue(DataBaseHelper.DataBaseIsEmpty());
    }

    @Test
    @DisplayName("поле Номер карты не заполнен")
    void shouldBeNoticeIfNumberCardIsEmpty(){

        var buyInCreditPage = new BuyInCreditPage();
        var date = DataHelper.getValidDate();
        buyInCreditPage.cardDataEntry(
                DataHelper.getEmpty(),
                date.getMonth(),
                date.getYear(),
                DataHelper.getValidName(),
                DataHelper.getValidCode());
        buyInCreditPage.clickBuy();
        buyInCreditPage.getErrorEmpty();

        Assertions.assertTrue(DataBaseHelper.DataBaseIsEmpty());
    }

    @Test
    @DisplayName("Неверный формат месяца (1 цифра)")
    void shouldBeNoticeIfMonthIsShort(){

        var buyInCreditPage = new BuyInCreditPage();
        buyInCreditPage.cardDataEntry(
                DataHelper.getApprovedNumberCard(),
                DataHelper.getOneNumber(),
                DataHelper.getValidDate().getYear(),
                DataHelper.getValidName(),
                DataHelper.getValidCode());
        buyInCreditPage.clickBuy();
        buyInCreditPage.getErrorFormat();

        Assertions.assertTrue(DataBaseHelper.DataBaseIsEmpty());
    }

    @Test
    @DisplayName("поле Месяц не заполнен")
    void shouldBeNoticeIfMonthIsEmpty(){

        var buyInCreditPage = new BuyInCreditPage();
        buyInCreditPage.cardDataEntry(
                DataHelper.getApprovedNumberCard(),
                DataHelper.getEmpty(),
                DataHelper.getValidDate().getYear(),
                DataHelper.getValidName(),
                DataHelper.getValidCode());
        buyInCreditPage.clickBuy();
        buyInCreditPage.getErrorEmpty();

        Assertions.assertTrue(DataBaseHelper.DataBaseIsEmpty());
    }

    @Test
    @DisplayName("в поле Месяц введено '00'")
    void shouldBeNoticeIfMonthIsWrong(){

        var buyInCreditPage = new BuyInCreditPage();
        buyInCreditPage.cardDataEntry(
                DataHelper.getApprovedNumberCard(),
                DataHelper.getZeros(),
                DataHelper.getValidDate().getYear(),
                DataHelper.getValidName(),
                DataHelper.getValidCode());
        buyInCreditPage.clickBuy();
        buyInCreditPage.getErrorWrongDateCard();

        Assertions.assertTrue(DataBaseHelper.DataBaseIsEmpty());
    }

    @Test
    @DisplayName("Неверный формат года (1 цифра)")
    void shouldBeNoticeIfYearIsShort(){

        var buyInCreditPage = new BuyInCreditPage();
        buyInCreditPage.cardDataEntry(
                DataHelper.getApprovedNumberCard(),
                DataHelper.getValidDate().getMonth(),
                DataHelper.getOneNumber(),
                DataHelper.getValidName(),
                DataHelper.getValidCode());
        buyInCreditPage.clickBuy();
        buyInCreditPage.getErrorFormat();

        Assertions.assertTrue(DataBaseHelper.DataBaseIsEmpty());
    }

    @Test
    @DisplayName("поле Год не заполнен")
    void shouldBeNoticeIfYearIsEmpty(){

        var buyInCreditPage = new BuyInCreditPage();
        buyInCreditPage.cardDataEntry(
                DataHelper.getApprovedNumberCard(),
                DataHelper.getValidDate().getMonth(),
                DataHelper.getEmpty(),
                DataHelper.getValidName(),
                DataHelper.getValidCode());
        buyInCreditPage.clickBuy();
        buyInCreditPage.getErrorEmpty();

        Assertions.assertTrue(DataBaseHelper.DataBaseIsEmpty());
    }

    @Test
    @DisplayName("Карта просрочена")
    void shouldBeNoticeIfCardExpired(){

        var buyInCreditPage = new BuyInCreditPage();
        var date = DataHelper.getPastDate();
        buyInCreditPage.cardDataEntry(
                DataHelper.getApprovedNumberCard(),
                date.getMonth(),
                date.getYear(),
                DataHelper.getValidName(),
                DataHelper.getValidCode());
        buyInCreditPage.clickBuy();
        buyInCreditPage.getErrorCardExpired();

        Assertions.assertTrue(DataBaseHelper.DataBaseIsEmpty());
    }

    @Test
    @DisplayName("Неверно указан срок действия карты (больше пяти лет от текущей)")
    void shouldBeNoticeIfOverFiveYearsDate(){

        var buyInCreditPage = new BuyInCreditPage();
        var date = DataHelper.getOverFiveYearsDate();
        buyInCreditPage.cardDataEntry(
                DataHelper.getApprovedNumberCard(),
                date.getMonth(),
                date.getYear(),
                DataHelper.getValidName(),
                DataHelper.getValidCode());
        buyInCreditPage.clickBuy();
        buyInCreditPage.getErrorWrongDateCard();

        Assertions.assertTrue(DataBaseHelper.DataBaseIsEmpty());
    }

    @Test
    @DisplayName("CVV/CVC короткий")
    void shouldBeNoticeIfCVCisShort(){

        var buyInCreditPage = new BuyInCreditPage();
        var date = DataHelper.getValidDate();
        buyInCreditPage.cardDataEntry(
                DataHelper.getApprovedNumberCard(),
                date.getMonth(),
                date.getYear(),
                DataHelper.getValidName(),
                DataHelper.getShortCode());
        buyInCreditPage.clickBuy();
        buyInCreditPage.getErrorFormat();

        Assertions.assertTrue(DataBaseHelper.DataBaseIsEmpty());
    }

    @Test
    @DisplayName("CVV/CVC не заполнен")
    void shouldBeNoticeIfCVCIsEmpty(){

        var buyInCreditPage = new BuyInCreditPage();
        var date = DataHelper.getValidDate();
        buyInCreditPage.cardDataEntry(
                DataHelper.getApprovedNumberCard(),
                date.getMonth(),
                date.getYear(),
                DataHelper.getValidName(),
                DataHelper.getEmpty());
        buyInCreditPage.clickBuy();
        buyInCreditPage.getErrorEmpty();

        Assertions.assertTrue(DataBaseHelper.DataBaseIsEmpty());
    }

    @Test
    @DisplayName("поле Владелец не заполнено")
    void shouldBeNoticeIfHolderIsEmpty(){

        var buyInCreditPage = new BuyInCreditPage();
        var date = DataHelper.getValidDate();
        buyInCreditPage.cardDataEntry(
                DataHelper.getApprovedNumberCard(),
                date.getMonth(),
                date.getYear(),
                DataHelper.getEmpty(),
                DataHelper.getValidCode());
        buyInCreditPage.clickBuy();
        buyInCreditPage.getErrorEmpty();

        Assertions.assertTrue(DataBaseHelper.DataBaseIsEmpty());
    }

    @Test
    @DisplayName("поле Владелец заполнено на кириллице")
    void shouldBeNoticeIfHolderInСyrillic(){

        var buyInCreditPage = new BuyInCreditPage();
        var date = DataHelper.getValidDate();
        buyInCreditPage.cardDataEntry(
                DataHelper.getApprovedNumberCard(),
                date.getMonth(),
                date.getYear(),
                DataHelper.getCyrillicName(),
                DataHelper.getValidCode());
        buyInCreditPage.clickBuy();
        buyInCreditPage.getErrorFormat();

        Assertions.assertTrue(DataBaseHelper.DataBaseIsEmpty());
    }

    @Test
    @DisplayName("поле Владелец заполнено пробелами")
    void shouldBeNoticeIfHolderInSpaces(){

        var buyInCreditPage = new BuyInCreditPage();
        var date = DataHelper.getValidDate();
        buyInCreditPage.cardDataEntry(
                DataHelper.getApprovedNumberCard(),
                date.getMonth(),
                date.getYear(),
                DataHelper.getMultipleSpaces(),
                DataHelper.getValidCode());
        buyInCreditPage.clickBuy();
        buyInCreditPage.getErrorEmpty();

        Assertions.assertTrue(DataBaseHelper.DataBaseIsEmpty());
    }

    @Test
    @DisplayName("поле Владелец заполнено спецсимволами")
    void shouldBeNoticeIfHolderInSpecialSymbols(){

        var buyInCreditPage = new BuyInCreditPage();
        var date = DataHelper.getValidDate();
        buyInCreditPage.cardDataEntry(
                DataHelper.getApprovedNumberCard(),
                date.getMonth(),
                date.getYear(),
                DataHelper.getSpecialSymbols(),
                DataHelper.getValidCode());
        buyInCreditPage.clickBuy();
        buyInCreditPage.getErrorFormat();

        Assertions.assertTrue(DataBaseHelper.DataBaseIsEmpty());
    }

    @Test
    @DisplayName("поле Владелец заполнено цифрами")
    void shouldBeNoticeIfHolderInNumbers(){

        var buyInCreditPage = new BuyInCreditPage();
        var date = DataHelper.getValidDate();
        buyInCreditPage.cardDataEntry(
                DataHelper.getApprovedNumberCard(),
                date.getMonth(),
                date.getYear(),
                DataHelper.getRandomNumbers(),
                DataHelper.getValidCode());
        buyInCreditPage.clickBuy();
        buyInCreditPage.getErrorFormat();

        Assertions.assertTrue(DataBaseHelper.DataBaseIsEmpty());
    }

    @Test
    @DisplayName("поле Владелец заполнено 27 символами")
    void shouldBeNoticeIfHolderInLongName(){

        var buyInCreditPage = new BuyInCreditPage();
        var date = DataHelper.getValidDate();
        buyInCreditPage.cardDataEntry(
                DataHelper.getApprovedNumberCard(),
                date.getMonth(),
                date.getYear(),
                DataHelper.getLongName(),
                DataHelper.getValidCode());
        buyInCreditPage.clickBuy();
        buyInCreditPage.getErrorFormat();

        Assertions.assertTrue(DataBaseHelper.DataBaseIsEmpty());
    }
}
