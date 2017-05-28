package utils;

import opennlp.tools.chunker.ChunkerME;

public class OpenNLPUtils {
	public static String[] getChunker(ChunkerME chunker,String[] tokenArray, String[] posArray){
		String[] chunkerArray = chunker.chunk(tokenArray,posArray);
		return chunkerArray;
	}
}
