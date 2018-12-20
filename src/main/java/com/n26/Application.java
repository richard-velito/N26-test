package com.n26;

import com.n26.converter.StatisticsConverter;
import com.n26.converter.TransactionRequestConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class Application {

    public static void main(String... args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean(name="conversionService")
    public ConversionService getConversionService () {
        ConversionServiceFactoryBean bean = new ConversionServiceFactoryBean();
        bean.setConverters(getConverters());
        bean.afterPropertiesSet();
        return bean.getObject();
    }

    private Set<?> getConverters () {
        Set<Converter> converters = new HashSet<Converter>();
        converters.add(new TransactionRequestConverter());
        converters.add(new StatisticsConverter());
        return converters;
    }

}
