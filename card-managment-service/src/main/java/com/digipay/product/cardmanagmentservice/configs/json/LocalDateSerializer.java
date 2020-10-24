package com.digipay.product.cardmanagmentservice.configs.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.github.eloyzone.jalalicalendar.JalaliDate;
import com.github.eloyzone.jalalicalendar.JalaliDateFormatter;

import java.io.IOException;
import java.time.LocalDate;

public class LocalDateSerializer extends JsonSerializer<LocalDate> {
    @Override
    public void serialize(LocalDate value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        JalaliDate jalaliDate = new JalaliDate(value.getYear(), value.getMonthValue(), value.getDayOfMonth());
        gen.writeString(jalaliDate.format(new JalaliDateFormatter("yyyy/mm/dd")));
    }
}
