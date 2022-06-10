package com.eduard.diploma.trader;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class InformationRecipient {
    private Long openTime;
    private String openPrice;
    private Long closeTime;
    private String closePrice;

    private String processingDate(Long date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("Europe/Moscow"));
        return simpleDateFormat.format(new Date(date));
    }

    @Override
    public String toString() {
        return "InformationRecipient{" + "\n" +
                "Open time: " + processingDate(openTime) + "\n" +
                "Open price: " + openPrice + "\n" +
                "Close time: " + processingDate(closeTime) + "\n" +
                "Close price: " + closePrice + "\n" +
                '}' + "\n";
    }
}
