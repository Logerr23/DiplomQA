package ru.netology.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;


public class BuyPage extends FormPage {
    private SelenideElement heading = $x("//h3[text()='Оплата по карте']");

    public BuyPage(){
        heading.shouldBe(visible);
    }

}
