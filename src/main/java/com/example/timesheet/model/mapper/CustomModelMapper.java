package com.example.timesheet.model.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomModelMapper extends ModelMapper {

    public <S, T> List<T> mapAll(List<S> source, Class<T> targetClass) {
        return source
                .stream()
                .map(element -> map(element, targetClass)).toList();
    }

    @Bean
    public CustomModelMapper modelMapper() {
        return new CustomModelMapper();
    }
}
