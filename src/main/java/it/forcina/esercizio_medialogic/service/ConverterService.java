package it.forcina.esercizio_medialogic.service;

import it.forcina.esercizio_medialogic.commons.Months;
import it.forcina.esercizio_medialogic.commons.Response;
import it.forcina.esercizio_medialogic.exception.TaxCodeException;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ConverterService {

    public Response convertToBirthDate(String taxCode) throws TaxCodeException {
        Pattern pattern = Pattern.compile("^(?:[A-Z][AEIOU][AEIOUX]|[AEIOU]X{2}|[B-DF-HJ-NP-TV-Z]{2}[A-Z]){2}(?:[\\dLMNP-V]{2}(?:[A-EHLMPR-T](?:[04LQ][1-9MNP-V]|[15MR][\\dLMNP-V]|[26NS][0-8LMNP-U])|[DHPS][37PT][0L]|[ACELMRT][37PT][01LM]|[AC-EHLMPR-T][26NS][9V])|(?:[02468LNQSU][048LQU]|[13579MPRTV][26NS])B[26NS][9V])(?:[A-MZ][1-9MNP-V][\\dLMNP-V]{2}|[A-M][0L](?:[1-9MNP-V][\\dLMNP-V]|[0L][1-9MNP-V]))[A-Z]$");
        Matcher matcher = pattern.matcher(taxCode);
        if(!matcher.find()) {
            throw new TaxCodeException("Invalid tax code");
        }
        StringBuilder birthDate = new StringBuilder();

        String year = taxCode.substring(6,8);
        String month = taxCode.substring(8,9);
        String day = taxCode.substring(9,11);

        birthDate.append(day);
        getMonth(month, birthDate);
        getYear(year, birthDate);

        LocalDate date = LocalDate.parse(birthDate, DateTimeFormatter.ofPattern("dd-MM-yyyy"));

        int age = calculateAge(date);

        return new Response(date, age);
    }

    private void getMonth(String month, StringBuilder string) {
        switch(Months.valueOf(month)) {
            case A -> string.append("-01-");
            case B -> string.append("-02-");
            case C -> string.append("-03-");
            case D -> string.append("-04-");
            case E -> string.append("-05-");
            case H -> string.append("-06-");
            case L -> string.append("-07-");
            case M -> string.append("-08-");
            case P -> string.append("-09-");
            case R -> string.append("-10-");
            case S -> string.append("-11-");
            case T -> string.append("-12-");
        }
    }

    private void getYear(String year, StringBuilder string) {
        try {
            DateFormat twoDigitFormat = new SimpleDateFormat("yy");
            Date d = twoDigitFormat.parse(year);
            DateFormat fourDigitFormat = new SimpleDateFormat("yyyy");
            string.append(fourDigitFormat.format(d));
        } catch (ParseException e) {
            throw new RuntimeException("Invalid year data", e);
        }
    }

    private int calculateAge(LocalDate date) {
        return Period.between(date, LocalDate.now(ZoneId.of("Europe/Rome"))).getYears();
    }
}
