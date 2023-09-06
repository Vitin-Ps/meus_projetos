package com.example.mybarbearia.services;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


@Component
public class StringEmMinutos {

    public static Date duracaoTotal(List<String> listaDuracao) {
        int horas = 0;
        int minutos = 0;
        int segundos = 0;

        for (String duracao : listaDuracao) {
            if(duracao != null) {
                String[] partes = duracao.split(":");
                if(partes.length > 2) {
                    horas += Integer.parseInt(partes[0]);
                    minutos += Integer.parseInt(partes[1]);
                    segundos += Integer.parseInt(partes[2]);
                } else {
                    minutos += Integer.parseInt(partes[0]);
                    segundos += Integer.parseInt(partes[1]);
                }

            }

        }
        int segundosTotais = segundos + (minutos * 60) + (horas * 3600);
        horas = segundosTotais / 3600;
        minutos = (segundosTotais % 3600) / 60;
        segundos = (segundosTotais % 60) % 60;

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, horas);
        calendar.set(Calendar.MINUTE, minutos);
        calendar.set(Calendar.SECOND, segundos);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTime();
    }

    public static String converterDateParaString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        return sdf.format(date);
    }


    public static Integer converterParaMinutosEmRacional(String duracao) {
        int horas = 0;
        int minutos = 0;
        int segundos = 0;

        if(duracao != null) {
            String[] partes = duracao.split(":");
            if(partes.length > 2) {
                horas += Integer.parseInt(partes[0]);
                minutos += Integer.parseInt(partes[1]);
                segundos += Integer.parseInt(partes[2]);
            } else {
                minutos += Integer.parseInt(partes[0]);
                segundos += Integer.parseInt(partes[1]);
            }

        }

        minutos = minutos + (segundos / 60) + (horas * 60);

        return minutos;
    }
}
