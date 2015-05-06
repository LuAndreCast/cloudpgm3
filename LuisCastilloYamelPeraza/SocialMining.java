
import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONObject;
import org.json.JSONException;

public class SocialMining {

	private static final Log log = LogFactory.getLog(SocialMining.class);
  public static class TokenizerMapper 
       extends Mapper<Object, Text, Text, IntWritable>{
    
    private final static IntWritable one = new IntWritable(1);
    private Text word = new Text();
      
    public void map(Object key, Text value, Context context
                    ) throws IOException, InterruptedException {
//      StringTokenizer itr = new StringTokenizer(value.toString());
//      while (itr.hasMoreTokens()) {
//        word.set(itr.nextToken());
//        context.write(word, one);
//      }
	try{
		JSONObject jsn = new JSONObject(value.toString());
		JSONObject jsnvalue = (JSONObject) jsn.get("value");
		JSONObject data = (JSONObject) jsnvalue.get("data");
		String text = (String) data.get("text");
		log.info("text");
		word.set(text);
		context.write(word,one);
	}catch(Exception e){
		e.printStackTrace();
	}    
	}//eo-map
  }//eo-map class
  
  public static class IntSumReducer 
       extends Reducer<Text,IntWritable,Text,IntWritable> {
    private IntWritable result = new IntWritable();

    public void reduce(Text key, Iterable<IntWritable> values, 
                       Context context
                       ) throws IOException, InterruptedException {
      int sum = 0;
//      for (IntWritable val : values) {
//        sum += val.get();
//      }
      //saving results
//      result.set(sum);
//      log.info(sum);
//      context.write(key, result);
 
     // Natural Disasters (http://en.wikipedia.org/wiki/Natural_disaster)
     String naturalDisasters[] = {
        "Avalanche","Earthquake","Volcano","Flood","Limnic","Tsunami",
        "Blizzard","Cyclone","Hurricane","Storm","Drought","Hailstorm",
        "Tornado","Wildfire","Fire"
       };
     
         // Keywords 
         //  hurricane
         //       [NOT ACTIVE]
         //       Remembering   Ago
         //       After         Insurance   Debt
         //      [ACTIVE]
         //       Cat      -means category, number after you should be a number
         //       Category -means category, number after you should be a number    
 
       //keywords that a natural disaster tweet should/could have  
        String triggerKeywords[] = {
           "prepare","on","in","at","over","evacuate","evacuation",
           "coming","closed","alert","aware","crossed","crossing",
           "weather","severe","north","south","east","west","coast",
           "gulf","region","wind","gust","season","hits","get",
           "ready","be","underground"
        };
        
        try{
           int HashTagNSCount =0 ; //count hashtag words with natural disasters 
           int keyTriggerWordsCount = 0;
           String currSentence = key.toString(); //current sentence

           //splitting sentence into a list
           String currentSentence[] = currSentence.split("\\s+");

           //thru each words in the list (sentence)
           for(int iter = 0;iter < currentSentence.length ; iter++)
           {
             String currWord = currentSentence[iter];
             //checking if the word has a #
             int pos = currWord.indexOf("#");
             if (pos >= 0)
             {
                if((pos+1 ) < currWord.length())
                {//hastag has an actual word
                    String wordWithOutHashTag = currWord.substring( pos+1 , currWord.length());
                      
                    //checking if the hashtag word is a natural disaster
                    for(int currNS =0; currNS< naturalDisasters.length; currNS++)
                    {
                    	String currentNaturalDisaster = naturalDisasters[currNS];
                    	int result = wordWithOutHashTag.compareToIgnoreCase(currentNaturalDisaster);
                     	if(result > 0) //hashtag word may be a natural disaster
                        {
                            int naturalDisasterWordLength = currentNaturalDisaster.length();
                            if(wordWithOutHashTag.length() >= naturalDisasterWordLength)//maybe match
                            {
                               String wordToCheck = wordWithOutHashTag.substring(0, naturalDisasterWordLength);
                               int sameWord = wordToCheck.compareToIgnoreCase(currentNaturalDisaster);
                               if(sameWord == 0)//natural disaster is in the front of the word
                               {
				   HashTagNSCount++;
                               }//eo-sameword
                             }//eo-possible match
         		}
                        else if (result ==0)//hashtag word is a natural disaster - words are the same
                        {
                           HashTagNSCount++;
                        }
                      }//eofl-checking all natural disasters
                   }//eo-valid hashtag
                 }//eo-hastag found
                 else 
                 { //current word is not a hashtag so lets see if it has a trigger word
                   for(int trigIter=0;trigIter < triggerKeywords.length;trigIter++)
                   {
                     String currTriggerWord = triggerKeywords[trigIter];
                     int currTriggerWordCount = currTriggerWord.length();
                     if( currWord.length() >= currTriggerWordCount )
		     {
                       String wordToCheck = currWord.substring(0, currTriggerWordCount );
                       int result2 = wordToCheck.compareToIgnoreCase(currTriggerWord);
                       if(result2==0)
                       {
                         keyTriggerWordsCount++;
                       }
                     }//eo-if
                   }//eo-trig words
                 }//eo-non hashtag word
               }//eofl-thru all the words in the tweet
               
                //The only tweets that are going to be taking into consideration are the following:
                //     atleast 2 trigger keywords              - keyTriggerWordsCount
                //     atleast 1 natural disaster hashtag word - HashTagNSCount
                               
                if( (keyTriggerWordsCount >=2 ) && (HashTagNSCount>=1) )
	        {
                    //log.info( currSentence );
                   result.set(sum);
                   log.info(sum);
                   context.write(key,result);         
               }else {
			//tweet is irrevalent                
		}
           }catch(Exception e){
         	e.printStackTrace();
           }
    }//eo-reducer
  }//eo-reducer class

  public static void main(String[] args) throws Exception {
    Configuration conf = new Configuration();
    String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
    if (otherArgs.length != 2) {
      System.err.println("Usage: socialmining <in> <out>");
      System.exit(2);
    }
    System.err.println("Social Mining program");
    Job job = new Job(conf, "social mining");
    job.setJarByClass(SocialMining.class);
    job.setMapperClass(TokenizerMapper.class);
    job.setCombinerClass(IntSumReducer.class);
    job.setReducerClass(IntSumReducer.class);
    job.setOutputKeyClass(Text.class);
    job.setOutputValueClass(IntWritable.class);
    FileInputFormat.addInputPath(job, new Path(otherArgs[0]));
    FileOutputFormat.setOutputPath(job, new Path(otherArgs[1]));
    System.exit(job.waitForCompletion(true) ? 0 : 1);
  }
}
