package com.company;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {
        Set<String> notUsedBricks = new HashSet<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader("plik.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                String brick = line.replaceAll("[\\\\r\\\\n]", "");
                if (sprawdzWarunki(brick) && brick.contains("O")) {
                    notUsedBricks.add(brick);
                }
                //ETAP 1

                // Wykonaj operacje na odczytanej linii
                if (sprawdzWarunki(brick)) {
                    System.out.println("Linia spełnia warunki: " + brick);
                } else {
                    System.out.println("Linia nie spełnia warunków: " + brick);
                }
                System.out.println("Odczytano: " + brick);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Linie ze znakiem 'O' spełniające warunki i dodane do zbioru notUsedBricks:");
        int countNotUsedBricks = 0;
        for (String brickWithO : notUsedBricks) {
            countNotUsedBricks = notUsedBricks.size();
        }
        System.out.println(countNotUsedBricks);
        System.out.println(notUsedBricks);
    }
    private static boolean sprawdzWarunki(String line) {
        String regex = "^\\d+:[(A-O)]{4}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(line);

        return matcher.matches();
    }
}
