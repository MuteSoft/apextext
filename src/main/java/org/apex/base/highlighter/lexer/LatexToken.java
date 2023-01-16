package org.apex.base.highlighter.lexer;

/** 
 * A LatexToken is a token that is returned by a lexer that is lexing a HTML
 * source file.  It has several attributes describing the token:
 * The type of token, the text of the token, the line number on which it
 * occurred, the number of characters into the input at which it started, and
 * similarly, the number of characters into the input at which it ended. <br>
 */ 
public class LatexToken extends Token {

  public final static int COMMAND = 0x100;
  public final static int COMMAND_CHAR = 0X101;

  public final static int TEXT = 0x200;
  
  public final static int COMMENT = 0xD00;

  public final static int WHITE_SPACE = 0xE00;

  public final static int ERROR_UNEXPECTED_COMMAND_CHAR = 0xF00;
  
  private int ID;
  private String contents;
  private int lineNumber;
  private int charBegin;
  private int charEnd;
  private int state;

  /**
   * Create a new token.
   * The constructor is typically called by the lexer
   *
   * @param ID the id number of the token
   * @param contents A string representing the text of the token
   * @param lineNumber the line number of the input on which this token started
   * @param charBegin the offset into the input in characters at which this token started
   * @param charEnd the offset into the input in characters at which this token ended
   */
  public LatexToken(int ID, String contents, int lineNumber, int charBegin, int charEnd){
    this (ID, contents, lineNumber, charBegin, charEnd, Token.UNDEFINED_STATE);
  }

  /**
   * Create a new token.
   * The constructor is typically called by the lexer
   *
   * @param ID the id number of the token
   * @param contents A string representing the text of the token
   * @param lineNumber the line number of the input on which this token started
   * @param charBegin the offset into the input in characters at which this token started
   * @param charEnd the offset into the input in characters at which this token ended
   * @param state the state the tokenizer is in after returning this token.
   */
  public LatexToken(int ID, String contents, int lineNumber, int charBegin, int charEnd, int state){
	this.ID = ID;
	this.contents = new String(contents);
	this.lineNumber = lineNumber;
	this.charBegin = charBegin;
	this.charEnd = charEnd;
    this.state = state;
  }

  /**
     * Get an integer representing the state the tokenizer is in after
     * returning this token.
     * Those who are interested in incremental tokenizing for performance
     * reasons will want to use this method to figure out where the tokenizer
     * may be restarted.  The tokenizer starts in Token.INITIAL_STATE, so
     * any time that it reports that it has returned to this state, the
     * tokenizer may be restarted from there.
     */
  public int getState(){
    return state;
  }

  /**
   * get the ID number of this token
   *
   * @return the id number of the token
   */
  public int getID(){
  	return ID;
  }

  /**
   * get the contents of this token
   *
   * @return A string representing the text of the token
   */
  public String getContents(){
  	return (new String(contents));
  }

  /**
   * get the line number of the input on which this token started
   *
   * @return the line number of the input on which this token started
   */
  public int getLineNumber(){
  	return lineNumber;
  }

  /**
   * get the offset into the input in characters at which this token started
   *
   * @return the offset into the input in characters at which this token started
   */
  public int getCharBegin(){
  	return charBegin;
  }

  /**
   * get the offset into the input in characters at which this token ended
   *
   * @return the offset into the input in characters at which this token ended
   */
  public int getCharEnd(){
 	return charEnd;
  }

  /** 
   * Checks this token to see if it is text.
   *
   * @return true if this token is an text, false otherwise
   */
  public boolean isText(){
  	return((ID >> 8) == 0x2);
  }
  
  /** 
   * Checks this token to see if it is a comment.
   * 
   * @return true if this token is a comment, false otherwise
   */
  public boolean isComment(){
  	return((ID >> 8) == 0xD);
  }

  /** 
   * Checks this token to see if it is White Space.
   * Usually tabs, line breaks, form feed, spaces, etc.
   * 
   * @return true if this token is White Space, false otherwise
   */
  public boolean isWhiteSpace(){
  	return((ID >> 8) == 0xE);
  }

  /** 
   * Checks this token to see if it is an Error.
   * Unfinished comments, numbers that are too big, unclosed strings, etc.
   * 
   * @return true if this token is an Error, false otherwise
   */
  public boolean isError(){
  	return((ID >> 8) == 0xF);
  }

	/**
	 * A description of this token.  The description should
	 * be appropriate for syntax highlighting.  For example
	 * "comment" is returned for a comment.
     *
	 * @return a description of this token.
	 */
	public String getDescription(){
		if (ID==COMMAND){
			return("identifier");
		} else if (ID==COMMAND_CHAR){
			return("operator");
		} else if (isText()){
			return("text");
		} else if (isComment()){		
			return("comment");
		} else if (isWhiteSpace()){
			return("whitespace");
		} else if (isError()){
		 	return("error");
		} else {
			return("unknown");
		}
	}

  /**
   * get a String that explains the error, if this token is an error.
   * 
   * @return a  String that explains the error, if this token is an error, null otherwise.
   */
  public String errorString(){
  	String s;
  	if (isError()){
  		s = "Error on line " + lineNumber + ": ";
  		switch (ID){
  		case ERROR_UNEXPECTED_COMMAND_CHAR:
  			s += "Unexpected command character: " + contents;
  		break;
		}
  			
  	} else {
  		s = null;
  	}
  	return (s);
  }

  /** 
   * get a representation of this token as a human readable string.
   * The format of this string is subject to change and should only be used
   * for debugging purposes.
   *
   * @return a string representation of this token
   */  
  public String toString() {
      return ("Token #" + Integer.toHexString(ID) + ": " + getDescription() + " Line " + 
      	lineNumber + " from " +charBegin + " to " + charEnd + " : " + contents);
  }
  
}
