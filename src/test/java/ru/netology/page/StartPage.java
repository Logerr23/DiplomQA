package ru.netology.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;

public class StartPage {

    private SelenideElement heading = $x("//h2[text()='Путешествие дня']");
    private SelenideElement buy = $x("//span[text()='Купить']");
    private SelenideElement buyInCredit = $x("//span[text()='Купить в кредит']");

    public StartPage(){
        heading.shouldBe(visible);
    }

    public BuyPage buyPage(){
        buy.click();
        return new BuyPage();
    }

    public BuyInCreditPage buyInCreditPage(){
        buyInCredit.click();
        return new BuyInCreditPage();
    }


}
