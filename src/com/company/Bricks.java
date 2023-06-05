package com.company;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Bricks {

    public static void main(String[] args) {
        Map<String, Integer> listBricks = new HashMap<>(); // klocki z listy spisanej przez Pania, gdzie: String - nazwa klocka, Integer- ilosc powtorzen danego klocka
        Map<String, Integer> bricksStageOne = new HashMap<>();
        Map<String, Integer> bricksStageTwo = new HashMap<>();
        Map<Integer, HashMap<String, Integer>> instructionsStageOne = new HashMap<>();
        Map<Integer, HashMap<String, Integer>> instructionsStageTwo = new HashMap<>(); // klocki z listy spisanej przez dzieci, gdzie: String - numer instrukcji, Hashmap - lista klockow z pojedynczej instrukcji(analogicznie do spisu intrukcji pani )
        Map<String, Integer> notUsedBricks = new HashMap<>();
        Map<Integer, Integer> countUsedStageOneBricks = new HashMap<>();
        Map<Integer, Integer> countNotUsedBricks = new HashMap<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader("plik.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                int number = Integer.parseInt(parts[0]);
                String code = parts[1];
                code = code.replaceAll("[\\\\r\\\\n]", "");
                String brick = line.replaceAll("[\\\\r\\\\n]", "");//czy mi to potrzebne

                if (isValidFile(brick) && code.contains("O")) {
                    countNotUsedBricks.put(number, countNotUsedBricks.getOrDefault(number, 0) + 1);
                    notUsedBricks.put(code, notUsedBricks.getOrDefault(code, 0) + 1);
                } else if (isValidFile(brick) && number == 0) {
                    listBricks.put(code, listBricks.getOrDefault(code, 0) + 1);
                } else if (isValidFile(brick) && number % 3 == 0) {

                    //biore kolejny numer%3, i jesli kazdy klocek z nr x = klocek z 0 to sciagam go z mapy 0 i dodaje do mapy countBricksStageOne,
                    // jesli brakuje chociaz jednego to przerywam i zapisuje do niewykonanych instrukcji
                    bricksStageOne.put(code, bricksStageOne.getOrDefault(code, 0) + 1);
                    instructionsStageOne.put(number, (HashMap<String, Integer>) bricksStageOne);
                } else if (isValidFile(brick)) {
                    //biore kolejny pozostaly numer, i jesli kazdy klocek z nr x = klocek z 0 to sciagam go z mapy 0 i dodaje do mapy countBricksStageOne,
                    // jesli brakuje chociaz jednego to przerywam i zapisuje do niewykonanych instrukcji
                    bricksStageTwo.put(code, bricksStageTwo.getOrDefault(code, 0) + 1);
                    instructionsStageTwo.put(number, (HashMap<String, Integer>) bricksStageTwo);
                }
            }


            int notUsedBricksCounter = countNotUsedBricks.values().stream().mapToInt(Integer::intValue).sum();

            System.out.println(notUsedBricksCounter);
            System.out.println(countNotUsedBricks);
            System.out.println(listBricks);
            System.out.println(instructionsStageOne);
            System.out.println(instructionsStageTwo);
            System.out.println(notUsedBricks);
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean isValidFile(String line) {
        String regex = "^\\d+:[(A-O)]{4}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(line);

        return matcher.matches();
    }


//    private int CountUsedStageOneBricks
//    private int CountUsedStageTwoBricks
//    private int CountUnusedBricks
//    private int CountMissingBricks
//    private int CountBuildedStructures
//    private int CountUnbuildedStructures

//    Liczbę klocków użytych w etapie I
//    Liczbę klocków użytych w etapie II
//    Liczbę klocków, które pozostały w pudełku po zakończeniu budowania
//    Łączną liczbę klocków, których brakowało w pudełku podczas realizacji poszczególnych instrukcji
//    Liczbę budowli, które udało się zbudowac
//    Liczbę budowli, których nie udało się zbudować
}
