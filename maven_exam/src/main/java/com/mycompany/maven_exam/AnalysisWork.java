package com.mycompany.maven_exam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Амина
 */
public class AnalysisWork {
    private HashMap<String, Double> frequency;
    private AnalysisWork analyst;
    
    public HashMap<String, Double> calcFreq(ArrayList<String> text)
    {
        double scale = Math.pow(10, 5);
        frequency = new HashMap<>();
        for(String word : text)
        {
            if (frequency.containsKey(word)) 
            {
                double value = frequency.get(word);
                frequency.put(word, ++value);
            }
            else 
            {
                frequency.put(word, 1.0);
            }
        }
        for(String key : frequency.keySet())
        {
            double freq = Math.ceil( frequency.get(key)/text.size() * scale) / scale;
            frequency.put(key, freq);
        }
        frequency = frequency.entrySet().stream().sorted(Collections.reverseOrder(Map.Entry.comparingByValue())).collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue,(e1, e2) -> e1,LinkedHashMap::new));
        return frequency;
    }
    
    public DefaultTableModel makeTable(HashMap<String, Double> frequency, JTable FreqTable)
    {
        DefaultTableModel freq_table = (DefaultTableModel) FreqTable.getModel();
        for (Map.Entry<String, Double> entry : frequency.entrySet()) 
        {
            freq_table.addRow(new Object[] {entry.getKey(), entry.getValue()});
        }
        return freq_table;
    }
    
    public void analyzeProcess(ArrayList<String> text, ArrayList<String> changed_text, JTable FreqTable, DefaultTableModel freq_table, JLabel NumberWordsField, JLabel NumberDelWordsField, JLabel PopularWordField, JLabel UnpopularWordField)
    {
        analyst = new AnalysisWork();
        frequency = new HashMap<>();
        frequency = analyst.calcFreq(changed_text);
        freq_table = (DefaultTableModel) FreqTable.getModel();
        freq_table.getDataVector().removeAllElements();
        freq_table = analyst.makeTable(frequency, FreqTable);
        Object popularWord = freq_table.getValueAt(0, 0);
        Object unpopularWord = freq_table.getValueAt(freq_table.getRowCount()-1, 0);
        NumberWordsField.setText("Количество слов в исходном файле: "+text.size());
        NumberDelWordsField.setText("Количество удаленных слов в файле: "+(text.size()-changed_text.size()));
        PopularWordField.setText("Самое часто встречающееся слово: "+popularWord);
        UnpopularWordField.setText("Самое редко встречающееся слово: "+unpopularWord);
    }
  
}
