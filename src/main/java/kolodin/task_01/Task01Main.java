package kolodin.task_01;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static java.nio.file.StandardCopyOption.*;

/**
 * Точка входа к заданию 1
 */
public class Task01Main {
    public static void main(String[] args) {
        try {
            fileBackup(".", "./backup");
        } catch (IOException e){
            e.printStackTrace();
        }
    }


    /**
     * функция, создающяя резервную копию всех файлов в директории(без поддиректорий)
     * во вновь созданную папку ./backup
     * @param s_dirFromBackup директория источник
     * @param s_dirToBackup директория назначения
     */
    public static void fileBackup(String s_dirFromBackup, String s_dirToBackup) throws IOException {
        Path dirTo = Path.of(s_dirToBackup);
        if (!Files.exists(dirTo)) {
            try{
                Files.createDirectory(dirTo);
            } catch (IOException e){
                throw new RuntimeException(e);
            }
         }
        try (Stream<Path> stream = Files.list(Path.of(s_dirFromBackup))) {
            stream.filter(volume -> volume.toFile().isFile())
                    .forEach(pathFrom -> {
                        Path pathTo = Paths.get(s_dirToBackup, pathFrom.toFile().getName());
                        try {
                            Files.copy(pathFrom, pathTo, REPLACE_EXISTING);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }
}
