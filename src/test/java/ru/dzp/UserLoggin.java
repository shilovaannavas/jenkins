package ru.dzp;

import com.microsoft.playwright.*;
import io.qameta.allure.Allure;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class UserLoggin {

    @Test
    @Tag("simple")
    void openSite() {
        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium()
                    .launch(new BrowserType.LaunchOptions().setHeadless(true));

            BrowserContext context = browser.newContext();
            Page page = context.newPage();

            Allure.step("Открываем сайт", () ->
                    page.navigate("https://my.dzp-test.devim.team/login")
            );

            page.waitForLoadState();

            Allure.step("Проверяем текст", () -> {
                assertThat(page.locator("body")).containsText("Мой текст");
            });

            Allure.step("Делаем скриншот", () -> {
                byte[] screenshot = page.screenshot();
                Allure.addAttachment("Screenshot", "image/png", new java.io.ByteArrayInputStream(screenshot), ".png");
            });
        }
    }
}
