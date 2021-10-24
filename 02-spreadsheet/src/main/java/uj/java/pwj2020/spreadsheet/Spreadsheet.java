package uj.java.pwj2020.spreadsheet;

public class Spreadsheet {

    public enum FieldType{value, reference, formula}
    public enum FormulaType{ADD, SUB, MUL, DIV, MOD}

    public boolean isNumber(char a){
        if(((int)a <= 57 && (int)a >= 48) || a == '-')
            return true;
        return false;
    }

    public boolean isReference(String a){

        if(a.charAt(0) == '$') return true;
        return false;
    }

    public int toNumber(String a, int beginIndex){

        int i = beginIndex;
        while(i < a.length() && isNumber(a.charAt(i))) i++;

        return Integer.parseInt(a.substring(beginIndex, i));
    }

    public FieldType checkType(String a){

        if(isNumber(a.charAt(0))) return FieldType.value;
        else
            if(isReference(a)) return FieldType.reference;
            else return FieldType.formula;
    }

    public FormulaType checkFormula(String a){

        FormulaType type = FormulaType.ADD;
        switch (a.charAt(1)){
            case 'A':
                type = FormulaType.ADD;
                break;
            case 'S':
                type = FormulaType.SUB;
                break;
            case 'D':
                type = FormulaType.DIV;
                break;
            case 'M':
                if(a.charAt(2) == 'O') type = FormulaType.MOD;
                else type = FormulaType.MUL;
                break;
        }

        return type;
    }

    public String calculateFormula(String a, String[][] input, FormulaType type, String[][] out){

        int i = 0;
        int[] x = new int[2];
        int[] address = new int[2];
        int[] address2 = new int[2];
        int result = 0;

        for(int j = 0; j < 2; j++) {
            while (i < a.length() - 1 && a.charAt(i) != '$' && !isNumber(a.charAt(i)))
                i++;

            if (isNumber(a.charAt(i))) {
                x[j] = toNumber(a, i);
            } else {
                address = getAddress(a, i + 1);

                while(checkType(out[address[0]][address[1]]) == FieldType.reference){
                    address2 = getAddress(out[address[0]][address[1]], 1);
                    out[address[0]][address[1]] = out[address2[0]][address2[1]];
                }

                while(checkType(out[address[0]][address[1]]) == FieldType.formula){
                    out[address[0]][address[1]] = calculateFormula(out[address[0]][address[1]], input, type, out);
                }

                x[j] = Integer.parseInt(out[address[0]][address[1]]);
            }
            while(i < a.length() &&  a.charAt(i) != ',') i++;
        }

        switch (type){
            case ADD -> result = x[0] + x[1];
            case SUB -> result = x[0] - x[1];
            case DIV -> result = x[0] / x[1];
            case MOD -> result = x[0] % x[1];
            case MUL -> result = x[0] * x[1];
        }

        return Integer.toString(result);
    }

    public int[] getAddress(String a, int beginIndex){
        int[] out = new int[2];

        out[1] = (int)a.charAt(beginIndex) - 65;

        int i = beginIndex + 1;
        while(i < a.length() && isNumber(a.charAt(i))) i++;
        out[0] = Integer.parseInt(a.substring(beginIndex + 1, i)) - 1;

        return out;
    }

    public String[][] calculate(String[][] input) {

        String[][] out = new String[input.length][input[0].length];

        FieldType type;
        FormulaType formType;

        for(int i = 0; i < input.length; i++){
            for(int j = 0; j < input[0].length; j++){
                type = checkType(input[i][j]);

                if(type != FieldType.value){
                    if(type == FieldType.reference){ //copy the value form another field
                        int[] address = getAddress(input[i][j], 1);// get the address form input string
                        out[i][j] = input[address[0]][address[1]];// get the value from address to current field

                        while(checkType(out[i][j]) == FieldType.reference){
                            address = getAddress(out[i][j], 1);
                            out[i][j] = input[address[0]][address[1]];
                        }
                    }
                    else{// formula type of field
                            formType = checkFormula(input[i][j]);
                            out[i][j] = calculateFormula(input[i][j], input, formType, out);
                    }
                }
                else {
                    out[i][j] = input[i][j];
                }
            }
        }

        return out;
    }

}
