package org.example;

import org.example.Exceptions.CleanException;
import org.example.Exceptions.NotEnoughCoffeeException;
import org.example.Exceptions.NotEnoughMilkException;
import org.example.Exceptions.NotEnoughWaterException;

import java.util.LinkedList;

import static org.example.Menu.scanner;

public class CoffeeMachine {

    private static int water = 2000;
    private static int milk = 700;
    private static int coffee = 800;
    private static int cupOfCoffee = 0;
    private static int cupOfEspresso = 0;
    private static int cupOfCappuccino = 0;
    private static boolean powerOn = false;
    public final int MAX_WATER = 2000;
    public final int MAX_MILK = 700;
    public final int MAX_COFFEE = 800;
    private final int MAX_CUP_OF_COFFEE = 10;
    private static LinkedList<String> history = new LinkedList<>();
    public static LinkedList<Profile> profiles = new LinkedList<>();

    public int getWater() {
        return water;
    }

    public int getMilk() {
        return milk;
    }

    public int getCoffee() {
        return coffee;
    }

    public int getCupOfCoffee() {
        return cupOfCoffee;
    }

    public static int getCupOfEspresso() {
        return cupOfEspresso;
    }

    public static int getCupOfCappuccino() {
        return cupOfCappuccino;
    }

    public boolean getPowerOn() {
        return powerOn;
    }

    public void setWater(int water) {
        CoffeeMachine.water = water;
    }

    public void setMilk(int milk) {
        CoffeeMachine.milk = milk;
    }

    public void setCoffee(int coffee) {
        CoffeeMachine.coffee = coffee;
    }

    public void setCupOfCoffee(int cupOfCoffee) {
        CoffeeMachine.cupOfCoffee = cupOfCoffee;
    }

    public void setCupOfEspresso(int cupOfEspresso) {
        CoffeeMachine.cupOfEspresso = cupOfEspresso;
    }

    public void setCupOfCappuccino(int cupOfCappuccino) {
        CoffeeMachine.cupOfCappuccino = cupOfCappuccino;
    }

    public void setPowerOn(boolean powerOn) {
        CoffeeMachine.powerOn = powerOn;
    }

    public void onOffCoffeeMachine() {
        if (getPowerOn()) {
            setPowerOn(false);
            System.out.println("Кофемашина выключена");
            history.add("Кофемашина выключена");
        }
        else {
            setPowerOn(true);
            System.out.println("Кофемашина включена");
            history.add("Кофемашина включена");
        }
    }

    public void isOn() {
        if (getPowerOn()) {
            Menu.menuCoffee();
        }
        else {
            System.out.println("Включите кофемашину, чтобы приготовить кофе");
        }
    }

    public static void saveProfile(Profile profile) {
        profiles.add(profile);
    }

    public void checkCreateProfile(int amountEspresso, int amountCappuccino) {
        if (amountEspresso < 0) {
            System.out.println("\nНекорректный ввод");
            return;
        }
        else if (amountEspresso > 10) {
            System.out.println("\nПревышено количество порций");
            return;
        }
        if (amountCappuccino < 0) {
            System.out.println("\nНекорректный ввод");
            return;
        }
        else if (amountCappuccino + amountEspresso > 10) {
            System.out.println("\nПревышено количество порций");
            return;
        }
        history.add("Создан именной набор");
    }

    public void createProfile() {
        Profile profile;
        System.out.print("Введите имя набора: ");
        String name = scanner.nextLine();
        name = scanner.nextLine();
        System.out.print("Введите количество порций эспрессо: ");
        int amountEspresso = scanner.nextInt();
        if (amountEspresso < 0) {
            System.out.println("\nНекорректный ввод");
            return;
        }
        System.out.print("Введите количество порций капучино: ");
        int amountCappuccino = scanner.nextInt();
        if (amountCappuccino < 0) {
            System.out.println("\nНекорректный ввод");
            return;
        }
        checkCreateProfile(amountEspresso, amountCappuccino);
        profile = new Profile(name, amountEspresso, amountCappuccino);
        for (int i = 0; i < profiles.toArray().length; i++) {
            if (name.equals(profiles.get(i).getNameProfile())) {
                System.out.println("\nНабор с таким именем уже создан");
                return;
            }
        }
        try {
            CoffeeMachine.saveProfile(profile);
        }
        catch (IndexOutOfBoundsException ex) {
            System.out.println("\nНекорректный ввод");
            scanner.next();
        }
    }

