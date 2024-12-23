public final class GeneralMethods {


    /**
     * Checks if the given string contains only letters
     * @param str is the given string
     * @return true if contains only letters, false otherwise
     */
    public static boolean onlyLetters(String str){
        for (char c: str.toCharArray()){
            if (!Character.isLetter(c) && !Character.isDigit(c)){
                return false;
            }
        }
        return true;
    }


}
