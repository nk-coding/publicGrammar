package com.nkcoding;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        ArrayList<GrammarTupel> grammar = new ArrayList<>();
        //generate a grammer for the exercise
        grammar.add(new GrammarTupel("Ba", "aB"));
        grammar.add(new GrammarTupel("Ca", "aC"));
        grammar.add(new GrammarTupel("CB", "BC"));
        grammar.add(new GrammarTupel("aB", "ab"));
        grammar.add(new GrammarTupel("bB", "bb"));
        grammar.add(new GrammarTupel("bC", "bc"));
        grammar.add(new GrammarTupel("cC", "cc"));


        List<String> lang = new ArrayList<>();
        //don't do first two steps, because by this you can get words with the desired lenth
        //this basicly ignores all Ableitungen with an S
        lang.add("aBCaBCaBC");
        ArrayList<String> found = new ArrayList<>();
        boolean foundAll = false;
        while (!foundAll) {
            foundAll = true;
            lang = nAbl(lang, grammar);
            ArrayList<String> toRemove = new ArrayList<>();
            for (String s : lang) {
                //check if it only contains lowercase chars
                if (s.toLowerCase().equals(s)) {
                    toRemove.add(s);
                    if (!found.contains(s)) {
                        found.add(s);
                    }
                } else {
                    foundAll = false;
                }
            }
            //remove all final words
            lang.removeAll(toRemove);
            //remove duplicates for performance
            lang = lang.stream().distinct().collect(Collectors.toList());
        }
        System.out.println(found);
    }

    //the most important method
    public static List<String> nAbl(List<String> old, List<GrammarTupel> grammar) {
        ArrayList<String> newWords = new ArrayList<>();
        for (String word : old) {
            char[] w = word.toCharArray();
            for (GrammarTupel gram : grammar) {
                char s = gram.left.charAt(0);
                for (int x = 0; x < w.length - gram.left.length() + 1; x++) {
                    if (w[x] == s) {
                        String possibleSub = word.substring(x, x + gram.left.length());
                        if (possibleSub.equals(gram.left)) {
                            newWords.add(word.substring(0, x) + gram.right + word.substring(x + gram.left.length()));
                        }
                    }
                }
            }
        }
        return newWords;
    }

    //generate sigma stern over with specific set of chars and specific length (only words of length l, not with lower length)
    public static List<String> sigs(int l, char... c) {
        List<String> sigs = new ArrayList<>();
        for (char ch : c) {
            sigs.add("" + ch);
        }
        for (int x = 1; x < l; x++) {
            sigs = append(c, sigs);
        }
        return sigs;
    }

    public static List<String> append(char[] c, List<String> old) {
        List<String> newList = new ArrayList<>();
        for (String s : old) {
            for (char ch : c) {
                newList.add(s + ch);
            }
        }
        return newList;
    }

    //count the amount of a specific char in s
    public static int countC(char c, String s) {
        int count = 0;
        for(int i = 0; i < s.length(); i++) {
            if(s.charAt(i) != c)
                count++;
        }
        return count;
    }
}
