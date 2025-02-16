package ru.netology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.SneakyThrows;
import org.junit.jupiter.api.*;
import ru.netology.page.TravellingOfDay;

import java.util.concurrent.TimeUnit;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.data.DataHelper.*;
import static ru.netology.data.SQLHelper.*;

public class TravellingOfDayTest {

    @BeforeAll
    public static void setupAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @BeforeEach
    public void openPage() {
        open("http://localhost:8080");
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
        cleanDatabase();
    }

    @Nested
    public class ValidCard {

        @Test
        @SneakyThrows
        @DisplayName("Оплата по карте с валидными данными")
        public void paymentValidCard() {
            var travellingOfDay = new TravellingOfDay();
            travellingOfDay.cardPayment();
            var info = getApprovedCard();
            travellingOfDay.sendingDate(info);
            TimeUnit.SECONDS.sleep(10);
            var expected = "APPROVED";
            var payment = getPayment();
            var order = getOrder();
            assertEquals(expected, payment.getStatus());
            assertEquals(payment.getTransaction_id(), order.getPayment_id());
            travellingOfDay.bankApproved();
        }

        @Test
        @SneakyThrows
        @DisplayName("Получение кредита по карте с валидными данными")
        public void creditValidCard() {
            var travellingOfDay = new TravellingOfDay();
            travellingOfDay.cardCredit();
            var info = getApprovedCard();
            travellingOfDay.sendingDate(info);
            TimeUnit.SECONDS.sleep(10);
            var expected = "APPROVED";
            var credit = getCreditRequest();
            var order = getOrder();
            assertEquals(expected, credit.getStatus());
            assertEquals(credit.getBank_id(), order.getCredit_id());
            travellingOfDay.bankApproved();
        }
    }

    @Nested
    public class InValidCard {

        @Test
    @SneakyThrows
    @DisplayName("Оплата по карте с невалидными данными")
    public void paymentInvalidCard() {
        var travellingOfDay = new TravellingOfDay();
        travellingOfDay.cardPayment();
        var info = getDeclined();
        travellingOfDay.sendingDate(info);
        TimeUnit.SECONDS.sleep(10);
        var expected = "DECLINED";
        var payment = getPayment();
        var order = getOrder();
        assertEquals(expected, payment.getStatus());
        assertEquals(payment.getTransaction_id(), order.getPayment_id());
        travellingOfDay.bankDeclined();
    }

    @Test
    @SneakyThrows
    @DisplayName("Кредит по карте с невалидными данными")
    public void creditInvalidCard() {
        var travellingOfDay = new TravellingOfDay();
        travellingOfDay.cardCredit();
        var info = getDeclined();
        travellingOfDay.sendingDate(info);
        TimeUnit.SECONDS.sleep(10);
        var expected = "DECLINED";
        var credit = getCreditRequest();
        var order = getOrder();
        assertEquals(expected, credit.getStatus());
        assertEquals(credit.getBank_id(), order.getCredit_id());
        travellingOfDay.bankDeclined();
    }
}

    @Nested
    public class NegativeTestsForPayment {

        @BeforeEach
        public void setPayment() {
            var travellingOfDay = new TravellingOfDay();
            travellingOfDay.cardPayment();
        }

        @Test
        @DisplayName("Отправка пустой формы на оплату")
        public void emptyForm() {
            var travellingOfDay = new TravellingOfDay();
            travellingOfDay.emptyForm();
        }

        @Test
        @DisplayName("Отправка формы с пустым полем 'Номер карты' на оплату")
        public void emptyFieldNumberCard() {
            var travellingOfDay = new TravellingOfDay();
            var info = getApprovedCard();
            travellingOfDay.emptyCardNumber(info);
        }

        @Test
        @DisplayName("Отправка формы с неполным номером карты на оплату")
        public void incompleteNumber() {
            var travellingOfDay = new TravellingOfDay();
            var info = getCardWithIncompleteNumber();
            travellingOfDay.invalidCardNumber(info);
        }

