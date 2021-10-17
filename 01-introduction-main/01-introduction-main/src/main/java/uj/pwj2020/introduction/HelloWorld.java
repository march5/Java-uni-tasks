package uj.pwj2020.introduction;

public class HelloWorld {
    public static void main(String[] args) {

        if(args.length == 0) System.out.println("No input parameters provided");
        else
        {
            for(int i = 0; i < args.length; i++)
                System.out.print(args[i] + "\n");
        }
    }
}
