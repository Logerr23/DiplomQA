package ru.netology.tests;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataBase;
import ru.netology.data.DataHelper;
import ru.netology.page.BuyPage;
import ru.netology.page.StartPage;

import static com.codeborne.selenide.Selenide.open;

public class TestBuy {

    @BeforeEach
    void setup(){
        Configuration.holdBrowserOpen = true;
        var startPage = open("http://localhost:8080", StartPage.class);
        startPage.buyPage();
    }

    @AfterEach
    public void clearDataBase(){
        DataBase.clearDataBase();
    }


    @Test
    @DisplayName("Успешная оплата")
    void shouldSuccessfullyPay(){

        var buyPage = new BuyPage();
        buyPage.cardDataEntry(
                DataHelper.approvedNumberCard().getNumberCard(),
                DataHelper.validDate().getMonth(),
                DataHelper.validDate().getYear(),
                DataHelper.validName().getName(),
                DataHelper.validCode().getCode());
        buyPage.clickBuy();
        buyPage.buttonNotificationVisible();
        buyPage.notificationOkVisible();
    }

    @Test
    @DisplayName("Попытка оплатить заблокированной картой")
    void shouldTryToPayWithDeclinedCard(){

        var buyPage = new BuyPage();
        buyPage.cardDataEntry(
                DataHelper.declinedNumberCard().getNumberCard(),
                DataHelper.validDate().getMonth(),
                DataHelper.validDate().getYear(),
                DataHelper.validName().getName(),
                DataHelper.validCode().getCode());
        buyPage.clickBuy();
        buyPage.buttonNotificationVisible();
        buyPage.notificationErrorVisible();
    }

    @Test
    @DisplayName("Короткий номер карты")
    void shouldBeNoticeIfNumberCardIsShort(){

        var buyPage = new BuyPage();
        buyPage.cardDataEntry(
                DataHelper.shortNumberCard().getNumberCard(),
                DataHelper.validDate().getMonth(),
                DataHelper.validDate().getYear(),
                DataHelper.validName().getName(),
                DataHelper.validCode().getCode());
        buyPage.clickBuy();
        buyPage.getErrorFormat();
    }

    @Test
    @DisplayName("поле Номер карты не заполнен")
    void shouldBeNoticeIfNumberCardIsEmpty(){

        var buyPage = new BuyPage();
        buyPage.cardDataEntry(
                DataHelper.empty().getWrongData(),
                DataHelper.validDate().getMonth(),
                DataHelper.validDate().getYear(),
                DataHelper.validName().getName(),
                DataHelper.validCode().getCode());
        buyPage.clickBuy();
        buyPage.getErrorEmpty();
    }

    @Test
    @DisplayName("Неверный формат месяца (1 цифра)")
    void shouldBeNoticeIfMonthIsShort(){

        var buyPage = new BuyPage();
        buyPage.cardDataEntry(
                DataHelper.approvedNumberCard().getNumberCard(),
                DataHelper.oneNumber().getWrongData(),
                DataHelper.validDate().getYear(),
                DataHelper.validName().getName(),
                DataHelper.validCode().getCode());
        buyPage.clickBuy();
        buyPage.getErrorFormat();
    }

    @Test
    @DisplayName("поле Месяц не заполнен")
    void shouldBeNoticeIfMonthIsEmpty(){

        var buyPage = new BuyPage();
        buyPage.cardDataEntry(
                DataHelper.approvedNumberCard().getNumberCard(),
                DataHelper.empty().getWrongData(),
                DataHelper.validDate().getYear(),
                DataHelper.validName().getName(),
                DataHelper.validCode().getCode());
        buyPage.clickBuy();
        buyPage.getErrorEmpty();
    }

    @Test
    @DisplayName("Неверный формат года (1 цифра)")
    void shouldBeNoticeIfYearIsShort(){

        var buyPage = new BuyPage();
        buyPage.cardDataEntry(
                DataHelper.approvedNumberCard().getNumberCard(),
                DataHelper.validDate().getMonth(),
                DataHelper.oneNumber().getWrongData(),
                DataHelper.validName().getName(),
                DataHelper.validCode().getCode());
        buyPage.clickBuy();
        buyPage.getErrorFormat();
    }

    @Test
    @DisplayName("поле Год не заполнен")
    void shouldBeNoticeIfYearIsEmpty(){

        var buyPage = new BuyPage();
        buyPage.cardDataEntry(
                DataHelper.approvedNumberCard().getNumberCard(),
                DataHelper.validDate().getMonth(),
                DataHelper.empty().getWrongData(),
                DataHelper.validName().getName(),
                DataHelper.validCode().getCode());
        buyPage.clickBuy();
        buyPage.getErrorEmpty();
    }

