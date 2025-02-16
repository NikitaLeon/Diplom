package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class TravellingOfDay {
    private SelenideElement heading = $$("h2").find(text("Путешествие дня"));
    private SelenideElement paybutton = $$("button").find(exactText("Купить"));
    private SelenideElement payheading = $$("h3").find(text("Оплата по карте"));
    private SelenideElement creditbutton = $$("button").find(exactText("Купить в кредит"));
    private SelenideElement creditheading = $$("h3").find(text("Кредит по данным карты"));
    private SelenideElement cardNumber = $(byText("Номер карты")).parent().$(".input__control");
    private SelenideElement cardNumberError = $(byText("Номер карты")).parent().$(".input__sub");
    private SelenideElement month = $(byText("Месяц")).parent().$(".input__control");
    private SelenideElement monthError = $(byText("Месяц")).parent().$(".input__sub");
    private SelenideElement year = $(byText("Год")).parent().$(".input__control");
    private SelenideElement yearError = $(byText("Год")).parent().$(".input__sub");
    private SelenideElement owner = $(byText("Владелец")).parent().$(".input__control");
    private SelenideElement ownerError = $(byText("Владелец")).parent().$(".input__sub");
    private SelenideElement cvc = $(byText("CVC/CVV")).parent().$(".input__control");
    private SelenideElement cvcError = $(byText("CVC/CVV")).parent().$(".input__sub");
    private SelenideElement continueButton = $$("button").find(exactText("Продолжить"));
    private SelenideElement notificationOk = $(byText("Успешно")).parent().$(".notification__title");
    private SelenideElement notificationError = $(".notification_status_error");

    public TravellingOfDay() {
        heading.shouldBe(visible);
    }

    public void cardPayment() {
        paybutton.click();
        payheading.shouldBe(visible);
    }

    public void cardCredit() {
        creditbutton.click();
        creditheading.shouldBe(visible);
    }

    public void emptyForm() {
        continueButton.click();
        cardNumberError.shouldBe(visible);
        monthError.shouldBe(visible);
        yearError.shouldBe(visible);
        ownerError.shouldBe(visible);
        cvcError.shouldBe(visible);
    }

    public void cardNumberErrorHidden() {
        monthError.shouldBe(hidden);
        yearError.shouldBe(hidden);
        ownerError.shouldBe(hidden);
        cvcError.shouldBe(hidden);
    }

    public void emptyCardNumber(DataHelper.CardInfo info) {
        month.setValue(info.getMonth());
        year.setValue(info.getYear());
        owner.setValue(info.getOwner());
        cvc.setValue(info.getCvc());
        continueButton.click();
        cardNumberError.shouldBe(visible);
        cardNumberErrorHidden();
    }

    public void monthErrorHidden() {
        cardNumberError.shouldBe(hidden);
        yearError.shouldBe(hidden);
        ownerError.shouldBe(hidden);
        cvcError.shouldBe(hidden);
    }

    public void emptyMonth(DataHelper.CardInfo info) {
        cardNumber.setValue(info.getNumberCard());
        year.setValue(info.getYear());
        owner.setValue(info.getOwner());
        cvc.setValue(info.getCvc());
        continueButton.click();
        monthError.shouldBe(visible);
        monthErrorHidden();
    }

    public void yearErrorHidden() {
        cardNumberError.shouldBe(hidden);
        monthError.shouldBe(hidden);
        ownerError.shouldBe(hidden);
        cvcError.shouldBe(hidden);
    }

    public void emptyYear(DataHelper.CardInfo info) {
        cardNumber.setValue(info.getNumberCard());
        month.setValue(info.getMonth());
        owner.setValue(info.getOwner());
        cvc.setValue(info.getCvc());
        continueButton.click();
        yearError.shouldBe(visible);
        yearErrorHidden();
    }

    public void ownerErrorHidden() {
        cardNumberError.shouldBe(hidden);
        monthError.shouldBe(hidden);
        yearError.shouldBe(hidden);
        cvcError.shouldBe(hidden);
    }

    public void emptyOwner(DataHelper.CardInfo info) {
        cardNumber.setValue(info.getNumberCard());
        month.setValue(info.getMonth());
        year.setValue(info.getYear());
        cvc.setValue(info.getCvc());
        continueButton.click();
        ownerError.shouldBe(visible);
        ownerErrorHidden();
    }

    public void cvcErrorHidden() {
        cardNumberError.shouldBe(hidden);
        monthError.shouldBe(hidden);
        yearError.shouldBe(hidden);
        ownerError.shouldBe(hidden);
    }

    public void emptyCVC(DataHelper.CardInfo info) {
        cardNumber.setValue(info.getNumberCard());
        month.setValue(info.getCvc());
        year.setValue(info.getYear());
        owner.setValue(info.getOwner());
        continueButton.click();
        cvcError.shouldBe(visible);
        cvcErrorHidden();
    }

    public void invalidCardNumber(DataHelper.CardInfo info) {
        cardNumber.setValue(info.getNumberCard());
        month.setValue(info.getMonth());
        year.setValue(info.getYear());
        owner.setValue(info.getOwner());
        cvc.setValue(info.getCvc());
        continueButton.click();
        cardNumberError.shouldBe(visible);
        cardNumberErrorHidden();
    }

    public void invalidMonth(DataHelper.CardInfo info) {
        cardNumber.setValue(info.getNumberCard());
        month.setValue(info.getMonth());
        year.setValue(info.getYear());
        owner.setValue(info.getOwner());
        cvc.setValue(info.getCvc());
        continueButton.click();
        monthError.shouldBe(visible);
        monthErrorHidden();
    }

    public void invalidYear(DataHelper.CardInfo info) {
        cardNumber.setValue(info.getNumberCard());
        month.setValue(info.getMonth());
        year.setValue(info.getYear());
        owner.setValue(info.getOwner());
        cvc.setValue(info.getCvc());
        continueButton.click();
        yearError.shouldBe(visible);
        yearErrorHidden();
    }

    public void invalidOwner(DataHelper.CardInfo info) {
        cardNumber.setValue(info.getNumberCard());
        month.setValue(info.getMonth());
        year.setValue(info.getYear());
        owner.setValue(info.getOwner());
        cvc.setValue(info.getCvc());
        continueButton.click();
        ownerError.shouldBe(visible);
        ownerErrorHidden();
    }

    public void invalidCVC(DataHelper.CardInfo info) {
        cardNumber.setValue(info.getNumberCard());
        month.setValue(info.getMonth());
        year.setValue(info.getYear());
        owner.setValue(info.getOwner());
        cvc.setValue(info.getCvc());
        continueButton.click();
        cvcError.shouldBe(visible);
        cvcErrorHidden();
    }

    public void sendingDate(DataHelper.CardInfo info) {
        cardNumber.setValue(info.getNumberCard());
        month.setValue(info.getMonth());
        year.setValue(info.getYear());
        owner.setValue(info.getOwner());
        cvc.setValue(info.getCvc());
        continueButton.click();
    }

    public void bankApproved() {
        notificationOk.shouldBe(visible);
    }

    public void bankDeclined() {
        notificationError.shouldBe(visible);
    }
}
