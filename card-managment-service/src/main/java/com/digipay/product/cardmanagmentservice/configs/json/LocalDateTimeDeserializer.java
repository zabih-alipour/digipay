package com.digipay.product.cardmanagmentservice.configs.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.github.eloyzone.jalalicalendar.DateConverter;
import com.github.eloyzone.jalalicalendar.JalaliDate;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {

    @Override
    public LocalDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        String s = p.readValueAs(String.class);
        String[] split = s.split("-");
        String[] datePart = split[0].trim().split("/");
        String[] timePart = split[0].trim().split(":");

        JalaliDate jalaliDate = new JalaliDate(Integer.parseInt(datePart[0]), Integer.parseInt(datePart[1]), Integer.parseInt(datePart[2]));
        LocalDate date = new DateConverter().jalaliToGregorian(jalaliDate);
        LocalTime time = LocalTime.of(Integer.parseInt(timePart[0]), Integer.parseInt(timePart[1]), Integer.parseInt(timePart[2]));
        return LocalDateTime.of(date, time);
    }
}