        @Test
        @DisplayName("Отправка формы с пустым полем 'Месяц' на оплату")
        public void emptyFieldMonth() {
            var travellingOfDay = new TravellingOfDay();
            var info = getApprovedCard();
            travellingOfDay.emptyMonth(info);
        }

        @Test
        @DisplayName("Отправка формы с просроченным месяцем на оплату")
        public void overdueMonth() {
            var travellingOfDay = new TravellingOfDay();
            var info = getCardWithOverdueMonth();
            travellingOfDay.invalidMonth(info);
        }

        @Test
        @DisplayName("Отправка формы с полем 'Месяц' со значением ниже граничного на оплату")
        public void lessMonth() {
            var travellingOfDay = new TravellingOfDay();
            var info = getCardWithLessMonth();
            travellingOfDay.invalidMonth(info);
        }

        @Test
        @DisplayName("Отправка формы с полем 'Месяц' со значением выше граничного на оплату")
        public void moreMonth() {
            var travellingOfDay = new TravellingOfDay();
            var info = getCardWithMoreMonth();
            travellingOfDay.invalidMonth(info);
        }

        @Test
        @DisplayName("Отправка формы с пустым полем 'Год' на оплату")
        public void emptyFieldYear() {
            var travellingOfDay = new TravellingOfDay();
            var info = getApprovedCard();
            travellingOfDay.emptyYear(info);
        }

        @Test
        @DisplayName("Отправка формы с просроченным годом на оплату")
        public void overdueYear() {
            var travellingOfDay = new TravellingOfDay();
            var info = getCardWithOverdueYear();
            travellingOfDay.invalidYear(info);
        }

        @Test
        @DisplayName("Отправка формы с годом превышающим срок действия карты на оплату")
        public void futureYear() {
            var travellingOfDay = new TravellingOfDay();
            var info = getCardWithYearOfFuture();
            travellingOfDay.invalidYear(info);
        }

        @Test
        @DisplayName("Отправка формы с пустым полем 'Владелец' на оплату")
        public void emptyFieldOwner() {
            var travellingOfDay = new TravellingOfDay();
            var info = getApprovedCard();
            travellingOfDay.emptyOwner(info);
        }

        @Test
        @DisplayName("Отправка формы с пробелом в поле 'Владелец' на оплату")
        public void ownerWithSpace() {
            var travellingOfDay = new TravellingOfDay();
            var info = getCardWithSpaceInFieldOwner();
            travellingOfDay.invalidOwner(info);
        }

        @Test
        @DisplayName("Отправка формы со спецсимволами в поле 'Владелец' на оплату")
        public void ownerWithSymbols() {
            var travellingOfDay = new TravellingOfDay();
            var info = getCardWithSimbolsInFieldOwner();
            travellingOfDay.invalidOwner(info);
        }

        @Test
        @DisplayName("Отправка формы с числовым значением в поле 'Владелец' на оплату")
        public void ownerWithNumber() {
            var travellingOfDay = new TravellingOfDay();
            var info = getCardWithNumberInFieldOwner();
            travellingOfDay.invalidOwner(info);
        }

        @Test
        @DisplayName("Отправка формы с пустым полем 'CVC/CVV' на оплату")
        public void emptyFieldCVC() {
            var travellingOfDay = new TravellingOfDay();
            var info = getApprovedCard();
            travellingOfDay.emptyCVC(info);
        }

        @Test
        @DisplayName("Отправка формы с неполным номером CVC на оплату")
        public void incompleteCVC() {
            var travellingOfDay = new TravellingOfDay();
            var info = getCardWithIncompleteCVC();
            travellingOfDay.invalidCVC(info);
        }
    }

    @Nested
    public class NegativeTestsForCredit {

        @BeforeEach
        public void setCredit() {
            var travellingOfDay = new TravellingOfDay();
            travellingOfDay.cardCredit();
        }

        @Test
        @DisplayName("Отправка пустой формы для кредита")
        public void emptyForm() {
            var travellingOfDay = new TravellingOfDay();
            travellingOfDay.emptyForm();
        }

