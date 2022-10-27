package com.betswap.market.infrastruture.utils.bigDecimalFormat;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class BigDecimalFormatSerializer extends JsonSerializer<BigDecimal> {

    private static final DecimalFormat FORMAT = new DecimalFormat("###.##");

    @Override
    public void serialize(BigDecimal value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {

        String text = null;
        //是否为空
        if (value != null) {
            try {
                //格式化是否为空
//                text = FORMAT.format(value);
                text =value .setScale(2, RoundingMode.DOWN).toPlainString();
            } catch (Exception e) {
                text = value.toString();
            }
        }
        if (text != null) {
            try {
                jsonGenerator.writeString(text);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