    @Test
    @DisplayName("Карта просрочена")
    void shouldBeNoticeIfCardExpired(){

        var buyPage = new BuyPage();
        buyPage.cardDataEntry(
                DataHelper.approvedNumberCard().getNumberCard(),
                DataHelper.pastDate().getMonth(),
                DataHelper.pastDate().getYear(),
                DataHelper.validName().getName(),
                DataHelper.validCode().getCode());
        buyPage.clickBuy();
        buyPage.getErrorCardExpired();
    }

    @Test
    @DisplayName("Неверно указан срок действия карты (больше пяти лет от текущей)")
    void shouldBeNoticeIfOverFiveYearsDate(){

        var buyPage = new BuyPage();
        buyPage.cardDataEntry(
                DataHelper.approvedNumberCard().getNumberCard(),
                DataHelper.overFiveYearsDate().getMonth(),
                DataHelper.overFiveYearsDate().getYear(),
                DataHelper.validName().getName(),
                DataHelper.validCode().getCode());
        buyPage.clickBuy();
        buyPage.getErrorWrongDateCard();
    }

    @Test
    @DisplayName("CVV/CVC короткий")
    void shouldBeNoticeIfCVCisShort(){

        var buyPage = new BuyPage();
        buyPage.cardDataEntry(
                DataHelper.approvedNumberCard().getNumberCard(),
                DataHelper.validDate().getMonth(),
                DataHelper.validDate().getYear(),
                DataHelper.validName().getName(),
                DataHelper.shortCode().getCode());
        buyPage.clickBuy();
        buyPage.getErrorFormat();
    }

    @Test
    @DisplayName("CVV/CVC не заполнен")
    void shouldBeNoticeIfCVCIsEmpty(){

        var buyPage = new BuyPage();
        buyPage.cardDataEntry(
                DataHelper.approvedNumberCard().getNumberCard(),
                DataHelper.validDate().getMonth(),
                DataHelper.validDate().getYear(),
                DataHelper.validName().getName(),
                DataHelper.empty().getWrongData());
        buyPage.clickBuy();
        buyPage.getErrorEmpty();
    }

    @Test
    @DisplayName("поле Владелец не заполнено")
    void shouldBeNoticeIfHolderIsEmpty(){

        var buyPage = new BuyPage();
        buyPage.cardDataEntry(
                DataHelper.approvedNumberCard().getNumberCard(),
                DataHelper.validDate().getMonth(),
                DataHelper.validDate().getYear(),
                DataHelper.empty().getWrongData(),
                DataHelper.validCode().getCode());
        buyPage.clickBuy();
        buyPage.getErrorEmpty();
    }

    @Test
    @DisplayName("поле Владелец заполнено на кириллице")
    void shouldBeNoticeIfHolderInСyrillic(){

        var buyPage = new BuyPage();
        buyPage.cardDataEntry(
                DataHelper.approvedNumberCard().getNumberCard(),
                DataHelper.validDate().getMonth(),
                DataHelper.validDate().getYear(),
                DataHelper.cyrillicName().getName(),
                DataHelper.validCode().getCode());
        buyPage.clickBuy();
        buyPage.getErrorFormat();
    }

    @Test
    @DisplayName("поле Владелец заполнено пробелами")
    void shouldBeNoticeIfHolderInSpaces(){

        var buyPage = new BuyPage();
        buyPage.cardDataEntry(
                DataHelper.approvedNumberCard().getNumberCard(),
                DataHelper.validDate().getMonth(),
                DataHelper.validDate().getYear(),
                DataHelper.multipleSpaces().getWrongData(),
                DataHelper.validCode().getCode());
        buyPage.clickBuy();
        buyPage.getErrorEmpty();
    }

    @Test
    @DisplayName("поле Владелец заполнено спецсимволами")
    void shouldBeNoticeIfHolderInSpecialSymbols(){

        var buyPage = new BuyPage();
        buyPage.cardDataEntry(
                DataHelper.approvedNumberCard().getNumberCard(),
                DataHelper.validDate().getMonth(),
                DataHelper.validDate().getYear(),
                DataHelper.multipleSpaces().getWrongData(),
                DataHelper.validCode().getCode());
        buyPage.clickBuy();
        buyPage.getErrorEmpty();
    }
}