    public void makeProfileCoffee(int number) {
        if (number > profiles.size()) {
            System.out.println("\nНекорректный ввод");
            return;
        }
        LinkedList<Profile> listProfiles = CoffeeMachine.profiles;
        Profile profile = listProfiles.get(number);
        int amountEspresso = profile.getAmountEspresso();
        int amountCappuccino = profile.getAmountCappuccino();

        makeProfileCupsOfCoffee(CoffeeRecipe.ESPRESSO, CoffeeRecipe.CAPPUCCINO, amountEspresso, amountCappuccino);
    }

    public void makeProfileCupsOfCoffee(CoffeeRecipe espresso, CoffeeRecipe cappuccino, int amountEspresso, int amountCappuccino) {
        if (getCupOfCoffee() + amountEspresso + amountCappuccino > MAX_CUP_OF_COFFEE) {
            System.out.println("\nКофемашина слишком грязная");
            return;
        }
        if (espresso.getWater() * amountEspresso + cappuccino.getWater() * amountCappuccino > getWater()) {
            System.out.println("\nНедостаточно воды");
            return;
        }
        if (espresso.getMilk() * amountEspresso + cappuccino.getMilk() * amountCappuccino > getMilk()) {
            System.out.println("\nНедостаточно молока");
            return;
        }
        if (espresso.getCoffee() * amountEspresso + cappuccino.getCoffee() * amountCappuccino > getCoffee()) {
            System.out.println("\nНедостаточно кофе");
            return;
        }
        makeCoffee(espresso, amountEspresso);
        makeCoffee(cappuccino, amountCappuccino);
        history.add("Приготовлено именной набор");
        System.out.println("Именной набор готов");
    }

    public void getIngredientsStatus() {
        System.out.printf("""
                Воды %d мл
                Молока %d мл
                Зёрен кофе %d г
                """,
                getWater(), getMilk(), getCoffee());
        history.add("Статус ингредиентов");
    }

    public void addWater(int amountWater) {
        if (amountWater < 0) {
            System.out.println("\nНекорректный ввод");
        }
        else if (getWater() + amountWater <= MAX_WATER) {
            setWater(getWater() + amountWater);
            System.out.printf("Воды в кофемашине %d мл\n", getWater());
            history.add("Добавлено воды: " + amountWater + " мл");
        }
        else {
            System.out.println("\nВы превысите объём резервуара");
        }
    }

    public void addMilk(int amountMilk) {
        if (amountMilk < 0) {
            System.out.println("\nНекорректный ввод");
        }
        else if (getMilk() + amountMilk <= MAX_MILK) {
            setMilk(getMilk() + amountMilk);
            System.out.printf("Молока в кофемашине %d мл\n", getMilk());
            history.add("Добавлено молока: " + amountMilk + " мл");
        }
        else {
            System.out.println("\nВы превысите объём резервуара");
        }
    }

    public void addCoffee(int amountCoffee) {
        if (amountCoffee < 0) {
            System.out.println("\nНекорректный ввод");
        }
        else if (getCoffee() + amountCoffee <= MAX_COFFEE) {
            setCoffee(getCoffee() + amountCoffee);
            System.out.printf("Кофе в кофемашине %d г\n", getCoffee());
            history.add("Добавлено кофе: " + amountCoffee + " г");
        }
        else {
            System.out.println("\nВы превысите объём резервуара");
        }
    }

