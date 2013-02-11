package com.example.StayAlive;


import java.util.*;

public class PersonalBankLogic {

    public int getCurrentBalance() {
        return currentBalance;
    }

    public  enum BankOperation{Deposit,WithDraw,Reserve}
    private  final int UNIVERSITY_FOOD =150;
    private final  int BUS_VRN =11;
    private final  int BUS_LIPETSK =12;
    private final int BUS_106 =19;
    private final int KEFIR =39;
    private final int CLEANING_WOMAN =60;
    private final int INTERNET =300;
    private final  int SALVE =350;
    final  int TRAIN_TO_HOME =71;
    final int BUS_TO_HOME =179;
    private final int BREAD =12;
    private final int MINIMAL_BALANCE=150;
    private final int MAXIMUM_ON_PIZZA=300;
    private final int PIZZA=100;
    final  String[] vacationDays={"Saturday","Sunday","Thursday"};
    final String[] trainingDays={"Saturday","Monday","Wednesday"};
    private   int RideOnBusOnBothEnds=2* BUS_VRN;
    private int eveningFood= BREAD + KEFIR;
    private  int TransportToHomeByTrain=RideOnBusOnBothEnds+ TRAIN_TO_HOME + BUS_106;
    private  int TransportToHomeByBus= BUS_TO_HOME + BUS_LIPETSK *2+ BUS_VRN;
    private   int universityDay =(UNIVERSITY_FOOD +RideOnBusOnBothEnds);
    private int studyDay=universityDay+eveningFood;
    private  int homeDay=2* KEFIR + BREAD +50;
    int currentBalance;
    private int reminder;
    private int reserve=0;

    Calendar calendar;

    public  PersonalBankLogic(int incomingMoney){
        currentBalance=incomingMoney;
        calendar=getCalendar();
    }
    public int calculateCountOfDaysToLive(boolean isPizzaAfterTennis,boolean isGoHomeByTrain,boolean isInternetExist,boolean  isSalveExist,int additionalSpend)
    {
        //init
        String currentDayOfWeek;
        int expenseToday;
        int balance=currentBalance;
        Calendar currentCalendar=getCalendar();
        //int additional
        additionalSpend+=isInternetExist?0: INTERNET ;
        additionalSpend+=isSalveExist?0: SALVE;
        additionalSpend+=isPizzaAfterTennis?MAXIMUM_ON_PIZZA:0;
        int costRideToHome=isGoHomeByTrain? TransportToHomeByTrain:TransportToHomeByBus;

        balance-=(costRideToHome+additionalSpend+CLEANING_WOMAN);

        do
        {
             currentDayOfWeek = dayWeek(currentCalendar);
             expenseToday=isHomeDay(currentDayOfWeek)?homeDay:studyDay;
             // expenseToday+=(isTrainingDay(currentDayOfWeek)&&isPizzaAfterTennis)?PIZZA:0;
             balance-=expenseToday;
             currentCalendar.add(Calendar.DAY_OF_MONTH, 1);

        }while(balance>MINIMAL_BALANCE);

        calendar.setTime(new Date(System.currentTimeMillis()));
        int countDaysToBeProvided=calculateCountDaysBetweenDates(calendar.getTime(),currentCalendar.getTime());
        reminder=balance;
        return  countDaysToBeProvided;
    }
    public int getRemainder() {
        return reminder;
    }
    public void AddOperation(int summa,String bankOperation)
    {
        if (bankOperation.equals("Вклад"))
            currentBalance+=summa;
        if (bankOperation.equals("Отложить на запас"))
        {
            currentBalance-=summa;
            reserve+=summa;
        }
        if (bankOperation.equals("Снять"))
            currentBalance-=summa;


    }
    private String dayWeek(Calendar calendar)
    {
        return calendar.getDisplayName(Calendar.DAY_OF_WEEK,Calendar.LONG,Locale.ENGLISH);
    }
    private boolean isHomeDay(String currentDay)
    {
         return (Arrays.asList(vacationDays).contains(currentDay))   ;

    }
    private Calendar getCalendar()
    {
        Date aDate = new Date(System.currentTimeMillis());
        Calendar newCalendar = GregorianCalendar.getInstance();
        newCalendar.setTime(aDate);
        return newCalendar;
    }
    private boolean isTrainingDay(String currentDay)
    {
        return (Arrays.asList(trainingDays).contains(currentDay))   ;

    }
    private int calculateCountDaysBetweenDates(Date start, Date end)
    {
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
