package stanfordNLPHttp;

import utils.StanfordUitls;
import utils.OpenNLPUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Properties;

import opennlp.tools.chunker.ChunkerME;
import opennlp.tools.chunker.ChunkerModel;
import opennlp.tools.util.InvalidFormatException;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import edu.stanford.nlp.pipeline.StanfordCoreNLP;

@SuppressWarnings("deprecation")
public class MyHttpServer {
	public static Properties props = new Properties();
	public static ChunkerME chunker;
	static {
		props.setProperty("annotators", "tokenize,ssplit,pos,lemma,ner,parse");
		//props.setProperty("tokenize.whitespace", "true"); // attention to those
		//props.setProperty("ssplit.isOneSentence", "true"); // one line is a sentence;

		// props.setProperty("ssplit.eolonly", "true");
		// props.setProperty("coref.algorithm", "neural");
		// props.setProperty("coref.neural.greedyness", "0.2");
	}
	static StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

	public static void main(String[] args) {
		InputStream chunkModelIn;
		try {
			chunkModelIn = new FileInputStream("model/en-chunker.bin");
			ChunkerModel chunkModel;
			try {
				chunkModel = new ChunkerModel(chunkModelIn);
				chunker = new ChunkerME(chunkModel);
				try {
					HttpServer hs = HttpServer.create(new InetSocketAddress(
							7777), 0);
					hs.createContext("/", new MyHandler());
					hs.setExecutor(null);
					hs.start();
					System.out.println("server is starting...");
				} catch (IOException e) {
					e.printStackTrace();
				}
			} catch (InvalidFormatException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}

	}
}

class MyHandler implements HttpHandler {
	public void getNER(){
		
	}
	public void handle(HttpExchange t) throws IOException {
		URI uri = t.getRequestURI();
		String uriPaht = uri.getPath();
		String uriQuery = uri.getQuery().split("=")[1];
		System.out.println(uriQuery);  //server prints
		ArrayList<ArrayList<ArrayList<String>>> retParams = StanfordUitls.getTokenPOS(
				MyHttpServer.pipeline, uriQuery);
		ArrayList<ArrayList<String>> tokensArrays = retParams.get(0);
		ArrayList<ArrayList<String>> posArrays = retParams.get(1);
		String[] tokenArray=null;String[] posArray =null;
		String[] chunkArray=null;
		ArrayList<String[]> chunkArrays = new ArrayList<String[]>();
		for(Integer i=0;i<tokensArrays.size();i++){
			tokenArray = (String[]) tokensArrays.get(0).toArray(new String[0]);
			posArray = (String[]) posArrays.get(0).toArray(new String[0]);
			chunkArray = OpenNLPUtils.getChunker(MyHttpServer.chunker, tokenArray, posArray);
			chunkArrays.add(chunkArray);
		}
		JSONObject obj = new JSONObject();
		try {
			obj.put("tokenArrays", tokensArrays);
			obj.put("posArrays", posArrays);
			obj.put("chunkArrays", chunkArrays);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/**
		 * we need to connect to python NER model to get the results.
		 */
		String url = "http://192.168.3.196:8000/NER?" + URLEncoder.encode(obj.toString(), "UTF-8");
		System.out.println(url);
		HttpClient client = new DefaultHttpClient();  
	    HttpGet request = new HttpGet(url);
	    HttpResponse responseNER = client.execute(request);  
        System.out.println("Response Code: " +  
        responseNER.getStatusLine().getStatusCode());
        JSONArray objNER = null;
		try {
			objNER = new JSONArray(EntityUtils.toString(responseNER.getEntity()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		Headers headers = t.getResponseHeaders();
		headers.set("Content-Type", String.format("application/json; charset=%s", StandardCharsets.UTF_8));
		String response = objNER.toString();
		t.sendResponseHeaders(200, response.length());
		OutputStream os = t.getResponseBody();
		os.write(response.getBytes());
		os.close();
	}

}