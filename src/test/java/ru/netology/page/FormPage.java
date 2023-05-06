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

    private SelenideElement notificationErrorCloser= $(".notification_status_error button");



    public void cardDataEntry(String card, String month, String year, String name, String code){
        cardField.setValue(card);
        monthField.setValue(month);
        yearField.setValue(year);
        nameField.setValue(name);
        codeField.setValue(code);
    }

    public void clickBuy(){
        paymentButton.click();
    }
    public void buttonNotificationVisible(){
        paymentButtonNotification.shouldBe(visible);
    }


    public void notificationErrorVisible(){
        notificationError.shouldBe(visible, Duration.ofSeconds(20));
    }

    public void notificationErrorNotVisible(){
        notificationError.shouldNot(visible, Duration.ofSeconds(20));
    }

    public void notificationErrorClose(){
        notificationErrorCloser.click();
    }

    public void notificationOkVisible(){
        notificationOk.shouldBe(visible, Duration.ofSeconds(20));
    }

    public void notificationOkNotVisible(){
        notificationOk.shouldNot(visible, Duration.ofSeconds(20));
    }






    public void getErrorEmpty(){
        errorEmpty.shouldBe(visible);

        errorFormat.shouldNot(visible);
        errorCardExpired.shouldNot(visible);
        errorWrongDateCard.shouldNot(visible);
    }
    public void getErrorFormat(){
        errorFormat.shouldBe(visible);

        errorEmpty.shouldNot(visible);
        errorCardExpired.shouldNot(visible);
        errorWrongDateCard.shouldNot(visible);
    }
    public void getErrorCardExpired(){
        errorCardExpired.shouldBe(visible);

        errorFormat.shouldNot(visible);
        errorEmpty.shouldNot(visible);
        errorWrongDateCard.shouldNot(visible);
    }
    public void getErrorWrongDateCard(){
        errorWrongDateCard.shouldBe(visible);

        errorFormat.shouldNot(visible);
        errorCardExpired.shouldNot(visible);
        errorEmpty.shouldNot(visible);
    }

}
