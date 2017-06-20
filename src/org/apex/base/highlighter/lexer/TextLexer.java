/*
 * TextLexer.java
 *
 * Created on May 28, 2007, 7:31 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.apex.base.highlighter.lexer;

import java.io.IOException;
import java.io.Reader;

/**
 *
 * @author Mrityunjoy_Saha
 */
public class TextLexer implements Lexer{
    
    /** Creates a new instance of TextLexer */
    public TextLexer() {
    }

    public Token getNextToken() throws IOException {
        return null;
    }

    public void reset(Reader reader, int yyline, int yychar, int yycolumn) throws IOException {
    }
    
}
