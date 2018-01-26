package uk.ac.ebi.uniprot.variation.hgvs.protein;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProteinHgvss {
    public final static String HGVS_BASE =
            "([\\w.]+)(\\:)([cgp])(\\.)(.+)"; // ENSMUST00000082421.1:c.115G>A;
    
    public final static Pattern HGVS_BASE_PATTERN = Pattern.compile(HGVS_BASE);
    
    public final static String HGVS_SUBSTITUTION="(\\d+)([a-zA-Z]+)(>)([a-zA-Z]+)";
    
    public final static Pattern HGVS_SUBSTITUTION_PATTERN = Pattern.compile(HGVS_SUBSTITUTION);
    
    public final static String HGVS_PROTEIN_SUBSTITUTION="(\\()?([a-zA-Z]+)(\\d+)([a-zA-Z*=]+)(\\))?";
    
    public final static Pattern HGVS_PROTEIN_SUBSTITUTION_PATTERN = Pattern.compile(HGVS_PROTEIN_SUBSTITUTION);
    
    public final static String HGVS_PROTEIN_DELETION="(\\()?([a-zA-Z]+)(\\d+)((_)([a-zA-Z]+)(\\d+))?(del)(\\))?";
    
    public final static Pattern HGVS_PROTEIN_DELETION_PATTERN = Pattern.compile(HGVS_PROTEIN_DELETION);
    
    public final static String HGVS_PROTEIN_DUPLICATION="(\\()?([a-zA-Z]+)(\\d+)((_)([a-zA-Z]+)(\\d+))?(dup)(\\))?";
    
    public final static Pattern HGVS_PROTEIN_DUPLICATION_PATTERN = Pattern.compile(HGVS_PROTEIN_DUPLICATION);
    
    public final static String HGVS_PROTEIN_INSERT="(\\()?([a-zA-Z]+)(\\d+)(_)([a-zA-Z]+)(\\d+)(ins)([a-zA-Z]+)?(\\d+)?(\\))?";
    public final static Pattern HGVS_PROTEIN_INSERT_PATTERN = Pattern.compile(HGVS_PROTEIN_INSERT);
    
    public final static String HGVS_PROTEIN_DELETION_INSERT="(\\()?([a-zA-Z]+)(\\d+)((_)([a-zA-Z]+)(\\d+))?(delins)([a-zA-Z]+)(\\))?";
    
    public final static Pattern HGVS_PROTEIN_DELETION_INSERT_PATTERN = Pattern.compile(HGVS_PROTEIN_DELETION_INSERT);
    
    
    public final static String HGVS_PROTEIN_FRAMESHIFT="(\\()?([a-zA-Z]+)(\\d+)([a-zA-Z]+)?(fs)(([a-zA-Z]+)(\\d+))?(\\))?";
    public final static Pattern HGVS_PROTEIN_FRAMESHIFT_PATTERN = Pattern.compile(HGVS_PROTEIN_FRAMESHIFT);
    
    public final static String HGVS_PROTEIN_EXTENSION_MET="(\\()?(Met)(\\d+)([a-zA-Z]+)?(ext)(-)(\\d+)(\\))?";
    public final static Pattern HGVS_PROTEIN_EXTENSION_MET_PATTERN = Pattern.compile(HGVS_PROTEIN_EXTENSION_MET);
    public final static String HGVS_PROTEIN_EXTENSION_TER="(\\()?(\\*|Ter)(\\d+)([a-zA-Z]+)(ext)([a-zA-Z]+)?(\\*|Ter)(\\d+|\\?)?(\\))?";
    public final static Pattern HGVS_PROTEIN_EXTENSION_TER_PATTERN = Pattern.compile(HGVS_PROTEIN_EXTENSION_TER);
    
    public final static String HGVS_PROTEIN_REPEAT="(\\()?([a-zA-Z]+)(\\d+)((_)([a-zA-Z]+)(\\d+))?(\\[)(\\d+)(\\])(\\))?";
    
    public final static Pattern HGVS_PROTEIN_REPEAT_PATTERN = Pattern.compile(HGVS_PROTEIN_REPEAT);
    
    

    public static ProteinHgvs create(String hgvsString){
        Matcher matcher = ProteinHgvss.HGVS_BASE_PATTERN.matcher(hgvsString);
        if(!matcher.matches()){
            return null;
        }
        String id = matcher.group(1);
        String type =  matcher.group(3);
        String hgvs = matcher.group(5);
        if(!type.equals("p")){
            return null;
        }
        String primaryId = id.indexOf(".") == -1 ? id : id.substring(0, id.indexOf("."));
        ProteinHgvs proteinHgvs = DeletionProteinHgvs.from(hgvs);
      
        if(proteinHgvs ==null){
            proteinHgvs= DuplicationProteinHgvs.from(hgvs);
        }
         
        if(proteinHgvs ==null){
            proteinHgvs= InsertionProteinHgvs.from(hgvs);
        }
        
        if(proteinHgvs ==null){
            proteinHgvs= DeletionInsertionProteinHgvs.from(hgvs);
        }
        if(proteinHgvs ==null){
            proteinHgvs= ExtensionProteinHgvs.from(hgvs);
        }
        if(proteinHgvs ==null){
            proteinHgvs= FrameshiftProteinHgvs.from(hgvs);
        }
        if(proteinHgvs ==null){
            proteinHgvs= SubstitutionProteinHgvs.from(hgvs);
        }   
        if(proteinHgvs ==null){
            proteinHgvs= RepeatProteinHgvs.from(hgvs);
        }
       
     
        if(proteinHgvs ==null){
            return null;
        }
        proteinHgvs.setPrimaryId(primaryId);
        
        return proteinHgvs;
        
    }

}
