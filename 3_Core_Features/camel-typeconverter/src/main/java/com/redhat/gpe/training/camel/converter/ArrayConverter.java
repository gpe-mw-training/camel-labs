package com.redhat.gpte.training.camel.converter;

import org.apache.camel.Converter;
import com.redhat.gpte.training.camel.bean.MyArray;
import org.apache.camel.Converter;
import org.apache.camel.Exchange;
import org.apache.camel.TypeConversionException;
import org.apache.camel.support.TypeConverterSupport;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

@Converter
public class ArrayConverter extends TypeConverterSupport {

    @Converter
    public static Vector<String> toVector(ArrayList<String> aList) {
        return new Vector<String>(aList);
    }

    @Override
    public <T> T convertTo(Class<T> type, Exchange exchange, Object value) throws TypeConversionException {
        MyArray l = (MyArray) value;
        Vector<String> v = new Vector<String>(l);
        return (T) v;
    }
}
