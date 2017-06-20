/*
 * DocumentLexers.java
 * Created on May 28, 2007, 5:18 PM
 *
 * Copyright (C) 2008 Mrityunjoy Saha
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package org.apex.base.highlighter.style;

import org.apex.base.highlighter.lexer.CLexer;
import org.apex.base.highlighter.lexer.HTMLLexer1;
import org.apex.base.highlighter.lexer.JavaLexer;
import org.apex.base.highlighter.lexer.PerlLexer;
import org.apex.base.highlighter.lexer.PlainLexer;
import org.apex.base.highlighter.lexer.PropertiesLexer;
import org.apex.base.highlighter.lexer.SQLLexer;

/**
 * Contains constants for document lexers.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class DocumentLexers {

    /**
     * Default lexer.
     */
    public static final Object DEFAULT_LEXER =
            PlainLexer.class;
    /**
     * A lexer for Java document.
     */
    public static final Object JAVA_LEXER =
            JavaLexer.class;
    /**
     * A lexer for JavaScript document.
     */
    public static final Object JAVASCRIPT_LEXER =
            PlainLexer.class;
    /**
     * A lexer for XML document.
     */
    public static final Object XML_LEXER =
            HTMLLexer1.class;
    /**
     * A lexer for JSP document.
     */
    public static final Object JSP_LEXER =
            HTMLLexer1.class;
    /**
     * A lexer for HTML document.
     */
    public static final Object HTML_LEXER =
            HTMLLexer1.class;
    /**
     * A lexer for C document.
     */
    public static final Object C_LEXER =
            CLexer.class;
    /**
     * A lexer for Perl document.
     */
    public static final Object PERL_LEXER =
            PerlLexer.class;
    /**
     * A lexer for SQL document.
     */
    public static final Object SQL_LEXER =
            SQLLexer.class;
    /**
     * A lexer for Properties document.
     */
    public static final Object PROPERTIES_LEXER =
            PropertiesLexer.class;

    /**
     * Constructs a new instance.
     */
    private DocumentLexers() {
    }
}