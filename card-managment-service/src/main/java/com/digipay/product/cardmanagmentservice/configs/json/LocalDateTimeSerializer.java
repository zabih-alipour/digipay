package com.digipay.product.cardmanagmentservice.configs.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.github.eloyzone.jalalicalendar.JalaliDate;
import com.github.eloyzone.jalalicalendar.JalaliDateFormatter;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class LocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {
    @Override
    public void serialize(LocalDateTime value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        JalaliDate jalaliDate = new JalaliDate(value.getYear(), value.getMonthValue(), value.getDayOfMonth());
        String date = jalaliDate.format(new JalaliDateFormatter("yyyy/mm/dd"));
        String time = value.getHour() + ":" + value.getMinute();
        gen.writeString(date.concat(" - ").concat(time));
    }
}
