package com.mycompany.maven_exam;

import com.google.common.collect.Iterables;
import java.util.ArrayList;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JRadioButtonMenuItem;

/**
 *
 * @author Амина
 */
public class TextWork {
    private ArrayList<String> new_text;
    TextWork textWorker;
    
    public ArrayList<String> changeText(ArrayList<String> text, ArrayList<String> ru_stop_words, ArrayList<String> eng_stop_words, JRadioButtonMenuItem UseRusButton, JRadioButtonMenuItem UseEngButton, JCheckBoxMenuItem UseRuStopWordsButton, JCheckBoxMenuItem UseEngStopWordsButton)
    {
        textWorker = new TextWork();
        text = textWorker.toLowerCase(text);
        text = textWorker.deleteSigns(text);
        if(UseRusButton.isSelected())
        {
            text = textWorker.deleteNotRus(text);
        }
        if(UseEngButton.isSelected())
        {
            text = textWorker.deleteNotEng(text);
        }
        if(UseRuStopWordsButton.getState())
        {
            textWorker.useStopWords(text, ru_stop_words);
        }
        if(UseEngStopWordsButton.getState())
        {
            textWorker.useStopWords(text, eng_stop_words);
        }
        return text;
    }
    
    
    public ArrayList<String> toLowerCase(ArrayList<String> text)
    {
        new_text = new ArrayList<>();
        for(int i=0; i<text.size(); i++)
        {
            new_text.add(text.get(i).toLowerCase());
        }
        return new_text;
    }
    
    public ArrayList<String> deleteSigns(ArrayList<String> text)
    {
        new_text = new ArrayList<>();
        for(int i=0; i<text.size(); i++)
        {
            String symbol = text.get(i).replaceAll("[^\\wа-я]", "").replaceAll("\\d", "");
            new_text.add(symbol);
        }
        new_text.removeIf(word -> word.equals(""));
        new_text.removeIf(word -> word.equals("\\pP+"));
        return new_text;
    }
    
    public ArrayList<String> deleteNotRus(ArrayList<String> text)
    {
        new_text = new ArrayList<>();
        for(int i=0; i<text.size(); i++)
        {
            new_text.add(text.get(i).replaceAll("[^а-я]", ""));
        }
        new_text.removeIf(word -> word.equals(""));
        return new_text;
    }

    public ArrayList<String> deleteNotEng(ArrayList<String> text)
    {
        new_text = new ArrayList<>();
        for(int i=0; i<text.size(); i++)
        {
            new_text.add(text.get(i).replaceAll("[^a-z]", ""));
        }
        new_text.removeIf(word -> word.equals(""));
        return new_text;
    }
    
    public void useStopWords(ArrayList<String> text, ArrayList<String> ru_stop_words)
    {
        for(String wordToRemove : Iterables.skip(ru_stop_words, 1)) 
        { 
            text.removeIf(word -> word.equals(wordToRemove));
        }
    }
}
