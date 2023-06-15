package com.example.denchiktranslate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class TranslationUtilities {
    private static ArrayList<String> curses = new ArrayList<>(Arrays.asList("блять", "сука", "нахуй", "ну типа", "пенис", "похуй"));
    private static int len = curses.size();
    public static void setCurses(ArrayList<String> curses){
        TranslationUtilities.curses = curses;
        TranslationUtilities.len = curses.size();
    }
    public static String translateString(String input){
        String parsedInput = input.replace("\t", " ");
        String[] split = parsedInput.split(" ");
        ArrayList<String> splitList = new ArrayList<>(Arrays.asList(split));
        splitList.removeAll(Arrays.asList("", null));
        int wordCount = 0;
        if(split.length == 0){
            return "None";
        }
        String lastWord = split[0];
        boolean newSentence = false;
        StringBuilder output = new StringBuilder();
        Random rand = new Random();
        for(String word: splitList){
            if(word.length() == 0){
                output.append(word).append(" ");
                continue;
            }
            if(newSentence){
                output.append(word.toLowerCase()).append(" ");
                newSentence = false;
            } else {
                output.append(word).append(" ");
            }
            if (rand.nextInt(100) + 1 > 50){
                String curse = curses.get(rand.nextInt(len));
                if(checkEnd(word) || checkNext(splitList, wordCount)){
                    output.append(curse.substring(0, 1).toUpperCase()).append(curse.substring(1)).append(" ");
                    newSentence = true;
                } else{
                    output.append(curse).append(" ");
                }
                lastWord = curse;
                wordCount++;
                continue;
            }
            lastWord = word;
            wordCount++;
        }
        if(checkEnd(lastWord)){
            output.append("Получается так");
        }
        else {
            output.append("получается так");
        }
        return output.toString();
    }

    public static boolean checkNext(ArrayList<String> split, int wordCount) {
        if(split.size() - 1 < wordCount + 1){
            return false;
        }
        if(split.get(wordCount + 1).length() == 0) return true;
        return Character.isUpperCase(split.get(wordCount + 1).charAt(0));
    }


    public static boolean checkEnd(String word){
        char[] check = word.toCharArray();
        if (check.length == 0) return false;
        return check[check.length - 1] == '.' || check[check.length - 1] == '!' || check[check.length - 1] == '?';
    }

}
