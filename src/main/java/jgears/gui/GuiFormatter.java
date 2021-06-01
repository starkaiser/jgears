/*
 * Copyright (C) 2021 Sorin Cătălin Păștiță
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package main.java.jgears.gui;
/**
 *
 * @author Sorin Cătălin Păștiță
 */
import javafx.util.StringConverter;

public final class GuiFormatter {
    public static final StringConverter<Double> converterMm = new StringConverter<Double>() {
        @Override
        public String toString(Double object) {
            return String.format("%.4f", object) + " mm";
        }
        @Override
        public Double fromString(String string) {
            return Double.parseDouble(string);
        }
    };

    public static  final StringConverter<Double> converterDeg = new StringConverter<Double>() {
        @Override
        public String toString(Double object) {
            return String.format("%.4f", object) + " deg";
        }

        @Override
        public Double fromString(String string) {
            return Double.parseDouble(string);
        }
    };

    public static  final StringConverter<Double> converterUl = new StringConverter<Double>() {
        @Override
        public String toString(Double object) {
            return String.format("%.4f", object) + " ul";
        }

        @Override
        public Double fromString(String string) {
            return Double.parseDouble(string);
        }
    };

    public static  final StringConverter<Integer> converterUlInt = new StringConverter<Integer>() {
        @Override
        public String toString(Integer object) {
            return String.format("%d", object) + " ul";
        }

        @Override
        public Integer fromString(String string) {
            return Integer.parseInt(string);
        }
    };

    public static final StringConverter<Double> converterKW = new StringConverter<Double>() {
        @Override
        public String toString(Double object) {
            return String.format("%.4f", object) + " kW";
        }

        @Override
        public Double fromString(String string) {
            return Double.parseDouble(string);
        }
    };

    public static final StringConverter<Double> converterRpm = new StringConverter<Double>() {
        @Override
        public String toString(Double object) {
            return String.format("%.4f", object) + " rpm";
        }

        @Override
        public Double fromString(String string) {
            return Double.parseDouble(string);
        }
    };

    public static final StringConverter<Double> converterNm = new StringConverter<Double>() {
        @Override
        public String toString(Double object) {
            return String.format("%.4f", object) + " Nm";
        }

        @Override
        public Double fromString(String string) {
            return Double.parseDouble(string);
        }
    };

    public static final StringConverter<Double> converterMPa = new StringConverter<Double>() {
        @Override
        public String toString(Double object) {
            return String.format("%.4f", object) + " MPa";
        }

        @Override
        public Double fromString(String string) {
            return Double.parseDouble(string);
        }
    };

    public static  final StringConverter<Integer> converterHrInt = new StringConverter<Integer>() {
        @Override
        public String toString(Integer object) {
            return String.format("%d", object) + " hr";
        }

        @Override
        public Integer fromString(String string) {
            return Integer.parseInt(string);
        }
    };
}

