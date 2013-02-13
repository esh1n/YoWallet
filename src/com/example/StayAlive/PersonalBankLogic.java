package com.example.StayAlive;


import java.util.*;

public class PersonalBankLogic {

    private int RideOnBusOnBothEnds,
                eveningFood,
                TransportToHomeByTrain,
                TransportToHomeByBus,
                universityDay,
                studyDay,
                homeDay;
    private int currentBalance;
    private int reminder;
    private int reserve = 0;
    Calendar calendar;
    private boolean isNeedInternet,isPizzaAfterTennis,isNeedSalve,isToHomeByTrain;
    private int addExpence;
    public int getCurrentBalance() {
        return currentBalance;
    }





    public PersonalBankLogic(int incomingMoney) {
        initDefaultSettings();
        setCostOfInputs();
        currentBalance = incomingMoney;
        calendar = getCalendar();
    }

    public int calculateCountOfDaysToLive(boolean isPizzaAfterTennis, boolean isGoHomeByTrain, boolean isNeedInternet, boolean isNeedSalve, int additionalSpend) {
        //init
        String currentDayOfWeek;
        int expenseToday;
        int balance = currentBalance;
        Calendar currentCalendar = getCalendar();
        //int additional
        additionalSpend += isNeedInternet ? Data.INTERNET : 0;
        additionalSpend += isNeedSalve ? Data.SALVE : 0;
        additionalSpend += isPizzaAfterTennis ? Data.MAXIMUM_ON_PIZZA : 0;
        int costRideToHome = isGoHomeByTrain ? TransportToHomeByTrain : TransportToHomeByBus;

        balance -= (costRideToHome + additionalSpend + Data.CLEANING_WOMAN);

        do {
            currentDayOfWeek = dayWeek(currentCalendar);
            expenseToday = isHomeDay(currentDayOfWeek) ? homeDay : studyDay;
            // expenseToday+=(isTrainingDay(currentDayOfWeek)&&isPizzaAfterTennis)?PIZZA:0;
            balance -= expenseToday;
            currentCalendar.add(Calendar.DAY_OF_MONTH, 1);

        } while (balance > Data.MINIMAL_BALANCE);

        calendar.setTime(new Date(System.currentTimeMillis()));
        int countDaysToBeProvided = calculateCountDaysBetweenDates(calendar.getTime(), currentCalendar.getTime());
        reminder = balance;
        return countDaysToBeProvided;
    }
    public void setCurrentBalance(int currentBalance)
    {
        this.currentBalance=currentBalance;
    }
    private void initDefaultSettings()
    {
        isNeedInternet=isNeedSalve=isPizzaAfterTennis=false;
        isToHomeByTrain=true;
        addExpence=400;
    }
    public void updateSettings(boolean isPizzaAfterTennis, boolean isGoHomeByTrain, boolean isNeedInternet, boolean isNeedSalve, int additionalSpend){
        this.isNeedInternet=isNeedInternet;
        this.isNeedSalve=isNeedSalve;
        this.isToHomeByTrain=isGoHomeByTrain;
        this.isPizzaAfterTennis=isPizzaAfterTennis;
        this.addExpence=additionalSpend;
    }
    public int calculateCountOfDaysToLive() {
       return calculateCountOfDaysToLive(isPizzaAfterTennis,isToHomeByTrain,isNeedInternet,isNeedSalve,addExpence);
    }

        public int getRemainder() {
        return reminder;
    }

    public void AddOperation(int summa, String bankOperation) {
        if (bankOperation.equals("Вклад"))
            currentBalance += summa;
        if (bankOperation.equals("Отложить на запас")) {
            currentBalance -= summa;
            reserve += summa;
        }
        if (bankOperation.equals("Снять"))
            currentBalance -= summa;


    }

    private String dayWeek(Calendar calendar) {
        return calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.ENGLISH);
    }

    private boolean isHomeDay(String currentDay) {
        return (Arrays.asList(Data.vacationDays).contains(currentDay));

    }

    private Calendar getCalendar() {
        Date aDate = new Date(System.currentTimeMillis());
        Calendar newCalendar = GregorianCalendar.getInstance();
        newCalendar.setTime(aDate);
        return newCalendar;
    }

    private boolean isTrainingDay(String currentDay) {
        return (Arrays.asList(Data.trainingDays).contains(currentDay));

    }

    private void setCostOfInputs() {
        RideOnBusOnBothEnds = 2 * Data.BUS_VRN;
        eveningFood = Data.BREAD + Data.KEFIR;
        TransportToHomeByTrain = RideOnBusOnBothEnds + Data.TRAIN_TO_HOME + Data.BUS_106;
        TransportToHomeByBus = Data.BUS_TO_HOME + Data.BUS_LIPETSK * 2 + Data.BUS_VRN;
        universityDay = (Data.UNIVERSITY_FOOD + RideOnBusOnBothEnds);
        studyDay = universityDay + eveningFood;
        homeDay = 2 * Data.KEFIR + Data.BREAD + 50;
    }

    private int calculateCountDaysBetweenDates(Date start, Date end) {
        calendar.setTime(start);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        long l = calendar.getTimeInMillis();

        calendar.setTime(end);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        l = calendar.getTimeInMillis() - l;
        return (int) (l / (24 * 60 * 60 * 1000));
    }


}
