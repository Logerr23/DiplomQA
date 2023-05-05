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

    private static String StringBuilder(int min, int max, String symbols){
        StringBuilder stringBuilder = new StringBuilder();
        Random random = new Random();

        int length = faker.number().numberBetween(min, max);

        for(int i = 0; i < length; i++) {
            stringBuilder.append(symbols.charAt(random.nextInt(symbols.length())));
        }
        return stringBuilder.toString();
    }



    @Value
    public static class NumberCard{
        private String numberCard;
    }

    public static NumberCard approvedNumberCard() {
        return new NumberCard("4444 4444 4444 4441");
    }

    public static NumberCard declinedNumberCard() {
        return new NumberCard("4444 4444 4444 4442");
    }

    public static NumberCard wrongNumberCard() {
        return new NumberCard(faker.numerify("#### #### #### ####"));
    }

    public static NumberCard shortNumberCard(){
        return new NumberCard(DataHelper.StringBuilder(1,15,"1234567890"));
    }


    @Value
    public static class Date{
        private String month;
        private String year;
    }

    public static Date getValidDate(){
        long timeShift = faker.random().nextInt(0, 60);
        LocalDate date = LocalDate.now().plusMonths(timeShift);
        String month = date.format(DateTimeFormatter.ofPattern("MM"));
        String year = date.format(DateTimeFormatter.ofPattern("yy"));
        return new Date(month, year);
    }

    public static Date getPastDate(){
        long timeShift = faker.random().nextInt(1, 120);
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

    @Value
    public static class Name{
        private String name;
    }

    public static Name validName(){
        faker = new Faker(new Locale("en"));
        String lastName = faker.name().lastName();
        String firstName = faker.name().firstName();
        return new Name(lastName + " " + firstName);
    }

    public static Name cyrillicName(){
        faker = new Faker(new Locale("ru_RU"));
        String lastName = faker.name().lastName();
        String firstName = faker.name().firstName();
        return new Name(lastName + " " + firstName);
    }


    @Value
    public static class Code{
        private String code;
    }

    public static Code validCode(){
        return new Code(faker.numerify("###"));
    }

    public static Code shortCode(){
       int random = faker.number().numberBetween(0,99);
        return new Code(Integer.toString(random));
    }


    @Value
    public static class WrongData{
        private String wrongData;
    }

    public static WrongData empty(){
        return new WrongData("");
    }

    public static WrongData oneNumber(){
        return new WrongData(faker.numerify("#"));
    }

    public static WrongData randomNumbers(){
        return new WrongData(DataHelper.StringBuilder(1,15,"0123456789"));
    }

    public static WrongData multipleSpaces(){
        return new WrongData(DataHelper.StringBuilder(1,15," "));
    }

    public static WrongData specialSymbols(){
        return new WrongData(DataHelper.StringBuilder(1,15,"!@#$%^&*()_+=,.<>?/"));
    }


}
