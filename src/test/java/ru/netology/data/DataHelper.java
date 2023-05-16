package ru.netology.data;

import com.github.javafaker.Faker;
import lombok.Value;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;

public class DataHelper {

    private DataHelper(){}

    private static Faker faker = new Faker();

    private static String stringBuilder(int min, int max, String symbols){
        StringBuilder stringBuilder = new StringBuilder();
        Random random = new Random();
        int length = faker.number().numberBetween(min, max);
        for(int i = 0; i < length; i++) {
            stringBuilder.append(symbols.charAt(random.nextInt(symbols.length())));
        }
        return stringBuilder.toString();
    }

    @Value
    public static class Date{
        private String month;
        private String year;
    }

    public static Date getValidDate(){
        long timeShift = faker.random().nextInt(12, 60);
        LocalDate date = LocalDate.now().plusMonths(timeShift);
        String month = date.format(DateTimeFormatter.ofPattern("MM"));
        String year = date.format(DateTimeFormatter.ofPattern("yy"));
        return new Date(month, year);
    }

    public static Date getPastDate(){
        long timeShift = faker.random().nextInt(2, 120);
        LocalDate date = LocalDate.now().minusMonths(timeShift);
        String month = date.format(DateTimeFormatter.ofPattern("MM"));
        String year = date.format(DateTimeFormatter.ofPattern("yy"));
        return new Date(month, year);
    }

    public static Date getOverFiveYearsDate(){
        long timeShift = faker.random().nextInt(61, 120);
        LocalDate date = LocalDate.now().plusMonths(timeShift);
        String month = date.format(DateTimeFormatter.ofPattern("MM"));
        String year = date.format(DateTimeFormatter.ofPattern("yy"));
        return new Date(month, year);
    }

    public static String getApprovedNumberCard() {
        return ("4444 4444 4444 4441");
    }
    public static String getDeclinedNumberCard() {
        return ("4444 4444 4444 4442");
    }
    public static String getWrongNumberCard() {
        return (faker.numerify("#### #### #### ####"));
    }
    public static String getShortNumberCard(){
        return (DataHelper.stringBuilder(1,15,"1234567890"));
    }

    public static String getValidName(){
        faker = new Faker(new Locale("en"));
        String lastName = faker.name().lastName();
        String firstName = faker.name().firstName();
        return (lastName + " " + firstName);
    }

    public static String getCyrillicName(){
        faker = new Faker(new Locale("ru_RU"));
        String lastName = faker.name().lastName();
        String firstName = faker.name().firstName();
        return (lastName + " " + firstName);
    }

    public static String getLongName(){
        return ("QWERTYUIOPASDFGHJKLZXCVBNMQ");
    }

    public static String getValidCode(){
        return (faker.numerify("###"));
    }

    public static String getShortCode(){
       int random = faker.number().numberBetween(0,99);
        return (Integer.toString(random));
    }
    public static String getEmpty(){
        return ("");
    }

    public static String getZeros(){
        return ("00");
    }

    public static String getOneNumber(){
        return (faker.numerify("#"));
    }

    public static String getRandomNumbers(){
        return (DataHelper.stringBuilder(1,15,"0123456789"));
    }

    public static String getMultipleSpaces(){
        return (DataHelper.stringBuilder(1,15," "));
    }

    public static String getSpecialSymbols(){
        return (DataHelper.stringBuilder(1,15,"!@#$%^&*()_+=,.<>?/"));
    }
}