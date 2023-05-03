package ru.netology.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;

public class BuyInCreditPage extends FormPage {
    private SelenideElement heading = $x("//h3[text()='Кредит по данным карты']");

    public BuyInCreditPage(){
        heading.shouldBe(visible);
    }
}
