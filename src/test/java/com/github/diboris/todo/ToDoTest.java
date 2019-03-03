package com.github.diboris.todo;

import io.github.bonigarcia.wdm.ChromeDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.bridge.SLF4JBridgeHandler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ToDoTest {

    private static final boolean CLOSE_BROWSER_AFTER_TEST = true;

    private WebDriver driver;

    @BeforeClass
    public static void setupClass() {
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();
        ChromeDriverManager.getInstance().setup();
    }

    @Before
    public void setupTest() {
        driver = new ChromeDriver();
    }

    @After
    public void tearDown() {
        if (driver != null && CLOSE_BROWSER_AFTER_TEST) {
            driver.quit();
        }
    }

    @Test
    public void should_manage_task() throws InterruptedException {
        driver.get("https://todomvc4tasj.herokuapp.com/#/");

        ToDoPage toDoPage = new ToDoPage(driver);
        toDoPage.initialize();

        toDoPage.addTask("buy a");
        assertEquals("buy a", toDoPage.getFirstTaskText());

        toDoPage.editFirstTask(" dog");
        assertEquals("buy a dog", toDoPage.getFirstTaskText());

        toDoPage.clickActiveLink();
        assertTrue(toDoPage.hasTasks());

        toDoPage.completeFirstTask();
        assertTrue(toDoPage.hasNoTasks());

        toDoPage.clickCompleteLink();
        assertEquals("buy a dog", toDoPage.getFirstTaskText());

        toDoPage.clickAllLink();
        assertEquals("buy a dog", toDoPage.getFirstTaskText());

        toDoPage.deleteFirstTask();
        assertTrue(toDoPage.hasNoTasks());
    }
}
