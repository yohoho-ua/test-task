package test;

import java.io.*;
import java.util.function.Supplier;

public class Provider implements Supplier<byte[]> {
    private File file;

    public Provider(File file) {
        this.file = file;
    }

    public File getFile() {
        return file;
    }


    @Override
    public byte[] get() {
        InputStream is;
        System.out.println(file);
        byte[] fileData = null;
        try {
            is = new FileInputStream(file);
            fileData = new byte[(int) file.length()];
            is.read(fileData);
            is.close();
            System.out.println(Thread.currentThread().toString() + " - provider read");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fileData;
    }

    public void saveFile(byte[] sha) {
              StringBuilder hexString = new StringBuilder(sha.length * 2);
        for(byte b: sha)
            hexString.append(String.format("%02x", b));
               try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
            writer.append(hexString.toString());
            writer.newLine();
            writer.close();
            System.out.println(Thread.currentThread().toString() + " - provider save");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*private void copyStream(InputStream input, OutputStream output) throws IOException
    {
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = input.read(buffer)) != -1)
        {
            output.write(buffer, 0, bytesRead);
        }
    }*/

}








