package cz.jmx.tomik.alkomer.android.models;

/**
 * Alkomer - Server App
 * --------------------
 * Gender Enum
 *
 * @copyright   Copyright (c) 2011 Tomas Vitek
 * @author      Tomas Vitek ~ http://tomik.jmx.cz

 * @package     Alkomer
 * @version     1.0
 */
public enum Gender {
    male, female;

    public double getBetaConstant() {
        switch (this) {
            case male:
                return 0.1f;
            case female:
                return 0.085f;
        }
        return 1;
    }

    public double getWaterInBodyConstant() {
        switch (this) {
            case male:
                return 0.76f;
            case female:
                return 0.6f;
        }
        return 1;
    }

    @Override
    public String toString() {
        switch (this) {
            case male:
                return "male";
            case female:
                return "female";
        }
        return null;
    }
}