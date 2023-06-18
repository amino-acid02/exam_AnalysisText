package com.mycompany.maven_exam;

import com.google.common.collect.Iterables;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.SwingConstants;

/**
 *
 * @author Амина
 */
public class TextWork {
    private ArrayList<String> new_text;
    private TextWork textWorker;
    
    public ArrayList<String> changeText(ArrayList<String> text, ArrayList<String> ru_stop_words, ArrayList<String> eng_stop_words, JRadioButtonMenuItem UseRusButton, JRadioButtonMenuItem UseEngButton, JCheckBoxMenuItem UseRuStopWordsButton, JCheckBoxMenuItem UseEngStopWordsButton, JList ruStopWordsList, JList engStopWordsList)
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
            textWorker.useStopWords(text, ru_stop_words, ruStopWordsList);
        }
        if(UseEngStopWordsButton.getState())
        {
            textWorker.useStopWords(text, eng_stop_words, engStopWordsList);
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
            String word = text.get(i).replaceAll("[^\\wа-я]", "").replaceAll("\\d", "");
            new_text.add(word);
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
    
    public void useStopWords(ArrayList<String> text, ArrayList<String> stop_words, JList stopWordsList)
    {
        List selectedValues = stopWordsList.getSelectedValuesList();
        for(String wordToRemove : Iterables.skip(stop_words, 1)) 
        { 
            if(selectedValues.contains(wordToRemove)) //слова, которые не хотим удалять из текста
            {
                continue;
            }
            text.removeIf(word -> word.equals(wordToRemove));
        }
    }
    
    public DefaultListModel createStopWordsList(ArrayList<String> stop_words)
    {
        DefaultListModel list = new DefaultListModel();
        for (String word : Iterables.skip(stop_words, 1))
        {
            list.addElement(word);
        }
        return list;
    }
    
    public void uploadStopWords(ArrayList<String> stop_words, JList stopWordsList, JDialog uploadStopWordsMessage, JCheckBoxMenuItem useStopWordsButton)
    {
        textWorker = new TextWork();
        ImageIcon img = new ImageIcon(System.getProperty("user.dir")+"/resources/add.png");
        
        stopWordsList.setModel(textWorker.createStopWordsList(stop_words));
        uploadStopWordsMessage.setVisible(true);
        uploadStopWordsMessage.setBounds(300,300,250,150);
        useStopWordsButton.setIcon(img);
        useStopWordsButton.setHorizontalTextPosition(SwingConstants.LEFT);
    }
}
