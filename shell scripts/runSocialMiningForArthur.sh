 #!/bin/bash
bin/hadoop jar socialmining.jar SocialMining -libjars json-20080701.jar -Dmapred.reduce.tasks=6 /Arthur /out_arthur
echo "Social Mining program ran for Arthur"
