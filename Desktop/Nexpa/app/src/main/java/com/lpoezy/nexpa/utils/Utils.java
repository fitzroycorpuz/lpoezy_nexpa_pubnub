package com.lpoezy.nexpa.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by pksimpson on 13/07/16.
 */
public class Utils {

    public static boolean isValidEmail(String enteredEmail){
        String emailregex = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.(?:[A-Z]{2,}|com|org))+$";
        Boolean result = enteredEmail.matches(emailregex);
        return ((!enteredEmail.isEmpty()) && (enteredEmail!=null) && (result));
    }

//    (?=.*\d)		#   must contains one digit from 0-9
//    (?=.*[a-z])		#   must contains one lowercase characters
//    (?=.*[A-Z])		#   must contains one uppercase characters
//    (?=.*[@#$%])		#   must contains one special symbols in the list "@#$%"
//    .		#     match anything with previous condition checking
//    {6,20}	#        length at least 6 characters and maximum of 20

    public static boolean isPasswordValid(String password){

        String PASSWORD_PATTERN = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})";

        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        Matcher matcher = pattern.matcher(password);

        return ((!password.isEmpty()) && (password!=null) && (matcher.matches()));
    }
}
