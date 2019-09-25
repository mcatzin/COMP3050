/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programming;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mariocatzin
 */
public class Programming {

    /**
     * @param args the command line arguments
     */
    private static File file;
    private static FileInputStream finput;
    //gobal variables
    public static int cclass;
    public static char[] lexeme = new char[100];
    public static char nchar;
    public static int lexlen;
    public static int token;
    public static int nextToken =0;
    //Character classes 
    private static final int letter = 0;
    private static final int digit =1;
    private static final int unknown = 99;
    
    //token codes
    private static final int int_lit = 20;
    private static final int ident =22;
    private static final int assign_op = 40;
    private static final int add_op = 42;
    private static final int sub_op = 44;
    private static final int mult_op = 46;
    private static final int div_op = 48;
    private static final int left_paren = 50;
    private static final int right_paren =52;
    private static final int EOF = -1;
    
            
    public static void addChar(){
        if(lexlen <= 98) {
            lexeme[lexlen++] = nchar;
            lexeme[lexlen] =0;
        }else{
            System.out.println("Error - lexeme is too long\n");
        }
    }
    public static void getChar() {
        try {
            if(finput.available() >0){
                nchar = (char)finput.read();
                if(Character.isLetter(nchar)){
                    cclass=letter;
                }else if(Character.isDigit(nchar)){
                    cclass= digit;
                }else {
                    cclass=unknown;
                }
            }else {
                cclass = EOF;
            }} catch (IOException ex) {
            Logger.getLogger(Programming.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   public static void nonBlank(){
       while(Character.isSpaceChar(nchar)){
           getChar();
       }
   }
   public static int lex() throws IOException{
       lexlen =0;
       nonBlank();
       switch(cclass){
           case letter:
               addChar();
               getChar();
               while(cclass == letter || cclass == digit){
                   addChar();
                   getChar();
               }
               nextToken =ident;
               break;
           case digit:
               addChar();
               getChar();
               while(cclass==digit){
                   addChar();
                   getChar();
               }
               nextToken = int_lit;
               break;
           case unknown:
               check(nchar);
               getChar();
               break;
           case EOF:
               nextToken = EOF;
               lexeme[0] = 'E';
               lexeme[1] = 'O';
               lexeme[2] = 'F';
               lexeme[3] = 0;
               break;
           default:
               
       }
       System.out.printf("The token is: %d\n" + "The lexeme is;\n", nextToken);
       for(int i=0;i<lexlen;i++){
           System.out.print(lexeme[i]);
       }
       System.out.println();
       
       
       return nextToken;
   }
    
    public static int check(char ch){
     
        switch(ch){
            case '(':
                addChar();
                nextToken = left_paren;
                break;
            case ')':
                addChar();
                nextToken = right_paren;
                break;
            case '+':
                addChar();
                nextToken = add_op;
                break;
            case '-':
                addChar();
                nextToken = sub_op;
                break;
            case '*':
                addChar();
                nextToken = mult_op;
                break;
            case '/':
                addChar();
                nextToken = div_op;
                break;
            default:
                addChar();
                nextToken = EOF;
                break;
        }
        return nextToken;
                        
        }
    
    public static void main(String[] args) {
        // TODO code application logic here
        
        lexlen =0;
        lexeme = new char[100];
      
        for(int i=0;i<100;i++){
            lexeme[i]='0';
        }
        file = new File("/Users/mariocatzin/Documents/filetext.txt");
        if(!(file.isFile() && file.canRead())){
            System.out.println(file.getName()+"cannot be read");
            
        }
        if(!file.exists()){
            System.out.println("filetext.txt does not exist");
           
        }
        try{
            finput = new FileInputStream(file);
            //char current;
            while(finput.available() >0){
                getChar();
                lex();
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Programming.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Programming.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
