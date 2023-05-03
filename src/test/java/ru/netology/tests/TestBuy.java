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
    @DisplayName("Should successfully pay")
    void shouldSuccessfullyPay(){

        var buyPage = new BuyPage();
        buyPage.cardDataEntry(DataHelper.approvedNumberCard().getNumberCard(),
                DataHelper.validDate().getMonth(),
                DataHelper.validDate().getYear(),
                DataHelper.validName().getName(),
                DataHelper.validCode().getCode());
        buyPage.clickBuy();
        buyPage.getNotificationOk();
    }



}
