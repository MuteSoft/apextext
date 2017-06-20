/*
 * DocumentType.java 
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
package org.apex.base.data;

import java.util.ArrayList;
import org.apex.base.util.StringUtil;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

/**
 * Different document types supported by editor application.
 * @author Mrityunjoy Saha
 * @version 1.2
 * @since Apex 1.0
 */
public class DocumentType implements IDocumentType {

    /**
     * The shared instance.
     */
    private static DocumentType defaultInstance;
    /**
     * The document type name.
     */
    private String name;
    /**
     * The default extension of the document type.
     */
    private String defaultExtension;

    /**
     * Sets the display name of the document type.
     * @param displayName The display name of the document type.
     * @see #getDisplayName()
     */
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Sets the document class of the document type. The document class must be a type of
     * org.apex.base.data.AbstractDocument.
     * @param documentClass The document class to be associated with the document type.
     * @see #getDocumentClass()
     */
    public void setDocumentClass(String documentClass) {
        this.documentClass = documentClass;
    }

    /**
     * Sets the name of the document type.
     * @param name Name of the document type.
     * @see #getName()
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the list of recognized tokens for this document type.
     * @param tokens A list of rcognized tokens.
     * @see #getTokens()
     */
    public void setTokens(List<String> tokens) {
        this.tokens = tokens;
    }
    /**
     * List of supported document types.
     */
    private static List<IDocumentType> docTypes = new ArrayList<IDocumentType>(
            10);
    /**
     * List of file extensions considered as executable.
     */
    private static List<String> executableExtns;
    /**
     * The display name of the document type.
     */
    private String displayName;
    /**
     * The list of recognized tokens.
     */
    private List<String> tokens;
    /**
     * The fully qualified document class name.
     */
    private String documentClass;
    /**
     * A boolean that indicates whether ot not the document type is custom or in built.
     */
    private boolean customTypeIndicator;

    /**
     * Creates an instance of document type with specified type name.
     * @param name The document category name.
     */
    protected DocumentType(String name) {
        this.name = name;
        add(this);
    }

    /**
     * Constructor to create document type.
     * <p>
     * <strong>While creating this document type it is not added to the master list of document types.</strong>
     * @param name The document type name.
     * @param displayName The display name of document type.
     * @param defaultExtension The default extension of document.
     * @param documentClass The document type class.
     * @param tokens The list of tokens supported by the document type.
     */
    public DocumentType(String name, String displayName, String defaultExtension,
            String documentClass, List<String> tokens) {
        this(name, defaultExtension, displayName, documentClass, tokens, false);
    }

    /**
     *  Constructor to create document type.
     * <p>
     * <strong>While creating this document type it is not added to the master list of document types.</strong>
     * @param name The document type name.     
     * @param displayName The display name of document type.
     * @param defaultExtension The default extension of document.
     * @param documentClass The document type class.
     * @param tokens The list of tokens supported by the document type.
     * @param customTypeIndicator Custom document type indicator.
     */
    public DocumentType(String name, String displayName, String defaultExtension,
            String documentClass, List<String> tokens,
            boolean customTypeIndicator) {
        this.name = name;
        this.displayName = displayName;
        this.defaultExtension = defaultExtension;
        this.documentClass = documentClass;
        this.tokens = tokens;
        this.customTypeIndicator = customTypeIndicator;
    }

