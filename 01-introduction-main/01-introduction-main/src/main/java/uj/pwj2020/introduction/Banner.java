package uj.pwj2020.introduction;

public class Banner {

    public static void addLetter(char letter, String[] out){

        if(letter == ' '){
            for(int i = 0; i < 7; i++){
                out[i] += "   ";
            }
        }
        else{
            int place = 8*((int)letter - 65);
            //                              A      B        C      D         E       F       G       H      Ip      J       Kp       L      M     N       O       P       R       S       T        U       W       Y       Z
            String[] rowLetters = new String[7];
            rowLetters[0] =     "   #    ######   #####  ######  ####### #######  #####  #     # ###           # #    #  #       #     # #     # ####### ######   #####  ######   #####  ####### #     # #     # #     # #     # #     # ####### ";
            rowLetters[1] =     "  # #   #     # #     # #     # #       #       #     # #     #  #            # #   #   #       ##   ## ##    # #     # #     # #     # #     # #     #    #    #     # #     # #  #  #  #   #   #   #       #  ";
            rowLetters[2] =     " #   #  #     # #       #     # #       #       #       #     #  #            # #  #    #       # # # # # #   # #     # #     # #     # #     # #          #    #     # #     # #  #  #   # #     # #       #   ";
            rowLetters[3] =     "#     # ######  #       #     # #####   #####   #  #### #######  #            # ###     #       #  #  # #  #  # #     # ######  #     # ######   #####     #    #     # #     # #  #  #    #       #       #    ";
            rowLetters[4] =     "####### #     # #       #     # #       #       #     # #     #  #      #     # #  #    #       #     # #   # # #     # #       #   # # #   #         #    #    #     #  #   #  #  #  #   # #      #      #     ";
            rowLetters[5] =     "#     # #     # #     # #     # #       #       #     # #     #  #      #     # #   #   #       #     # #    ## #     # #       #    #  #    #  #     #    #    #     #   # #   #  #  #  #   #     #     #      ";
            rowLetters[6] =     "#     # ######   #####  ######  ####### #        #####  #     # ###      #####  #    #  ####### #     # #     # ####### #        #### # #     #  #####     #     #####     #     ## ##  #     #    #    ####### ";

            if(letter == 'I'){
                for(int i = 0; i < 7; i++){
                    if(out[i] == null) out[i] = rowLetters[i].substring(place, place+4 );
                    else
                        out[i] += rowLetters[i].substring(place, place+4 );
                }
            }
            else if (letter == 'K'){
                for(int i = 0; i < 7; i++){
                    if(out[i] == null) out[i] = rowLetters[i].substring(place, place+7 );
                    else
                        out[i] += rowLetters[i].substring(place, place+7 );
                }
            }
            else
            for(int i = 0; i < 7; i++){
                if(out[i] == null) out[i] = rowLetters[i].substring(place, place+8 );
                else
                    out[i] += rowLetters[i].substring(place, place+8 );
            }
        }
    }

    public String[] toBanner(String input) {

        String[] out;
        if(input == null){
            out = new String[0];
        }
        else{
            input = input.toUpperCase();
            input = input.trim();

            out = new String[7];

            for(int i = 0; i < input.length(); i++)
                addLetter(input.charAt(i), out);
        }

        return out;

    }

}
