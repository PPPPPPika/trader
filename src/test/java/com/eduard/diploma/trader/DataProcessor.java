package com.eduard.diploma.trader;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class DataProcessor {
    private BigDecimal ceiling1 = new BigDecimal("2");
    private final BigDecimal ceiling1_default = new BigDecimal("3");

    private final BigDecimal divider1 = new BigDecimal("3");

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class TransformData{
        private Long time;
        private String openPrice;
        private String closePrice;

        @Override
        public String toString() {
            return "TransformData{" +
                    "time=" + processingDate(time) +
                    ", openPrice='" + openPrice + '\'' +
                    ", closePrice='" + closePrice + '\'' +
                    '}';
        }

        private String processingDate(Long date){
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("Europe/Moscow"));
            return simpleDateFormat.format(new Date(date));
        }
    }

    public TransformData algorithm(List<InformationRecipient> list){
        BigDecimal firstValue = new BigDecimal(list.get(0).getOpenPrice());
        TransformData transformData1 = new TransformData();
        for (int i = 0; i < list.size(); i++){
            if (ceiling1.doubleValue() > 0){
                if (i == 0){
                    //System.out.println("First element " + list.get(i));

                    BigDecimal value = new BigDecimal(list.get(i).getClosePrice())
                                    .multiply(new BigDecimal("100"))
                                    .divide(firstValue, 8, RoundingMode.DOWN)
                                    .subtract(new BigDecimal("100"));

                    if (value.doubleValue() > 0){
                        value = value.divide(divider1, 8, RoundingMode.DOWN);
                    }
                    ceiling1 = ceiling1.add(value);

                    //System.out.println("TEST1 ceiling " + ceiling1);
                    //System.out.println("TEST1 ceiling double value " + ceiling1.doubleValue());

                    if (ceiling1.doubleValue() > ceiling1_default.doubleValue()) {
                        ceiling1 = ceiling1_default;
                        //System.out.println("TEST2 ceiling " + ceiling1);
                        //System.out.println("TEST2 ceiling double value " + ceiling1.doubleValue());
                    }
                }
                else {
                    //System.out.println("Element " + i + ": " + list.get(i));

                    BigDecimal value = new BigDecimal(list.get(i).getClosePrice())
                            .multiply(new BigDecimal("100"))
                            .divide(new BigDecimal(list.get(i - 1).getClosePrice()), 8, RoundingMode.DOWN)
                            .subtract(new BigDecimal("100"));

                    //System.out.println(value.doubleValue());
                    if (value.doubleValue() > 0){
                        value = value.divide(divider1, 8, RoundingMode.DOWN);
                    }
                    ceiling1 = ceiling1.add(value);


                    //System.out.println("TEST3 ceiling " + ceiling1);
                    //System.out.println("TEST4 ceiling double value " + ceiling1.doubleValue());
                    //System.out.println("TEST4 ceiling default double value " + ceiling1_default.doubleValue());

                    if (ceiling1.doubleValue() > ceiling1_default.doubleValue()) {
                        ceiling1 = ceiling1_default;
                        //System.out.println("TEST5 ceiling " + ceiling1);
                        //System.out.println("TEST6 ceiling double value " + ceiling1.doubleValue());
                    }
                    transformData1 = new TransformData(list.get(i).getOpenTime(), list.get(i).getOpenPrice(), list.get(i).getClosePrice());
                }
            }
            else {
                //System.out.println(ceiling1);
                //return list.get(i).getOpenTime() + "\n" + "Open price: " + list.get(0).getOpenPrice() + "\n" + "Close price: " + list.get(i).getClosePrice().toString() + "\n";
                return new TransformData(list.get(i).getOpenTime(), list.get(0).getOpenPrice(), list.get(i).getClosePrice());
            }
        }
        System.out.println(ceiling1);
        return transformData1;
    }


}