    /**
     * Default instance creator. This constructor to be used only by
     * framework and only once.
     * <strong>Note: Do not use this constructor to create document types used by application.</strong>
     */
    protected DocumentType() {
        // Default instance.
    }
    /**
     * Document type that represents a Java document.
     */
    private static final DocumentType JAVA = new DocumentType(
            IDocumentType.JAVA_DOCUMENT) {

        private List<String> tokenList = null;

        @Override
        public String getDisplayName() {
            return "Java";
        }

        @Override
        public String getStartCommentToken() {
            return "//";
        }

        @Override
        public String getEndCommentToken() {
            return "";
        }

        @Override
        public List<String> getTokens() {
            if (this.tokenList == null || this.tokenList.isEmpty()) {
                this.tokenList = new ArrayList<String>(20);
                this.tokenList.addAll(Arrays.asList(new String[]{"body",
                            "reference", "text", "reservedWord",
                            "identifier", "literal",
                            "separator", "operator", "comment", "whitespace",
                            "error"}));
            }
            return this.tokenList;
        }

        @Override
        public String getDocumentClass() {
            return "org.apex.base.data.JavaDocument";
        }
    };
    /**
     * Document type that represents a JSP_DOCUMENT document.
     */
    private static final DocumentType JSP = new DocumentType(
            IDocumentType.JSP_DOCUMENT) {

        private List<String> tokenList = null;

        @Override
        public String getDisplayName() {
            return "JSP";
        }

        @Override
        public String getStartCommentToken() {
            return "<%--";
        }

        @Override
        public String getEndCommentToken() {
            return "--%>";
        }

        @Override
        public List<String> getTokens() {
            if (this.tokenList == null || this.tokenList.isEmpty()) {
                this.tokenList = new ArrayList<String>(20);
                this.tokenList.addAll(Arrays.asList(new String[]{"body", "tag",
                            "endtag",
                            "reference", "text",
                            "name",
                            "value", "reservedWord", "identifier", "literal",
                            "separator", "operator", "comment",
                            "whitespace", "error"
                        }));
            }
            return this.tokenList;
        }

        @Override
        public String getDocumentClass() {
            return "org.apex.base.data.JSPDocument";
        }
    };
    /**
     * Document type that represents a JavaScript document.
     */
    private static final DocumentType JAVASCRIPT = new DocumentType(
            IDocumentType.JAVASCRIPT_DOCUMENT) {

        private List<String> tokenList = null;

        @Override
        public String getDisplayName() {
            return "JavaScript";
        }

        @Override
        public String getStartCommentToken() {
            return "//";
        }

        @Override
        public String getEndCommentToken() {
            return "";
        }

        @Override
        public List<String> getTokens() {
            if (this.tokenList == null || this.tokenList.isEmpty()) {
                this.tokenList = new ArrayList<String>(20);
                this.tokenList.addAll(Arrays.asList(new String[]{"reference",
                            "text",
                            "reservedWord",
                            "identifier", "literal",
                            "separator", "operator", "comment",
                            "whitespace", "error"
                        }));
            }
            return this.tokenList;
        }

        @Override
        public String getDocumentClass() {
            return "org.apex.base.data.JavaScriptDocument";
        }
    };
    /**
     * Document type that represents a SQL_DOCUMENT document.
     */
    private static final DocumentType SQL = new DocumentType(
            IDocumentType.SQL_DOCUMENT) {

        private List<String> tokenList = null;

        @Override
        public String getDisplayName() {
            return "SQL";
        }

        @Override
        public String getStartCommentToken() {
            return "--";
        }

        @Override
        public String getEndCommentToken() {
            return "";
        }

        @Override
        public List<String> getTokens() {
            if (this.tokenList == null || this.tokenList.isEmpty()) {
                this.tokenList = new ArrayList<String>(20);
                this.tokenList.addAll(Arrays.asList(new String[]{"reference",
                            "text",
                            "reservedWord",
                            "identifier", "literal",
                            "separator", "operator", "comment",
                            "whitespace", "error"
                        }));
            }
            return this.tokenList;
        }

        @Override
        public String getDocumentClass() {
            return "org.apex.base.data.SQLDocument";
        }
    };
    /**
     * Document type that represents an XML_DOCUMENT document.
     */
    private static final DocumentType XML = new DocumentType(
            IDocumentType.XML_DOCUMENT) {

        private List<String> tokenList = null;

        @Override
        public String getDisplayName() {
            return "XML";
        }

        @Override
        public String getStartCommentToken() {
            return "<!--";
        }

        @Override
        public String getEndCommentToken() {
            return "-->";
        }

        @Override
        public List<String> getTokens() {
            if (this.tokenList == null || this.tokenList.isEmpty()) {
                this.tokenList = new ArrayList<String>(20);
                this.tokenList.addAll(Arrays.asList(new String[]{"body", "tag",
                            "endtag",
                            "reference", "name",
                            "value", "text", "reservedWord", "identifier",
                            "literal",
                            "separator", "operator", "comment",
                            "whitespace", "error"
                        }));
            }
            return this.tokenList;
        }

        @Override
        public String getDocumentClass() {
            return "org.apex.base.data.XMLDocument";
        }
    };
    /**
     * Document type that represents a Properties document.
     */
    private static final DocumentType PROPERTIES = new DocumentType(
            IDocumentType.PROPERTIES_DOCUMENT) {

        private List<String> tokenList = null;

        @Override
        public String getDisplayName() {
            return "Properties";
        }

        @Override
        public String getStartCommentToken() {
            return "#";
        }

        @Override
        public String getEndCommentToken() {
            return "";
        }

        @Override
        public List<String> getTokens() {
            if (this.tokenList == null || this.tokenList.isEmpty()) {
                this.tokenList = new ArrayList<String>(20);
                this.tokenList.addAll(Arrays.asList(new String[]{"reference",
                            "name",
                            "value", "text", "separator", "operator", "comment",
                            "whitespace", "error"
                        }));
            }
            return this.tokenList;
        }

        @Override
        public String getDocumentClass() {
            return "org.apex.base.data.PropertiesDocument";
        }
    };
    /**
     * Document type that represents a Perl document.
     */
    private static final DocumentType PERL = new DocumentType(
            IDocumentType.PERL_DOCUMENT) {

        private List<String> tokenList = null;

        @Override
        public String getDisplayName() {
            return "Perl";
        }

        @Override
        public String getStartCommentToken() {
            return "//";
        }

        @Override
        public String getEndCommentToken() {
            return "";
        }

        @Override
        public List<String> getTokens() {
            if (this.tokenList == null || this.tokenList.isEmpty()) {
                this.tokenList = new ArrayList<String>(20);
                this.tokenList.addAll(Arrays.asList(new String[]{"body",
                            "reference", "text",
                            "reservedWord",
                            "identifier", "literal",
                            "separator", "operator", "comment", "whitespace",
                            "error"
                        }));
            }
            return this.tokenList;
        }

        @Override
        public String getDocumentClass() {
            return "org.apex.base.data.PerlDocument";
        }
    };
    /**
     * Document type that represents a C_DOCUMENT document.
     */
    private static final DocumentType C = new DocumentType(
            IDocumentType.C_DOCUMENT) {

        private List<String> tokenList = null;

        @Override
        public String getDisplayName() {
            return "C";
        }

        @Override
        public String getStartCommentToken() {
            return "//";
        }

        @Override
        public String getEndCommentToken() {
            return "";
        }

        @Override
        public String getDefaultExtension() {
            return "cpp";
        }

        @Override
        public List<String> getTokens() {
            if (this.tokenList == null || this.tokenList.isEmpty()) {
                this.tokenList = new ArrayList<String>(20);
                this.tokenList.addAll(Arrays.asList(new String[]{"body",
                            "reference", "text",
                            "reservedWord",
                            "identifier", "literal",
                            "separator", "operator", "comment", "whitespace",
                            "error"
                        }));
            }
            return this.tokenList;
        }

        @Override
        public String getDocumentClass() {
            return "org.apex.base.data.CDocument";
        }
    };
    /**
     * Document type that represents a HTML_DOCUMENT document.
     */
    private static final DocumentType HTML = new DocumentType(
            IDocumentType.HTML_DOCUMENT) {

        private List<String> tokenList = null;

        @Override
        public String getDisplayName() {
            return "HTML";
        }

        @Override
        public String getStartCommentToken() {
            return "<!--";
        }

        @Override
        public String getEndCommentToken() {
            return "-->";
        }

        @Override
        public List<String> getTokens() {
            if (this.tokenList == null || this.tokenList.isEmpty()) {
                this.tokenList = new ArrayList<String>(20);
                this.tokenList.addAll(Arrays.asList(new String[]{"body", "tag",
                            "endtag",
                            "reference", "name",
                            "value", "text", "reservedWord", "identifier",
                            "literal",
                            "separator", "operator", "comment",
                            "whitespace", "error"
                        }));
            }
            return this.tokenList;
        }

        @Override
        public String getDocumentClass() {
            return "org.apex.base.data.HTMLDocument";
        }
    };
    /**
     * Document type that represents a default document.
     * <p>
     * For a document when no specific type is matched {@code DEFAULT_DOCUMENT} type is assigned.
     * <p>
     * This is the only document type which must be supported by base application and hence made public.
     */
    public static final DocumentType DEFAULT = new DocumentType(
            IDocumentType.DEFAULT_DOCUMENT) {

        @Override
        public String getDisplayName() {
            return "Default";
        }

        @Override
        public List<String> getTokens() {
            Vector<String> tokens = new Vector<String>();
            tokens.addAll(Arrays.asList(new String[]{}));
            return tokens;
        }

        @Override
        public String getDocumentClass() {
            return "org.apex.base.data.DefaultDocument";
        }
    };

