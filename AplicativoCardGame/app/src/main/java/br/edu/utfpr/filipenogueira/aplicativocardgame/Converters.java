package br.edu.utfpr.filipenogueira.aplicativocardgame;

import androidx.room.TypeConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Converte LocalDate <-> String para persistência no Room.
 * A conversão para String usa ISO (yyyy-MM-dd) para armazenamento, e para exibição
 * a Activity/Adapter pode formatar para o Locale atual.
 */
public class Converters {

    // Armazenamento: ISO (invariável) - facilita migração/interoperabilidade
    @TypeConverter
    public static String fromLocalDate(LocalDate date) {
        return date == null ? null : date.toString(); // ISO-8601 yyyy-MM-dd
    }

    @TypeConverter
    public static LocalDate toLocalDate(String value) {
        return value == null ? null : LocalDate.parse(value);
    }

    // Utilitário opcional de formatação local (não usado diretamente pelo Room)
    public static String formatForDisplay(LocalDate date, Locale locale) {
        if (date == null) return "";
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd MMM yyyy", locale);
        return date.format(fmt);
    }
}
