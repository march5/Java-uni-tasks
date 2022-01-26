package uj.java.pwj2020.delegations;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.time.zone.*;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.chrono.ChronoLocalDateTime;
import java.time.format.DateTimeFormatter;

public class Calc {

    BigDecimal calculate(String name, String start, String end, BigDecimal dailyRate) {

        BigDecimal ret = new BigDecimal("0.00");

        //we get the LocalDateTimes of start and end
        LocalDateTime startDateTime = LocalDateTime.parse(start.substring(0,16).replace(' ', 'T'));
        LocalDateTime endDateTime = LocalDateTime.parse(end.substring(0,16).replace(' ','T'));

        //then get its zones
        ZoneId startZone = ZoneId.of(start.substring(17));
        ZoneId endZone = ZoneId.of(end.substring(17));

        //and create ZonedDateTimes
        ZonedDateTime startZoned = ZonedDateTime.of(startDateTime, startZone);
        ZonedDateTime endZoned = ZonedDateTime.of(endDateTime, endZone);

        if(startZoned.compareTo(endZoned) >= 0){//if start is the same or later than end we return 0
            return ret;
        }

        BigDecimal day = new BigDecimal("1440.00");//minutes in 24 hours

        BigDecimal duration = new BigDecimal(ChronoUnit.MINUTES.between(startZoned, endZoned));//minutes between start and end

        BigDecimal fullDays = duration.divideAndRemainder(day)[0];//full 24 hour days between start and end

        ret = ret.add(dailyRate.multiply(fullDays));//we add fullDays multiplied by dailyRate to the result

        duration = duration.divideAndRemainder(day)[1];//we calculate the minutes after full days

        if(duration.compareTo(new BigDecimal("0.00")) == 0){//if there are none, we return the result
            return ret;
        }

        if(duration.compareTo(new BigDecimal("720.00")) == 1){//if there are more than 720 minutes (8 hours) we add one more DailyRate
            ret = ret.add(dailyRate);
        }
        else if (duration.compareTo(new BigDecimal("480.00")) == 1){//if not, then if there are more than 480 minutes (8 hours) we add DailyRate divided by 2
            ret = ret.add(dailyRate.divide(new BigDecimal("2.00"), new MathContext(4)));
        }
        else{//if not, then we add DailyRate divided by 3
            ret = ret.add(dailyRate.divide(new BigDecimal("3.00"), new MathContext(4)));
        }

        return ret;//return the result
    }
}
