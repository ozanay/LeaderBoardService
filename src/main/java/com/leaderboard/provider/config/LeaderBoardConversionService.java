package com.leaderboard.provider.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.convert.support.GenericConversionService;

public class LeaderBoardConversionService extends GenericConversionService {
    public <T> List<T> convertToList(Iterable<?> anyList, Class<T> targetClass) {
        List<T> convertedList = new ArrayList<>();
        if (anyList != null) {
            anyList.forEach(any -> {
                T converted = convert(any, targetClass);
                convertedList.add(converted);
            });
        }
        
        return convertedList;
    }
}