    /**
     * Returns default extension of a document type.
     * @return Default document extension.
     */
    public String getDefaultExtension() {
        return (this.defaultExtension == null)
                ? name.toLowerCase()
                : defaultExtension.toLowerCase();
    }

    /**
     * Returns the document class.
     * @return The document class.
     * @see #setDocumentClass(java.lang.String)
     */
    public String getDocumentClass() {
        return documentClass;
    }

    /**
     * Returns the default token used to mark the start of comment block.
     * @return The start comment token.
     */
    public String getStartCommentToken() {
        return "//";
    }

    /**
     * Returns the default token used to mark the end of comment block.
     * @return The end comment token.
     */
    public String getEndCommentToken() {
        return "";
    }

    /**
     *Returns display name of document type.
     * @return The display name of document type.
     * @see #setDisplayName(java.lang.String)
     */
    @Override
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Returns a list of syntax token categories.
     * @return A list of syntax token categories.
     * @see #setTokens(java.util.List)
     */
    public List<String> getTokens() {
        return tokens;
    }

    /**
     * Returns a complete list of supported document types.
     * <p>
     * It excludes the {@code DEFAULT_DOCUMENT} document type.
     * @return A list of supported document types.
     */
    public List<IDocumentType> getDocumentTypes() {        
        return docTypes;
    }