    public void makeCupsOfCoffee(CoffeeRecipe recipe, int amount) {
        if (amount < 0) {
            System.out.println("\nНекорректный ввод");
            return;
        }
        for (int i = 0; i <= amount; i++) {
            if (reviewException(recipe, amount)) {
                return;
            }
        }
        makeCoffee(recipe, amount);
        System.out.println(recipe.getName() + " готово");
    }

    public boolean reviewException(CoffeeRecipe recipe, int amount) {
        try {
            checkConditions(recipe, amount);
        }
        catch (CleanException ex) {
            System.out.println("Кофемашина грязная");
            return true;
        }
        catch (NotEnoughWaterException ex) {
            System.out.println("Недостаточно воды");
            return true;
        }
        catch (NotEnoughMilkException ex) {
            System.out.println("Недостаточно молока");
            return true;
        }
        catch (NotEnoughCoffeeException ex) {
            System.out.println("Недостаточно кофе");
            return true;
        }
        return false;
    }

    public void checkConditions(CoffeeRecipe recipe, int amount) throws CleanException, NotEnoughWaterException, NotEnoughMilkException, NotEnoughCoffeeException {
        checkClean();
        checkWater(recipe, amount);
        checkMilk(recipe, amount);
        checkCoffee(recipe, amount);
    }

    public void checkClean() throws CleanException {
        if (getCupOfCoffee() >= MAX_CUP_OF_COFFEE) {
            throw new CleanException();
        }
    }

    public void checkWater(CoffeeRecipe recipe, int amount) throws NotEnoughWaterException {
        if (recipe.getWater() * amount > getWater()) {
            throw new NotEnoughWaterException();
        }
    }

    public void checkMilk(CoffeeRecipe recipe, int amount) throws NotEnoughMilkException {
        if (recipe.getMilk() * amount >= getMilk()) {
            throw new NotEnoughMilkException();
        }
    }

    public void checkCoffee(CoffeeRecipe recipe, int amount) throws NotEnoughCoffeeException {
        if (recipe.getCoffee() * amount >= getCoffee()) {
            throw new NotEnoughCoffeeException();
        }
    }

    public void makeCoffee(CoffeeRecipe recipe, int amount) {
        setWater(getWater() - recipe.getWater() * amount);
        setMilk(getMilk() - recipe.getMilk() * amount);
        setCoffee(getCoffee() - recipe.getCoffee() * amount);
        setCupOfCoffee(getCupOfCoffee() + amount);
        if (recipe.getName().equals("Эспрессо")) {
            setCupOfEspresso(getCupOfEspresso() + amount);
        }
        if (recipe.getName().equals("Капучино")) {
            setCupOfCappuccino(getCupOfCappuccino() + amount);
        }
    }

    public void getAmountPreparedCoffee() {
        System.out.printf("""
                \nСделано порций эспрессо: %d
                Сделано порций капучино: %d
                """, CoffeeMachine.getCupOfEspresso(), CoffeeMachine.getCupOfCappuccino());
        history.add("Сколько порций кофе было сделано");
    }

    public void isClean() {
        if (getCupOfCoffee() == 0) {
            System.out.println("Кофемашина не требуется в чистке");
        }
        else {
            setCupOfCoffee(0);
            System.out.println("Кофемашина очищена");
            history.add("Очистить кофемашину");
        }
    }

    public void getRecipeCoffee(CoffeeRecipe coffeeRecipe) {
        System.out.printf("Рецепт для %s:\nВоды %d мл\nМолока %d мл\nЗёрен кофе %d г\n",
                coffeeRecipe.getName(), coffeeRecipe.getWater(), coffeeRecipe.getMilk(), coffeeRecipe.getCoffee());
        history.add("Рецепт " + coffeeRecipe.getName());
    }

    public void getHistory() {
        history.add("История действий");
        for (String action : history) {
            System.out.println(action);
        }
        System.out.printf("""
                Приготовленно порций эспрессо: %d
                Приготовленно порций капучино: %d
                """, getCupOfEspresso(), getCupOfCappuccino());
    }
}
