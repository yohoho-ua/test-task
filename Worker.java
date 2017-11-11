package test;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Worker extends Thread{

    private static Worker instance;
    public static Worker getInstance() {
        return instance == null ? instance = new Worker() : instance;
    }

     public static synchronized byte[] getSHA(byte [] fileData) {
         System.out.println(Thread.currentThread().toString() + " - worker");
            byte [] hash = null;
         try {
             MessageDigest md = MessageDigest.getInstance("SHA-256");
             md.update(fileData);
             hash = md.digest();
            // System.out.println(Arrays.toString(hash));
         } catch (NoSuchAlgorithmException x) {
             x.printStackTrace(System.err);
         }finally {
             return hash;
         }
     }
}
