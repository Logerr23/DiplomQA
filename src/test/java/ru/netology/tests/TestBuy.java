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
                DataHelper.approvedNumberCard().getNumberCard(),
                date.getMonth(),
                date.getYear(),
                DataHelper.validName().getName(),
                DataHelper.validCode().getCode());
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
                DataHelper.declinedNumberCard().getNumberCard(),
                date.getMonth(),
                date.getYear(),
                DataHelper.validName().getName(),
                DataHelper.validCode().getCode());
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
                DataHelper.wrongNumberCard().getNumberCard(),
                date.getMonth(),
                date.getYear(),
                DataHelper.validName().getName(),
                DataHelper.validCode().getCode());
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
                DataHelper.shortNumberCard().getNumberCard(),
                date.getMonth(),
                date.getYear(),
                DataHelper.validName().getName(),
                DataHelper.validCode().getCode());
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
                DataHelper.empty().getWrongData(),
                date.getMonth(),
                date.getYear(),
                DataHelper.validName().getName(),
                DataHelper.validCode().getCode());
        buyPage.clickBuy();
        buyPage.getErrorEmpty();

        Assertions.assertTrue(DataBaseHelper.DataBaseIsEmpty());
    }

    @Test
    @DisplayName("Неверный формат месяца (1 цифра)")
    void shouldBeNoticeIfMonthIsShort(){

        var buyPage = new BuyPage();
        buyPage.cardDataEntry(
                DataHelper.approvedNumberCard().getNumberCard(),
                DataHelper.oneNumber().getWrongData(),
                DataHelper.getValidDate().getYear(),
                DataHelper.validName().getName(),
                DataHelper.validCode().getCode());
        buyPage.clickBuy();
        buyPage.getErrorFormat();

        Assertions.assertTrue(DataBaseHelper.DataBaseIsEmpty());
    }

    @Test
    @DisplayName("поле Месяц не заполнен")
    void shouldBeNoticeIfMonthIsEmpty(){

        var buyPage = new BuyPage();
        buyPage.cardDataEntry(
                DataHelper.approvedNumberCard().getNumberCard(),
                DataHelper.empty().getWrongData(),
                DataHelper.getValidDate().getYear(),
                DataHelper.validName().getName(),
                DataHelper.validCode().getCode());
        buyPage.clickBuy();
        buyPage.getErrorEmpty();

        Assertions.assertTrue(DataBaseHelper.DataBaseIsEmpty());
    }

    @Test
    @DisplayName("в поле Месяц введено '00'")
    void shouldBeNoticeIfMonthIsWrong(){

        var buyPage = new BuyPage();
        buyPage.cardDataEntry(
                DataHelper.approvedNumberCard().getNumberCard(),
                DataHelper.zeros().getWrongData(),
                DataHelper.getValidDate().getYear(),
                DataHelper.validName().getName(),
                DataHelper.validCode().getCode());
        buyPage.clickBuy();
        buyPage.getErrorWrongDateCard();

        Assertions.assertTrue(DataBaseHelper.DataBaseIsEmpty());
    }

    @Test
    @DisplayName("Неверный формат года (1 цифра)")
    void shouldBeNoticeIfYearIsShort(){

        var buyPage = new BuyPage();
        buyPage.cardDataEntry(
                DataHelper.approvedNumberCard().getNumberCard(),
                DataHelper.getValidDate().getMonth(),
                DataHelper.oneNumber().getWrongData(),
                DataHelper.validName().getName(),
                DataHelper.validCode().getCode());
        buyPage.clickBuy();
        buyPage.getErrorFormat();

        Assertions.assertTrue(DataBaseHelper.DataBaseIsEmpty());
    }

    @Test
    @DisplayName("поле Год не заполнен")
    void shouldBeNoticeIfYearIsEmpty(){

        var buyPage = new BuyPage();
        buyPage.cardDataEntry(
                DataHelper.approvedNumberCard().getNumberCard(),
                DataHelper.getValidDate().getMonth(),
                DataHelper.empty().getWrongData(),
                DataHelper.validName().getName(),
                DataHelper.validCode().getCode());
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
                DataHelper.approvedNumberCard().getNumberCard(),
                date.getMonth(),
                date.getYear(),
                DataHelper.validName().getName(),
                DataHelper.validCode().getCode());
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
                DataHelper.approvedNumberCard().getNumberCard(),
                date.getMonth(),
                date.getYear(),
                DataHelper.validName().getName(),
                DataHelper.validCode().getCode());
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
                DataHelper.approvedNumberCard().getNumberCard(),
                date.getMonth(),
                date.getYear(),
                DataHelper.validName().getName(),
                DataHelper.shortCode().getCode());
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
                DataHelper.approvedNumberCard().getNumberCard(),
                date.getMonth(),
                date.getYear(),
                DataHelper.validName().getName(),
                DataHelper.empty().getWrongData());
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
                DataHelper.approvedNumberCard().getNumberCard(),
                date.getMonth(),
                date.getYear(),
                DataHelper.empty().getWrongData(),
                DataHelper.validCode().getCode());
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
                DataHelper.approvedNumberCard().getNumberCard(),
                date.getMonth(),
                date.getYear(),
                DataHelper.cyrillicName().getName(),
                DataHelper.validCode().getCode());
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
                DataHelper.approvedNumberCard().getNumberCard(),
                date.getMonth(),
                date.getYear(),
                DataHelper.multipleSpaces().getWrongData(),
                DataHelper.validCode().getCode());
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
                DataHelper.approvedNumberCard().getNumberCard(),
                date.getMonth(),
                date.getYear(),
                DataHelper.specialSymbols().getWrongData(),
                DataHelper.validCode().getCode());
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
                DataHelper.approvedNumberCard().getNumberCard(),
                date.getMonth(),
                date.getYear(),
                DataHelper.randomNumbers().getWrongData(),
                DataHelper.validCode().getCode());
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
                DataHelper.approvedNumberCard().getNumberCard(),
                date.getMonth(),
                date.getYear(),
                DataHelper.longName().getName(),
                DataHelper.validCode().getCode());
        buyPage.clickBuy();
        buyPage.getErrorFormat();

        Assertions.assertTrue(DataBaseHelper.DataBaseIsEmpty());
    }
}
