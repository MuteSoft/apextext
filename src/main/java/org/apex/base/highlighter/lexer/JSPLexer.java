/*
 * JSPLexer.java
 *
 * Created on May 28, 2007, 7:32 PM
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
public class JSPLexer implements Lexer{
    
    /** Creates a new instance of JSPLexer */
    public JSPLexer() {
    }

    /**
     * 
     * @throws java.io.IOException 
     * @return Returns the next token.
     */
    public Token getNextToken() throws IOException {
        return null;
    }

    /**
     * 
     * @param reader 
     * @param yyline 
     * @param yychar 
     * @param yycolumn 
     * @throws java.io.IOException 
     */
    public void reset(Reader reader, int yyline, int yychar, int yycolumn) throws IOException {
    }
    
}
