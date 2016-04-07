package alg;

import java.util.Scanner;

/**
 *
 * @author stankmic
 */
public class Main {
    private static double param;
    private static int cardinality = 0;
    private static double totalLength;
    
    public static void main(String[] args) {
      //input
        Scanner input = new Scanner(System.in);
        double Ax = input.nextDouble();
        double Ay = input.nextDouble();
        double Bx = input.nextDouble();
        double By = input.nextDouble();
        double Cx = input.nextDouble();
        double Cy = input.nextDouble();
        param = input.nextDouble();
      //count result
        totalLength = triangleCircuit(Ax,Ay, Bx,By, Cx,Cy);
        divideTriangle(Ax,Ay, Bx,By, Cx,Cy);
      //output
        System.out.println(cardinality + " " + String.format("%.2f", totalLength));
    }
    
  /*executive functions*/
    
    private static void divideTriangle
            (double Ax, double Ay,
            double Bx, double By,
            double Cx, double Cy)
    {   
        if (triangleCircuit(Ax,Ay, Bx,By, Cx,Cy) <= param) {
            cardinality++;
            return;
        }
        double[] longSide = findLongestTriangleSide(Ax,Ay, Bx,By, Cx,Cy);
        double thirdTanglePtX = longSide[4];
        double thirdTanglePtY = longSide[5];
        
        double[] newPoints = getOneThirdPoints(longSide[0],longSide[1], longSide[2],longSide[3]);
        double Dx = newPoints[0];
        double Dy = newPoints[1];
        double Ex = newPoints[2];
        double Ey = newPoints[3];
        
        totalLength += lineLength(thirdTanglePtX,thirdTanglePtY, Dx,Dy);
        totalLength += lineLength(thirdTanglePtX,thirdTanglePtY, Ex,Ey);
        
        divideTriangle(thirdTanglePtX,thirdTanglePtY, longSide[0],longSide[1], Dx,Dy);
        divideTriangle(thirdTanglePtX,thirdTanglePtY, Dx,Dy, Ex,Ey);
        divideTriangle(thirdTanglePtX,thirdTanglePtY, Ex,Ey, longSide[2],longSide[3]);
    }
    
    private static double lineLength(double Ax,double Ay, double Bx,double By) {
        double xAB = Bx-Ax;
        double yAB = By-Ay;
        return Math.sqrt(xAB*xAB + yAB*yAB);
    }
    
    private static double[] findLongestTriangleSide
            (double Ax,double Ay,
            double Bx,double By,
            double Cx,double Cy)
    {
        double ABlen = lineLength(Ax,Ay, Bx,By);
        double AClen = lineLength(Ax,Ay, Cx,Cy);
        double BClen = lineLength(Bx,By, Cx,Cy);
        
        double max;
        if(ABlen > AClen) {
            max = ABlen;
        } else {
            max = AClen;
        }
        if(BClen > max) {
            double[] BC = {Bx,By, Cx,Cy, Ax,Ay};
            return BC;
        }
        
        if (max==ABlen) {
            double[] AB = {Ax,Ay, Bx,By, Cx,Cy};
            return AB;
        } else {
            double[] AC = {Ax,Ay, Cx,Cy, Bx,By};
            return AC;
        }
    }
    
    private static double triangleCircuit
            (double Ax,double Ay,
            double Bx,double By,
            double Cx,double Cy)
    {
        return lineLength(Ax,Ay, Bx,By) + lineLength(Ax,Ay, Cx,Cy) + lineLength(Bx,By, Cx,Cy);
    }
    
    private static double[] getOneThirdPoints(double Ax,double Ay,
                                        double Bx,double By) {
        double thirdVector_ABx = (Bx-Ax)/3;
        double thirdVector_ABy = (By-Ay)/3;
        
        double[] thirdPointsArray = {
            Ax+thirdVector_ABx, Ay+thirdVector_ABy,
            Bx-thirdVector_ABx, By-thirdVector_ABy
        };
        
        return thirdPointsArray;
    }
}