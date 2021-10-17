package uj.pwj2020.introduction;

public class Reverser {

    public String reverse(String input) {

        if(input == null) return null;
        input.trim();
        String out = new String();

        for(int i = input.length() - 1; i >= 0; i--)
            out = out + input.charAt(i);

        return out;
    }

    public String reverseWords(String input) {

        if(input == null) return null;
        input.trim();
        String out = new String();

        int i = 0, j = 0;
        while (i < input.length() && j < input.length()){

            if((input.charAt(i) >= 'a' && input.charAt(i) <= 'z') || (input.charAt(i) >= 'A' && input.charAt(i) <= 'Z')){
                j = i + 1;
                while (j < input.length() - 1 && input.charAt(j) != ' ')
                    j++;

                if(j < input.length())
                out = input.substring(i,j+1).trim() + ' ' + out;
                else{
                    out = input.substring(i) + ' ' + out;
                }

                i = j + 1;
            }

        }
        return out.trim();
    }

}
