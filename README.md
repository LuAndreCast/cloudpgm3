# cloudpgm3
program 3 for cloud programming

Using hadoop mapreduce to process large twitter data to determine the tweets that are natural disasters



tightvnc installation:
    https://www.youtube.com/watch?v=x9xTyh63Tos

connect to a slave VM from Master VM
    ssh slave1

connect to from slave VM to Master VM
    ssh master

See all files in hadoop (from Master VM)
    cd hadoop-1.2.1
    bin/hadoop dfs -ls /

See data from a specific folder/file (from Master VM) - outputted as a json object
    bin/hadoop dfs -cat /Arthur | less

see the data (on slaves VM)
    [change directory to data folder]    cd ~/data/hadoop/dfs/data/current/
    [list data] ls -l | less

    [temp storage]
    cd ~/data/hadoop_local/ttprivate/taskTracker/visa/jobcache/job*

see commands from dfs:
    bin/hadoop dfs help
    [delete] bin/hadoop dfs -rmr /Arthur_grep_output
    [list all files] bin/hadoop dfs -ls /
    DO NOT DELETE THE ARTHUR AND KINGFIRE FOLDER

location of hadoop examples
    /home/visa/hadoop-1.2.1./src/examples/org/apache/hadoop/examples
    //check data - in one of the 3 slaves
    directory: /data/hadoop_local/userlogs/LASTJOB/ATTEMPT/
    command:  less syslog

To check MapReduce status:
    http://localhost:50030

To check HDFS status: 
    http://localhost:50070

To manage jobs: 
    bin/hadoop job

To manage HDFS: 
    bin/hadoop dfs

To run your job with multiple reduce tasks (recommended): 
    bin/hadoop jar socialmining.jar WordCount -Dmapred.reduce.tasks=6 /arthur /arthur_output


To use the logger in your program:

    //import at the beginning of your program

    import org.apache.commons.logging.Log;

    import org.apache.commons.logging.LogFactory;

    //declare in your class

    private static final Log log = LogFactory.getLog(WordCount.class);

    //call log.info or log.debug where you want to log

    log.info(value.toString());

    //if you log inside of a map or reduce task, go to 
    /data/hadoop_local/userlogs/jobxxx
    to check the log

To use the json parser class:

    Google json-2008071.jar and download it (json jar 20080701.jar)

    //import the jason parser at the beginning of your program

    import org.json.JSONObject;

    import org.json.JSONException;

TUTORIALS
(hadoop and java)    https://www.youtube.com/watch?v=PAaIAdtx080


Compiling and running instructions
    Step 0 START all namenodes
        cd hadoop-1.2.1 
        sh start-all.sh

    Step 1 prepare program (compile and put into a jar file)
        compile a java program
        javac -classpath “../*:../lib/*” SocialMining.java

        [location of libraries] ~/hadoop-1.2.1/lib

        pack program into a jar 
        (NEED TO BE OUT OF DIRECTORY FOLDER THAT IS GOING TO BE PACKED)
        jar -cvf socialmining.jar -C socialmining/ .
        
    Step 2 run program
        bin/hadoop jar socialmining.jar SocialMining /KingFire /KingFireout 

        [hadoop examples located 
        inside the jar file hadoop-examples-1.2.1.jar 
        or /hadoop-1.2.1/src/examples/org/apache/hadoop/examples/  
        ]
        bin/hadoop jar hadoop-examples-1.2.1.jar grep /Arthur /arthurOut12 rain

        [running without json]
        bin/hadoop jar wordcount.jar WordCount -Dmapred.reduce.tasks=6 /Kingfire /Kingfire_out1

        [running with json]
        bin/hadoop jar socialmining.jar SocialMining -libsjar json-20080701.jar -Dmapred.reduce.tasks=6 /Kingfire /Kingfire_out1

    Step 3 see data run from web browser (FROM MASTER VM ONLY)
        open web browser
        localhost:50030/jobtracker.jsp

    Step 4 STOP all namenodes
        cd hadoop-1.2.1 
        sh stop-all.sh



   