        @Test
        @DisplayName("Отправка формы с пустым полем 'Номер карты' для кредита")
        public void emptyFieldNumberCard() {
            var travellingOfDay = new TravellingOfDay();
            var info = getApprovedCard();
            travellingOfDay.emptyCardNumber(info);
        }

        @Test
        @DisplayName("Отправка формы с неполным номером карты для кредита")
        public void incompleteNumber() {
            var travellingOfDay = new TravellingOfDay();
            var info = getCardWithIncompleteNumber();
            travellingOfDay.invalidCardNumber(info);
        }

        @Test
        @DisplayName("Отправка формы с пустым полем 'Месяц' для кредита")
        public void emptyFieldMonth() {
            var travellingOfDay = new TravellingOfDay();
            var info = getApprovedCard();
            travellingOfDay.emptyMonth(info);
        }

        @Test
        @DisplayName("Отправка формы с просроченным месяцем для кредита")
        public void overdueMonth() {
            var travellingOfDay = new TravellingOfDay();
            var info = getCardWithOverdueMonth();
            travellingOfDay.invalidMonth(info);
        }

        @Test
        @DisplayName("Отправка формы с полем 'Месяц' со значением ниже граничного для кредита")
        public void lessMonth() {
            var travellingOfDay = new TravellingOfDay();
            var info = getCardWithLessMonth();
            travellingOfDay.invalidMonth(info);
        }

        @Test
        @DisplayName("Отправка формы с полем 'Месяц' со значением выше граничного для кредита")
        public void moreMonth() {
            var travellingOfDay = new TravellingOfDay();
            var info = getCardWithMoreMonth();
            travellingOfDay.invalidMonth(info);
        }

        @Test
        @DisplayName("Отправка формы с пустым полем 'Год' для кредита")
        public void emptyFieldYear() {
            var travellingOfDay = new TravellingOfDay();
            var info = getApprovedCard();
            travellingOfDay.emptyYear(info);
        }

        @Test
        @DisplayName("Отправка формы с просроченным годом для кредита")
        public void overdueYear() {
            var travellingOfDay = new TravellingOfDay();
            var info = getCardWithOverdueYear();
            travellingOfDay.invalidYear(info);
        }

        @Test
        @DisplayName("Отправка формы с годом превышающим срок действия карты для кредита")
        public void futureYear() {
            var travellingOfDay = new TravellingOfDay();
            var info = getCardWithYearOfFuture();
            travellingOfDay.invalidYear(info);
        }

        @Test
        @DisplayName("Отправка формы с пустым полем 'Владелец' для кредита")
        public void emptyFieldOwner() {
            var travellingOfDay = new TravellingOfDay();
            var info = getApprovedCard();
            travellingOfDay.emptyOwner(info);
        }

        @Test
        @DisplayName("Отправка формы с пробелом в поле 'Владелец' для кредита")
        public void ownerWithSpace() {
            var travellingOfDay = new TravellingOfDay();
            var info = getCardWithSpaceInFieldOwner();
            travellingOfDay.invalidOwner(info);
        }

        @Test
        @DisplayName("Отправка формы со спецсимволами в поле 'Владелец' для кредита")
        public void ownerWithSymbols() {
            var travellingOfDay = new TravellingOfDay();
            var info = getCardWithSimbolsInFieldOwner();
            travellingOfDay.invalidOwner(info);
        }

        @Test
        @DisplayName("Отправка формы с числовым значением в поле 'Владелец' для кредита")
        public void ownerWithNumber() {
            var travellingOfDay = new TravellingOfDay();
            var info = getCardWithNumberInFieldOwner();
            travellingOfDay.invalidOwner(info);
        }

        @Test
        @DisplayName("Отправка формы с пустым полем 'CVC/CVV' для кредита")
        public void emptyFieldCVC() {
            var travellingOfDay = new TravellingOfDay();
            var info = getApprovedCard();
            travellingOfDay.emptyCVC(info);
        }

        @Test
        @DisplayName("Отправка формы с неполным номером CVC для кредита")
        public void incompleteCVC() {
            var travellingOfDay = new TravellingOfDay();
            var info = getCardWithIncompleteCVC();
            travellingOfDay.invalidCVC(info);
        }
    }
}

