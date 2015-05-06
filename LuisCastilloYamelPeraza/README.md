Instructions

  Location of the SocialMining Program:
      ~/hadoop-1.2.1/socialMining

  How to run the Program and see its output:
      [Running program with Arthur Data]
         1) change to the hadoop directory, do this by typping the below command in terminal
              cd ~/hadoop-1.2.1/

         2) delete previous output files, do this by typping the below command in terminal
             sh deleteOutputFiles.sh

          3) Run Program with Arthur, type in the terminal the following command
              sh runSocialMiningForArthur.sh

          4) after the program has finishing running, compile the output files onto one text file
              do this by typping the below command in terminal
                  mergeArthurFiles.sh
         
          5) open the text file named "arthurOutput.txt" , do this by typping the below command in terminal
                less arthurOutput.txt

      [Running program with KingFire Data]
         1) change to the hadoop directory, do this by typping the below command in terminal
              cd ~/hadoop-1.2.1/

         2) delete previous output files, do this by typping the below command in terminal
             sh deleteOutputFiles.sh

          3) Run Program with King Fire, type in the terminal the following command
              sh runSocialMiningForKingFire.sh

          4) after the program has finishing running, compile the output files onto one text file
              do this by typping the below command in terminal
                  mergeKingFiles.sh
         
          5) open the text file named "KingFireOutput.txt" , do this by typping the below command in terminal
                less KingFireOutput.txt

[Program Information]
Our Social Media Program implementation is as follows:
 The Tweet sentence is broken down into a list (each item on the list is a word).
 We then iterate thru each item on the list,
    we check if the current word is a HASHTAG (#) word
      if it is then we check if the hashtag word its on out natural disaster List.
          if it is then we updated the count of natural disasters.
      if the word IS not a HASHTAG word then we check if that word is trigger word (trigger words are words than be used to identify or rule out the tweet possibly being a natural disaster tweet).
          If the word is a trigger word, then we update the count of the trigger words.

  At the end, once all the words in the tweet have been checked,
  we only write to file the sentences that have atleast the following:
    2 trigger words
    1 hashtag word that is natural disaster


  trigger words:
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


  hashtag natural disasters words:
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
