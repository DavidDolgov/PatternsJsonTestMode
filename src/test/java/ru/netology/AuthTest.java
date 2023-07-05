package ru.netology;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.*;

class AuthTest {

    DataGenerator info = new DataGenerator();

    @BeforeEach
    void setUp() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");
    }

    @Test
    @DisplayName("Should successfully login with active registered user")
    void shouldSuccessfulLoginIfRegisteredActiveUser() {
        DataName registration = DataGenerator.getNewUser("active");
        $("[data-test-id=login] input").setValue(registration.getLogin());
        $("[data-test-id=password] input").setValue(registration.getPassword());
        $(".button").click();
        $x("//h2[contains(text(), 'Личный кабинет')]").should(Condition.appear);
    }

    @Test
    @DisplayName("Should get error message if login with blocked registered user")
    void shouldGetErrorIfBlockedUser() {
        DataName registration = DataGenerator.getNewUser("blocked");
        $("[data-test-id=login] input").setValue(registration.getLogin());
        $("[data-test-id=password] input").setValue(registration.getPassword());
        $(".button").click();
        $(".notification__content").shouldHave(Condition.exactText("Ошибка! " + "Пользователь заблокирован"));
    }

    @Test
    @DisplayName("Should get error message if login with not registered user")
    void shouldGetErrorIfNotRegisteredUser() {
        DataName registration = DataGenerator.getNewUser("active");
        $("[data-test-id=login] input").setValue(info.randomName("en"));
        $("[data-test-id=password] input").setValue(info.randomPassword("en"));
        $(".button").click();
        $("[data-test-id=error-notification] .notification__content").shouldHave(Condition.exactText("Ошибка! " + "Неверно указан логин или пароль"));
    }

    @Test
    @DisplayName("Should get error message if login with wrong login")
    void shouldGetErrorIfWrongLogin() {
        DataName registration = DataGenerator.getNewUser("active");
        $("[data-test-id=login] input").setValue(info.randomName("en"));
        $("[data-test-id=password] input").setValue(registration.getPassword());
        $(".button").click();
        $("[data-test-id=error-notification] .notification__content").shouldHave(Condition.exactText("Ошибка! " + "Неверно указан логин или пароль"));
    }

    @Test
    @DisplayName("Should get error message if login with wrong password")
    void shouldGetErrorIfWrongPassword() {
        DataName registration = DataGenerator.getNewUser("active");
        $("[data-test-id=login] input").setValue(registration.getLogin());
        $("[data-test-id=password] input").setValue(info.randomPassword("en"));
        $(".button").click();
        $("[data-test-id=error-notification] .notification__content").shouldHave(Condition.exactText("Ошибка! " + "Неверно указан логин или пароль"));

    }
}