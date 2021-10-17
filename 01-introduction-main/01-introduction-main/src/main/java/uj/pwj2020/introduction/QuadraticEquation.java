package uj.pwj2020.introduction;

public class QuadraticEquation {

    public double[] findRoots(double a, double b, double c) {

        double[] result;
        double d = (b*b) - (4*a*c);

        double root = Math.sqrt(d);

        if(d > 0){
            result  = new double[2];
            result[0] = (-b + root) / (2*a);
            result[1] = (-b - root) / (2*a);
        }
        else if(d == 0){
            result = new double[1];
            result[0] = (-b + root) / (2*a);
        }
        else
        {
            result = new double[0];
        }

        return result;
    }


}

