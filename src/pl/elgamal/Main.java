package pl.elgamal;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.SecureRandom;
import java.util.Random;

public class Main {

    private BigInteger P;
    private BigInteger G;
    private BigInteger X; // private key
    private BigInteger Y;
    private BigInteger K;
    private BigInteger A;
    private BigInteger B;
    private static BigInteger M = BigInteger.valueOf(10);



    private static BigInteger generatePrimeP() {
        return BigInteger.probablePrime(512, new Random());
    }

    private static BigInteger generateK(BigInteger p) {
        BigInteger K = BigInteger.probablePrime(p.bitLength(), new SecureRandom());
        BigInteger pMinusOne = p.subtract(BigInteger.ONE);

        while (K.gcd(pMinusOne).compareTo(BigInteger.ONE) != 0) {
            K = BigInteger.probablePrime(p.bitLength(), new SecureRandom());
        }
        return K;
    }



    public static BigInteger[] signMessage(BigInteger M) {
        BigInteger p, g, x, y, k, a, b, pMinusOne, inverseK = null;
        boolean isExceptionThrown;

        do {
            p = generatePrimeP();
            g = new BigInteger(p.bitLength()-1, new Random());//BigInteger.valueOf(9);
            x = new BigInteger(p.bitLength()-2, new Random());//BigInteger.valueOf(23);
            y = g.modPow(x, p);
            k = generateK(p);//BigInteger.valueOf(125);//generateK(p);
            a = g.modPow(k, p);
            pMinusOne = p.subtract(BigInteger.ONE);

            try {
                inverseK = k.modInverse(pMinusOne);
            } catch (ArithmeticException e) {
                isExceptionThrown = true;
                continue;
            }

            isExceptionThrown = false;
        } while (isExceptionThrown);

        b = M.subtract(x.multiply(a)).multiply(inverseK).mod(pMinusOne);

        System.out.println("p = " + p);
        System.out.println("g = " + g);
        System.out.println("y = " + y);
        System.out.println("x (private key) = " + x);
        System.out.println("k = " + k);
        System.out.println("a = " + a);
        System.out.println("b = " + b);
        checkCorrectness(computeV1(g,M,p), computeV2(y,a,b,p));
        return new BigInteger[] { p,g,x,y,k,a,b };
    }

    public static BigInteger computeV1(BigInteger g, BigInteger M, BigInteger p) {
        return g.modPow(M, p);
    }

    public static BigInteger computeV2(BigInteger y, BigInteger a, BigInteger b, BigInteger p) {
        BigInteger yr = y.modPow(a, p);
        BigInteger rs = a.modPow(b, p);
        return yr.multiply(rs).mod(p);
    }

    public static String checkCorrectness(BigInteger v1, BigInteger v2) {
        if (v1.compareTo(v2) == 0)
           return "CORRECT";
        else
            return "UNCORRECT";
    }

    public static byte[] convertFile(Path path) {
        byte[] fileInBytesArray = null;

        try {
            fileInBytesArray = Files.readAllBytes(path);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fileInBytesArray;
    }


}