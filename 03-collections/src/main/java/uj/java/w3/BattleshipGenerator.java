package uj.java.w3;

import javax.print.attribute.standard.Sides;

public interface BattleshipGenerator {

    String generateMap();

    static BattleshipGenerator defaultInstance() {
        return new BattleshipGenerator() {

            @Override
            public String generateMap() {

                String map = new String();

                for(int i = 0; i < 100; i++)
                    map += ".";

                map = generateQuad(map);
                map = generateTriple(map);
                map = generateDouble(map);
                map = generateSingle(map);

                System.out.println(map);
                return map;
            }

            public String generateSingle(String map){

                String re = map;
                int location;

                int shipsGenerated = 0;

                while(shipsGenerated < 4){

                    location = (int) ((Math.random() * 100));
                    if(checkSingle(re,location)){
                        re = addSingle(re,location);
                        shipsGenerated++;
                    }
                }
                return re;
            }

            public boolean checkSingle(String map, int location){
                return checkLocation(location,map);
            }

            public String addSingle(String map, int location){
                return map.substring(0,location) + '#' + map.substring(location + 1);
            }

            public String generateDouble(String map){

                String re = map;
                int location;
                int direction = 0;
                int shipsGenerated = 0;

                while (shipsGenerated < 3){

                    location = (int) ((Math.random() * 2));

                    switch (location){
                        case 0:
                            direction = 1;
                            break;
                        case 1:
                        default:
                            direction = 0;
                            break;
                    }

                    location = (int) ((Math.random() * 99));

                    if(checkDouble(re,location,direction)){
                        re = addDouble(re,location,direction);
                        shipsGenerated++;
                    }
                }
                return re;
            }

            public String addDouble(String map, int location, int direction){

                String re = new String();

                if(direction == 0){
                    re = map.substring(0,location) + '#' + map.substring(location + 1);
                    re = re.substring(0,location + 1) + '#' + re.substring(location + 2);
                }
                else
                {
                    if(location < 100)
                        re = map.substring(0,location) + '#' + map.substring(location + 1);
                    else
                        re = map.substring(0,location) + '#';

                    re = re.substring(0,location - 10) + '#' + re.substring(location - 9);
                }

                return re;
            }

            public boolean checkDouble(String map, int location, int direction){

                if(checkSingle(map,location)){
                    if(direction == 0){
                        if((location + 1) % 10 != 0 && checkLocation(location + 1, map)){
                            return true;
                        }
                    }
                    else
                    {
                        if(location > 9 && checkLocation(location - 10, map)){
                            return true;
                        }
                    }
                }
                return false;
            }

            public String generateTriple(String map){
                String re = map;
                int location;
                int direction1;
                int direction2;
                int shipsGenerated = 0;

                while (shipsGenerated < 2){
                    location = (int) ((Math.random() * 4));

                    switch (location){
                        case 0:
                            direction1 = 1;
                            direction2 = 1;
                            break;
                        case 2:
                            direction1 = 1;
                            direction2 = 0;
                            break;
                        case 3:
                            direction1 = 0;
                            direction2 = 1;
                            break;
                        case 1:
                        default:
                            direction1 = 0;
                            direction2 = 0;
                            break;
                    }

                    location = (int) ((Math.random() * 99));

                    if(checkTriple(re,location,direction1,direction2)){
                        re = addTriple(re,location,direction1, direction2);
                        shipsGenerated++;
                    }

                }

                return re;
            }

            public boolean checkTriple(String map, int location, int direction1, int direction2){

                if(checkSingle(map,location)){
                    if(direction1 == 0){
                        if((location + 1) % 10 != 0 && checkDouble(map, location + 1, direction2)){
                            return true;
                        }
                    }
                    else
                    if(location > 9 && checkDouble(map,location - 10, direction2)){
                        return true;
                    }
                }
                return false;
            }

            public String addTriple(String map, int location, int direction1, int direction2){

                String re = map;
                re = addSingle(re,location);

                if(direction1 == 0){
                    re = addDouble(re,location + 1, direction2);
                }
                else re = addDouble(re, location - 10, direction2);

                return re;
            }

            public String generateQuad(String map){
                String re = map;
                int location;
                int direction1 = 0;
                int direction2 = 0;
                int shipsGenerated = 0;

                while(shipsGenerated < 1){

                    location = (int) ((Math.random() * 4));

                    switch (location){
                        case 0:
                            direction1 = 1;
                            direction2 = 1;
                            break;
                        case 2:
                            direction1 = 1;
                            direction2 = 0;
                            break;
                        case 3:
                            direction1 = 0;
                            direction2 = 1;
                            break;
                        case 1:
                        default:
                            direction1 = 0;
                            direction2 = 0;
                            break;
                    }

                    location = (int) ((Math.random() * 99));

                    if(checkQuad(map, location, direction1, direction2))
                    {
                        re = addQuad(map, location, direction1, direction2);
                        shipsGenerated++;
                    }
                }
                /*for(int i = 0; i < 100; i ++){
                    System.out.print(re.charAt(i));
                    if((i + 1) % 10 == 0) System.out.println();
                }*/

                return re;
            }

            public boolean checkQuad(String map, int location, int direction1, int direction2){

                if(checkDouble(map,location,direction1)){
                    if( direction1 == 0 && (location + 2) % 10 != 0){
                        if(checkDouble(map,location + 2, direction2)){
                            return true;
                        }
                    }
                    else if(direction1 == 1)
                    {
                        if((location + 1) % 10 != 0 && location > 9 && checkDouble(map,location + 1, direction2) ){
                            return true;
                        }
                    }
                }

                return false;
            }

            public String addQuad(String map, int location, int direction1, int direction2){
                String re;

                re = addDouble(map,location,direction1);

                if(direction1 == 0) {
                    re = addDouble(re, location + 2, direction2);
                }
                else re = addDouble(re, location + 1, direction2);

                return re;
            }

            public boolean checkLocation(int location, String map){

                if(map.charAt(location) == '#') return false; //sprawdzenie obecnej lokacji
                else if((location > 0 && (location % 10) != 0 && map.charAt(location - 1) == '#')) return false; //sprawdzenie lewej strony
                else if((location < 99 && (location + 1) % 10 != 0 && map.charAt(location + 1) == '#')) return false;//sprawdzenie prawej strony
                else if((location < 90 && map.charAt(location + 10) == '#')) return false;//sprawdzenie dolu
                else if((location > 9 && map.charAt(location - 10) == '#')) return false;//sprawdzenie góry
                else if((location > 9) && (location + 1) % 10 != 0 && map.charAt(location - 9) == '#') return false; // sprawdzenie prawa gora
                else if((location < 89) && (location + 1) % 10 != 0 && map.charAt(location + 11) == '#') return false; //sprawdzenie prawy dol
                else if((location > 10) && (location % 10) != 0 && map.charAt(location - 11) == '#') return false; //sprawdzenie lewa gora
                else if((location < 90) && (location % 10) != 0 && map.charAt(location + 9) == '#') return false;//sprawdzenie lewy dol
                else return true;
            }

        };
    }

}
