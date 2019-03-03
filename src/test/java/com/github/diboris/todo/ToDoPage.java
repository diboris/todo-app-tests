package com.github.diboris.todo;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ToDoPage {

    private WebDriver driver;
    private WebDriverWait wait;

    public ToDoPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 15);
    }

    public void initialize() throws InterruptedException {
        By newToDo = By.id("new-todo");
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(newToDo));
        Thread.sleep(3000);
    }

    public void addTask(String text) throws InterruptedException {
        WebElement newToDoElement = driver.findElement(By.id("new-todo"));
        newToDoElement.sendKeys(text);
        Thread.sleep(1000);
        newToDoElement.sendKeys(Keys.ENTER);
        Thread.sleep(1000);
    }

    public String getFirstTaskText() {
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//ul[@id='todo-list']/li[1]")));
        WebElement addedTask = driver.findElement(By.xpath("//ul[@id='todo-list']/li[1]"));
        return addedTask.getText();
    }

    public void editFirstTask(String text) {
        WebElement input = driver.findElement(By.xpath("//ul[@id='todo-list']/li[1]"));
        Actions builder = new Actions(driver);
        Action mouseOverHome = builder
                .moveToElement(input)
                .doubleClick()
                .build();
        mouseOverHome.perform();
        WebElement inputEditActiveStatus = driver.findElement(By.xpath("//li[@class='active editing']/input"));
        inputEditActiveStatus.sendKeys(text);
        inputEditActiveStatus.sendKeys(Keys.ENTER);
    }

    public void clickActiveLink() {
        WebElement activeLink = driver.findElement(By.xpath("//a[@href='#/active']"));
        activeLink.click();
    }

    public boolean hasTasks() {
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//ul[@id='todo-list']/li")));
        return driver.findElements(By.xpath("//ul[@id='todo-list']/li")).size() > 0;
    }

    public void completeFirstTask() {
        WebElement checkTask = driver.findElement(By.xpath("//ul[@id='todo-list']/li[1]//input[@type='checkbox']"));
        checkTask.click();
    }

    public boolean hasNoTasks() {
        if (driver.findElements(By.xpath("//ul[@id='todo-list']/li")).size() == 0) {
            return true;
        }
        WebElement element = driver.findElement(By.xpath("//ul[@id='todo-list']/li"));
        return element.isDisplayed() == false;
    }

    public void clickCompleteLink() {
        WebElement completedLink = driver.findElement(By.xpath("//a[@href='#/completed']"));
        completedLink.click();
    }

    public void clickAllLink() {
        WebElement allLink = driver.findElement(By.xpath("//a[@href='#/']"));
        allLink.click();
    }

    public void deleteFirstTask() {
        WebElement deletingInput = driver.findElement(By.xpath("//ul[@id='todo-list']/li[1]//input[@type='checkbox']"));
        Actions builder = new Actions(driver);
        Action mouseOverHome = builder
                .moveToElement(deletingInput)
                .build();
        mouseOverHome.perform();
        WebElement destroyButton = driver.findElement(By.xpath("//button[@class='destroy']"));
        destroyButton.click();
    }
}
