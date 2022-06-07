package net.ausiasmarch.wildcart.helper;

public class ValidationHelper {

    public static final String EMAIL_PATTERN = "^.+@.+\\..+$";
    public static final String CODIGO_PATTERN = "^([A-Z0-9]{1,6}-)[A-Za-z0-9]{5,200}$";

    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean validateStringLength(String strNombre, int minlength, int maxlength) {
        if (strNombre.length() >= minlength && strNombre.length() <= maxlength) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean validatePattern(String strInput, String strPattern) {
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(strPattern);
        java.util.regex.Matcher m = p.matcher(strInput);
        return m.matches();
    }

    public static boolean validateDNI(String itDNI) {
        String strDNI = itDNI.trim().replaceAll(" ", "");
        if (strDNI.length() == 9) {
            if (isNumeric(strDNI.substring(0, 7))) {
                int intPartDNI = Integer.parseInt(strDNI.substring(0, 7));
                char cLetraDNI = strDNI.charAt(8);
                int valNumDni = intPartDNI % 23;
                if ("TRWAGMYFPDXBNJZSQVHLCKE".charAt(valNumDni) != cLetraDNI) {
                    return false;
                } else {
                    return true;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public static boolean validateEmail(String email) {
        if (!validateStringLength(email, 3, 255)) {
            return false;
        }
        String ePattern = "^.+@.+\\..+$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }

    public static boolean validateLogin(String strLogin) {
        if (!validateStringLength(strLogin, 6, 20)) {
            return false;
        }
        String ePattern = "^[a-zA-Z0-9]([._-](?![._-])|[a-zA-Z0-9]){4,18}[a-zA-Z0-9]$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(strLogin);
        return m.matches();
    }

    public static boolean validateRange(int iNumber, int iMin, int iMax) {
        if (iNumber >= iMin && iNumber <= iMax) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean validateRange(double iNumber, double iMin, double iMax) {
        if (iNumber >= iMin && iNumber <= iMax) {
            return true;
        } else {
            return false;
        }
    }

}
