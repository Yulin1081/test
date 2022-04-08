package uk.ac.soton.SEG16;

import java.util.HashSet;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

public class InputLimiter {

    public static HashSet<TextField> InvalidFields = new HashSet<TextField>();
    
    //#region OLD FUNCTIONS, TO BE REMOVED
    public static void MakeNumericOnly(final TextField field) {
        field.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("[0-9]*")) {
                    field.setText(oldValue);
                }
            }
        });
    }

    public static void MakeFloatOnly(final TextField field) {
        field.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("[0-9]*.[0-9]*")) {
                    field.setText(oldValue);
                }
            }
        });
    }
    //#endregion

    static final String CORRECT_FORMAT = "-fx-background-color: GAINSBORO; -fx-text-fill: black;";
    static final String INCORRECT_FORMAT = "-fx-background-color: #c25353; -fx-text-fill: GAINSBORO;";

    public static void MakeInt (final TextField field) {
        field.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed (ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (isInteger(newValue)) {
                    // Can be converted to an integer
                    field.setStyle(CORRECT_FORMAT);
                    InvalidFields.remove(field);
                    return;
                }
                else {
                    field.setStyle(INCORRECT_FORMAT);
                    InvalidFields.add(field);
                    return;
                }
            }
        });
    }

    public static void MakeFloat (final TextField field) {
        field.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed (ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (isFloat(newValue)) {
                    // Can be converted to a float
                    field.setStyle(CORRECT_FORMAT);
                    InvalidFields.remove(field);
                    return;
                }

                field.setStyle(INCORRECT_FORMAT);
                InvalidFields.add(field);
                return;
            }
        });
    }

    public static void MakePositiveInt (final TextField field) {
        field.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed (ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (isInteger(newValue)) {
                    // Can be converted to an int...
                    try {
                        int num = Integer.parseInt(newValue);
                        if (isPositive(num)) {
                            field.setStyle(CORRECT_FORMAT);
                            InvalidFields.remove(field);
                            return;
                        }
                    } catch (Exception e) { }
                }

                field.setStyle(INCORRECT_FORMAT);
                InvalidFields.add(field);
            }
        });
    }

    public static void MakePositiveFloat (final TextField field) {
        field.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed (ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (isFloat(newValue)) {
                    // Can be converted to an int...
                    try {
                        Float num = Float.parseFloat(newValue);
                        if (isPositive(num)) {
                            field.setStyle(CORRECT_FORMAT);
                            InvalidFields.remove(field);
                            return;
                        }
                    } catch (Exception e) { }
                }

                field.setStyle(INCORRECT_FORMAT);
                InvalidFields.add(field);
            }
        });
    }

    public static boolean IsValid () {
        return InvalidFields.isEmpty();
    }

    // String conversion checks
    public static boolean isInteger (String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    public static boolean isFloat (String value) {
        try {
            Float.parseFloat(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // Greater than 0 checks
    public static boolean isPositive (int value) {
        return value > 0;
    }
    public static boolean isPositive (float value) {
        return value > 0.0f;
    }

    // Not 0 checks
    public static boolean isZero (int value) {
        return value == 0;
    }
    public static boolean isZero (float value) {
        return value == 0.0f;
    }
}
