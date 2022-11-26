package com.newroutes.models.mappers.sendinblue;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SendinblueServiceMap {

    private final Gson gson = new Gson();
    private static final String DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";


    public String map(List<String> tags) {
        return gson.toJson(tags);
    }

    public List<String> map(String tags) {

        if ( tags == null || tags.equals("") ) {
            return new ArrayList<>();
        }

        Type type = new TypeToken<List<String>>(){}.getType();

        return gson.fromJson( tags, type );
    }

    @SneakyThrows
    public Date mapDate(String date) {

        SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN);
        return sdf.parse(date);
    }

    public String mapDate(Date date) {

        SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN);
        return sdf.format(date);
    }
}
