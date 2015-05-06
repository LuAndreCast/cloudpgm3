#!/bin/bash
bin/hadoop jar socialmining.jar SocialMining -libjars json-20080701.jar -Dmapred.reduce.tasks=6 /KingFire /out_king
echo "Social Mining program ran for KingFire"
