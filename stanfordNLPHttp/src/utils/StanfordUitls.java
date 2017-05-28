package utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Properties;

import edu.stanford.nlp.coref.data.Document;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.CoreAnnotations.NamedEntityTagAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;


public class StanfordUitls {
	
	
	public static ArrayList<ArrayList<ArrayList<String>>> getTokenPOS(StanfordCoreNLP pipeline,String strs){
		Annotation document = new Annotation(strs);
		pipeline.annotate(document);
		List<CoreMap> sentences = document.get(SentencesAnnotation.class);
		/**
		 * may be contain a context, not only a sentence!
		 */
		
		ArrayList<ArrayList<String>> tokenArrays = new ArrayList<ArrayList<String>>();
		ArrayList<ArrayList<String>> posArrays = new ArrayList<ArrayList<String>>();
		//ArrayList<String> neArray = new ArrayList<String>();
		for(CoreMap sentence: sentences) {
			ArrayList<String> tokenArray = new ArrayList<String>();
			ArrayList<String> posArray = new ArrayList<String>();
			
			
		      // traversing the words in the current sentence
		      // a CoreLabel is a CoreMap with additional token-specific methods
		      for (CoreLabel token: sentence.get(TokensAnnotation.class)) {
		        // this is the text of the token
		        String word = token.get(TextAnnotation.class);
		        // this is the POS tag of the token
		        String pos = token.get(PartOfSpeechAnnotation.class);
		        //String ne = token.get(NamedEntityTagAnnotation.class);
		        tokenArray.add(word);
		        posArray.add(pos);
		        System.out.println(word+":"+pos);
		        //neArray.add(ne);
		      }
		      tokenArrays.add(tokenArray);
		      posArrays.add(posArray);
		}
		ArrayList<ArrayList<ArrayList<String>>> retParams = new ArrayList<ArrayList<ArrayList<String>>>();
		retParams.add(tokenArrays);
		retParams.add(posArrays);
 		//retParams.add(neArray);
		return retParams;
	}
	
	
}
