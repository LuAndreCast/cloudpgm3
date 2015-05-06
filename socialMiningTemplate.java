package socialmining;

//import java.util.ArrayList; //needed of hashtag list

public class SocialMining {
    public static void main(String[] args) {
    
        /*sample data*/
        String tweets[] = { 

           "Schools & government are closed; the DC Beltway " +
           "almost deserted after Hurricane Sandy " +
           "http://bit.ly/1k8cXim  #hurricane #Sandy" ,

            "Utility crews and trucks head north the day after " +
           " Hurricane Sandy hits DC http://bit.ly/1bkfhNV " +
           "PHOTO #hurricane #Sandy",

            "Don’t be underprepared for a quiet #hurricane season."+
        " http://www.usatoday.com/story/news/nation/2015/04/25/kostigen-quiet-hurricane-season/26233039/ … ",

            "@JaredLeto \"...let it all burn\n" +
                 "This #Hurricane’s chasing us all undergound...\" ❤ ",

            "@lifehouse #Hurricane moves up to #38 on this weeks"
         + " American Top 40 charts! Be sure to keep requesting all week!",

            "2005 #HurricaneWilma over south Florida - everyone "
            + "on the east coast dealing with a large, Cat 1 wind field ",

            "Ya'll better stop getting high, and get low! Tornado"
            + " coming! #420 #Hurricane #WatchOut"

        };
         
        /* Natural Disasters (http://en.wikipedia.org/wiki/Natural_disaster)  */
        String naturalDisasters[] = {
            "Avalanche",
            "Earthquake",
            "Volcano",
            "Flood",
            "Limnic",
            "Tsunami",
            "Blizzard",
            "Cyclone",
            "Hurricane",
            "Storm",
            "Drought",
            "Hailstorm",
            "Tornado",
            "Wildfire",
            "Fire"
        };
     
        /* Keywords 
            hurricane
                [NOT ACTIVE]
                Remembering
                Ago
                After
                Insurance
                Debt
                
                [ACTIVE]
                Cat      -means category, number after you should be a number
                Category -means category, number after you should be a number    
        */
        
     
       /*keywords that a natural disaster tweet should/could have*/  
        String triggerKeywords[] = {
                "prepare",
                "on",
                "in",
                "at",
                "over",
                "evacuate",
                "evacuation",
                "coming",
                "closed",
                "alert",
                "aware",
                "crossed",
                "crossing",
                "weather",
                "severe",
                "north",
                "south",
                "east",
                "west",
                "coast",
                "gulf",
                "region",
                "wind",
                "gust",
                "season",
                "hits",
                "get",
                "ready",
                "be",
                "underground"
        };
        
        try{
            for(int i =0; i < 7; i++){
               
                
                
                /*# hashtag list from tweet */
//                ArrayList<String> hashtagList = new ArrayList<String>();
                int HashTagNSCount =0 ; //count hashtag words with natural disasters 
                int keyTriggerWordsCount = 0;
                String currSentence = tweets[i]; /*current sentence*/

                /*splitting sentence into a list*/
                String currentSentence[] = currSentence.split("\\s+");

                /*thru each words in the list (sentence) */
                for(int iter = 0;iter < currentSentence.length ; iter++)
                {
                    String currWord = currentSentence[iter];
                    
                    /*checking if the word has a # */
                    int pos = currWord.indexOf("#");
                    if (pos >= 0){
                        if((pos+1 ) < currWord.length()){//hastag has an actual word
                            String wordWithOutHashTag = currWord.substring( pos+1 , currWord.length());
                            
                            /*checking if the hashtag word is a natural disaster*/
                            for(int currNS =0; currNS< naturalDisasters.length; currNS++){
                                String currentNaturalDisaster = naturalDisasters[currNS];
                                int result = wordWithOutHashTag.compareToIgnoreCase(currentNaturalDisaster);
                                if(result > 0) //hashtag word may be a natural disaster
                                {
                                    int naturalDisasterWordLength = currentNaturalDisaster.length();
                                    if( wordWithOutHashTag.length() >= naturalDisasterWordLength )//possible match
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
//                        hashtagList.add(wordWithOutHashTag);//adding word to hashtag list
                        }//eo-valid hashtag
                    }//eo-hastag found
                    else 
                    { /*current word is not a hashtag so lets see if it has a trigger word*/
                      for(int trigIter=0;trigIter < triggerKeywords.length;trigIter++){
                          String currTriggerWord = triggerKeywords[trigIter];
                          int currTriggerWordCount = currTriggerWord.length();
                          if( currWord.length() >= currTriggerWordCount ){
                            String wordToCheck = currWord.substring(0, currTriggerWordCount );
                              int result2 = wordToCheck.compareToIgnoreCase(currTriggerWord);
                              if(result2==0){
                                  keyTriggerWordsCount++;
                              }
                          }//eo-if
                      }//eo-trig words
                    }//eo-non hashtag word
                    
//                    System.out.println( currentSentence[iter] );//testing
                }//eofl-thru all the words in the tweet

//                System.out.println("\nThe hastag's found are the following:\n" 
//                        + hashtagList.toString() );
//                System.out.println("Natural disasters Count " + HashTagNSCount);
//                System.out.println("trigger key word Count " + keyTriggerWordsCount);
                
                /*The only tweets that are going to be taking into consideration
                    are the following:
                        atleast 2 trigger keywords              - keyTriggerWordsCount
                        atleast 1 natural disaster hashtag word - HashTagNSCount
                */
               
                if( (keyTriggerWordsCount >=2 ) && (HashTagNSCount>=1) ){
                    System.out.println("\n-----------------------");
                    System.out.println("Tweet#"+i);
                    System.out.println("-----------------------");
                    System.out.println( currSentence );//testing
                }

            }//eofl
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }//eotester
}//eoc
