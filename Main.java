package test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import static java.util.concurrent.CompletableFuture.supplyAsync;

public class Main {

    public static void main(String[] args) {
        if(args.length != 1) {
            System.out.println("usage: java Main \"path_to_folder\"");
            System.exit(1);
        }


        List<File> filesInFolder = null;
        try {
            //filesInFolder = Files.walk(Paths.get("F://media//tt"))
            filesInFolder = Files.walk(Paths.get(args[0]))
                    .filter(Files::isRegularFile)
                    .map(Path::toFile)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }


        List<Provider> providers = new ArrayList<>();

        for (File file : filesInFolder) {
            providers.add(new Provider(file));
        }



        ArrayList<CompletableFuture<byte[]>> futures = new ArrayList<>();
        for (Provider provider : providers) {
            CompletableFuture startFuture = CompletableFuture
                    .supplyAsync(provider)
                    .thenApply(Worker::getSHA)
                    .thenAcceptAsync(provider::saveFile);

            futures.add(startFuture);
        }


        for (CompletableFuture<byte[]> future : futures) {
            try {
                future.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }




    }
}
