package test;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Worker {


     public static synchronized byte[] getSHA(byte [] fileData) {
         System.out.println(Thread.currentThread().toString() + " - worker calculate sha-256");
            byte [] hash = null;
         try {
             MessageDigest md = MessageDigest.getInstance("SHA-256");
             md.update(fileData);
             hash = md.digest();
         } catch (NoSuchAlgorithmException x) {
             x.printStackTrace(System.err);
         }finally {
             return hash;
         }
     }
}
