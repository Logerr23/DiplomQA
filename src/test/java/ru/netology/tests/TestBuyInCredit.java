package ru.netology.tests;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.BuyInCreditPage;
import ru.netology.page.StartPage;

import static com.codeborne.selenide.Selenide.open;

public class TestBuyInCredit {

    @BeforeEach
    void setup(){
        Configuration.holdBrowserOpen = true;
        var startPage = open("http://localhost:8080", StartPage.class);
        startPage.buyInCreditPage();
    }


    @Test
    @DisplayName("Must successfully pay")
    void shouldSuccessfullyPay(){

        var buyInCreditPage = new BuyInCreditPage();
        var date = DataHelper.getValidDate();
        buyInCreditPage.cardDataEntry(DataHelper.approvedNumberCard().getNumberCard(),
                date.getMonth(),
                date.getYear(),
                DataHelper.validName().getName(),
                DataHelper.validCode().getCode());
        buyInCreditPage.clickBuy();
        buyInCreditPage.buttonNotificationVisible();
        buyInCreditPage.notificationOkVisible();

    }
}
