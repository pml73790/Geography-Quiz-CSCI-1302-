This file explaining how to compile and execute my code for project5.

1. Verify the Path & JAVA_HOME environment is properly set up before using Maven by:
   + Make sure the account log in with: ssh -Y user@odin.cs.uga.edu
   + cd /home/myid/pml73790
   + mvn -version (to make sure we are using Apache Maven 3.8.6)
   + source .bash_profile (to double check before moving to step 2. Correct any errors before login into odin)

2. Change directory to project 5 by using:
   + cd /home/myid/pml73790/cs1302/project5

3. Compile the project with Maven by using: mvn compile

4. Run Quiz application on odin: mvn javafx:run

5. Once the project is compiled, the quiz results will be save in quizzes.dat, locate in current working directory (cd ~/project5) 

6. Clean the project with Maven by using: mvn clean
   
