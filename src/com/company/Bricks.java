package com.company;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Bricks {
    Map<String, Integer> listBricks = new HashMap<>(); // klocki z listy spisanej przez Pania, gdzie: String - nazwa klocka, Integer- ilosc powtorzen danego klocka
    Map<String, Integer> bricksStageOne = new HashMap<>();
    Map<String, Integer> bricksStageTwo = new HashMap<>();
    static Map<Integer, HashMap<String, Integer>> instructionsStageOne = new HashMap<>();
    Map<Integer, HashMap<String, Integer>> instructionsStageTwo = new HashMap<>(); // klocki z listy spisanej przez dzieci, gdzie: String - numer instrukcji, Hashmap - lista klockow z pojedynczej instrukcji(analogicznie do spisu intrukcji pani )
    Map<String, Integer> notUsedBricks = new HashMap<>();
    Map<Integer, Integer> countNotUsedBricks = new HashMap<>();
    Map <Integer, Map <String, Integer>>missingBricksForInstruction = new HashMap<>();
    void readAndStoreBricks(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;

        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(":");
            int number = Integer.parseInt(parts[0]);
            String code = parts[1];
            code = code.replaceAll("[\\\\r\\\\n]", "");
            String brick = line.replaceAll("[\\\\r\\\\n]", "");//czy mi to potrzebne

            if (isValidLine(brick) && code.contains("O")) {
                countNotUsedBricks.put(number, countNotUsedBricks.getOrDefault(number, 0) + 1);
                notUsedBricks.put(code, notUsedBricks.getOrDefault(code, 0) + 1);
            } else if (isValidLine(brick) && number == 0) {
                listBricks.put(code, listBricks.getOrDefault(code, 0) + 1);
            } else if (isValidLine(brick) && number % 3 == 0) {

                //biore kolejny numer%3, i jesli kazdy klocek z nr x = klocek z 0 to sciagam go z mapy 0 i dodaje do mapy countBricksStageOne,
                // jesli brakuje chociaz jednego to przerywam i zapisuje do niewykonanych instrukcji
                bricksStageOne.put(code, bricksStageOne.getOrDefault(code, 0) + 1);
                instructionsStageOne.put(number, (HashMap<String, Integer>) bricksStageOne);
            } else if (isValidLine(brick)) {
                //biore kolejny pozostaly numer, i jesli kazdy klocek z nr x = klocek z 0 to sciagam go z mapy 0 i dodaje do mapy countBricksStageOne,
                // jesli brakuje chociaz jednego to przerywam i zapisuje do niewykonanych instrukcji
                bricksStageTwo.put(code, bricksStageTwo.getOrDefault(code, 0) + 1);
                instructionsStageTwo.put(number, (HashMap<String, Integer>) bricksStageTwo);
            }
        }


        int notUsedBricksCounter = countNotUsedBricks.values().stream().mapToInt(Integer::intValue).sum();

        System.out.println("Klocki po wczytaniu: ");
        System.out.println("Nie uzyte klocki: " + notUsedBricksCounter);
        System.out.println(listBricks);
        System.out.println(instructionsStageOne);
        System.out.println(instructionsStageTwo);
        System.out.println(notUsedBricks);
        reader.close();
    }

    int processInstructions(Map<Integer, HashMap<String, Integer>> instructions){
        int numberOfUsedBricks = 0;
        instructions.entrySet().stream()
                .forEach(entry -> {
                    Integer key = entry.getKey();
                    HashMap<String, Integer> value = entry.getValue();
                    System.out.println("Instrukcja: " + key + ", klocki do instrukcji: " + value);
                    if(checkOneInstruction(key,value)) {
                        System.out.println("Instrukcja moze byc wykonana");
                        getBricksFromBox(value);
                    }

                });
        System.out.println(missingBricksForInstruction.get(6));
        return numberOfUsedBricks;
    }

    boolean checkOneInstruction(int instructionId, HashMap<String, Integer>instruction){
        AtomicBoolean correctInstruction = new AtomicBoolean(true);
        HashMap<String, Integer>missingBricks = new HashMap<>();
        instruction.entrySet().stream()
                .forEach(entry -> {
                    String brick = entry.getKey();
                    Integer neededBricks = entry.getValue();

                    //check if you have bricks for instrucion
                    System.out.println(brick + " " + neededBricks);
                    System.out.println("Bricks in a box " + listBricks.get(brick));
                    int availableBricks = listBricks.get(brick);
                    if(availableBricks < neededBricks) {
                        correctInstruction.set(false);
                        System.out.println("You dont have enough bricks");
                        missingBricks.put(brick, neededBricks - availableBricks);
                    }
                });
        missingBricksForInstruction.put(instructionId, missingBricks);
        return correctInstruction.get();
    }

    void getBricksFromBox(HashMap<String, Integer> instruction) {
        instruction.entrySet().stream()
                .forEach(entry -> {
                    String brick = entry.getKey();
                    Integer neededBricks = entry.getValue();
                    listBricks.put(brick, listBricks.get(brick) - neededBricks);
                });
    }

    public static void main(String[] args) {
        Bricks bricks = new Bricks();
        try {
            bricks.readAndStoreBricks("plik.txt");
            bricks.processInstructions(instructionsStageOne);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    private static boolean isValidLine(String line) {
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
