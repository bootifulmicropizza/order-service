package com.bootifulmicropizza.service.order.domain;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.math.BigDecimal;

import static java.math.BigDecimal.ROUND_HALF_EVEN;

public class MoneySerializer extends JsonSerializer<BigDecimal> {

    @Override
    public void serialize(BigDecimal decimal, JsonGenerator generator, SerializerProvider provider) throws IOException {
        generator.writeNumber(decimal.setScale(2, ROUND_HALF_EVEN));
    }
}