    /**
     * Returns a complete list of supported document types.
     * @return A list of supported document types.
     */
    public List<IDocumentType> getDocumentTypesIncludingDefault() {
        List<IDocumentType> modifiedDocList = new ArrayList<IDocumentType>(
                getDocumentTypes());
        modifiedDocList.add(getDefaultDocumentType());
        return modifiedDocList;
    }

    /**
     * Returns the default document type.
     * @return Default document type.
     */
    public static IDocumentType getDefaultDocumentType() {
        return DEFAULT;
    }

    /**
     * Adds a document type to list of supported document types.
     * <p>
     * In case the {@code  DocumentType.DEFAULT} type is passed it is not added to the list.
     * @param documentType A document type.
     */
    public void add(IDocumentType documentType) {
        if (docTypes == null) {
            docTypes = new ArrayList<IDocumentType>(10);
        }        
        if (documentType != null) {
            // Do not add default document type.
            if (!documentType.getName().equalsIgnoreCase(
                    IDocumentType.DEFAULT_DOCUMENT) && !documentType.equals(
                    DEFAULT)) {
                if (!docTypes.contains(documentType)) {
                    docTypes.add(documentType);
                }
            }
        }        
    }

    /**
     * Adds a document type to list of supported document types.
     * <p>
     * In case the {@code  DocumentType.DEFAULT} type is passed it is not removed from the list.
     * @param documentType A document type.
     */
    public void remove(IDocumentType documentType) {
        if (docTypes != null && !documentType.equals(DEFAULT)) {
            for (IDocumentType dType : docTypes) {
                if (dType.equals(documentType)) {
                    docTypes.remove(dType);
                    break;
                }
            }
        }
    }

    /**
     * Returns a list of file extensions considered as executable.
     * @return A list of file extensions.
     */
    public List<String> getExecutableExtensions() {
        if (executableExtns == null) {
            executableExtns = new Vector<String>();
            executableExtns.add("BAT");
            executableExtns.add("EXE");
            executableExtns.add("JAR");
            executableExtns.add("SH");
        }
        return executableExtns;
    }

    /**
     * Determies whether or not a given file is executable from its
     * extension.
     * @param extension The file extension.
     * @return {@code true} if file is executable; otherwise returns {@code false}.
     */
    public boolean isExecutable(String extension) {
        if (StringUtil.isNullOrEmpty(extension)) {
            return false;
        }
        if (getExecutableExtensions().contains(extension.toLowerCase())) {
            return true;
        }
        return false;
    }

    /**
     * This method always returns the same insytance of document type. And
     * this document type is a supporting one and does not represent any valid
     * document type.
     * @return The shared default document type instance.
     */
    public static DocumentType getSharedInstance() {
        if (defaultInstance == null) {
            defaultInstance = new DocumentType();
        }
        return defaultInstance;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return getDisplayName();
    }

    public boolean equals(String docTypeName) {
        if (StringUtil.isNullOrEmpty(docTypeName)) {
            return false;
        }
        return this.getName().equalsIgnoreCase(docTypeName);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DocumentType other = (DocumentType) obj;
        if (this.getName().equalsIgnoreCase(other.getName())) {
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + (this.name != null
                ? this.name.hashCode()
                : 0);
        return hash;
    }

    public IDocumentType getDocumentTypeByName(String name) {
        if (name != null) {
            for (IDocumentType dType : docTypes) {
                if (dType.getName().equalsIgnoreCase(name)) {
                    return dType;
                }
            }
        }
        return null;
    }

    public boolean isCustomDocumentType() {
        return this.customTypeIndicator;
    }

    /**
     * Creates and returns a deep copy of this configuration object.
     * @return A clone of this instance.
     * @throws java.lang.CloneNotSupportedException
     */
    @Override
    @SuppressWarnings("unchecked")
    public Object clone() throws CloneNotSupportedException {        
        DocumentType clonedDocType = (DocumentType) super.clone();
        clonedDocType.setTokens((List<String>) new ArrayList<String>(this.
                getTokens()).clone());
        return clonedDocType;
    }
}
