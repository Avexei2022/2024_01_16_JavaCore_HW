package kolodin.task_02;

import java.io.*;
import java.util.Arrays;
import java.util.Random;

/**
 * Точка входа к заданию 2
 */
public class Task02Main {
    public static void main(String[] args) {
        String file = "TicTacToe.bin";
        int[] ticTacToe_value = createRandomGameValues(9);
        System.out.println("\nИсходный массив значений игры: "
                + Arrays.toString(ticTacToe_value));

        System.out.println("\nСохранение результатов в файл: " + file);
        try {
            saveResult(file, ticTacToe_value);
            System.out.println("\nРезультат игры успешо сохранен в файл: " + file);
        } catch (IOException e){
            System.out.println(e.getMessage());
        }

        System.out.println("\nЗагрузка результатов игры из файла: " + file);
        try {
            int[] loadedArray = loadResult(file);
            System.out.println("\nЗагруженный из файла " + file
                    + " массив значений игры: " + Arrays.toString(loadedArray));
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
   }

    /**
     * Эмулятор случайных значений игры
     * @param size размер массива
     * @return массив целых чисел в диапазоне от 0 до 3 включительно
     */
    public static int[] createRandomGameValues(int size) {
        return Arrays.stream(new int[size])
                .map(v-> new Random().nextInt(0, 4))
                .toArray();
    }

    /**
     * Сохранение результатов в файл
     * @param file - имя файла
     * @param array - массив целых чисел
     * @throws IOException
     */
    private static void saveResult(String file, int[] array) throws IOException{
        try (FileOutputStream fos = new FileOutputStream(file)){
            for (int i = 0; i < array.length; i +=3) {
                int[] row = {array[i], array[i+1], array[i+2]};
                fos.write(codingRow(row));
            }
        } catch (IOException e){
            throw new IOException("Ошибка записи в файл" + e.getMessage());
        }
    }

    /**
     * Загрузчик данных из файла
     * @param file - имя файла
     * @return массив целых чисел
     * @throws IOException
     */
    private static int[] loadResult(String file) throws IOException{
        int[] array = new int[9];
        try (FileInputStream fis = new FileInputStream(file)){
            for (int i = 0; i < array.length; i +=3) {
                int[] row = decodingRow(fis.read());
                array[i] = row[0];
                array[i+1] = row[1];
                array[i+2] = row[2];
            }
        } catch (IOException e){
            throw new IOException("Ожибка загрузки данных из файла" + e.getMessage());
        }
        return array;
    }

    /**
     * Кодировщик массива из целых чисел в диапазоне от 0 до 3 включительно
     * @param row входящий массив целых чисел
     * @return возвращает целое число
     */
    public static int codingRow(int[] row) {
        int encodedRow = 0;
        for (int i = 0; i < row.length; i++) {
            encodedRow = encodedRow << 2;
            encodedRow = encodedRow | row[i];
        }
        return encodedRow;
    }

    /**
     * Декодирование значений
     * @param row целое число для декодирования
     * @return возвращает массив из трех целых чисел в диапазоне от 0 до 3 включительно
     */
    public static int[] decodingRow(int row) {
        int[] array = new int[3];
        for (int i = array.length - 1; i >= 0; i--) {
            int value = row & 3;
            row = row >> 2;
            array[i] = value;
        }
        return array;
    }
}

