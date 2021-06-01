package main.java.jgears.gui;

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

