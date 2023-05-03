package ru.netology.page;

import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class FormPage {
    private SelenideElement cardField = $x("//span[text()='Номер карты']/following::span/input");
    private SelenideElement monthField = $x("//span[text()='Месяц']/following::span/input");
    private SelenideElement yearField = $x("//span[text()='Год']/following::span/input");
    private SelenideElement nameField = $x("//span[text()='Владелец']/following::span/input");
    private SelenideElement codeField = $x("//span[text()='CVC/CVV']/following::span/input");

    private SelenideElement paymentButton = $x("//span[text()='Продолжить']");
    private SelenideElement paymentButtonNotification = $x("//span[text()='Отправляем запрос в Банк...']");


    private SelenideElement errorEmpty = $x("//span[text()='Поле обязательно для заполнения']");
    private SelenideElement errorFormat = $x("//span[text()='Неверный формат']");
    private SelenideElement errorCardExpired = $x("//span[text()='Истёк срок действия карты']");
    private SelenideElement errorWrongDateCard = $x("//span[text()='Неверно указан срок действия карты']");

    private SelenideElement notificationError = $(".notification_status_error");
    private SelenideElement notificationOk = $(".notification_status_ok");


    public void cardDataEntry(String card, String month, String year, String name, String code){
        cardField.setValue(card);
        monthField.setValue(month);
        yearField.setValue(year);
        nameField.setValue(name);
        codeField.setValue(code);
    }

    public void clickBuy(){
        paymentButton.click();
        paymentButtonNotification.shouldBe(visible);
    }


    public void getNotificationError(){
        notificationError.shouldBe(visible, Duration.ofSeconds(15));
    }
    public void getNotificationOk(){
        notificationOk.shouldBe(visible, Duration.ofSeconds(15));
    }


    public void getErrorEmpty(){
        errorEmpty.shouldBe(visible);
    }
    public void getErrorFormat(){
        errorFormat.shouldBe(visible);
    }
    public void getErrorCardExpired(){
        errorCardExpired.shouldBe(visible);
    }
    public void getErrorWrongDateCard(){
        errorWrongDateCard.shouldBe(visible);
    }

}
