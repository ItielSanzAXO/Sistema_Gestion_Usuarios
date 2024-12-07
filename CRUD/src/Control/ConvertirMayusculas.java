/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 *
 * @author ItielSanz
 */
public class ConvertirMayusculas extends PlainDocument{
    
    public void insertString(int offset, String srt,AttributeSet attr) throws BadLocationException{
        super.insertString(offset, srt.toUpperCase(), attr);
    }

}
